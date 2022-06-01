package com.example.transactionpropagationtest;

import com.example.transactionpropagationtest.controller.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionPropagationTestApplicationTests {

    @Autowired
    TestController controller;

    @Test
    @DisplayName("Required 테스트, 모든 테이블에 데이터 삽입 되지 않음")
    void required() {
       controller.buyTicketRequired("required", 7);
    }

    @Test
    @DisplayName("RequiredNew 테스트, buyticket 테이블에만 데이터 삽입")
    void requiredNew() {
        controller.buyTicketRequiredNew("requiredNew", 7);
    }

    @Test
    @DisplayName("외부 트랜잭션에서 Supports 테스트, Required 와 동일")
    void supportsInTransaction() {
        controller.buyTicketSupports("supportInTransaction", 7);
    }

    @Test
    @DisplayName("외부 트랜잭션이 아닌 상태에서 Supports 테스트, remainingtickets 테이블에 저장 되지 않음")
    void supportsNotTransaction() {
        controller.buyTicketSupportsNoTransaction("supportNotTransaction", 7);
    }

    @Test
    @DisplayName("NotSupported 테스트, 트랜잭션이 없는 것과 동일")
    void notSupported() {
        controller.buyTicketNotSupported("NotSupported", 7);
    }

    @Test
    @DisplayName("Mandatory 테스트, 트랜잭션이 있는 곳에서 호출시 정상 동작")
    void mandatoryInTransaction() {
      controller.buyTicketMandatoryInTransaction("mandatoryInTransaction", 3);
    }

    @Test
    @DisplayName("Mandatory 테스트, 트랜잭션이 없는 곳에서 호출하면 에러")
    void mandatoryNoTransaction() {
        controller.buyTicketMandatoryNoTransaction("mandatory", 3);
    }

    @Test
    @DisplayName("never 테스트, 트랜잭션 내에서 호출하면 에러")
    void never() {
        controller.buyTicketNever("never", 3);
    }

}
