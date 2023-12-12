# NCP 배포관리 task 수행 용 계정 만들기

ncp에서 제공하는 기본적으로 관리자 암호를 입력하여 root 계정을 사용합니다. aws의 경우, key 파일을 통해 사용자 계정으로 접속하고 root 접속을 막습니다.

ncp 역시 이러한 방식이 가능하다고 합니다. 다만, 키를 분실하면 조금 작업이 번거로워질 수 있기에 관리를 잘하셔야합니다. 이번 예제에서는 root 접속을 막는 과정은 생략하고 ci/cd에 활용할 배포관리용 계정을 따로 만들어볼까합니다.

ncp 서버를 만들어서 root 계정으로 접속하는 방법은 알고 있다는 가정하에 진행하겠습니다. 혹시 이 방법을 모르시는 분은 공식 문서(https://guide.ncloud-docs.com/docs/ko/server-overview)를 차근차근 읽어보시는 걸 추천드립니다.

### 1. 사용자 계정 만들기

먼저 root 계정으로 ncp 서버에 ssh 접속을 합니다.

1. **task 계정 만들기**

```shell
$ ssh -i path/to/key.pem root@{ip}
# 관리자 암호 입력

useradd -c "Runner for NCLOUD Default account" -d /home/nrunner -s /bin/bash nrunner
```

2. **그룹 추가하기**

```shell
$ id nrunner
uid=1001(nrunner) gid=1001(nrunner) groups=1001(nrunner) # 사용자 계정은 1000 부터 1001로 만들어짐

# 우분투의 경우, wheel 그룹이 없으므로 직접 생성하셔야 합니다.
$ sudo groupadd wheel
$ gpasswd -a nrunner wheel
Adding user nrunner to group wheel
$ gpasswd -a nrunner systemd-journal
Adding user nrunner to group systemd-journal
```

3. **패스워드 없이 sudo 사용가능하게 하기**

```shell
$ cat << EOF > /etc/sudoers.d/10-nrunner-users
> nrunner ALL=(ALL) NOPASSWD:ALL
> EOF
$ chmod 440 /etc/sudoers.d/10-nrunner-users
```

### 2. Key 파일 생성하기

1. **계정 디렉토리로 이동**

```shell
$ cd /home/nrunner
```

2. **key 생성**

```shell
$ openssl genrsa -out runner.pem 4096
$ chmod 600 runner.pem
$ chown nrunner:nrunner runner.pem
```

3. **ncloud 계정으로 로그인할 수 있도록 authorized_keys 생성**

```shell
$ mkdir .ssh
$ ssh-keygen -y -f runner.pem > .ssh/authorized_keys
$ chmod 700 .ssh
$ chmod 600 .ssh/authorized_keys
$ chown -R nrunner:nrunner .ssh
```

### 3. sshd_config 수정

1. PasswordAuthentication no	로 변경
2. PubkeyAuthentication yes

## 주요 이슈

1. permission denied(publickey) -> 개인키와 짝이 안맞는 공개키가 있기도 하지만.. 접속하려는 호스트의 주소는 맞아도 사용자 계정에 오타가 발생한다면 가끔 저런 오류가 발생한다.