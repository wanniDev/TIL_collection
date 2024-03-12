# CQRS 패턴

명령 쿼리 책임 분리 (CQRS) 패턴은 데이터 변이 또는 시스템의 명령 부분을 쿼리 부분과 분리합니다. 처리량, 지연 시간 또는 일관성에 대한 요구 사항이 서로 다른 경우 CQRS 패턴을 사용하여 업데이트와 쿼리를 분리할 수 있습니다. CQRS 패턴은 애플리케이션 두 부분, 즉 명령(Command) 측과 쿼리(Query) 측으로 분할합니다. 커맨드 측은 `create update`, 및 `delete` 요청을 처리합니다. 쿼리 측에서는 `query` 읽기 전용 복제본을 사용하여 파트를 실행합니다.

아래 그림은 DynamoDB와 같은 NoSQL 데이터 스토어를 사용하여 쓰기 처리량을 최적화하고 유연한 쿼리 기능을 제공합니다. 따라서 데이터를 추가할 때 액세스 패턴이 잘 정의된 워크로드에서 쓰기 확장성을 높을 수 있습니다. Aurora와 같은 관계형 데이터베이스는 복잡한 쿼리 기능을 제공합니다. DynamoDB 스트림은 Aurora 테이블을 업데이트하는 Lambda 함수로 데이터를 전송합니다.

![image](https://github.com/wanniDev/TIL_collection/assets/81374655/5eaf6637-07d6-4f3b-a2fe-ae467fdb5c25)

다음과 같은 경우 이 패턴을 사용하는 것이 좋습니다.

- Database-per-service 패턴을 구현했으며 여러 마이크로 서비스의 데이터를 조인하려고 합니다.
- 읽기 및 쓰기 워크로드에는 확장, 지연 시간 및 일관성에 대한 별도의 요구 사항이 있습니다.
- 읽기 쿼리의 최종 일관성은 괜찮습니다.

## CQRS in SpringBoot

springBoot를 활용하여 최대한 간단하게 CQRS를 구현해보도록 하겠습니다. 단일 모듈로 간단하게 구현하였으므로, 불필요한 확장성을 고려한 레이어 계층은 별도로 구성하진 않았습니다.

- JDK 17
- spring boot 3.1.3
- spring data jpa
- h2 database

### HelloController

```java
@RestController
@RequestMapping("api/hello")
public class HelloController {
    private final HelloCommandService helloCommandService;
    private final HelloQueryService helloQueryService;

    public HelloController(HelloCommandService helloCommandService, HelloQueryService helloQueryService) {
        this.helloCommandService = helloCommandService;
        this.helloQueryService = helloQueryService;
    }
		
  	// Command
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> commandHello(@RequestBody HelloDomainModel domainModel) {
        helloCommandService.createNewHello(domainModel);
        return ResponseEntity.ok().build();
    }
		
  	// Query
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HelloDto> QueryHello(@PathVariable Long id) {
        HelloDto helloDto = helloQueryService.readOneFrom(id);
        return ResponseEntity.ok(helloDto);
    }
}
```

### HelloCommandService

```java
@Service
@Transactional
public class HelloCommandService {
    private final HelloJpaCommandRepository helloJpaCommandRepository;

    public HelloCommandService(HelloJpaCommandRepository helloJpaCommandRepository) {
        this.helloJpaCommandRepository = helloJpaCommandRepository;
    }

    public void createNewHello(HelloDomainModel domainModel) {
        HelloEntity newInstance = HelloEntity.newInstance(domainModel.stringValue(), domainModel.numberValue());
        helloJpaCommandRepository.save(newInstance);
    }
}

```

### HelloDomainModel (Command 모델)

```java
public record HelloDomainModel(String stringValue, int numberValue) {
}
```

### HelloQueryService

```java
@Service
@Transactional(readOnly = true)
public class HelloQueryService {
    private final HelloJpaQueryRepository helloJpaQueryRepository;

    public HelloQueryService(HelloJpaQueryRepository helloJpaQueryRepository) {
        this.helloJpaQueryRepository = helloJpaQueryRepository;
    }

    public HelloDto readOneFrom(Long id) {
        HelloEntity helloEntity = helloJpaQueryRepository.findById(id).orElseThrow();
        return HelloDto.of(helloEntity);
    }

    public HelloDto readOneFrom(String stringValue) {
        HelloEntity helloEntity = helloJpaQueryRepository.findByStringValue(stringValue).orElseThrow();
        return HelloDto.of(helloEntity);
    }

    public HelloDto readOneFrom(int numberValue) {
        HelloEntity helloEntity = helloJpaQueryRepository.findByNumberValue(numberValue).orElseThrow();
        return HelloDto.of(helloEntity);
    }
}
```

### HelloDto(Query 모델)

```java
public record HelloDto(String stringValue, Integer numberValue, LocalDateTime createAt, LocalDateTime updateAt, HelloStatus helloStatus) {

    public static HelloDto of(HelloEntity helloEntity) {
        return new HelloDto(
                helloEntity.getStringValue(),
                helloEntity.getNumberValue(),
                helloEntity.getCreateAt(),
                helloEntity.getUpdateAt(),
                helloEntity.getHelloStatus()
        );
    }
}
```

### Hello Entity

```java
@Entity
public class HelloEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stringValue;

    private Integer numberValue;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private HelloStatus helloStatus;

    private int stage;

    protected HelloEntity() {}

    protected HelloEntity(String stringValue, Integer numberValue) {
        this.stringValue = stringValue;
        this.numberValue = numberValue;
        this.createAt = LocalDateTime.now();
        this.helloStatus = HelloStatus.PROCESSING;
        this.stage = helloStatus.getCode();
    }

    public static HelloEntity newInstance(String stringValue, int numberValue) {
        return new HelloEntity(stringValue, numberValue);
    }

	// getters...
}
```

위 코드는 CQRS의 기본 컨셉인 쓰기(Command)와 쿼리(Query)의 분리입니다.

`ProductCommandService`와 `ProductQueryService`는 각각 쓰기와 읽기 작업만을 담당하며, `ProductController`는 이 두 서비스를 모두 사용합니다.

당연하게도 이 예시는 가장 간단한 사례이며, MSA와 같이 정말 정교하고 세밀한 아키텍처에서는 레이어간에 공유하는 모델의 분리 및 각 모듈간의 의존성 분리 등을 더 신경써야 합니다.