# JWT in action -1- jwt 주요 표준에 대해 알아보자

> 목표
> jwt에 대한 내용을 자세히 살펴보고, 단순히 사용법만 익히는 것이 아니라, 상황에 맞게 여러 트레이드오프를 고려하여 제대로 응용할 수 있는 역량을 기르고자 합니다.

## 용어 설명

### JSON Web Token(JWT)

- JWT는 두 당사자 간에 전송될 'Claim'을 표현하는데 사용되는 간결하고 URL 안전한 방법입니다. 
- JWT 내의 'Claim'은 JSON 웹 서명(JWS) 구조의 페이로드로 사용되거나 JSON 웹 암호화(JWE) 구조의 평문으로 사용됩니다.
- 클레임이 디지털 서명되거나 메시지 인증 코드(MAC)로 무결성이 보호되고/또는 암호화될 수 있게 하는 JSON 객체로 인코딩됩니다.

### JSON Web Signature(JWS)

- JWS는 JSON 기반 데이터 구조를 사용하여 디지털 서명이나 Message Authentication Codes(MAC)로 보안이 강화된 컨텐츠를 나타냅니다. 
- 이 명세서와 함께 사용하기 위한 암호화 알고리즘과 식별자는 별도의 JSON Web Algorithm(JWA) 명세서에서 확인할 수 있습니다. 
- 관련 암호화 기능은 JSON Web Encryption 명세서에서 확인할 수 있습니다.

### JSON Web Encryption(JWE)

- JWE는 JSON 기반 데이터 구조를 사용하여 암호화된 컨텐츠를 나타냅니다. 
- 이 명세서와 함께 사용할 암호화 알고리즘과 식별자는 별도의 JSON Web Algorithm(JWA) 명세서에서 설명됩니다.
- 관련 디지털 서명 및 메시지 인증 코드(MAC) 기능은 JWS 명세서에 설명 됩니다.

### JSON Web Key(JWK)

- JWK는 암호화 키를 나타내는 JSON 데이터 구조입니다. 
- 이 명세서는 또한 JWK 집합을 나타내는 JWK 집합 JSON 데이터 구조를 정의합니다. 
- 이 명세서와 함께 사용될 암호화 알고리즘과 식별자는 별도의 JWA 명세서에서 설명됩니다.

### JSON Web Algorithm(JWA)

- JWA는 JWS, JWE, JWK 명세서와 함께 사용될 암호화 알고리즘과 식별자를 등록합니다.

## 기술적인 관점에서 JWT는 무엇인가요?

JWT는 정보를 간결하고 안전한 방식으로 전송하기 위한 범용 텍스트 기반 메시징 포맷입니다. 일반적으로 JWT는 웹상에서 인증 토큰을 보내고 받는 용도로 활용되고 있습니다. 하지만, 필요에 따라서 문자열, 이미지, 문서 등 바이트 배열로 표현될 수 있는 어떤 데이터의 메시지로도 사용될 수 있습니다.

가장 간단한 형태의 JWT는 두 가지를 포함합니다.

1. `payload` : JWT 내의 주요 데이터를 포함합니다.
2. `header` :  payload와 메시지 자체에 대한 메타데이터를 나타내는 key/value 쌍을 가진 JSON 형태로 구성합니다.

### payload는 json 뿐만 아니라 다양한 형태로 활용될 수 있다.

`payload`의 형태는 다양합니다. 문자열, 이미지, 문서 등 어떤 것이든지 될 수 있습니다. 따라서, JSON 객체도 `payload`가 될 수 있습니다. 많은 개발자들은 `payload`를 JSON으로 만드는것을 선호합니다. `payload`가 이 방식으로 사용될때, JSON 클레임 객체라고 불리며, 각 객체 내의 key/value 쌍은 클레임이라고 불립니다.

### JWT 보안 기법

크게 두 가지 방법으로 서명(JWS), 암호화(JWE)가 있습니다. 이 방식들을 통해, JWT의 정보를 안전하게 보내고 받을 수 있도록 합니다.

마지막으로, JSON은 사람 관점에서는 가독성이 좋지만 공백과 대괄호 등이 다수 포함되어있어서 그리 효율적인 메시지 형식은 아닙니다. 따라서, JWT는 Base64URL 인코딩 문자열로 압축된 HTTP 헤더나, URL 등으로 좀 더 압축된 형태로 웹 전반에 효율적으로 전송될 수 있습니다.

## JWT 예시

`payload`와 `header`에 대해서 설명했는데요. 이 두 가지가 어떻게 활용되어 토큰이 생성되는지 살펴보겠습니다. 

1. JWT에 사용될 header와 payload를 준비합니다.

**header**

```json
{
  "alg": "none"
}
```

**payload**

```
The true sign of intelligence is not knowledge but imagination.
```

2. JSON 문서의 불필요한 공백을 제거합니다.

```java
String header = '{"alg":"none"}';
String payload = 'The true sign of intelligence is not knowledge but imagination.'
```

3. 각 문자열을 UTF-8 바이트 형태로 변환하고, Base64URL로 인코딩을 진행합니다.

```java
String encodedHeader = base64URLEncode( header.getBytes("UTF-8") )
String encodedPayload = base64URLEncode( payload.getBytes("UTF-8") )
```

4. header와 claims(payload)를 `.` 기호로 연결시켜줍니다.

```java
String compact = encodedHeader + '.' + encodedPayload + '.'
```

`compact` 가 수행된 최종 JWT 토큰 문자열은 아래와 같습니다.

```
eyJhbGciOiJub25lIn0.VGhlIHRydWUgc2lnbiBvZiBpbnRlbGxpZ2VuY2UgaXMgbm90IGtub3dsZWRnZSBidXQgaW1hZ2luYXRpb24u.
```

이러한 토큰을 'unprotected' JWT라고 합니다. 어떠한 디지털 서명 혹은 암호화을 적용하지 않았기 때문이죠. 당연하게도 이러한 토큰은 탈취당할경우 쉽게 변조될 수 있습니다.

## JWS 예시

이번에는 JWT에 디지털 서명을 적용해보겠습니다. 디지털 서명은 JWT가 제 3자에 의해 변조되는 것을 막아줍니다.

1. JWT에 사용될 header와 payload를 살펴보겠습니다.

**header**

```json
{
  "alg": "HS256"
}
```

**payload**

```json
{
  "sub": "Joe"
}
```

이번에는 header에 **`HS256`(HMAC using SHA-256)**을 작성하여 JWT를 해당 암호화 알고리즘으로 서명하게 하였습니다. `payload`에는 `sub` / `Joe` 로 Json을 구성하였습니다.

claim을 구성하는 방법은 매우 다양합니다. 이렇게 다양한 방법들을 모아 하나의 표준으로 자리잡게 되었는데, 이 표준을 [Registered Claims](https://tools.ietf.org/html/rfc7519#section-4.1)라고 합니다. 위 예시의 `sub` 역시 claim을 구성하는 표준으로 Subject를 의미합니다.

2. JSON 문서의 불필요한 공백을 지웁니다.

```java
String header = '{"alg":"HS256"}';
String claims = '{"sub":"Joe"}';
```

3. 각 문자열을 UTF-8 바이트 형태로 변환하고, Base64URL로 인코딩을 진행합니다.

```java
String encodedHeader = base64URLEncode( header.getBytes("UTF-8") );
String encodedClaims = base64URLEncode( claims.getBytes("UTF-8") );
```

4. 마찬가지로 인코딩이 수행된 header와 Claims를 `.`으로 연결합니다.

```java
String concatenated = encodedHeader + '.' + encodedClaims;
```

5. 강력한 암호화 알고리즘이 적용된 비밀키 혹은 개인키를 준비하시고, `concatenated` 의 서명을 수행할 적절한 서명 알고리즘을 적용시킵니다. (이번 예시에서는 HMAC-SHA-256을 적용했습니다.)

```java
SecretKey key = getMySecretKey();
byte[] signature = hmacSha256( concatenated, key );
```

6. 디지털 서명은 바이트 배열 형태로 표현되기 때문에, Base64URL 인코딩을 적용시켜서 `concatenated`과 `.`으로 구분지으며 둘을 합칩니다.

```java
String compact = concatenated + '.' + base64URLEncode( signature );
```

이러한 과정을 거치면 아래와 같은 토큰을 획득할 수 있습니다.

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.1KP0SsvENi7Uz1oQc07aXTL7kpQG5jBNIybqr60AlD4
```

이러한 형태의 토큰을 signed JWT, JWS라고 부릅니다.

## JWE 예시

앞선 예시를 보면, JWT 토큰의 정보는 외부에서도 얼마든지 확인이 가능합니다. JWS의 목적이 결국엔 변조 및 부인 방지이기에 외부로부터 정보를 숨기는 기능은 없습니다. 따라서, 만약에 JWT에 주민등록번호, 집 주소나 계좌번호 등의 민감정보를 포함한다면 해당 정보를 숨길 필요가 있습니다.

이러한 수요를 충족시키기 위해서는 fully-encrypted JWT, 줄여서 JWE를 활용해야 합니다. JWE는 payload에 암호화를 적용하였으며, 인증되지 않은 사용자는 해당 데이터를 읽을 수도 변조시킬수도 없습니다. JWE는 [Authenticated Encryption with Associated Data](https://en.wikipedia.org/wiki/Authenticated_encryption#Authenticated_encryption_with_associated_data_(AEAD)), AEAD 라는 표준을 준수하여 데이터를 완전히 암호화하고 보호합니다.

AEAD의 내용은 이 문서에서 설명하기에는 내용이 너무 방대합니다. 다만 JWE가 적용된 토큰은 아래와 같은 형태로 드러납니다. (줄바꿈은 가독성을 위해 임의로 적용시킨것으로, 실제로는 줄바꿈을 수행하지 않습니다.)

```
eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.
6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ.
AxY8DCtDaGlsbGljb3RoZQ.
KDlTtXchhZTGufMYmOYGS4HffxPSUrfmqCHXaI9wOGY.
U0m_YmjN04DJvceFICbCVQ
```

## 마무리

JWT에 대한 내용을 대략적으로 살펴보고 JWT를 만드는 방법을 살펴보았습니다. JWS와 JWE의 경우, 토큰을 만들기위해 필요한 일련의 과정이 있습니다만, 이 과정을 일일이 전부 구현하기에는 난이도가 있으며 토큰의 내용에 따라 보안 이슈도 많이 발생할 수 있습니다. 그래서 개발자들은 검증된 라이브러리를 많이 활용하는데요. Java의 경우는 JJWT 라이브러리가 가장 많이 사용됩니다.
다음 게시글에서는 JJWT를 활용하여 앞서 설명한 JWT를 어떤 방식으로 구성하는지 알아보겠습니다.

## 출처

- https://datatracker.ietf.org/doc/html/rfc7519#section-4.1
- https://github.com/jwtk/jjwt