

## Cascade, 엔티티의 상태 변화를 전파 시키는 옵션.

### 엔티티의 상태

- **Transient** : JPA가 모르는 상태
- **Persistent** : JPA가 관리중인 상태
- **Detached** : JPA가 더이상 관리하지 않은 상태
- **Removed** : JPA가 관리하긴 하지만 삭제하기로 한 상태

## Session에 따른 엔티티 상태변화

- **Transient**

  ```java
  new Object();
  ```

- **Transient -> Persitent**

  ```java
  Session.save()
  ```

- **Persistent**

  ```java
  Session.get();
  Session.load();
  Query.iterate();
  ...
  ```

- **Persistent -> Removed**

  ```java
  Session.delete()
  ```

- **Persistent -> Detached**

  ```java
  Session.evict();
  Session.clear();
  Session.close();
  ```

- **Detached -> Persistent**

  ```java
  Session.update();
  Session.merge();
  Session.saveOrUpdate();
  ```

## Dirty Checking

- 변경 감지라고도 하고, 영속성 컨텍스트가 상태의 변화를 검사하는 행위를 이야기 한다.

- JPA는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영해준다.

- 당연히 영속성 컨텍스트가 관리하는 엔티티에만 적용된다. 따라서 detached(준영속), Transient(비영속) 상태에서는 이러한 변경 감지를 하지 않는다.

## write behind

- 쓰기지연이라고도 한다.
- Transactional 블록 안에서 객체를 한번 persist 하고 flush를 하더라도 해당 객체와 동일한 snapshot 객체는 여전히 영속성 컨텍스트안에 존재한다.
- 즉, JPA는 영속성 상태의 변화에 따라 바로 쿼리를 날리지 않고 이 스냅샷 객체와 비교하면서, Transactional 블록이 끝나는 시점에 최종 쿼리를 날린다.

