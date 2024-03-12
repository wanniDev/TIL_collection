# Mixin in JAVA

믹스인은 객체지향에서 일부 코드를 클래스에 주입할 수 있는 매커니즘을 제공하는 개념입니다. 믹스인 프로그래밍이란 기능 단위가 클래스에서 생성된 다음 다른 클래스와 혼합되는 개발 스타일을 이야기 합니다.

이렇게 글로만 표현하려니 헷갈릴겁니다. 코드를 보면 금방 와닿을겁니다.

## 조류(Bird)와 비둘기(Dove)

조류의 특징이 뭘까요? 아마 대부분 날다(fly)를 생각할 수 있습니다. 그렇다면 이 개념을 자바로 구현해봅시다. Bird는 날아다니는 '행동'이 가능하니, 인터페이스로 표현하면 다음과 같을겁니다.

```java
public interface Bird {
  void fly();
}
```

이제 구체적인 조류인 비둘기를 구현해보죠.

```java
public class Dove {
  void fly() {
    System.out.println("비둘기가 날아간다!");
  }
}
```

이렇게만 보면 큰 문제가 없어보입니다. 다만 명목상 조류일 뿐 조류같지 않은 조류도 있다는 점을 생각해보면, 인터페이스 설계에 문제가 있을 것 같네요.

## 조류(Bird)와 펭귄(Penguin)

알고계신 분도 있겠지만, 놀랍게도 펭귄은 조류입니다.

![r/todayilearned - TIL that in 2008 the BBC created a film trailer for a nature documentary featuring flying penguins. It was hosted by Monty Python’s Terry Jones and directed by Prof Alid Loyas, an Anagram for April Fools Day. Indeed, the trailer premiered April 1st, as an elaborate April Fools…](https://external-preview.redd.it/8vbhQ9PvStlu077uzX4oXNd0v5-v3t4TbRgImI5Kb3s.jpg?auto=webp&v=enabled&s=db20ee168e9c06593165109b9a2566a458911ff0)

위 사진은 BBC에서 만우절 행사로 만든 영상으로, 펭귄은 사실 조류였다는 사실을 꽤 유머러스한 방식으로 표현하였습니다.

그런데, 이 부분을 고려해보면 기존 코드에 문제가 생겼습니다. 과연 Bird 인터페이스에 `fly()`를 넣는게 맞을까요? 아니라면 어떻게 해야할까요? 이런 류의 문제가 발생하는 것을 막기위한 객체지향 프로그래밍 기법으로 'MixIn'을 다시 한 번 소개해볼 수 있겠네요.

## 조류(Bird)는 조류일뿐, 날 수 있음(Flyable)의 가능 여부는 별개

이제 날 수 있는 조류와 그렇지 못한 조류를 구분하기 위해 `fly()` 함수는 다른 인터페이스로 분리해놓겠습니다.

```java
public interface Flyable {
	void fly();
}

public interface Bird {
  
}

public class Dove implements Bird, Flyable {
	void fly() {
    System.out.println("Fly dove");
  }
}

public class penguin implements Bird {
  
}
```

이렇게 하면, 펭귄은 조류이지만 날지 못하고, 비둘기는 조류이면서 날 수 있는 객체로 분류 할 수 있게 되었습니다. 그런데, 또 문제가 있습니다..!

## 빌런 개발자1: 인간은 비행기를 타잖아? 그러니까 인간(Human)도 날 수 있어(Flyable)

만약 이 상황이 현업이었다면 현기증이 나겠네요. 우선 코드부터 보겠습니다.
```java
public class Human implements Flyable {
  void fly() {
    System.out.println("Flying human :)");
  }
}
```

이건 게시글과는 상관없는 이야기일 수 있지만, 정말 인간이 비행기를 탄다는 걸 표현하려면, 이렇게 Human에게 Flyable 인터페이스를 하는 것보다는 Plane이라는 객체에게 어딘가로 날아가도록 '요청'하는 것이 좀 더 매끄러울 것입니다.

```java
public class Main {
  public static void main(String[] args) {
    Human human = new Human();
    Plane plane = new Plane();
    
    plane.fly(human, "출발점", "도착점");
  }
}
```

사실 위의 예시 코드도 클린코드 관점에서 보면, `plane.fly(...)` 함수도 매개변수가 3개가 되기 때문에 개선해야할 대상이긴 하지만.. 주제랑은 벗어나는 이야기니, 그 부분은 넘어가겠습니다...

먼저 Human에 Flyable을 넣지 못하게 하는 방법 중 한가지는 위의 코드처럼 변경하도록 다른 착한 개발자가 리뷰를 해주는 것입니다. 하지만 안타깝게도 코드리뷰를 해주는 문화를 가진 회사보다 그렇지 못한 회사가 더 많은게 현실이죠... 그리고, 좀 더 어렵지만 강제성이 있는 방법을 제시 해야할겁니다.

자바에서 제공하는 접근제어자를 활용하는 방법이죠.

## default 제어자로 사용할 수 있는 여지를 주지말자

자바에서는 여러가지 접근 제어자를 제공합니다. 개발자는 이를 통해 해당 코드의 접근 허용 범위를 제어할 수 있죠.

그 중에서도 default 제어자는 같은 패키지끼리만 공유할 수 있게하는 제약이 있습니다. 이를 활용해 Human에 Flyable을 넣으려고 할 때 컴파일 에러로 사용할 수 있는 여지를 주지 않도록 할 수 있습니다.

```java
package code.bird;

interface Flyable {
  void fly();
}


package code.human;
public class Human implements Flyable { // complie 에러 발생
  
}
```