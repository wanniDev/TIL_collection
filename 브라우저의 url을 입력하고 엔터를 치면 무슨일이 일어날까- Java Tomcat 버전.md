# 브라우저의 url을 입력하고 엔터를 치면 무슨일이 일어날까: Java Tomcat 버전

만약 사용자가 브라우저에 "localhost:8080/hello-servlet"을 치고 엔터를 치면 Java, Tomcat에서 무슨일이 일어날지에 대해 설명해보겠습니다.

먼저 해당 uri 패턴과 매핑되는 서블릿부터 살펴보겠습니다.

```java
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
```

매우 전형적이고 간단한 서블릿 코드입니다. uri 패턴이  '/hello-servlet'이라면 이 서블릿을 매핑하여 doGet(혹은 doService)를 실행하여 여러가지 비즈니스 로직을 수행하게 됩니다. 위 예제의 경우, 응답타입을 `text/html`로 지정하고 응답페이로드에 인위적으로 html 스크립트를 작성하여 리턴하는 코드입니다.

참고로, 자바 엔터프라이즈 공식 라이브러리가 `javax`에서 `jakarta`로 전환한지는 얼마 되지 않았습니다. 때문에 만약 IDE를 활용하여 java 웹 애플리케이션 프로젝트를 생성할 경우, 해당 모듈이 지원하는 자바 엔터프라이즈 버전과 탐캣에서 지원하는 자바 엔터프라이즈 버전과 꼭 비교해보아야 합니다.

그리고 웰컴 페이지 역할을 할 `index.jsp`도 살펴 보겠습니다.

```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>
```

이 jsp는 탐캣이 실행되면 기본적으로 보여주는 웰컴 페이지로 위 링크를 클릭하면 앞서 작성한 서블릿의 내용을 보여주게 됩니다.

이제 탐캣이 서블릿을 매핑하고 요청을 처리하여 응답을 리턴하는 과정을 설명하도록 하겠습니다.

## Tomcat의 동작과정

### 0. Tomcat 실행

요청을 처리하려면 요청을 처리할 서버를 구동시켜야 합니다. 탐캣의 경우, 해당 오픈소스 디렉토리 `bin`안의 `startup.sh`를 통해 서버를 구동시킵니다. `startup.sh`는 탐캣을 실행하기위해 필요한 일련의 과정들을 대신 수행해주는 스크립트 파일입니다. 해당 스크립트에서 수행하는 작업은 간단하게 나열해보면

1. pid 체크
2. 로그 기록 설정 적용 및 로그를 기록할 파일 존재여부 확인 및 생성
3. 탐캣 실행 명령어 수행

탐캣 실행시 필요한 다양한 설정 구성도 있지만, 이 부분은 실행 여부만 확인하는거라면 자세히 몰라도 크게 문제는 없을 것 같습니다. 이렇게 탐캣 자체에서 제공해주는 스크립트가 있기 때문에 개발자는 아래와 같은 한줄의 명령어로 탐캣 서버를 가동시키고 또 종료할 수 있습니다.

```shell
$ ${TOMCAT_DIR}/bin/startup.sh # 탐캣 실행
$ ${TOMCAT_DIR}/bin/shutdown.sh # 탐캣 종료
```

탐캣 구성에 필요한 설정은 `conf/server.xml`에서 관리할 수 있습니다.

### 1. HTTP 요청 생성

사용자의 웹 브라우저는 HTTP GET 요청을 생성하고, 이 요청을 localhost의 8080 포트로 전송합니다.

### 2. Tomcat Connector 동작

Tomcat의 Connector 컴포넌트가 8080 포트에서 이 요청을 수신합니다. 자바 `Socket.accept()`와 비슷한 역할을 합니다.

### 3. 스레드 할당

Tomcat은 내부에 스레드 풀을 관리합니다. connector에서 요청을 수신하면 스레드풀의 스레드를 하나 할당하여 요청을 처리합니다. java의 ExecutorService에서 제공하는 스레드풀과 거의 동일한 역할을 수행합니다.

```java
Runnable worker = threadPool.allocateWorker();
```

### 4. HTTP 요청 파싱

할당받은 스레드는 HTTP 요청을 파싱하여 HTTP 메서드, URI, 헤더, 본문 등을 분석합니다.

```java
HttpRequest request = new HttpRequest(inputStream);
```

### 5. 서블릿 매핑

`web.xml` 파일이나 애노테이션을 통해 URI 패턴(``/hello-servlet`)과 대응하는 서블릿(`HelloServlet`)을 찾습니다. 서블릿을 매핑하는 방법은 2가지가 있습니다. 하나는 `프로젝트_경로/src/main/webapp/WEB-INF/web.xml`에 서블릿 매핑 설정을 작성하는 겁니다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">
    <servlet>
        <servlet-name>helloServlet</servlet-name>
        <servlet-class>me.hello.servlet.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>helloServlet</servlet-name>
        <url-pattern>/hello-servlet</url-pattern>
    </servlet-mapping>
</web-app>
```

또 하나는 `@WebServlet` 애노테이션을 활용하는 것입니다.

```java
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
		//....
}
```

이번 예제에서는 좀 더 간편한 방법으로 애노테이션을 적용시켰습니다.

### 6. 서블릿 초기화 및 로딩

`HelloServlet`이 아직 메모리에 로드되지 않았다면, Tomcat은 서블릿의 `init()` 메소드를 호출합니다.

```java
HelloServlet helloServlet = new HelloServlet();
helloServlet.init();
```

### 7. HttpServletRequest 및 HttpServletResponse 생성

서블릿의 표준 클래스인 HttpServlet은 아래와 같은 메소드를 제공합니다.

```java
public void doGet(HttpServletRequest request, HttpServletResponse response) {...}
```

해당 함수의 매개 변수인 `HttpServletRequest`, `HttpServletResponse`은 서블릿에서 http 요청과 응답의 모든 내용을 다루는데 필요한 인터페이스로 구현체는 탐캣에서 제공합니다.

```java
HttpServletRequest httpRequest = new HttpServletRequestImpl();
HttpServletResponse httpResponse = new HttpServletResponseImpl();
```

### 8. 서블릿 메소드 호출

`HelloServlet`의 `service()` 메소드를 호출합니다. 이 메소드 내에서는 HTTP 메소드 타입에 따라 `doGet()`, `doPost()` 등을 호출합니다. 이번 예제의 경우, 사용자의 uri 입력을 통한 단순 페이지 입장으로 GET 메소드에 해당합니다.

### 9. 응답 생성

`HelloServelt`이 응답을 생성하면, Tomcat은 이를 HTTP 형식에 맞게 변환하여 클라이언트에게 전송합니다.

```java
httpResponse.flushBuffer();
```

### 10. 스레드 반환

```java
threadPool.releaseWorker(worker);
```



```
GET / HTTP/1.1
Host: localhost:8000
Connection: keep-alive
Cache-Control: max-age=0
sec-ch-ua: "Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "macOS"
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Sec-Fetch-Site: none
Sec-Fetch-Mode: navigate
Sec-Fetch-User: ?1
Sec-Fetch-Dest: document
Accept-Encoding: gzip, deflate, br
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
Cookie: Idea-e5827ed3=3a162ea4-6ccf-43a6-88ca-7d63cadfafb3; JSESSIONID=2CA896D7CF1D79F0136B632F7CD6A100
```

```java
[
  POST / HTTP/1.1, 
  User-Agent: PostmanRuntime/7.32.3, 
  Accept: */*, 
  Postman-Token: d5d0be29-dd7a-4560-bc8f-85f4249d3e43, 
  Host: localhost:8000, 
  Accept-Encoding: gzip, 
  deflate, 
  br, 
  Connection: keep-alive, 
  Content-Type: multipart/form-data; boundary=--------------------------230300044208677179655735, 
  Cookie: JSESSIONID=F0D7E21882BDD2C3F7A36A115E1E744D, 
  Content-Length: 280, 
   ,
  ----------------------------230300044208677179655735, 
  Content-Disposition: form-data; name="hello",
   ,
 hellovalue, 
 ----------------------------230300044208677179655735, 
 Content-Disposition: form-data; name="bye",
  , 
 byeValue, 
 ----------------------------230300044208677179655735--
]
```

