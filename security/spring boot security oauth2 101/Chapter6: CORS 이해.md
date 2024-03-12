# Cors 이해

> **CORS**(Cross-Origin Resource Sharing, 교차 출처 리소스 공유)

- HTTP 헤더를 사용하여, 한 출처에서 실행 중인 웹 애플리케이션이 다른 출처의 선택한 자원에 접근할 수 있는 권한을 부여하도록 브라우저에 알려주는 체제
- 웹 애플리케이션 리소스가 자신의 출처와 다를 때 브라우저는 요청 헤더에 Origin 필드에 요청 출처를 함께 담아 교차 출처 HTTP 요청을 실행한다.
- 출처를 비교하는 로직은 서버에 구현된 스펙이 아닌 브라우저에 구현된 스펙 기준으로 처리되며 브라우저는 클라이언트의 요청 헤더와 서버의 응답헤더를 비교해서 최종 응답을 결정한다.
- 두개의 출처를 비교하는 방법은 URL의 구성요소 중 **Protocol, Host, Port** 이 세가지가 동일한지 확인하면 되고 나머지는 틀려도 상관없다.

![image](https://github.com/wanniDev/TIL_collection/assets/81374655/c00bd797-515e-458d-9343-ed140d076d4a)

- https://domain-a.com의 프론트 엔드 JavaScript 코드가 XMLHttpRequest를 사용하여 https://domain-b.com/data.json을 요청하는 경우 보안상의 이유로 브라우저는 스크립트에서 교차 출처 HTTP 요청을 제한한다. 
- XMLHttpRequest와 Fetch API는 동일 출처 정책을 따르기 때문에 이 API를 사용하는 웹 애플리케이션은 자신의 출처와 동일한 리소스만 불러올 수 있으며, 다른 출처의 리소스를 불러오려면 그 출처에서 올바른 CORS 헤더를 포함한 응답을 반환해야 한다.

## Simple Request

- Simple Request는 예비 요청(Preflight) 과정 없이 바로 서버에 본 요청을 한 후, 서버가 응답의 헤더에 Access-Control-Allow-Origin 과 같은 값을 전송하면 브라우저가 서로 비교 후 CORS 정책 위반여부를 검사하는 방식이다.
- **제약 사항**
  - GET, POST, HEAD 중의 한가지 Method를 사용해야 한다.
  - 헤더는 Accept, Accept-Language, Content-Language, Content-Type, DPR, Downlink, Save-Data, Viewport-Width Width 만 가능하고 Custom Header는 허용되지 않는다.
  - Content-type은 application/x-www-urlencoded, multipart/form-data, text/plain 만 가능하다.

## Preflight Request

- 브라우저는 요청을 한번에 보내지 않고, 예비요청과 본요청으로 나누어 서버에 전달하는데 브라우저가 예비요청을 보내는 것을 Preflight 라고 하며 이 예비 요청의 메소드에는 OPTIONS가 사용된다.
- 예비요청의 역할은 본 요청을 보내기 전에 브라우저 스스로 안전한 요청인지 확인하는 것으로 요청 사양이 Simple Request 에 해당하지 않을 경우 브라우저가 Preflight Request를 실행한다.
- 브라우저가 보낸 요청을 보면 Origin에 대한 정보 뿐만 아니라 예비 요청 이후에 전송할 본 요청에 대한 다른 정보들도 함께 포함되어 있는 것을 볼 수 있다.
- 이 예비 요청에서 브라우저는 Access-Control-Request-Headers 를 사용하여 자신이 본 요청에서 Content-Type 헤더를 사용할 것을 알려주거나, Access-Control-Request-Method를 사용하여 GET 메소드를 사용할 것을 서버에 미리 알려주고 있다.
- 서버가 보내준 응답 헤더에 포함된 Access-Control-Allow-Origin: https://security.io의 의미는 해당 URL 외의 다른 출처로 요청할 경우 CORS 정책을 위반했다고 판단하고 오류 메시지를 내고 응답을 버리게 된다.

### 동일 출처 기준

| URL                                     | 동일 출처 | 근거                                                      |
| --------------------------------------- | --------- | --------------------------------------------------------- |
| https://security.io/auth?username=user1 | O         | 스킴, 호스트, 포트가 동일                                 |
| https://user:password@security.io       | O         | 스킴, 호스트, 포트가 동일                                 |
| http://api.security.io                  | X         | 스킴이 다름                                               |
| https://api.security.io                 | X         | 호스트가 다름                                             |
| https://security.io:8000                | ?         | 브라우저의 구현에 따라 다름, explorer는 포트자체를 무시함 |

### CORS 해결: 서버에서 Access-Control-Allow-* 세팅

- **Access-Control-Allow-Origin **- 헤더에 작성된 출처만 브라우저가 리소스를 접근할 수 있도록 허용한다.
  - `*`, https://security.io
- **Access-Control-Allow-Methods** - preflight request 에 대한 응답으로 실제 요청 중에 사용할 수 있는 헤더 필드 이름을 나타낸다.
  - 기본값은 GET, POST, HEAD, OPTIONS, `*`
- **Access-Control-Allow-Credentials **- 실제 요청에 쿠키나 인증 등의 사용자 자격 증명이 포함될 수 있음을 나타낸다. Client의 `credentials:include` 일경우 true 필수
- **Access-Control-Max-Age **- preflight 요청 결과를 캐시 할 수 있는 시간을 나타내는 것으로 해당 시간동안은 preflight 요청을 다시 하지 않게 된다.

## CorsConfigurer

- Spring Security 필터 체인에 CorsFilter를 추가합니다.
- corsFilter 라는 이름의 Bean이 제공되면 해당 CorsFilter가 사용됩니다.
- corsFilter 라는 이름의 Bean이 없고 CorsConfigurationSource 빈이 정의된 경우 해당 CorsConfiguration이 사용된다.
- CorsConfigurationScource 빈이 정의되어 있지 않은 경우 Spring MVC가 클래스 경로에 있으면 HandlerMappingIntrospector가 사용된다.

## CorsFilter

- CORS 예비 요청을 처리하고 CORS 단순 및 본 요청을 가로채고, 제공된 CorsConfigurationSource를 통해 일치된 정책에 따라 CORS 응답 헤더와 같은 응답을 업데이트하기 위한 필터이다.
- Spring MVC Java 구성과 Spring MVC XML 네임스페이스에서 CORS를 구성하는 대안이라 볼 수 있다.
- 스프링 웹에 의존하는 응용 프로그램이나 `java.servlet`에서 CORS 검사를 수행해야 하는 보안 제약 조건에 유용한 필터이다.