# Kotlin, Webflux

## **구성**

1. kotlin coroutine
2. reactive programming
3. spring webflux

## **주요 키워드**

- **Kotlin**: Coroutines, Flow
- **Reactive Programming**: Mono, Flux
- **Spring WebFlux**: RouterFunction, HandlerFunction, WebFilter
- **R2DBC**: DatabaseClient, CoroutineRepository
- **API**: REST, JWT, OAuth2
- **Testing**: WebTestClient, MockK
- **DevOps**: Docker, Kubernetes, Jenkins

## **Step1: kotlin coroutine**

coroutine은 비동기 프로그래밍을 위한 kotlin이 가진 특징 중 하나로, '경량 스레드'로도 알려져 있습니다. couroutine은 비동기 task를 간결하고 이해하기 쉬운 동기 스타일의 코드로 작성할 수 있도록 돕습니다.

**coroutine vs threads**

- **경량 :** coroutine은 훨씬 적은 메모리를 사용하고, 수만개의 coroutine을 동시에 실행하는 것이 가능합니다.
- **비용 :** Thread 작업에 비해 Coroutine은 컨텍스트 스위칭 비용이 낮습니다.

**주요 컴포넌트**

- **suspend 함수 :** 다른 coroutines에게 실행을 양보할 수 있는 함수입니다. coroutine 내에서만 호출할 수 있습니다.
- **CoroutineScope :** Coroutine의 실행 영역을 정의합니다. 일반적으로 Coroutine은 어떤 Scope 내에서 실행되며, 그 Scope는 Coroutine의 생명주기를 관리합니다.
- **CoroutineContext :** Coroutine의 실행 환경을 나타냅니다. (예: Dispatcher를 통해 Coroutine이 어떤 스레드에서 실행될지 결정)

**Dispatcher & Thread**

- Dispatcher :

   Coroutine이 어떤 스레드에서 동작할지를 결정한다.

  - `Dispatchers.Main` : UI와 관련된 작업에 사용
  - `Dispatchers.IO` : I/O 작업에 사용 (네트워크 호출, 디스크 읽기/쓰기 등)
  - `Dispatchers.Default`: CPU 집약적 작업에 사용
  - `Dispatchers.Unconfined` : 즉시 실행하되, 첫 번째 `suspend` 점에서 호출자에게 양보

**Flow**

- **Flow**: 비동기적으로 여러 값(value)을 전달할 수 있는 Coroutine의 기능으로, ReactiveX의 Observable과 유사합니다.
- **cold stream**: Flow는 값이 요청(request)될 때까지 코드를 실행하지 않는 "cold" 스트림입니다.

**coroutine example**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    // 'launch' coroutine의 작업이 완료될 때까지 기다림
}
```

여기서 `runBlocking`은 주로 메인 함수 또는 테스트에서 사용됩니다. `launch`는 내부에서 coroutine을 생성해서 병렬 실행을 가능합니다. `delay` 함수는 `suspend` 함수로 코루틴이 잠시 대기하게 만들지만, 스레드를 차단(block) 하지 않습니다.

### **coroutine tutorial 1: Suspend Functions**

```kotlin
import kotlinx.coroutines.*

suspend fun simpleSuspendFunction(): Int {
    delay(1000L)  // 비동기로 1초 대기
    return 42
}

fun main() = runBlocking {
    val result = simpleSuspendFunction()
    println("Result: $result")
}
```

- 위에서 정의했듯이, suspend 함수는 대기중인 작업이 있을 때 스레드를 차단하지 않고 코드의 실행을 일시중지합니다. 이렇게 활용하면 리소스를 좀 더 효율적으로 활용할 수 있습니다.
- WebFlux의 `Mono`와 `Flux`를 사용하여 이와 유사한 비동기 동작을 수행할 수 있씁니다.

### **coroutine tutorial 2: Coroutine Builders - launch, async, runBlocking**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    // launch
    val job = launch {
        delay(500L)
        println("Task from launch")
    }

    // async
    val deferred = async {
        delay(1000L)
        return@async "Task from async"
    }

    println("Waiting for tasks...")
    println("Async result: ${deferred.await()}")
    job.join()
}
```

- `launch`는 결과 값을 반환하지 않는 작업을 시작합니다.
- `async`는 `Deferred<T>`를 반환하여 나중에 결과를 받을 수 있게 합니다.
- `runBlocking`은 현재 스레드를 차단하며 주로 테스트나 메인 함수에서 사용됩니다.
- WebFlux에서는 컨트롤러에서 리액티브 스트림을 반환하고, Spring이 이를 처리합니다. Coroutine을 사용하면 suspend 함수를 직접 반환할 수 있습니다.

### **coroutine tutorial 3: Flows**

```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(500L)
        emit(i)
    }
}

fun main() = runBlocking {
    simpleFlow().collect { value ->
        println("Collected: $value")
    }
}
```

- Flows는 여러 값을 비동기적으로 나타내는 방법을 이야기 합니다.
- Coroutines의 대응되는 스트림 개념으로, 여러 값을 시간에 걸쳐 비동기적으로 전달합니다.
- WebFlux의 `Flux`와 유사한 역할을 합니다.

### **coroutine tutorial 4: Coroutine Context and Dispatchers**

```kotlin
import kotlinx.coroutines.*

suspend fun ioTask() = withContext(Dispatchers.IO) {
    println("IO task on thread: ${Thread.currentThread().name}")
}

suspend fun defaultTask() = withContext(Dispatchers.Default) {
    println("Default task on thread: ${Thread.currentThread().name}")
}

suspend fun mainTask() = withContext(Dispatchers.Main) {
    println("Main task on thread: ${Thread.currentThread().name}")
}

fun main() = runBlocking {
    ioTask()
    defaultTask()
    // mainTask()  // 주의: 콘솔 애플리케이션에서는 Dispatchers.Main을 사용할 수 없습니다.
}
```

- Coroutine Context와 Dispatchers는 코루틴이 어디서 실행될지를 결정하는 컨텍스트를 이야기 합니다.
- 코루틴의 동작을 제어하는데 활용됩니다. 예를 들어, IO 작업을 위한 디스패처, 메인 스레드에서 UI를 업데이트하기 위한 디스패처 등이 있습니다.
- WebFlux에서는 스케줄러를 통해 리액티브 작업을 조절합니다. Coroutine의 디스패처와 유사한 역할을 합니다.

## **Step2: Reactive Programming**

Reactive Programming은 데이터의 흐름과 변경에 기반한 프로그래밍 패러다임입니다. 해당 패러다임을 이해하는데 필요한 핵심 개념은 다음과 같습니다.

1. Observable (관찰 가능한 객체)
   - 데이터 스트림을 생성하고, 이 스트림에 항목을 push하는 데이터 소스입니다.
   - Observable은 0개, 1개 또는 여러 항목을 비동기적으로 방출할 수 있습니다.
   - 예시로는 마우스 이벤트, 웹 요청, 컬렉션 변경 등이 있습니다.
2. Observer (관찰자)
   - Observable을 구독하고, Observable이 방출하는 항목들을 수신하여 처리하는 엔터티입니다.
   - Observable이 방출하는 데이터를 받아서 특정 로직을 수행합니다.
3. Subscription (구독)
   - Observable과 Observer 사이의 연결입니다.
   - Observer가 Observable을 구독할 때 생성되며, 이를 통해 데이터 스트림을 수신하게 됩니다.
   - 구독을 중지하면 해당 Observer는 더 이상 데이터를 수신하지 않습니다.
4. Operators (연산자)
   - 데이터 스트림을 변환, 필터링, 결합하는 데 사용되는 함수입니다.
   - `map`, `filter`, `merge`와 같은 다양한 연산자가 있습니다.
   - 연산자를 이용하여 복잡한 데이터 변환 및 조작 작업을 비동기적으로 수행할 수 있습니다.
5. Schedulers (스케줄러)
   - Observable의 항목 방출이나 Observer의 처리 로직이 실행되는 스레드를 제어합니다.
   - UI 작업, 백그라운드 작업, IO 작업 등 다양한 스레드에서 작업을 수행할 수 있게 됩니다.

### **Reactive Programming의 장점:**

1. **비동기 처리의 간소화**: Reactive Programming을 사용하면 복잡한 비동기 작업을 더욱 쉽게 표현할 수 있습니다.
2. **scalable(확장성)**: 리액티브 시스템은 요청의 수가 증가하더라도 잘 확장될 수 있습니다.
3. **reactive(반응형)**: 데이터나 이벤트의 변화에 실시간으로 반응할 수 있습니다.
4. **robust(튼튼한) 에러 핸들링**: 연산자를 통해 에러를 쉽게 처리하거나 복구하는 메커니즘이 있습니다.

### **Reactive Programming의 단점:**

1. **높은 러닝 커브**: 전통적인 명령형 프로그래밍과는 다르므로 초기 학습이 어려울 수 있습니다.
2. **디버깅의 어려움**: 비동기 스트림은 디버깅하기 복잡할 수 있습니다.

'Reactive Programming'은 주로 복잡한 이벤트 기반 시스템, 대용량 데이터 처리, 웹 애플리케이션과 같이 비동기 처리가 많이 필요한 경우에 유용합니다.

위 개념의 이해를 돕기위해 항목별로 튜토리얼 코드를 정리하겠습니다. 본 예시는 Reactive Programming에서 널리 사용되는 RxJava 라이브러리를 활용했습니다.

### r**eactive tutorials 1: Observable**

```kotlin
import io.reactivex.Observable

fun main() {
    val observable: Observable<String> = Observable.create { emitter ->
        emitter.onNext("Hello")
        emitter.onNext("Reactive")
        emitter.onNext("World!")
        emitter.onComplete()
    }

    observable.subscribe { item -> println(item) }
}
```

### r**eactive tutorials 2: Observer**

```kotlin
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun main() {
  val observable = Observable.just("Hello", "Reacitve", "World!")

  val observer: Observer<String> = object : Observer<String> {
    override fun onComplete() {
      println("All items received!")
    }

    override fun onSubscribe(d: Diposable) {}

    override fun onNext(item: String) {
      println(item)
    }

    override fun onError(e: Throwable) {
      println("Error received: ${e.message}")
    }
  }

  observable.subscribe(observer)
}
```

### r**eactive tutorials 3: Subscription**

```kotlin
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

fun main() {
    val observable = Observable.just("Hello", "Reactive", "World!")

    val subscription: Disposable = observable.subscribe(
        { item -> println(item) },
        { error -> println("Error: ${error.message}") },
        { println("Done!") }
    )

    // If needed, you can dispose the subscription to stop receiving items.
    // subscription.dispose()
}
```

### r**eactive tutorials 4: Operators**

```kotlin
import io.reactivex.Observable

fun main() {
    val observable = Observable.just(1, 2, 3, 4, 5)

    observable
        .filter { it % 2 == 0 }  // Filters out odd numbers
        .map { it * it }         // Maps items to their squares
        .subscribe { item -> println(item) }
}
```

### r**eactive tutorials 5: Schedulers**

```kotlin
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    val observable = Observable.create<String> { emitter ->
        println("Observable thread: ${Thread.currentThread().name}")
        emitter.onNext("Hello")
        emitter.onComplete()
    }

    observable
        .subscribeOn(Schedulers.io())  // Define which thread to run the Observable on
        .observeOn(Schedulers.computation())  // Define which thread the subscriber should run on
        .subscribe {
            println("Subscriber thread: ${Thread.currentThread().name}")
            println(it)
        }

    Thread.sleep(1000)  // Give it some time for async operations to complete
}
```

## **Step3: Spring webflux**

spring webflux는 reactive programming 모델을 지원하는 웹 프레임워크입니다. 그렇기 때문에 webFlux는 대용량 처리 및 고성능 웹 애플리케이션을 위한 선택이 될 수 있습니다. spring webflux를 이해하기 위해선 다음과 같은 핵심 개념을 이해해야 합니다.

1. Reactive Streams

   - Reactive Streams는 데이터 항목들의 비동기 스트림을 표현하는 표준입니다.
   - `Publisher`, `Subscriber`, `Subscription`, `Processor`라는 네 가지 주요 인터페이스로 구성됩니다.
   - Spring WebFlux는 이러한 Reactive Streams를 구현하기 위해 Project Reactor를 사용합니다.

2. Project Reactor

   - Spring WebFlux에서 사용하는 리액티브 라이브러리입니다.

   - ```
     Mono
     ```

     와 

     ```
     Flux
     ```

     라는 두 가지 주요 리액티브 타입을 제공합니다.

     - `Mono`: 0 또는 1개의 항목을 방출하는 스트림을 표현합니다.
     - `Flux`: 여러 항목을 방출하는 스트림을 표현합니다.

3. Non-blocking Runtime

   - WebFlux는 전통적인 서블릿 기반의 Spring MVC와 달리, 넌블로킹 런타임 환경에서 실행됩니다.
   - Netty, Undertow, Servlet 3.1+ 컨테이너 등에서 동작할 수 있습니다.

4. Model-View-Controller Pattern

   - Spring MVC와 유사하게, WebFlux도 MVC 패턴을 따르며 `@Controller`, `@RequestMapping` 등의 애노테이션을 사용할 수 있습니다.
   - 차이점이 있다면 WebFlux 컨트롤러는 `Mono`와 `Flux` 타입을 반환할 수 있습니다.

5. Router Function

   - 함수형 스타일로 라우팅과 핸들링을 정의할 수 있습니다.
   - 선언적인 방식으로 요청 매핑과 처리를 할 수 있어, 더 유연하고 조합하기 쉽습니다.

### **Spring WebFlux의 장점:**

1. **비동기 및 넌블로킹**: WebFlux는 비동기적인 방식으로 요청을 처리하기 때문에, 대규모 동시 요청에도 높은 처리능력을 보여줄 수 있습니다.
2. **Scalability (확장성)**: WebFlux는 리액티브 스트림을 사용하여 백 프레셔 (back-pressure)를 제어하고, 자원을 효율적으로 활용하므로, 시스템 확장성이 향상됩니다.
3. **Flexible Data Handling**: 대용량 데이터 스트림 처리, 서버-센트 이벤트, WebSocket과 같은 유스케이스를 쉽게 처리할 수 있습니다.

### **Spring WebFlux의 단점:**

1. **학습 곡선**: 전통적인 Spring MVC와는 다르므로, 초기 학습이 어려울 수 있습니다.
2. **디버깅의 어려움**: 비동기 및 리액티브 코드는 디버깅하기 복잡할 수 있습니다.

Spring WebFlux는 비동기적이며 리액티브한 웹 애플리케이션의 개발에 최적화되어 있습니다. 그러나 모든 애플리케이션에서 WebFlux를 사용할 필요는 없으며, 특히 I/O 바운드 작업이 많은 경우나 리액티브 프로그래밍의 장점을 활용할 수 있는 상황에서 선택하는 것이 좋습니다.

### **tutorial 1: Reactive Streams**

Project Reactor의 Mono와 Flux를 사용하였습니다.

```kotlin
@RestController
class ReactiveController {
  // Mono 예시
  @GetMapping("/mono")
  fun getMono(): Mono<String> {
    return Mono.just("Hello from Mono!")
  }

  // Flux 예시
  @GetMapping("/flux")
  fun getFlux(): Flux<String> {
    return Flux.just("Hello", "from", "Flux!")
  }
}
```

### **tutorial 2: Non-blocking Runtime (WebClient 사용)**

WebClient는 non-blocking, reactive 웹 클라이언트로써 WebFlux 환경에서 RESTful 서비스를 호출하는 데 사용됩니다.

```kotlin
@Service
class WebClientService(val webClientBuilder: WebClient.Builder) {

    private val client = webClientBuilder.baseUrl("<https://api.example.com>").build()

    fun fetchSomething(): Mono<String> {
        return client.get()
            .uri("/some-endpoint")
            .retrieve()
            .bodyToMono(String::class.java)
    }
}
```

### **tutorial 3: Model-View-Controller Pattern**

```kotlin
@RestController
@RequestMapping("/users")
class UserController(val userRepository: UserRepository) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): Mono<User> {
        return userRepository.findById(id)
    }

    @PostMapping("/")
    fun createUser(@RequestBody user: User): Mono<User> {
        return userRepository.save(user)
    }
}
```

### **tutorial 4: Router Functions**

```kotlin
@Configuration
class RouterConfig {

    @Bean
    fun route(userHandler: UserHandler): RouterFunction<ServerResponse> {
        return RouterFunctions.route()
            .GET("/api/users/{id}", userHandler::getUser)
            .POST("/api/users", userHandler::createUser)
            .build()
    }
}

@Component
class UserHandler(val userRepository: UserRepository) {

    fun getUser(req: ServerRequest): Mono<ServerResponse> {
        val userId = req.pathVariable("id")
        return userRepository.findById(userId)
            .flatMap { user -> ServerResponse.ok().bodyValue(user) }
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun createUser(req: ServerRequest): Mono<ServerResponse> {
        val user = req.bodyToMono(User::class.java)
        return userRepository.save(user)
            .flatMap { u -> ServerResponse.ok().bodyValue(u) }
    }
}
```