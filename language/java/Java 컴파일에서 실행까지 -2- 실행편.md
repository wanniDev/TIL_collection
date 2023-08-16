# Java 컴파일에서 실행까지 -2- 실행편

> 본 게시물은 학습 목적으로, [Back to the Essence - Java 컴파일에서 실행까지 - (2)](https://homoefficio.github.io/2019/01/31/Back-to-the-Essence-Java-%EC%BB%B4%ED%8C%8C%EC%9D%BC%EC%97%90%EC%84%9C-%EC%8B%A4%ED%96%89%EA%B9%8C%EC%A7%80-2/)의 내용을 토대로 내용을 정리하고 있습니다.

자바 애플리케이션은 `java` 명령어로 실행할 수 있습니다. 해당 명령어의 특징은 다음과 같습니다.

> - 자바 애플리케이션 시작
> - JRE(Java Runtime Enviroment) 시작
> - 인자로 지정된 클래스(`public static void main(String[] args)`를 포함하고 있는 클래스)를 로딩하고, `main()` 메서드를 호출한다.

## JDK, JRE, JVM

`java`는 JRE를 시작한다. JDK, JRE, JVM의 관계 먼저 파악하자면 아래 사진 한장으로 파악해볼 수 있습니다.

![image](https://github.com/payments-laboratory/payments-lab-api/assets/81374655/b318f1bd-a164-40d0-ae3f-7866ce32ff67)

각각 의 구조를 간략하게 정리해보면 아래와 같습니다.

- **JDK**: 자바 개발 환경 - 컴파일러, 역어셈블러, 디버거, 의존관계분석 등 개발에 필요한 도구 제공
- **JRE**: 자바 실행 환경 - 자바 실행 명령, 클래스로더와 바이트코드의 실행에 필요한 기본 라이브러리 제공
- **JVM**: 자바 가상 머신 - 바이트코드 인터프리터, JIT 컴파일러, 링커, 명령어 세트, 가비지 컬렉터, 런타임 데이터 영역(메모리) 등 OS에 독립적으로 실행될 수 있는 추상층 제공

자바 애플리케이션이 실행되는 과정을 간략하게 정리해보면 아래처럼 수행해볼 수 있겠습니다.

> JDK를 사용해서 바이트코드(class 파일)를 만들고,
>
> JRE를 사용해서 바이트코드를 실행하면,
>
> JVM이 가동되면서 바이트 코드의 실질적인 실행(실제 OS에 메모리 할당/회수, 시스템 명령 호출 등 요청)을 담당합니다.

## JRE 시작

`java` 명령 실행에 의해 JRE가 시작된다는 것은 결국 `java` 명령어의 인자로 지정된 클래스를 실행하기 위해 자바 실행 환경이 조성됨을 의미한다.

`java` 명령어의 인자로 지정한 설정 옵션에 맞게 JVM이 실행되고, JVM이 클래스로더를 이용해서 `initial class`를 `create`하고, `initial class`를 `link`하고,  `initialize`하고, main 메서드를 호출한다.([JVM 스펙](https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-5.html#jvms-5.2) 참고)

편의상 `initial class`는 시작 클래스 `create`는 생성, `link`는 링크, `initialize`는 초기화, `load`는 로딩이라고 지칭하겠습니다. 여기서 생성(create)은 JVM의 힙(heap)에 객체를 생성하는 것이 아니라 JVM의 메모리 어딘가에 자료구조를 생성하는 것을 모두 지칭합니다.

## 런타임 데이터 영역

`java` 명령어로 자바 애플리케이션을 실행하면 JVM이 실행되면서 시작 클래스를 생성, 링크, 초기화하고 main 메소드를 호출한다고 했습니다. 시작 클래스를 생성한다는 것은 시작 클래스의 바이트코드를 읽어서 JVM의 메모리는 어떻게 생겼을까?

JVM은 프로그램의 실행에 사용되는 메모리를 런타임 데이터 영역(Runtime Data Area)라고 부르는 몇 가지 영역으로 나눠서 관리합니다. 스펙의 목차를 보면 다음과 같이 정리해볼 수 있습니다.

1. PC 레지스터
2. JVM 스택
3. 힙(Heap)
4. 메서드 영역(Method Area)
5. 런타임 상수 풀(Run-Time Constnat Pool)
6. 네이티브 메서드 스택

위의 6가지 영역은 각 영역별로 특징이 명확하기 때문에 다음과 같이 그림으로 정리해서 약간 입체적으로 구분하는 것이 더 적합합니다.

![image](https://github.com/payments-laboratory/payments-lab-api/assets/81374655/f13a969f-d59f-45f7-b43c-dbcdecc324bd)

여기서 '단위'로 구분 단계를 추가한 이유는 스펙에도 `per-class`, `per-thread`라는 표현이 나오기 때문입니다. 여기서 '단위'는 생명 주기와 생성 단위를 의미합니다.

- **JVM 단위에 속하는 힙과 메소드 영역은 JVM이 시작될 때 생성되고, JVM이 종료될 때 소멸되며, JVM 하나에 힙 하나 메소드 영역 하나가 생성**됩니다.
- **Class 단위에 속하는 런타임 상수 풀은 클래스가 생성/소멸될 때 함께 생성/소멸되며, 클래스 하나에 런타임 상수 풀도 하나가 생성**됩니다.
- **Thread 단위에 속하는 PC 레지스터, JVM 스택, 네이티브 메소드 스택도 스레드가 생성/소멸될 때 함께 생성/소멸되며 스레드 하나에 PC 레지스터, JVM 스택, 네이티브 메소드 스택도 각 하나씩 생성**됩니다.



