# Spring KafkaTestConfig

`spring-kafka-test` 에서 제공하는 [리포지토리](https://github.com/spring-projects/spring-kafka/tree/main/spring-kafka-test/src/test)의 학습 테스트 코드를 분석하여 이론적으로만 파악하던 카프카를 좀 더 실무에 적용하기 쉽게 코드로 익혀보도록 하겠습니다.

```kotlin
@Configuration
@EnableKafka
class Config {

  @Volatile
  lateinit var received: String

  @Volatile
  lateinit var batchReceived: String

  @Volatile
  var error: Boolean = false

  @Volatile
  var batchError: Boolean = false

  val latch1 = CountDownLatch(1)

  val latch2 = CountDownLatch(1)

  val batchLatch1 = CountDownLatch(1)

  val batchLatch2 = CountDownLatch(1)

  @Value("\${" + EmbeddedKafkaBroker.SPRING_EMBEDDED_KAFKA_BROKERS + "}")
  private lateinit var brokerAddresses: String

  @Bean
  fun kpf(): ProducerFactory<String, String> {
    val configs = HashMap<String, Any>()
    configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.brokerAddresses
    configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    return DefaultKafkaProducerFactory(configs)
  }

  @Bean
  fun kcf(): ConsumerFactory<String, String> {
    val configs = HashMap<String, Any>()
    configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.brokerAddresses
    configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
    configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
    configs[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
    configs[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
    return DefaultKafkaConsumerFactory(configs)
  }

  @Bean
  fun kt(): KafkaTemplate<String, String> {
    return KafkaTemplate(kpf())
  }

  val eh = object: CommonErrorHandler {
    override fun handleOne(
      thrownException: Exception,
      record: ConsumerRecord<*, *>,
      consumer: Consumer<*, *>,
      container: MessageListenerContainer
    ): Boolean {
      error = true
      latch2.countDown()
      return true
    }

    override fun handleBatch(
      thrownException: Exception,
      recs: ConsumerRecords<*, *>,
      consumer: Consumer<*, *>,
      container: MessageListenerContainer,
      invokeListener: Runnable
    ) {
      if (!recs.isEmpty) {
        batchError = true;
        batchLatch2.countDown()
      }
    }
  }

  @Bean
  fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
    val factory: ConcurrentKafkaListenerContainerFactory<String, String>
      = ConcurrentKafkaListenerContainerFactory()
    factory.consumerFactory = kcf()
    factory.setCommonErrorHandler(eh)
    return factory
  }

  @Bean
  fun kafkaBatchListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
    val factory: ConcurrentKafkaListenerContainerFactory<String, String>
        = ConcurrentKafkaListenerContainerFactory()
    factory.isBatchListener = true
    factory.consumerFactory = kcf()
    factory.setCommonErrorHandler(eh)
    return factory
  }

  @KafkaListener(id = "kotlin", topics = ["kotlinTestTopic1"], containerFactory = "kafkaListenerContainerFactory")
  fun listen(value: String) {
    this.received = value
    this.latch1.countDown()
  }

  @KafkaListener(id = "kotlin-batch", topics = ["kotlinBatchTestTopic1"], containerFactory = "kafkaBatchListenerContainerFactory")
  fun batchListen(values: List<ConsumerRecord<String, String>>) {
    this.batchReceived = values.first().value()
    this.batchLatch1.countDown()
  }

  @Bean
  fun checkedEx(kafkaListenerContainerFactory : ConcurrentKafkaListenerContainerFactory<String, String>) :
      ConcurrentMessageListenerContainer<String, String> {

    val container = kafkaListenerContainerFactory.createContainer("kotlinTestTopic2")
    container.containerProperties.groupId = "checkedEx"
    container.containerProperties.messageListener = MessageListener<String, String> {
      if (it.value() == "fail") {
        throw Exception("checked")
      }
    }
    return container;
  }

  @Bean
  fun batchCheckedEx(kafkaBatchListenerContainerFactory :
             ConcurrentKafkaListenerContainerFactory<String, String>) :
      ConcurrentMessageListenerContainer<String, String> {

    val container = kafkaBatchListenerContainerFactory.createContainer("kotlinBatchTestTopic2")
    container.containerProperties.groupId = "batchCheckedEx"
    container.containerProperties.messageListener = BatchMessageListener<String, String> {
      if (it.first().value() == "fail") {
        throw Exception("checked")
      }
    }
    return container;
  }

}

```

### 1. 클래스 정의 및 애노테이션

```kotlin
@Configuration
@EnableKafka
class Config {...}
```

- `@Configuration` : `Config`라는 클래스가 Spring의 빈을 구성(Configuration)하는 클래스임을 나타냅니다. 이 클래스 내에서 정의된 메소드들은 Spring의 Bean으로 관리됩니다.
- `@EnableKafka` : Kafka 리스너를 활성화하기 위한 애노테이션입니다. Kafka 메시지 리스너 컨테이너 관리를 위해 필요합니다.

### 2. 변수 선언

```kotlin
@Volatile
lateinit var received: String
```

- `@Volatile`  메모리의 가시성을 보장함으로서 멀티스레드 환경에서 변수의 동시성 관리를 위해 사용됩니다. `lateinit var` 키워드는 변수가 나중에 초기화될 것임을 나타냅니다.

### 3. Embedded Kafka 브로커 주소

```kotlin
@Value("\${" + EmbeddedKafkaBroker.SPRING_EMBEDDED_KAFKA_BROKERS + "}")
private lateinit var brokerAddresses: String
```

- 이 라인은 `@Value` 어노테이션을 사용하여 임베디드 Kafka 브로커의 주소를 가져옵니다. 이 주소는 테스트 중인 Kafka 프로듀서 및 컨슈머의 설정에 사용됩니다.

### 4. 프로듀서 팩토리 설정

```kotlin
@Bean
fun kpf(): ProducerFactory<String, String> {
    val configs = HashMap<String, Any>()
    configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.brokerAddresses
    // ...
    return DefaultKafkaProducerFactory(configs)
}
```

- Kafka 프로듀서를 생성하기 위한 `ProducerFactory` 빈을 설정합니다. 이 메소드는 Kafka 프로듀서의 기본 설정을 정의합니다.

### 5. 컨슈머 팩토리 설정

```kotlin
@Bean
fun kcf(): ConsumerFactory<String, String> {
    val configs = HashMap<String, Any>()
    configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.brokerAddresses
    // ...
    return DefaultKafkaConsumerFactory(configs)
}
```

- Kafka 컨슈머를 생성하기 위한 `ConsumerFactory` 빈을 설정합니다. 이 메소드는 Kafka 컨슈머의 기본 설정을 정의합니다.

### 6. Kafka 템플릿 Bean

```kotlin
@Bean
fun kt(): KafkaTemplate<String, String> {
    return KafkaTemplate(kpf())
}
```

- `KafkaTemplate` 빈을 설정합니다. 이 빈은 Kafka 메시지를 보내는 데 사용됩니다.

### 7. 리스너 컨테이너 팩토리 설정

```kotlin
@Bean
fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
    val factory: ConcurrentKafkaListenerContainerFactory<String, String>
        = ConcurrentKafkaListenerContainerFactory()
    factory.consumerFactory = kcf()
    factory.setCommonErrorHandler(eh)
    return factory
}
```

- Kafka 리스너 컨테이너 팩토리를 설정합니다. 이 팩토리는 Kafka 메시지를 수신하는 리스너를 생성하는 데 사용됩니다.

### 8. Kafka 리스너 설정

```kotlin
@KafkaListener(id = "kotlin", topics = ["kotlinTestTopic1"], containerFactory = "kafkaListenerContainerFactory")
fun listen(value: String) {
    this.received = value
    this.latch1.countDown()
}
```

- 특정 Kafka 토픽에 대한 리스너를 정의합니다. 이 메소드는 메시지를 수신할 때마다 호출됩니다.

### 그외

- `latch` : 동시성 프로그래밍에서 사용되는 용어로, 여러 스레드가 특정 지점까지 도달할 때까지 대기하게 하는 동기화 메커니즘을 의미합니다. Java, Kotlin 진영에서는 `CountDownLatch`의 형태로 널리 상용됩니다.
- `CountDownLatch`: 주어진 횟수만큼 이벤트가 발생할 때까지 한 스레드 또는 여러 스레드가 대기하도록 명령하는 데 사용됩니다. latch는 초기 카운트로 시작하며, `countDown()` 메소드가 호출될 때마다 카운트가 감소합니다. 카운트가 0에 도달하면, latch에서 대기 중인 모든 스레드가 진행을 재개합니다.