# Spring Data JPA 소개

`JpaRespository<Entity, Id>` 인터페이스

- 매직 인터페이스
- `@Repository`가 없어도 빈으로 등록해줌.

`@EnableJpaRepository`

- spring boot 를 사용하지 않았다면, `@Configuration` 애노테이션이 붙은 클래스 위에 `@EnableJpaRepository`를 붙여야한다.
- 매직의 시작

매직은 어떻게 이뤄지나?

- 시작은 `@Import(JpaRepositoriesRegistart.class)`
- 핵심은 `ImportBeanDefinitionRegistart` 인터페이스

## DeepDive를 해보고 싶다?

- `@EnableJpaRepositories`
- `JpaRepositoriesRegistrar.class`
- `RepositoryBeanDefinitionRegistrarSupport.class`
- `ImportBeanDefinitionRegistrar.class`
- `ImportBeanDefinitionRegistrar.registerBeanDefinitions`
  - `delegate.registerRepositoriesIn(registry, extension)`
    - `RepositoryConfiguration.class`
    - `definitions.add(...)`