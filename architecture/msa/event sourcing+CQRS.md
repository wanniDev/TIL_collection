# Event Sourcing

event sourcing에 대해 알려면 우선 event driven의 개념에 대해 어느정도는 알고 있어야 합니다.

**Event Sourcing이란?**

- 데이터의 마지막 상태만 저장하는 것이 아닌, 해당 데이터에 수행된 전체 이력을 기록
- 데이터 구조는 단순
- 데이터의 일관성과 트랜잭션 처리 가능
- 데이터 저장소(event store)의 개체를 직접 업데이트하지 않기 때문에, 동시성에 대한 충동 문제 해결

**event sourcing 특징1: 도메인 주도 설계**

- Aggregate
- Projection

**event sour  특징2: 메시지 중심의 비동기 작업 처리**

**단점**

- 모든 이벤트에 대한 복원 -> 스냅샷 도입
  - 예를들어, 1번부터 100번까지의 트랜잭션만 가지고 있고, 101번부터 200번까지의 트랜잭션만 갖고 있는 식으로 데이터를 잘라서 저장하는 방식
- 다양한 데이터 조회 -> CQRS

## CQRS (Command and Query Responsivility Segregation)

명령과 조회의 책임 분리

- 상태를 변경하는 Command
- 조회를 담당하는 Query

## Saga pattern

- Application에서 Transaction 처리
  - Choreography, Orchestration
- Application이 분리된 경우에는 각각의 Local transaction만 처리
- 각 App에 대한 연속적인 Transaction에서 실패할 경우
  - Rollback 처리 구현 -> 보상 Transaction
- 데이터의 원자성을 보장하지는 않지만, 일관성을 보장

**Choreography-based saga**

1. 주문 서비스에서 주문 요청(POST)을 수신하고 PENDING 상태의 주문 생성
2. 주문 생성 미벤트 전달
3. 고객 서비스의 Event handler가 Credit 예약 시도
4. 결과 이벤트 전달
5. 주문 서비스의 Event handler를 통해 주문 승인 or 거부

**Orchestration-based saga**

1. 주문 서비스 주문 요청(POST)을 수신하고 Create Order saga orchestrator 생성
2. Order saga orchestrator가 PENDING 상태의 주문 생성
3. Credit 예약 명령을 고객 서비스에 전달
4. 고객 서비스가 Credit 예약 처리
5. 결과 메시지 전달
6. Order saga orchestrator에서 주문의 승인 or 거부

두 접근 방식은 각각의 장단점을 가지고 있습니다. Choreography는 시스템을 더 유연하고 확장 가능하게 만들어주지만, 복잡한 비즈니스 로직에서는 전체 트랜잭션 흐름을 추적하기 어려울 수 있습니다. 반면, Orchestration은 흐름을 명확하게 이해하고 관리할 수 있지만, 중앙 집중화된 오케스트레이터가 병목이나 단일 실패 지점이 될 수 있습니다.

프로젝트의 요구 사항과 복잡도, 팀의 경험 등을 고려하여 가장 적합한 방식을 선택하는 것이 중요합니다.