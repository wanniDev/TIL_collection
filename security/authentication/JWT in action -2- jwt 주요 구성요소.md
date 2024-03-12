# JWT in action -2- jwt 주요 구성요소

> 목표
> jwt에 대한 내용을 자세히 살펴보고, 단순히 사용법만 익히는 것이 아니라, 상황에 맞게 여러 트레이드오프를 고려하여 제대로 응용할 수 있는 역량을 기르고자 합니다.

바로 이전에는 JWT와 관련된 용어를 살펴보고 대략적인 예시로 어떻게 JWT가 구성되는지 살펴봤습니다. 이번에는 JWT 토큰을 구성하는 header와 Jwt Claim(JSON으로 구성되는 payload 표준 목록)의 표준 대략적으로 알아보고 정리 해보겠습니다.

## 왜 표준을 알아야할까요?

원할한 커뮤니케이션과 오픈소스를 유연하게 활용하기 위함입니다. JWT를 처음부터 끝까지 직접 개발하여 사용하는 개발자는 매우 드뭅니다. 대부분, 오픈소스를 활용하는데, JJWT와 같은 유명한 오픈소스는 아래와 같은 표준을 참고하여 라이브러리를 제공합니다.

- [RFC 7519: JSON Web Token (JWT)](https://tools.ietf.org/html/rfc7519)
- [RFC 7515: JSON Web Signature (JWS)](https://tools.ietf.org/html/rfc7515)
- [RFC 7516: JSON Web Encryption (JWE)](https://tools.ietf.org/html/rfc7516)
- [RFC 7517: JSON Web Key (JWK)](https://tools.ietf.org/html/rfc7517)
- [RFC 7518: JSON Web Algorithms (JWA)](https://tools.ietf.org/html/rfc7518)
- [RFC 7638: JSON Web Key Thumbprint](https://www.rfc-editor.org/rfc/rfc7638.html)
- [RFC 9278: JSON Web Key Thumbprint URI](https://www.rfc-editor.org/rfc/rfc9278.html)
- [RFC 7797: JWS Unencoded Payload Option](https://www.rfc-editor.org/rfc/rfc7797.html)
- [RFC 8037: Edwards Curve algorithms and JWKs](https://www.rfc-editor.org/rfc/rfc8037)

따라서, JJWT에서 제공하는 api 함수는 위의 RFC 표준에서 사용되는 용어와 방침을 기반으로 설계됩니다. JWT와 관련된 RFC 표준을 파악하는 것은 다소 지루할 수 있지만, 오픈소스 코드는 RFC 표준에서 권장하는 방법을 거의 그대로 따르고 있기 때문에 해당 표준 내용을 인지하고 있으면 오픈소스의 코어 코드들을 일일이 파악하지 않아도 비즈니스 룰에 따라 JWT를 유연하게 활용할 수 있습니다.

또한, 별도로 컨벤션을 지정하지 않아도 같은 오픈소스를 활용하고 있는 개발자들과 원할하게 커뮤니케이션이 가능하므로 높은 협업 효율도 기대해볼 수 있습니다.

## JOSE 헤더

JOSE(JavaScript Object Signing and Encryption) 헤더는 JWT가 서명(JWS)된 경우와 암호화(JWE)가 적용된 경우에 활용되는 암호화 알고리즘의 종류를 지정하고 optional로 JWT의 추가 속성을 설명하는 JSON 객체의 멤버 모음집이라 할 수 있습니다. RFC 표준에서 제공하는 헤더의 종류는 아래와 같습니다.

| 항목명             | 의미                                                         |
| ------------------ | ------------------------------------------------------------ |
| typ (Type)         | JWT의 미디어 타입을 선언합니다. JWT가 다른 타입의 객체와 함께 사용될 수 있는 어플리케이션 데이터 구조에 포함될 수 있을 때 유용합니다. 값이 "JWT"인 경우, 이 객체가 JWT임을 나타냅니다. |
| cty (Content Type) | 이 헤더 매개변수는 JWT의 구조적 정보를 전달하는 데 사용됩니다. 중첩된 서명이나 암호화 작업이 사용되지 않는 일반적인 경우에는 사용하지 않는 것이 권장됩니다. 중첩된 서명 또는 암호화가 사용된 경우, "cty" 값은 "JWT"여야 하며, 이는 이 JWT 내에 중첩된 JWT가 포함되어 있음을 나타냅니다. |
| alg (Algorithm)    | JWT가 서명되거나 암호화될 때 사용된 알고리즘을 지정합니다. 이 매개변수는 JWT의 보안을 위해 매우 중요하며, 서명이나 암호화에 사용된 정확한 알고리즘을 나타냅니다. |
| kid (Key ID)       | 특정 서명 또는 암호화 키를 식별하는 데 사용됩니다. 이 매개변수는 여러 키 중에서 적절한 키를 선택하는 데 사용될 수 있습니다. |

## JWT Claims

JWT Claim은 토큰을 주고받는 개체들에게 전달하는 메시지들을 JSON 형태로 분류한 일종의 모음집입니다. claim의 명칭은 중복되어선 안되며, 의무적으로 전부 사용하지는 않고, 상황에 따라 선택적으로 활용됩니다. 또한, 아래 표에서 제시한 claim 말고도 별도로 private 한 요소도 포함시킬 수 있습니다.

| 항목명                | 의미                                                         |
| --------------------- | ------------------------------------------------------------ |
| iss (Issuer)          | JWT를 발행한 주체를 식별합니다. 일반적으로 어플리케이션에 특정하며, 값은 대소문자를 구분하는 문자열입니다. |
| sub (Subject)         | JWT의 주제(대상)를 식별합니다. JWT는 주로 이 주제에 대한 성명을 담고 있습니다. 값은 대소문자를 구분하는 문자열이며, 발행자에 의해 지역적으로 유일하거나 전 세계적으로 유일해야 합니다. |
| aud (Audience)        | JWT의 수신자를 식별합니다. JWT를 처리해야 하는 각 주체는 수신자 클레임에 자신을 식별하는 값을 가져야 합니다. 값은 대소문자를 구분하는 문자열의 배열이 될 수 있으며, 단일 수신자의 경우 문자열이 될 수도 있습니다. |
| exp (Expiration Time) | JWT의 만료 시간을 나타냅니다. 이 시간 이후에는 JWT가 처리되어서는 안 됩니다. 값은 NumericDate 형식의 숫자여야 합니다. |
| nbf (Not Before)      | JWT가 처리되기 전에는 받아들여질 수 없는 시간을 나타냅니다. 현재 날짜/시간은 "nbf" 클레임에 나열된 not-before 날짜/시간 이후여야 합니다. 값은 NumericDate 형식의 숫자여야 합니다. |
| iat (Issued At)       | JWT가 발행된 시간을 나타냅니다. 이 클레임은 JWT의 연령을 결정하는 데 사용될 수 있습니다. 값은 NumericDate 형식의 숫자여야 합니다. |
| jti (JWT ID)          | JWT에 대한 고유 식별자를 제공합니다. 이 식별자는 데이터 객체마다 다른 값이 우연히 할당될 확률이 무시할 수 있을 정도로 낮게 할당되어야 합니다. 값은 대소문자를 구분하는 문자열입니다. |