### 엔티티 타입과 Value 타입

- 식별자가 있어야 하는가? 엔티티 타입 : value 타입
- 독립적으로 존재해야 하는가? 엔티티 타입 : value 타입

### Value 타입 종류

- 기본 타입 (String, Date, Boolean, ...)
- Conposite Value 타입
- Collection Value 타입
  - 기본 타입의 컬렉션
  - 컴포짓 타입의 콜렉션

### Composite Value 타입 매핑

- `@Embadable`
- `@Embadded`
- `@AttributeOverrides`
- `@AttributeOverride`

```java
@Embaddable
public class Address {
  private String street;
  private String city;
  private String state;
  private String zipCode;
}
```

> Account

```java
@Embadded
@AttributeOverrides(
  {
    name = "street",
    column = @Column(name = "home_street")
  })
private Address address;
```

