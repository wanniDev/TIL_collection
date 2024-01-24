# JPA 무한재귀 및 N+1 해결하기

jpa는 orm으로서 jvm 기반 웹 애플리케이션에서 SQL을 일일이 쓰지 않고도 RDBMS와 쉽게 연동이 가능합니다. 왜냐면 대부분의 쿼리르 jpa가 자동으로 구성해주기 때문이죠. 그러나 이러한 편리함에 의존하여, 남용하면 성능을 지나치게 낮추는 일이 발생합니다. 대표적으로 무한재귀와 N+1 이슈입니다.

이해하기 쉽게 예제 코드와 같이 진행해보겠습니다. 아래 Post와 Tag 엔티티가 있습니다. 그리고 두 엔티티간의 양방향 관계를 반영하기 위한 엔티티 PostTag가 있습니다.

```java
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTag> postTags = new HashSet<>();

    protected Post() {}

    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private Post(String title, String content, Set<PostTag> postTags) {
        this.title = title;
        this.content = content;
        this.postTags = postTags;
    }

    public static Post newInstance(String title, String content) {
        return new Post(title, content);
    }

    public static Post newInstance(String title, String content, Set<PostTag> postTags) {
        return new Post(title, content, postTags);
    }

		// getter, setter
}

```

```java
@Entity
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected Tag() {}

    private Tag(String name) {
        this.name = name;
    }

    public static Tag newInstance(String name) {
        return new Tag(name);
    }
		
  // getter, setter
}
```

```java
@Entity
public class PostTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    protected PostTag() {}

    private PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    public static PostTag newInstance(Post post, Tag tag) {
        return new PostTag(post, tag);
    }
	
  	// getter, setter
}
```

이제 post 목록을 불러 들이는 api를 구현하겠습니다. 기본적인 페이징 처리가 되어있으며, api를 호출하면 그대로 Post 콜렉션을 리턴합니다. 의도한 json 포맷은 다음과 같습니다.

```json
{
  {
      "id": 9,
      "title": "title9",
      "content": "content9",
      "tags": [
          "tag2",
          "tag3",
          "tag1"
      ]
  },
.......
}
```



```java
@RestController
@RequestMapping("post")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> findPosts(
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false, defaultValue = "9") int pageSize
    ) {
        return ResponseEntity.ok(postService.findPost(pageNum, pageSize, "DESC", "id"));
    }
}
```

```java
@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final JpaTagRepository tagRepository;

    public PostService(PostRepository postRepository, JpaTagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

		public List<Post> findPost(int pageNum, int pageSize, String direction, String... properties) {
        return postRepository.findAll(pageNum, pageSize, direction, properties);
    }
}

```

이렇게 구성하고 `http://127.0.0.1:8080/post` 를 GET으로 호출하면 어떻게 될까요? 결론부터 말씀드리면, 무한 재귀로 인한 스택오버플로우 오류가 발생합니다.

왜냐면, Post와 PostTag는 양방향 관계이고 이 엔티티를 JSON 포맷으로 변환하는 과정에서 상호 참조가 발생하다보니, 결국 스택오버플로우가 발생한 것이죠. 이를 해결하기 위한 방법은 여러가지가 있습니다.

1. @JsonIgnore
2. DTO
3. @JsonIdentityInfo

1, 3번의 경우 도메인간의 연관관계를 망가트리고 jpa를 직관적으로 활용하기 어렵게 만드는 요소가 될 수 있습니다. 어차피, 코드간의 결합도를 줄이기 위해선 dto를 활용해야하는 이번 경우에는 2번 방법을 선택하기로 했습니다.

```java
public record PostDto(
        Long id,
        String title,
        String content,
        List<String> tags
) {
    public static PostDto fromEntity(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTags().stream().map(Tag::getName).toList());
    }
}
```

이번 경우에 dto는 record를 활용하기로 했습니다. lombok을 활용하지 않고도 불필요한 getter를 작성할 필요가 없기 때문이죠. 그리고 이제 PostService와 PostRestController에 변경된 사항을 반영하면 아래와 같습니다.

```java
public List<PostDto> findPost(
  int pageNum, int pageSize, String direction, String... properties) {
        return postRepository
          .findAll(pageNum, pageSize, direction,properties)
          .stream()
          .map(PostDto::fromEntity)
          .toList();
}

@GetMapping
public ResponseEntity<List<PostDto>> findPosts(
        @RequestParam(required = false, defaultValue = "0") int pageNum,
        @RequestParam(required = false, defaultValue = "9") int pageSize) {
    return ResponseEntity.ok(postService.findPost(pageNum, pageSize, "DESC", "id"));
}

```

이렇게 수정하면, 더이상 무한 재귀 현상은 일어나지 않습니다. 하지만, 해당 api를 실행했을때 발생하는 쿼리를 추적하면 아래와 같이 나옵니다.

```
Hibernate: /* <criteria> */ select p1_0.id,p1_0.content,p1_0.title from post p1_0 order by p1_0.id desc limit ?,?
Hibernate: /* <criteria> */ select count(p1_0.id) from post p1_0
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select t1_0.id,t1_0.name from tag t1_0 where t1_0.id=?
Hibernate: select t1_0.id,t1_0.name from tag t1_0 where t1_0.id=?
Hibernate: select t1_0.id,t1_0.name from tag t1_0 where t1_0.id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
Hibernate: select pt1_0.post_id,pt1_0.id,pt1_0.tag_id from post_tag pt1_0 where pt1_0.post_id=?
```

post_tag 테이블을 page_size 만큼 select 쿼리를 수행하는데, 아무래도 post와 연관되어 있는 tag 레코드들을 검색하기 위함인듯 합니다. 비록 N+1 문제를 1차적으로 피하기 위해 postTag와 연관된 post, tag의 Fetch 전략을 Lazy로 했으나, 어차피 참조를 하는순간 추가 조회가 발생하므로 단순히 Fetch 전략을 제어하는 것만으로는 문제가 해결될 것 같진 않습니다. N+1 문제를 근복적으로 해결하기 위한 방법으로는 대표적으로 2가지가 있습니다.

## 해결책1: Fetch Join, Inner Join

N+1이 발생하는 근본적인 원인은 앞에서 예시를 본바와 같이 한쪽 테이블만 조회 하고 연결된 테이블을 각각 따로 조회하기 때문입니다. 따라서, 미리 두 테이블을 Join 하여 한 번에 모든 테이블을 조회한다면 N+1 문제가 발생하지 않을 것입니다.

Fetch Join이란, JPQL 에서 성능 개선 및 최적화를 위해 제공하는 기능으로, 연관된 엔티티나 컬렉션을 한번에 조회하는 역할을 합니다. 다음과 같은 방법을 활용해볼 수 있습니다.

```java
public interface TeamRepository extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	@Query("select distinct t from Team t join fetch t.members")
	List<Team> findAllWithJoinFetch();
}
```

### Fetch Join 단점

- 쿼리 한 번에 모든 데이터를 가져옵니다. 따라서 JPA의 Pageable 사용이 불가능합니다.
- 1:N 관계가 두 개 이상인 경우 사용이 불가능 합니다. (MultipleBagFetchException 발생)
- 패치 조인 대상에게 별칭(as) 부여가 불가능합니다.
- 번거롭게 쿼리문을 작성해야합니다. (이럴거면 jpa 왜씀...? 이런 의문이 들 수 있습니다.)

## 해결책2: Entity Graph, outer Join

`@EntityGraph`의 `attributePaths`는 같이 조회할 연관 엔티티명을 적어서 사용하며 `,`를 통해 여러개를 설정이 가능합니다.

사실 성능 최적화 측면에서는 기본적으로 outer join 보다 inner join이 성능 최적화에 유리합니다.

```java
public interface TeamRepository extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	@EntityGraph(attributePaths = {"members"})
	List<Team> findAll();
}
```

EntityGraph의 경우에는 SpringJPA의 기본 메소드에 활용가능하며, Pageable 등에도 설정가능합니다.

```java
public interface TeamRepository extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	@Orverride
	@EntityGraph(attributePaths = {"members"})
	Page<Team> findAll(@Nullable Specification<T> spec, Pageable pageable);
}
```

## 주의 사항

FetchJoin과 EntityGraph는 공통적으로 '카테시안 곱'이 발생하여 데이터를 중복 조회할 수 있습니다. 여기서 카테시안 곱이란, 두 테이블 사이에 유효 조인 조건을 적지 않을 경우 해당 테이블에 대한 모든 테이터를 결합하여 테이블에 존재하는 행 갯수를 곱한만큼의 결과 값이 반환되는 것입니다.

