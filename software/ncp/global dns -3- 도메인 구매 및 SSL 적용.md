# global dns -3- 도메인 구매 및 SSL 적용

업무수행을 위해 ncp에 서버를 배포하여 도메인을 지정하고, ssl을 적용해야할 일이 생겼습니다. 단순 블로깅으로도 충분히 진행이 가능하지만, 이렇게 업무를 진행하면 금방 잊어먹을 수 있으므로 글로 정리하여 되새김질을 해볼까 합니다.

## 도메인

사전적 의미로 영역이지만, 사실 이 도메인이라는 단어는 종사하는 직종에 따라 매우 달라집니다. 해당 도메인에 대한 내용을 다루는 글은 아니므로 자세한 설명은 생략하겠습니다. 여기서 도메인은 '서비스 제공자의 영역'입니다. 서비스의 구조에 따라 이 영역을 이루는 서버는 단 하나일수도 있고, 여러개일수도 있습니다. 일반적으로 사용자는 DNS라는 서비스를 통해 지정된 웹 url을 통해 접근합니다.

## SSL(Secure Sockets Layer)

pki 기반의 보안 소켓 계층으로 인증서를 발급받아 브라우저와 서버 사이의 암호화된 연결을 수립하는데 사용됩니다. 이 암호화된 연결은 터널링이라고도 부릅니다. 이러한 SSL 인증서는 이론적으로는 MITM(Man In The Middle, 중간자 공격)에 취약하다고 합니다. 하지만, 이러한 취약점은 SSL 인증서가 최신화되면서 거의 보완이된 상태이고, 설령 탈취당한다 하더라도 SSL 인증서 발급기관에서 충분히 보상하고 as를 수행해줍니다. 따라서, 이러한 취약점은 SSL 인증서를 너무 옛날 걸로 사용하지 않는이상 걱정하지 않아도 되는 부분입니다.

(사실 이 부분은 웹 서비스 개발자들이 관여할 수 있는 부분이 아닙니다.)

## Let's Encrypt

SSL/TLS를 무료로, 그리고 사용자 친화적으로 활성화할 수 있도록 '디지털 인증서'를 제공하는 CA입니다. 무료지만, 인증서의 유효기간은 90일로, 3개월에 한 번은 갱신이 필요합니다.

## 1. 개인 도메인 구입하기

NCP Global DNS를 사용하려면, 개인 도메인이 필요합니다. 개인 도메인은 호스팅 사이트에서 쉽게 구입할 수 있습니다. 저의 경우는 '가비아(https://www.gabia.com/)'에서 구매했습니다. 

구매한 도메인은 개인 대시보드를 통해 확인할 수 있습니다.

<img width="915" alt="스크린샷 2023-12-05 오전 10 44 56" src="https://github.com/wanniDev/TIL_collection/assets/81374655/e126036e-8c66-426f-99d4-b2d19fb970c7">

## 2. NCP Global DNS에 구매한 도메인 등록

`NCP 콘솔 > Networking > Global DNS`

![image](https://github.com/wanniDev/TIL_collection/assets/81374655/9dc45cc5-4293-40ad-8fe5-bc314ec80a2d)

`+ 도메인 추가`를 통해 구매한 도메인을 입력합니다.

<img width="498" alt="스크린샷 2023-12-05 오전 11 04 41" src="https://github.com/wanniDev/TIL_collection/assets/81374655/ce5d9d01-831b-4bc7-8217-d0867e214816">

등록한 도메인이 정상적으로 완료되면 Global DNS 서비스에서 제공하는 네임 서버를 확인할 수 있습니다.

ns1-1.ns-ncloud.com. 
ns1-2.ns-ncloud.com.

가비아의 경우 자체적으로 DNS 서버를 운영하고 있습니다. 따라서 구매한 도메인은 자동으로 가비아의 도메인 서버로 등록됩니다. NCP Global DNS에 도메인 등록이 완료되었으면, 가비아의 도메인 통합 관리툴에서 네임서버 변경을 진행하면 됩니다.

<img width="1498" alt="스크린샷 2023-12-05 오전 11 26 04" src="https://github.com/wanniDev/TIL_collection/assets/81374655/707bffe5-747f-49d2-ac34-2f5da5942fec">

참고로 네임 서버 변경까지는 최대 48시간이 소요될 수 있습니다.

## 3. 서버에 도메인 호스트 연결 및 확인

네임 서버 변경이 완료되었다면, NCP Global DNS 서비스에서 레코드 추가를 통해 서버에 호스트 정보를 매핑할 수 있습니다.

<img width="1203" alt="스크린샷 2023-12-05 오전 11 39 54" src="https://github.com/wanniDev/TIL_collection/assets/81374655/34c27ce5-8a64-4c64-ba55-6e2cc4fe88c0">

ncp에서 서버를 개설하고 공인 아이피를 할당받을 수 있습니다. 해당 공인 아이피를 통해 Global DNS 서비스의 레코드 추가를 통해 서버와 호스트를 연결합니다.

<img width="698" alt="스크린샷 2023-12-05 오전 11 43 30" src="https://github.com/wanniDev/TIL_collection/assets/81374655/a422c1b1-14f3-42e2-89cd-d884dab0c329">

***추가를 했으면 꼭 `설정 적용`을 통해 잘 반영될 수 있도록 합니다.***

레코드의 정상등록 여부는 nslookup 명령어를 통해 확인해볼 수 있습니다.

```shell
> nslookup api.wannidev.com
Server:		208.91.112.53
Address:	208.91.112.53#53

Non-authoritative answer:
Name:	api.wannidev.com
Address: 223.130.137.248
```

## 4. SSL 발급

먼저 ncp 운영 서버에 ssh 접속을 합니다.

```shell
$ ssh root@{IP주소}
비밀번호 입력
```

다음으로 ssl 인증서를 발급받습니다. ssl 인증서를 받으려면 cerbot 을 설치해야 합니다.

```shell
$ sudo apt update
$ sudo snap install core; sudo snap refresh core;
$ sudo apt-get remove certbot
$ sudo snap install --classic certbot
```

다음으로 명령어를 입력하여 SSL 인증서를 발급 받습니다.

cerbot은 기본적으로 ECDSA로 인증서를 발급합니다. 그러나 NCP는 ECDSA를 인식하지 못합니다. 따라서 RSA로 명시해줘야 합니다.

```shell
$ sudo certbot certonly --manual -d {도메인.com} -d *.{도메인.com} --preferred-challenges dns --key-type rsa
```

위 명령어를 입력하고 다음에 나오는 레코드를 NCP Global DNS에 입력해줍니다.

```shell
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Please deploy a DNS TXT record under the name:

api.wannidev.com.

with the following value:

${여기에 나오는 레코드를 입력}
```

<img width="705" alt="스크린샷 2023-12-05 오후 12 39 00" src="https://github.com/wanniDev/TIL_collection/assets/81374655/b4d5871a-4067-4755-b703-19413680e93b">

레코드 입력 및 설정 적용이 완료되면, 터미널로 돌아가 엔터를 칩니다. 성공하면 아래와 같은 문구가 생깁니다.

```shell
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Press Enter to Continue

Successfully received certificate.
......
```

이제 아래 명령어를 통해 인증서에 대한 정보를 찾습니다.

```shell
sudo certbot certificates

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Found the following certs:
  Certificate Name: -
    Serial Number: -
    Key Type: RSA
    Domains: -
    Expiry Date: 2023-07-15 07:01:05+00:00 (VALID: 89 days)
    Certificate Path: path/fullchain.pem
    Private Key Path: Path/privkey.pem
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
```

여기까지 왔으면 이제 SSL 인증서 발급은 완료되었습니다.

## 5. Spring Boot 서버에 https 적용하기

발급받은 SSL 인증서를 적용하는 방법은 다양합니다. 저의 경우는 가장 익숙한 Spring Boot 웹 서버에 https를 적용하는 방법을 채택하였습니다.

만약 테스트용 프로젝트가 없으면, https://start.spring.io/를 통해, 간단하게 프로젝트를 하나 구성해줍니다. ssl 적용에 사용되는 인증서 파일은 fullchain.pem, privkey.pem 입니다. 하지만, 스프링 부트에서는 pem을 인식하지 못하게 때문에 두 파일을 PKCS12 형식으로 변경해야합니다.

```shell
$ sudo openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name ttp -CAfile chain.pem -caname root
```

위 명령어를 입력하면 `Enter Export Password`가 나오는데 여기서 입력한 비밀번호는 스프링 부트에 적용할 때 필요하므로 잊으시면 안됩니다.

비밀번호 입력까지 끝마쳤으면, keystore.p12 파일이 생성되는데 이 파일을 스프링부트 프로젝트 `resources`에 옮깁니다.

![스크린샷 2023-12-05 오후 1 01 26](https://github.com/wanniDev/TIL_collection/assets/81374655/0e7490d0-0ee9-4e9a-9a3f-f94300866a9c)

그리고 `application.yml`에  ssl 구성 정보를 입력합니다.

```yaml
server:
  ssl:
    key-store: classpath:keystore.p12
    key-store-type: PKCS12
    key-store-password: 안알려줌
  port: 443
```

이제 ide로 실행하면 `https://localhost:443` 으로 실행이 가능하고, jar로 ncp 서버에 배포하면 https가 적용된 도메인 주소로 서비스를 이용할 수 있게됩니다.

<img width="358" alt="스크린샷 2023-12-05 오후 1 09 21" src="https://github.com/wanniDev/TIL_collection/assets/81374655/9f59faa8-77e0-4205-b7ce-27f6da8257b0">

여기까지 입니다. 미숙한 설명을 애써 참으며 끝까지 따라오시느라 수고 많으셨습니다.



## 참고

- https://enginnersnack.tistory.com/5
- https://velog.io/@leesomyoung/letsencrypt%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-HTTPS-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
- https://velog.io/@ghj616/NCP-lets-encrypt-SSL-%EC%9D%B8%EC%A6%9D%EC%84%9C-%EB%93%B1%EB%A1%9D-%EC%9D%B8%EC%A6%9D%EC%84%9C%EA%B0%80-%EC%9C%A0%ED%9A%A8%ED%95%98%EC%A7%80-%EC%95%8A%EC%8A%B5%EB%8B%88%EB%8B%A4
- https://jiwontip.tistory.com/83
