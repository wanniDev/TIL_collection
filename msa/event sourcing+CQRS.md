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