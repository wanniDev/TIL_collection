# 냄새 1. 이해하기 힘든 이름

- 깔끔한 코드에서 가장 중요한 것 중 하나가 바로 "좋은 이름"이다.
- 함수, 변수, 클래스, 모듈의 이름 등 모두 어떤 역할을 하는지 어떻게 쓰이는지 직관적이어야 한다.
- 사용할 수 있는 리팩토링 기술
  - 함수 선언 변경하기 (Change Function Declaration)
  - 변수 이름 바꾸기 (Rename Variable)
  - 필드 이름 바꾸기 (Rename Field)

## 예제 코드

> before

```java
public class StudyDashboard {
  private Set<String> usernames = new HashSet<>();

  private Set<String> reviews = new HashSet<>();

  private void studyReviews(GHIssue issue) throws IOException {
      List<GHIssueComment> comments = issue.getComments();
      for (GHIssueComment comment : comments) {
          usernames.add(comment.getUserName());
          reviews.add(comment.getBody());
      }
  }

  public Set<String> getUsernames() {
      return usernames;
  }

  public Set<String> getReviews() {
      return reviews;
  }

  public static void main(String[] args) throws IOException {
      GitHub gitHub = GitHub.connect();
      GHRepository repository = gitHub.getRepository("whiteship/live-study");
      GHIssue issue = repository.getIssue(30);

      StudyDashboard studyDashboard = new StudyDashboard();
      studyDashboard.studyReviews(issue);
      studyDashboard.getUsernames().forEach(System.out::println);
      studyDashboard.getReviews().forEach(System.out::println);
  }
}
```
### Refactoring1. 함수 선언 변경하기

> 함수 이름 변경하기, 메소드 이름 변경하기, 매개변수 추가하기, 매개변수 제거하기, 시그니처 변경하기

- 좋은 이름을 가진 함수는 함수가 어떻게 구현되었는지 코드를 보지 않아도 이름만 보고도 이해할 수 있다.
- 좋은 이름을 찾아내는 방법은? 함수에 주석을 작성한 다음, 주석을 함수 이름으로 만들어 본다.
- 함수의 매개변수는....
  - 함수 내부의 문맥을 결정한다. (예, 전화번호 포매팅 함수)
  - 의존성을 결정한다. (Payment 만기일 계산 함수)

```java
public class StudyDashboard {

    private Set<String> usernames = new HashSet<>();

    private Set<String> reviews = new HashSet<>();

    // 이미 리뷰를 로딩하고 있는 시점에서, 어떤 이슈를 로딩하는지 조회가 가능하다. 따라서, 매개변수로 issue를 전달할 필요가 없다.
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        List<GHIssueComment> comments = issue.getComments();
        for (GHIssueComment comment : comments) {
            usernames.add(comment.getUserName());
            reviews.add(comment.getBody());
        }
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public Set<String> getReviews() {
        return reviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getUsernames().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }
}
```

### Refactoring2. 변수 이름 바꾸기

- 더 많이 사용되는 변수일수록 그 이름이 더 중요하다.
  - 람다식에서 사용하는 변수 vs 함수의 매개변수
- 다이나믹 타입을 지원하는 언어에서는 타입을 이름에 넣기도 한다.
- 여러 함수에 걸쳐 쓰이는 필드 이름에는 더 많이 고민하고 이름을 짓는다.

```java
public class StudyDashboard {

    private Set<String> usernames = new HashSet<>();

    private Set<String> reviews = new HashSet<>();
    
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        List<GHIssueComment> reviews = issue.getComments();
      	// 댓글 자체를 번역하는 comment가 맞지만 comment의 목적은 리뷰이기 때문에 리뷰라고 명명하는게 더 타당하다.
        for (GHIssueComment review : reviews) {
            usernames.add(review.getUserName());
            this.reviews.add(review.getBody());
        }
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public Set<String> getReviews() {
        return reviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
      	// lambda 함수의 경우, 접근 범위가 매우 좁고, 바로 전 함수에서 사용되는 변수를 바로 알 수 있어서 굳이 명시할 필요가 없다.
        studyDashboard.getUsernames().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }
}
```

### Refactoring3. 필드 이름 바꾸기

- Record 자료 구조의 필드 이름은 프로그램 전반에 걸쳐 참조될 수 있기 때문에 매우 중요하다.
  - Record 자료 구조 : 특정 데이터와 관련있는 필드를 묶어놓은 자료 구조
  - 파이썬의 Dictionary, 줄여서 dicts.
  - C#의 Record
  - 자바 14 버전부터 record 지원
  - 자바에서는 Getter와 Setter 메소드 이름도 필드의 이름과 비슷하게 간주할 수 있다.

```java
public class StudyDashboard {

    // record 를 활용하여 코드를 간결하게 하면서(getter/setter 축소), 필드 이름 리팩토링
    private Set<StudyReview> studyReviews = new HashSet<>();

    /**
     * 스터디 리뷰 이슈에 작성되어 있는 리뷰어 목록과 리뷰를 읽어옵니다.
     * @throws IOException
     */
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        List<GHIssueComment> reviews = issue.getComments();
        for (GHIssueComment review : reviews) {
            studyReviews.add(new StudyReview(review.getUserName(), review.getBody()));

        }
    }

    public Set<StudyReview> getStudyReviews() {
        return studyReviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getStudyReviews().forEach(System.out::println);
    }
}
```

