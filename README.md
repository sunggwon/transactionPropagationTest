# transactionPropagationTest

@Transactional 전파속성을 테스트

테스트를 위한 테이블 구조는 다음과 같이 설계하였다.

![image](https://user-images.githubusercontent.com/17060341/171417101-9ef4238b-2e53-4d9a-9144-4ddf49700f99.png)

Test 코드에 다음의 전파 속성을 테스트한다.

- required
- requiredNew
- supports
  - 트랜잭션 안에서 호출
  - 트랜잭션이 아닌 상태에서 호출
- not supports
- mandatory
  - 트랜잭션 안에서 호출
  - 트랜잭션이 없는 곳에서 호출
- never
