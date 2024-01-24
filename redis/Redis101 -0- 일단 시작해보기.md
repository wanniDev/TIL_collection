# Redis101 -0- 일단 시작해보기

Java에서는 보통 Redis와 연결시키는 클라이언트를 활용하는데, Jedis와 Lettuce가 있다. 단순하게 시작하기에는 Jedis가 좋지만, 처리량 및 응답속도 측면에서는 Lettuce가 더 우수하며, spring data redis에서도 기본적으로 Lettuce를 활용하고 있습니다.

Lettuce가 더 우수한지에 대한 검증은 아래 사이트에 의해 이미 검증된바 있습니다.

https://jojoldu.tistory.com/418

## Redis의 특징 살펴보기

- **실시간 응답 :** 레디스는 인메모리 데이터베이스로서, RDBMS 보다 속도가 훨씬 빠릅니다.
- **단순성 :** 다양한 자료구조를 지원하며, 따라서 임피던스 불일치 해소가 가능합니다.
- **고가용성 :** 복제가 쉽습니다. 장애 탐지 후 fail-over가 가능합니다.
- **확장성 :** 클러스터링이 쉽습니다.
- **클라우드 네이티브/멀티 클라우드 :** 레디스는 멀티 클라우드에 특화된 소프트웨어 입니다.

## Redis의 활용 범위

- **Database :** 
  - 간편한 설치, 최소한의 리소스로 높은 처리량을 선보입니다. 
  - 기본적으로 가용성이 높아서 로드 밸런서 및 프록시 같은 추가 서비스를 설치할 필요가 없습니다. 
  - 인메모리 디비 특성상 영속화가 어려울 수 있지만, AOF(Append Only File), RDB(Redis DataBase)에 의해 디스크에 주기적으로 저장 가능합니다. 즉, 데이터 유실시 백업 파일을 통해 복구가 가능합니다.
- **MessageBroker** : 
  - MSA와 같이 서비스별로 완전히 분리돼 있는 구조에서는 각 서비스간의 지속적인 통신이 필요합니다.
  - 그래서, 메시지 큐, 스트림 같은 메시지 부로커를 통해 서비스들간에 데이터를 비동기로 전달할 수 있는 통신 채널을 구현하는 것이 좋습니다.
  - 레디스는 이와 같은 요구사항을 충족하는 pub/sub 기능을 제공합니다.

그 밖에 session store, cache store 등의 역할을 수행하기도 합니다만, 이 부분은 추후에 더 정리해보도록 하겠습니다.

## Redis 설치

직접 소프트웨어를 설치하는 방법도 있습니다. 하지만, Redis 사용법 자체에 집중하기 위해, 도커를 활용하겠습니다.

```shell
$ docker run --name hello-redis -p 6379:6379 -d redis
```

## Redis 구성하기

우선 Spring Initializer를 통해 Java Spring 프로젝트를 구성합니다. 그리고 아래와 같이 spring data redis 의존성을 추가해줍니다.

```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-redis'
```

이제 redis configuration 컴포넌트를 구성합니다.

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
```

Serializer를 따로 구성하지 않아도 스프링에서 조회할 땐 정상 동작하지만, redis-cli에서 직렬화되지 않은 상태로 저장됩니다. Serializer 세팅에 대해 간략하게 설명하자면

- setKey(Value)Serializer가 있어야 String, list, set, sorted set을 직렬화 할 수 있습니다. 
- setHashKey(Value)Serializer가 있어야 hash도 직렬화 할 수 있습니다.

따라서, Serializer를 별도로 구성해야, redis-cli 에서도 원할한 작업이 가능합니다.

## Redis 기본 함수 구성하기

spring data redis는 레디스 명령어를 좀 더 직관적으로 전송해주기 위해 `RedisTemplate`을 제공해줍니다. `RedisTemplate`는 redis-cli 에서 사용되는 명령어들을 좀 더 직관적으로 수행할 수 있는 함수들을 제공해줍니다.
아래 예시는 redis에서 가장 보편적으로 많이 사용되는 string, set, sorted set, list 의 기본적인 명령어들을 수행하는 코드 위주로 작성했습니다.

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisOperationWrapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedisTemplate<String, String> redisTemplate;

    public RedisOperationWrapper(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // string
    public void addString(String key, String value) {
        ValueOperations<String, String> stringOperation = redisTemplate.opsForValue();
        stringOperation.set(key, value);
        logger.info("redis command for value operation done. add key: {}, value: {}", key, value);
    }

    public String getString(String key) {
        ValueOperations<String, String> stringOperation = redisTemplate.opsForValue();
        return stringOperation.get(key);
    }

    // list
    public void addRightList(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, value);
        logger.info("redis command for list operation done. add right key: {}, value: {}", key, value);
    }

    public void addLeftList(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, value);
        logger.info("redis command for list operation done. add left key: {}, value: {}", key, value);
    }

    public List<String> getList(String key, long start, long end) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, start, end);
    }

    // set
    public void addSet(String key, String... values) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, values);
        logger.info("redis command for set operation done. add key: {}, value: {}", key, Arrays.toString(values));
    }

    public Set<String> getSet(String key) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    // sorted set
    public void sortedSet(String key, String value, int score) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, value, score);
        logger.info("redis command for sorted set operation done. add key: {}, value: {}, score: {}", key, value, score);
    }

    public Set<String> getSortedSet(String key, long start, long end) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.range(key, start, end);
    }

    // hash
    public void setHash(String key, String hashKey, String value) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, value);
        logger.info("redis command for hash operation done. add key: {}, hashKey: {}, value: {}", key, hashKey, value);
    }

    public Map<Object, Object> getHash(String key) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }
}
```

