# RedisTemplate transaction

### `RedisTemplate.execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline)`

```java
public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {

		Assert.isTrue(initialized, "template not initialized; call afterPropertiesSet() before using it");
		Assert.notNull(action, "Callback object must not be null");

		RedisConnectionFactory factory = getRequiredConnectionFactory();
		RedisConnection conn = RedisConnectionUtils.getConnection(factory, enableTransactionSupport);

		try {

			boolean existingConnection = TransactionSynchronizationManager.hasResource(factory);
			RedisConnection connToUse = preProcessConnection(conn, existingConnection);

			boolean pipelineStatus = connToUse.isPipelined();
			if (pipeline && !pipelineStatus) {
				connToUse.openPipeline();
			}

			RedisConnection connToExpose = (exposeConnection ? connToUse : createRedisConnectionProxy(connToUse));
			T result = action.doInRedis(connToExpose);

			// close pipeline
			if (pipeline && !pipelineStatus) {
				connToUse.closePipeline();
			}

			return postProcessResult(result, connToUse, existingConnection);
		} finally {
			RedisConnectionUtils.releaseConnection(conn, factory);
		}
}
```

레디스 커맨드 명령어를 수행한다면 다음과 같이 단계별로 구성할 수 있겠습니다.

1. RedisConnectionFactory 구성
2. RedisConnection 구성
3. RedisConnection preProcess 구성
4. pipelineStatus 구성
5. RedisConnection proxy 생성여부 결정 및 수행
6. callback.doInRedis(redisConnection)
7. postProcessResult(result, conn, existingConn)

### 1. RedisConnectionFactory

```java
public RedisConnectionFactory getRequiredConnectionFactory() {
  RedisConnectionFactory connectionFactory = getConnectionFactory();
  Assert.state(connectionFactory != null, "RedisConnectionFactory is required");
  return connectionFactory;
}

public RedisConnectionFactory getConnectionFactory() {
  return this.connectionFactory;
}
```

connectionFactory는 redis 클라이언트를 구성하는 팩토리 클래스라고 보면됩니다. 대표적으로 Jedis, lettuce가 있습니다. Jedis는 단순하지만 오직 단일 스레드로만 동작하기에 한계가 명확합니다. 현재 spring-data-redis는 기본적으로 lettuce를 사용하기 때문에, 거의 대부분 connectionFactory 구현체는 lettuce라고 보시면 됩니다.

### 2. RedisConnection

> RedisConnectionUtils

```java
/**
 * Obtain a {@link RedisConnection} from the given 
 		{@link RedisConnectionFactory}. Is aware of existing connections
 * bound to the current transaction (when using a transaction manager) 
 		or the current thread (when binding a connection to a closure-scope).
 *
 * @param factory connection factory for creating the connection.
 * @param transactionSupport whether transaction support is enabled.
 * @return an active Redis connection with transaction management if requested.
 */
public static RedisConnection getConnection(RedisConnectionFactory factory, boolean transactionSupport) {
  return doGetConnection(factory, true, false, transactionSupport);
}

public static RedisConnection doGetConnection(RedisConnectionFactory factory, boolean allowCreate, boolean bind, boolean transactionSupport) {

//....

		return connection;
	}
```

Redis 서버와 커넥션을 형성하기 위한 코드입니다. ConnectionFactory에서 Connection을 만들면 이 Connection으로 Redis 커맨드를 수행할 수 있게 됩니다.

### 3. RedisConnection preProcess 구성

```java
protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
  return connection;
}

protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
  return new DefaultStringRedisConnection(connection);
}
```

RedisTemplate은 Redis 서버와 커넥션을 형성해주는 것 말고도, redis 커멘드를 수행할때 입력값 및 출력값을 java 서버 혹은 레디스에 맞는 형태도 직렬화 혹은 역직렬화를 수행합니다.

RedisTemplate은 제네릭 타입을 활용하여, 직렬화, 역직렬화 타입의 형태를 정합니다. spring data redis의 경우, redisTemplate을 빈으로 등록하면서 그 타입을 지정해줄 수 있습니다. 개발자가 인위적으로 타입을 지정하는 방법도 있지만, spring boot가 제공하는 auto-configure를 활용하기도 합니다. preProcessConnection은 그 자동설정 기능을 적용시키기 위해 있는 코드입니다.

### 4. pipelineStatus 구성

```java
boolean pipelineStatus = connToUse.isPipelined();
			if (pipeline && !pipelineStatus) {
				connToUse.openPipeline();
			}
```

만약 MULTI, WATCH 와 같은 파이프라인이 적용되는 경우 해당 코드가 실행됩니다. 

### 5. RedisConnection proxy 생성여부 결정 및 수행

```java
RedisConnection connToExpose = (exposeConnection ? connToUse : createRedisConnectionProxy(connToUse));

T result = action.doInRedis(connToExpose);
```

`exposeConnection`은 `execute` 메소드의 매개변수로, 사용자가 `RedisConnection`을 직접 노출시킬지 여부를 결정하는 데 사용됩니다. 즉, `exposeConnection`이 `true`일 경우, `connToUse`을 직접 사용하고 `false`일 경우, 프록시 연결(`createRedisConnectionProxy(connToUse)`) 입니다.

이 부분은 사용자가 Redis 연결을 어떻게 다룰지 선택할 수 있게 하는 옵션입니다. 이는 유연성을 제공하면서 동시에 연결 관리의 복잡성이나 안전성과 관련된 고려사항을 사용자에게 맡깁니다.

### 6. callback.doInRedis(redisConnection)

```java
T result = action.doInRedis(connToExpose);

// close pipeline
if (pipeline && !pipelineStatus) {
  connToUse.closePipeline();
}
```

`action.doInRedis(connToExpose)`: 제공된 `RedisCallback`을 사용하여 실제 Redis 작업을 수행합니다. `connToExpose`는 실제 사용되는 Redis 연결입니다.

### 7. postProcessResult(result, conn, existingConn)

```java
@Nullable
protected <T> T postProcessResult(@Nullable T result, RedisConnection conn, boolean existingConnection) {
  return result;
}
```

결과를 후처리하고 반환합니다.

## 그렇다면, 언제 TransactionManager가 개입할까?

일반적인 RedisTemplate 설정을 등록하는 코드를 살펴보겠습니다.

```java
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setEnableTransactionSupport(true); // redis Transaction On !
    return redisTemplate;
  }

  @Bean // 만약 PlatformTransactionManager 등록이 안되어 있다면 해야함, 되어있다면 할 필요 없음
  public PlatformTransactionManager transactionManager() throws SQLException {
    // 사용하고 있는 datasource 관련 내용, 아래는 JDBC
    return new DataSourceTransactionManager(datasource()); 

    // JPA 사용하고 있다면 아래처럼 사용하고 있음
    return new JpaTransactionManager(entityManagerFactory);
  }
}
```

여기서 `redisTemplate.setConnectionFactory(redisConnectionFactory);` 부분을 살펴 보겠습니다.

```java
/**
 * If set to {@code true} {@link RedisTemplate} will participate 
   in ongoing transactions using
 * {@literal MULTI...EXEC|DISCARD} to keep track of operations.
 *
 * @param enableTransactionSupport whether to participate in ongoing transactions.
 * @since 1.3
 * @see RedisConnectionUtils.getConnection(RedisConnectionFactory, boolean)
 * @see TransactionSynchronizationManager.isActualTransactionActive()
 */
public void setEnableTransactionSupport(boolean enableTransactionSupport) {
  this.enableTransactionSupport = enableTransactionSupport;
}
```

위 주석을 살펴보면, `setEnableTransactionSupport`에서  `RedisConnectionUtils`로 커넥션을 획득하면서 트랜잭션 매니저의 개입 여부를 정한다고 합니다. 해당 코드는 앞서 `redisTemplate`에서 `execute`를 실행하는 예제 코드를 통해 확인해볼 수 있습니다.

```java
public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {

		Assert.isTrue(initialized, "template not initialized; call afterPropertiesSet() before using it");
		Assert.notNull(action, "Callback object must not be null");

		RedisConnectionFactory factory = getRequiredConnectionFactory();
		RedisConnection conn = RedisConnectionUtils.getConnection(factory, enableTransactionSupport);

		try {

			boolean existingConnection = TransactionSynchronizationManager.hasResource(factory);
			RedisConnection connToUse = preProcessConnection(conn, existingConnection);
			
      //.......
      
			return postProcessResult(result, connToUse, existingConnection);
		} finally {
			RedisConnectionUtils.releaseConnection(conn, factory);
		}
}
```

이제 `TransactionSynchronizationManager.hasResource(factory);` 코드를 살펴보겠습니다.

### TransactionSynchronizationManager.hasResource(factory);

```java
/**
 * Check if there is a resource for the given key bound to the current thread.
 * @param key the key to check (usually the resource factory)
 * @return if there is a value bound to the current thread
 * @see ResourceTransactionManager#getResourceFactory()
 */
public static boolean hasResource(Object key) {
  Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
  Object value = doGetResource(actualKey);
  return (value != null);
}

/**
* Actually check the value of the resource that is bound for the given key.
*/
@Nullable
private static Object doGetResource(Object actualKey) {
  Map<Object, Object> map = resources.get();
  if (map == null) {
    return null;
  }
  Object value = map.get(actualKey);
  // Transparently remove ResourceHolder that was marked as void...
  if (value instanceof ResourceHolder resourceHolder && resourceHolder.isVoid()) {
    map.remove(actualKey);
    // Remove entire ThreadLocal if empty...
    if (map.isEmpty()) {
      resources.remove();
    }
    value = null;
  }
  return value;
}
```

`TransactionSynchronizationManager`에는 현재 커넥션에서 사용되고 있는, 스레드를 ThreadLocal에 묶어서 `@Transactional`이 선언된 비즈니스 로직이 스레드 하나에 바인딩되어, 순차적으로 코드가 수행되고 성공 여부에 따라 커밋여부를 정합니다.

redisTemplate의 경우, 이러한 `TransactionSynchronizationManager`와 커넥션 스레드와 바인딩하여, 나머지 비즈니스 로직을 실행할 수 있는 장치를 마련해두었습니다.