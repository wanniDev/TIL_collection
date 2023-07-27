# Primitive 타입과 Reference 타입

**Primitive 타입**: 기본적인 데이터 타입으로 정수, 실수, 논리 값 등을 나타냅니다.

**Reference 타입**: 객체를 나타내는 타입으로 객체의 메모리 주소값을 저장합니다.

이 둘의 차이점은 메모리의 저장 위치와 크기 입니다. 전자의 경우, 크기는 고정되어 있고 메모리의 stack에 저장되며, 후자의 경우 객체의 크기에 따라 가변적이며, Heap에 저장됩니다.

### Reference 타입 메모리 관리 방식

Reference 타입의 변수에는 해당 객체의 주소값이 저장됩니다. 이 주소값은 해당 객체의 메모리 위치를 가리키고 변수는 해당 객체를 참조하게 됩니다.

따라서, 객체의 내부의 값을 변경하면 해당 객체를 참조하는 변수에 영향을 미칩니다. 이는 객체를 공유하고 있는 모든 변수에 영향을 끼칩니다.

## 부록: Primitive vs Reference Data Types in JavaScript

> https://www.freecodecamp.org/news/primitive-vs-reference-data-types-in-javascript/

