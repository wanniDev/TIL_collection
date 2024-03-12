# 다중 컨테이너 애플리케이션

이제 기존 앱 컨테이너에서 MySQL을 추가 해보겠습니다. 일반적으로 컨테이너는 한 가지 일을 수행해야 합니다. 그런데 보통 애플리케이션을 운영하는데 MySQL과 같은 DB 컨테이너를 별도로 실행하는 이유는 다음과 같습니다.

- 데이터베이스와 다르게 API 및 프론트엔드를 확장해야 할 가능성이 높습니다.
- 별도의 컨테이너를 사용하면 격리된 상태에서 버전을 관리하고 업데이트할 수 있습니다.
- 로컬에서 데이터베이스용 컨테이너를 사용할 수 있지만 프로덕션 환경에서는 다른 관리 서비스를 사용할 수 있습니다.
- 여러 프로세스를 실행하려면 프로세스 관리자가 필요하므로(컨테이너는 하나의 프로세스만 시작함) 컨테이너 시작/종료시 복잡성이 추가됩니다.

이러한 이유로 아래와 같이 여러 컨테이너에서 앱을 실행하는 것이 가장 좋습니다.

![image](https://github.com/wanniDev/bullsAndCows/assets/81374655/79f16ca6-6c25-4a14-8290-831d653027da)

## 컨테이너 네트워킹

컨테이너는 기본적으로 격리되어 실행되며 동일한 시스템의 다른 프로세스나 컨테이너에 대해 전혀 모릅니다. 한 컨테이너가 다른 컨테이너와 통신하도록 하려면 네트워킹을 적용해야 합니다. 서로 통신할 두 컨테이너를 동일한 네트워크에 배치하면 서로 통신이 가능해집니다.

## MySQL 시작

컨테이너를 네트워크에 배치하는 방법은 2가지 입니다.

1. 컨테이너를 시작할 때 네트워크를 할당합니다.
2. 이미 실행 중인 컨테이너를 네트워크에 연결합니다.

우선 네트워크를 생성하고 MySQL 컨테이너를 띄워서 연결해보겠습니다.

1. 네트워크를 만듭니다.

   ```shell
   $ docker network create todo-app
   ```

2. MySQL 컨테이너를 시작하고 네트워크에 연결합니다. 추가로 해당 데이터베이스를 초기화하는 데 사용할 몇 가지 환경 변수를 정의할 것입니다. MySQL 환경 변수에 대한 내용은 [MySQL Docker 허브 목록](https://hub.docker.com/_/mysql/) 에서 '환경 변수' 섹션을 참고해보세요.

   ```shell
   $ docker run -d \
        --network todo-app --network-alias mysql \
        -v todo-mysql-data:/var/lib/mysql \
        -e MYSQL_ROOT_PASSWORD=secret \
        -e MYSQL_DATABASE=todos \
        mysql:8.0
   ```

    `--network-alias`라는 명령어를 주목해주세요. 이 부분은 추후에 좀 더 자세히

3. 데이터베이스가 실행 중인지 확인하고 싶다면, 아래 명령어를 통해 직접 데이터베이스 컨테이너에 접근하고 쿼리를 실행해보세요.

   ```shell
   $ docker exec -it $(docker container ls | grep mysql:8.0 | awk '{print $1}') mysql -u root -p
   ```


4. 확인했다면, MySQL shell을 종료하여 컴퓨터의 shell로 돌아갑니다.

```mysql
mysql> exit
```

## MySQL에 연결하기

MySQL이 실행중임을 확인했다면, 애플리케이션과 연결할 준비가 되었다는 뜻입니다. 그런데, 사용은 어떻게 할까요? 동일한 네트워크에서 다른 컨테이너를 실행하는 경우 컨테이너를 어떻게 찾을까요? 각 컨테이너는 고유한 ip 주소가 있기 때문에 이를 활용하면 도비니다. 

이번 실습에서는 컨테이너 네트워킹을 제대로 활용할 수 있는 [nicolaka/netshoot](https://github.com/nicolaka/netshoot) 컨테이너를 사용해볼 예정입니다. 진행 방식은 다음과 같습니다.

1. `nicolaka/netshoot` 이미지를 사용하여 새 컨테이너를 시작합니다. 동일한 네트워크에 연결해야 합니다.

   ```shell
   $ docker run -it --network todo-app nicolaka/netshoot
   ```

2. 컨테이너 내부에서 `dig`을 통해 DNS를 활용해볼 수 있습니다. 아래 명령어를 통해, `mysql`에 해당하는 호스트 ip 주소를 조회할 수 있습니다.

   ```shell
   $ dig mysql

3. 만약 명령어가 제대로 수행되었다면 아래와 같은 로그가 출력됩니다.

   ```shell
   ; <<>> DiG 9.18.13 <<>> mysql
   ;; global options: +cmd
   ;; Got answer:
   ;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 10724
   ;; flags: qr rd ra; QUERY: 1, ANSWER: 1, AUTHORITY: 0, ADDITIONAL: 0
   
   ;; QUESTION SECTION:
   ;mysql.                         IN      A
   
   ;; ANSWER SECTION:
   mysql.                  600     IN      A       172.19.0.2
   
   ;; Query time: 3 msec
   ;; SERVER: 127.0.0.11#53(127.0.0.11) (UDP)
   ;; WHEN: Mon Jun 05 02:37:54 UTC 2023
   ;; MSG SIZE  rcvd: 44
   ```

"ANSWER SECTION"에서 로 확인되는 `A`레코드를 확인해보면, 찾고자 하는 컨테이너의 ip 주소를 조회해볼 수 있습니다.

이전에 MySQL 컨테이너를 띄울때 전달한 환경 변수 중 하나인 `--network-alias`를 미리 선언하였기 때문에 우리는 이 dns ip 주소를 몰라도, 특정 앱이 `mysql` 로 지정된 호스트에 연결하기만 하면 `mySQL` 데이터베이스와 통신한다는 것을 알 수 있습니다.

## MySQL로 애플리케이션 실행하기

todo 애플리케이션은 MySQL 연결을 하기 위한 몇 가지 환경 변수 설정을 지원합니다.

- **`MYSQL_HOST` :** 실행 중인 MySQL 서버의 호스트 이름
- **`MYSQL_USER` :** 연결에 사용할 사용자 이름
- **`MYSQL_PASSWORD` :** 연결에 사용할 암호
- **`MYSQL_DB` :** 연결되면 사용할 데이터베이스

> env 환경변수를 통해 연결 설정을 지정하는 것은 개발용으로는 허용되지만 프로덕션 환경에서는 별로 권장되지 않습니다. 도커의 보안 책임자였던, Diogo Monica는  https://blog.diogomonica.com//2017/03/27/why-you-shouldnt-use-env-variables-for-secret-data/를 통해 그 이유와 대응방안에 대해 잘 정리 해놓았습니다.
>
> 간단히 정리해보면, 컨테이너 오케스트레이션 프레임워크에서 제공하는 secret support를 활용하는 것입니다. 이 secret는 동작하는 컨테이너에서 파일 형태로 마운트 됩니다. 개발을 하다보면 종종 `_FILE` 이라는 접미사가 포함된 환경 변수들을 보게 될텐데, 이러한 형태의 환경변수가 바로 secret 값을 지정하는 것입니다.
>
> 예를 들어, `MYSQL_PASSWORD_FILE`를 변수로 지정하면, 앱이 참조 파일의 내용을 connection 연결 암호로 사용합니다. Docker는 이러한 환경 변수를 지원하기 위해 아무 작업도 수행하지 않습니다. 앱은 변수를 찾고 파일 내용을 가져올 수 있기만 하면 됩니다.



1. 위에서 이야기한 각 환경 변수를 지정하고 컨테이너를 앱 네트워크에 연결합니다. 터미널이  `getting-started/app`  디렉토리에 지정되어 있는지 확인해봐야 합니다.

   ```shell
   docker run -dp 3000:3000 \
      -w /app -v "$(pwd):/app" \
      --network todo-app \
      -e MYSQL_HOST=mysql \
      -e MYSQL_USER=root \
      -e MYSQL_PASSWORD=secret \
      -e MYSQL_DB=todos \
      node:18-alpine \
      sh -c "yarn install && yarn run dev"
   ```

2. 컨테이너에 대한 로그를 살펴보고 mySQL과 제대로 연결이 되어있는지를 살펴봐야 합니다.

   ```shell
   $ docker logs -f $(docker container ls | grep node:18-alpine | awk '{print $1}')
   
   yarn install v1.22.19
   [1/4] Resolving packages...
   success Already up-to-date.
   Done in 0.24s.
   yarn run v1.22.19
   $ nodemon src/index.js
   [nodemon] 2.0.20
   [nodemon] to restart at any time, enter `rs`
   [nodemon] watching path(s): *.*
   [nodemon] watching extensions: js,mjs,json
   [nodemon] starting `node src/index.js`
   Waiting for mysql:3306.
   Connected!
   Connected to mysql db at host mysql
   Listening on port 3000
   ```

3. 브라우저 앱을 열고 항목을 몇 개 추가해보세요.

4. mysql 데이터베이스에 연결하고 항목이 데이터베이스에 기록되고 있는지 확인해보세요.

   ```shell
   $ docker exec -it $(docker container ls | grep mysql:8.0 | awk '{print $1}') mysql -p todos
   
   mysql> select * from todo_items;
   +--------------------------------------+--------+-----------+
   | id                                   | name   | completed |
   +--------------------------------------+--------+-----------+
   | df030d73-a6ae-4119-a534-e003f40e3cf6 | hello1 |         0 |
   | d125a1d1-2e24-4ea7-9aef-d2ebe97dffa0 | hello2 |         0 |
   | fd451dce-ad31-4b88-af69-c4494dcbc1c5 | hello3 |         0 |
   +--------------------------------------+--------+-----------+
   ```
