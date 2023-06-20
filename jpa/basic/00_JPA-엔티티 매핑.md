# JPA 엔티티 맵핑

### @Entity

- "엔티티"는 객체 세상에서 부르는 이름
- 보통 클래스와 같은 이름을 사용하기 때문에 값을 변경하지 않음
- 엔티티의 이름은 JQL에서 쓰임

### @Table

- "릴레이션" 세상에서 부르는 이름
- @Entity의 이름이 기본값
- 테이블의 이름은 SQL에서 쓰임

### @Id

- 엔티티의 주키를 매핑할 때 사용
- 자바의 모든 primitive 타입과 그 랩퍼 타입을 사용할 수 있음
  - Date랑 BigDecimal, BigInteger도 사용 가능

### @GenerateValue

- 주키의 생성 방법을 맵핑하는 애노테이션
- 생성 전략과 생성기를 설정할 수 있다.
  - 기본 전략은 AUTO: 사용하는 DB에 따라 적절한 전략 선택
  - TABLE, SEQUENCE, IDENTITY 중 하나

### @Column

- unique
- nullable
- length
- columnDefinition
- ...

### @Temporal

- 현재 JPA 2.1까지는 Date와 Calendar 밖에 지원을 안함

### @Transient

- 컬럼으로 매핑하고 싶지 않은 멤버 변수