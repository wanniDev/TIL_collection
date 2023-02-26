# 00. Schema

## 요약

1. 데이터베이스 소프트웨어의 기초
2. SQL 문법 기초
3. 스키마를 만들고 컨텐츠가 적절한지 확인하는 방법

## 일반적인 데이터베이스 구조

![img](https://leetcode.com/explore/learn/card/Figures/SQL/c1_database_structure.png)

### 1. Database Software

MySQL, Postgre SQL, MsSQL과 같은 데이터베이스 관리 소프트웨어 그 차체를 의미한다.

### 2. Database

### 3. Schema

### 4. Table

### 5. Data

데이터베이스에서 다루는 데이터 그 자체로서, 가장 중요한 부분이라 할 수 있다. 데이터의 종류의 따라 다르게 저장될 수 있다.

## Create Schema

```sql
CREATE SCHEMA `new_schema`
```

스키마를 만들때, 인코딩 설정이 필요할 수 있다. 왜냐면, 사람의 언어에 따라 사용되는 기호는 매우 다양하기 때문이다. 아래의 경우 가장 기본적인  4-Byte [UTF-8 Unicode Encoding](https://datatracker.ietf.org/doc/html/rfc3629) 인코딩 설정을 적용하도록 한다.

```sql
DEFAULT CHRACTER SET utf8mb4
```

참고로, `utf8mb4_unicode_ci` 인코딩 문자열도 있는데, 이는 이모지를 표현하는데 필요하다.

```sql
COLLATE utf8mb4_unicode_ci;
```

인코딩에 대해 더 알고 싶다면 [What is UTF-8 Encoding](https://blog.hubspot.com/website/what-is-utf-8)를 참고하라.

```sql
CREATE SCHEMA `new_schema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

