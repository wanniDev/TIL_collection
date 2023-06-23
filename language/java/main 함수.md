# JAVA의 main 함수를 이해하자

main 함수는 Java를 배워본 사람들은 다 접해본 함수입니다. 만약 JAVA를 통해 helloworld를 출력하려면 아래와 같습니다.

```java
public class Sample {
  public static void main(String[] args) {
    System.out.println("helloworld");
  }
}
```

c, c++를 공부해보신 분들은 아시겠지만, main 함수는 애플리케이션이 실행되면 일종의 약속으로 애플리케이션이 실행되면 제일먼저 실행되는 함수입니다.

public static void는 무엇이며, 괄호() 안에 들어간 String[] args는 무엇일까요?

하나씩 짚어보겠습니다.

**public**

public은 접근 제어자로서, 외부에서 접근할 수 있는 일종의 제약입니다. 종류는 제약이 강한 순서대로 private -> protected -> public 이 있으며, default는 클래스 내부와 동일한 패키지에서 접근할 수 있습니다.

public은 어느곳에서든 해당 객체를 참조할 수 있다는 의미입니다.

**static**

클래스 멤버 변수와 클래스 멤버 메서드를 선언할 때 사용되는 키워드입니다. 객체의 생성 없이 클래스 자체에 대한 접근이 가능합니다. static으로 정의된 함수 또는 클래스는 JAVA가 컴파일 되는 순간 정의가 됩니다. 그리고 이후에 static이 아닌 객체가 정의가 됩니다.

**void**

해당 함수를 실행한 이후로 리턴되는 값이 없다는 뜻을 가진 키워드입니다. 단지 해당 함수가 종료된 후 호출한 부분으로 이동할 뿐입니다.

**String[] args**

String은 연속된 문자들을 관리하는데 필요한 기능들을 분류한 Java의 클래스이며 `[]`라는 기호는 String이라는 클래스가 연속적으로 나열되어있는 배열이라는 것을 표시하는 키워드 입니다. args는 그러한 배열의 변수명입니다.



정리한 내용들을 종합해보면 결국 메인 메소드는 **자바 애플리케이션 실행시 제일 먼저 동작하며 어느 객체에서든 접근할 수 있으며 자바가 컴파일 정의되고 돌려주는 값이 없는 함수**입니다.

**args의 역할**

args의 경우 아래처럼 새로 배열을 선언하고 각 배열의 인덱스에 값을 지정해서 출력을 할 수 있습니다.

```java
public class Sample {
  public static void main(String[] args) {
    args = new String[3];
		args[0] = "일";
		args[1] = "이";
		args[2] = "삼";
		
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
  }
}
```

그러나 실제로 args는 위의 예제처럼 직접사용하지 않습니다. args는 java라는 명령어를 통해 클래스를 호출할 경우 넘겨받는 arguments 입니다. 실제로는 아래와 같이 사용할 수 있겠습니다.

```java
public class Sample {

    public static void main(String[] args) {

		if( args.length < 3 ){
			System.out.println("전달되는 argumets 의 갯수가 3개 이상이어야 합니다.");
			return;
		}

		for( int i=0; i < args.length; i++ ){
			System.out.println(args[i]);
		}
      
	}
}
```

> terminal

```shell
$ javac Sample.java
$ java Sample 1 2
전달되는 arguments 의 갯수가 3개 이상이어야 합니다.
$ java Sample 1 2 3
1
2
3
```

만약에 인코딩 문제로 코드가 실행되지 않고 있다면 아래 처럼 java 커맨드에 추가로 인코딩 타입을 지정해주어야 합니다.

```shell
$ java Sample.java -encoding UTF-8
```

