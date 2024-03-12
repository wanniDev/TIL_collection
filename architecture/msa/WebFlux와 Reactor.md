# WebFlux와 Reactor

WebFlux의 리액티브 프로그래밍은 기존의 Spring MVC의 전통적인 명령형 프로그래밍과는 다른 패러다임을 갖기 때문에 코드 스타일과 구조가 다소 다릅니다. 따라서, db 트랜잭션과 연동하는 비즈니스 로직을 구성할 때는 리액티브 프로그래밍에서 사용되는 스레딩 모델에 대해 이해하는 것이 중요합니다.

**1. Reactor 스레드**

WebFlux의 백본인 Reactor는 주로 두 가지 유형의 스레드로 작동합니다.

- **`eventLoop`**  : I/O 작업에 최적화되어 있으며, 기본적으로 비동기로 동작합니다.
- **`boundedElastic`** : 블로킹 작업을 수행하기 위해 디자인되었으며, 블로킹 작업을 수행하는 동안 이벤트 루프가 차단되지 않도록 합니다.

**2. R2DBC 스레드**

- R2DBC는 리액티브 프로그래밍을 위한 데이터베이스 드라이버입니다. 이것은 기본적으로 비동기 방식으로 작동하며, 따라서 주로 `eventLoop` 스레드에서 실행됩니다.

## event loop

WebFlux에서 자주 언급되는 Reactor는 리액티브 프로그래밍을 위한 Java 라이브러리입니다. Reactor는 리액티브 스트림 명세를 구현하며, 주로 `Flux`와 `Mono`가 있습니다. Reactor가 내부적으로 사용하는 스레드 모델을 이해하려면, Reactor의 이벤트 루프와 기존의 이벤트 루프 기판 시스템(Node.js)과의 차이를 알아야 합니다.

1. **기존의 이벤트 루프 : **Node.js는 단일 스레드 기반의 이벤트 루프를 사용합니다. 이는 요청 처리, IO 작업, 타이머 등 모든 작업이 하나의 스레드에서 처리된다는 것을 의미합니다. 비동기 IO 작업이 시작되면, 이벤트 루프는 다른 작업을 계속 처리하다가 IO 작업이 완료되면 해당 작업의 콜백을 실행합니다.

   ```
      ┌───────────────────────────┐
   ┌─>│           timers          │
   │  └─────────────┬─────────────┘
   │  ┌─────────────┴─────────────┐
   │  │     pending callbacks     │
   │  └─────────────┬─────────────┘
   │  ┌─────────────┴─────────────┐
   │  │       idle, prepare       │
   │  └─────────────┬─────────────┘      ┌───────────────┐
   │  ┌─────────────┴─────────────┐      │   incoming:   │
   │  │           poll            │<─────┤  connections, │
   │  └─────────────┬─────────────┘      │   data, etc.  │
   │  ┌─────────────┴─────────────┐      └───────────────┘
   │  │           check           │
   │  └─────────────┬─────────────┘
   │  ┌─────────────┴─────────────┐
   └──┤      close callbacks      │
      └───────────────────────────┘
      
   * 각각의 사각 박스는 eventLoop에서 "phase"라고 부릅니다.
   
   - timers: this phase executes callbacks scheduled by setTimeout() and setInterval().
   - pending callbacks: executes I/O callbacks deferred to the next loop iteration.
   - idle, prepare: only used internally.
   - poll: retrieve new I/O events; execute I/O related callbacks (almost all with the exception of close callbacks, the ones 
   - scheduled by timers, and setImmediate()); node will block here when appropriate.
   - check: setImmediate() callbacks are invoked here.
   - close callbacks: some close callbacks, e.g. socket.on('close', ...).
   
   출처: https://nodejs.org/en/docs/guides/event-loop-timers-and-nexttick
   ```

2. Reactor의 스레드 모델: Reactor는 여러 이벤트 루프 스레드를 관리하는 `Schedulers`를 사용합니다. 이는 Reactor가 멀티 스레드 환경에서 동작할 수 있다는 것을 의미합니다.  주요 `Schedulers`는 다음과 같습니다.

   - `Schedulers.parallel()`: CPU 코어 수에 맞춰 고정된 수의 스레드를 가진 스케줄러입니다. CPU-bound 작업에 적합합니다.
   - `Schedulers.elastic()`: 필요에 따라 스레드를 생성하고, 필요 없어지면 제거하는 스케줄러입니다. I/O-bound 작업에 적합합니다.
   - `Schedulers.boundedElastic()`: `elastic`과 유사하지만, 동시에 생성할 수 있는 스레드 수에 상한이 있습니다.
   - `Schedulers.single()`: 단일 스레드를 사용하는 스케줄러입니다.
   - `Schedulers.immediate()`: 현재 스레드에서 작업을 실행합니다.

WebFlux는 Netty라고 하는 이벤트 루프 기반의 네트워크 프레임워크위에서 동작합니다. 따라서 대부분의 요청은 Netty의 이벤트 루프 스레드에서 처리되며, 위에서 언급했던 스케줄러를 `.subscribeOn()`와 `.publishOn()`를 통해 지정하지 않는다면 이러한 이벤트 루프 스레드에서 작업을 수행하게 됩니다.

![image](https://github.com/f-lab-edu/payment-lab/assets/81374655/685e5bae-46d2-4097-8a1c-43154b0ed653)

netty에서 이벤트루프는 아래와 같이 관리합니다.

![image](https://github.com/f-lab-edu/payment-lab/assets/81374655/7696df2a-ea58-4414-9b52-a27f3b1809a4)

## boundedElastic

`boundedElastic` 스케줄러는 잠재적으로 블로킹되는 작업을 처리하기 위해 설계되었으며, 여러 스레드를 사용하여 병렬 작업을 처리하면서도 자원의 과도한 사용을 제한하기 위해 최적화되어 있습니다. 해당 스케줄러의 주요특징은 다음과 같습니다.

- **다중 스레드 : **`boundedElastic`은 단일 스레드가 아니라 여러 스레드로 구성된 스레드 풀을 사용합니다. 따라서 여러 개의 병렬 작업을 동시에 처리할 수 있습니다.
- **유동적인 스레드 수 : **`boundedElastic`의 스레드 풀은 요청에 따라 스레드 수가 유동적으로 변경될 수 있습니다. 즉, 요청이 많아질 때는 스레드 수가 증가하고, 요청이 줄어들면 스레드 수가 감소합니다.
- **블로킹 작업을 위한 최적화 : **`boundedElastic`은 I/O 또는 잠재적으로 블로킹 되는 작업(db 호출, 파일 IO, 네트워크 호출)을 처리하는 데에 최적화 되어있습니다. 이러한 작업들은 리액티브 스트림의 메인 스레드에서 실행될 경우 전체 시스템의 반응성을 저하시킬 수 있기 때문에 별도의 스레드에서 처리되어야 합니다.
- **제한된 자원 : **`boundedElastic`이라는 네이밍에서 알 수 있듯이 "제한된(bounded)" 자원을 사용합니다. 이는 특정한 수의 스레드만 사용하여 자원을 과도하게 사용하는 것을 방지합니다.

**boundedElastic의 동작**

1. 필요할 때마다 새로운 스레드 생성.
2. 각 스레드는 task 큐에서 task를 가져와 처리합니다.
3. task가 완료되면 해당 스레드는 다시 task 큐로 돌아와 다음 작업을 기다립니다.
4. 만약 스레드가 일정 시간 동안 사용되지 않으면, 그 스레드는 종료됩니다.

