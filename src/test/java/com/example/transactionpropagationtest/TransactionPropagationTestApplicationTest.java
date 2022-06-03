package com.example.transactionpropagationtest;

import com.example.transactionpropagationtest.controller.TestController;
import com.example.transactionpropagationtest.mapper.BuyerMapper;
import com.example.transactionpropagationtest.mapper.SellerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionPropagationTestApplicationTest {

    @Autowired
    TestController controller;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    BuyerMapper buyerMapper;

    @BeforeEach
    void before() {
        buyerMapper.deleteAllRows();
        sellerMapper.deleteAllRows();
    }

    @Test
    @DisplayName("Required 테스트, 모든 테이블에 데이터 삽입 되지 않음")
    void required() {
        //given
        String name = "required";
        int count = 7; // 내부 트랜잭션에서 remaining 테이블 삽입 에러 발생

        //when
        assertThrows(UnexpectedRollbackException.class,
                ()->controller.buyTicketRequired(name, count));

        //then
        assertEquals(buyerMapper.getBuyTicket().size(), 0);
        assertEquals(sellerMapper.getRemainingTicket().size(), 0);
    }

    @Test
    @DisplayName("RequiredNew 테스트, buyticket 테이블에만 데이터 삽입")
    void requiredNew() {
        //given
        String name = "requiredNew";
        int count = 7; // 내부 트랜잭션에서 remaining 테이블 삽입 에러 발생

        //when
        controller.buyTicketRequiredNew(name, count);

        //then
        var list = buyerMapper.getBuyTicket();
        var before = list.get(0);
        var after = list.get(1);
        assertEquals(name, before.getName());
        assertEquals(count, before.getCount());
        assertEquals(name + "_after", after.getName());
        assertEquals(count, after.getCount());
        assertEquals(0, sellerMapper.getRemainingTicket().size());
    }

    @Test
    @DisplayName("외부 트랜잭션에서 Supports 테스트, Required 와 동일")
    void supportsInTransaction() {
        //given
        String name = "requiredNew";
        int count = 7; // 내부 트랜잭션에서 remaining 테이블 삽입 에러 발생

        //when
        assertThrows(UnexpectedRollbackException.class,
                ()->controller.buyTicketSupports(name, count));

        //then
        assertEquals(0,buyerMapper.getBuyTicket().size());
        assertEquals(0,sellerMapper.getRemainingTicket().size());
    }

    @Test
    @DisplayName("외부 트랜잭션이 아닌 상태에서 Supports 테스트, remainingtickets 테이블에 저장 되지 않음")
    void supportsNotTransaction() {
        //given
        String name = "supportNotTransaction";
        int count = 7; // 내부 트랜잭션에서 remaining 테이블 삽입 에러 발생

        //when
        controller.buyTicketSupportsNoTransaction(name, count);

        //then
        var list = buyerMapper.getBuyTicket();
        var before = list.get(0);
        var after = list.get(1);
        assertEquals(name, before.getName());
        assertEquals(count, before.getCount());
        assertEquals(name + "_after", after.getName());
        assertEquals(count, after.getCount());
        assertEquals(0, sellerMapper.getRemainingTicket().size());
    }

    @Test
    @DisplayName("NotSupported 테스트, 부모 트랜잭션이 없는 경우, 트랜잭션이 없는 것과 동일")
    void notSupportedNoTransaction() {
        //given
        String name = "NotSupportedNoTransaction";
        int count = 7; // 내부 트랜잭션에서 remaining 테이블 삽입 에러 발생

        //when
        controller.buyTicketNotSupportedNoTransaction(name, count);

        //then
        var list = buyerMapper.getBuyTicket();
        var before = list.get(0);
        var after = list.get(1);
        assertEquals(name, before.getName());
        assertEquals(count, before.getCount());
        assertEquals(name + "_after", after.getName());
        assertEquals(count, after.getCount());
        assertEquals(0, sellerMapper.getRemainingTicket().size());
    }

    @Test
    @DisplayName("NotSupported 테스트, 부모 트랜잭션이 있는 경우, 트랜잭션이 없는 것과 동일")
    void notSupported() {
        //given
        String name = "NotSupported";
        int count = 7; // 내부 트랜잭션에서 remaining 테이블 삽입 에러 발생

        //when
        controller.buyTicketNotSupported(name, count);

        //then
        var list = buyerMapper.getBuyTicket();
        var before = list.get(0);
        var after = list.get(1);
        assertEquals(name, before.getName());
        assertEquals(count, before.getCount());
        assertEquals(name + "_after", after.getName());
        assertEquals(count, after.getCount());
        assertEquals(0, sellerMapper.getRemainingTicket().size());
    }

    @Test
    @DisplayName("Mandatory 테스트, 트랜잭션이 있는 곳에서 호출시 정상 동작")
    void mandatoryInTransaction() {
        //given
        String name = "mandatoryInTransaction";
        int count = 3; // 정상 동작

        //when
        controller.buyTicketMandatoryInTransaction(name, count);

        //then
        var list = buyerMapper.getBuyTicket();
        var before = list.get(0);
        var after = list.get(1);
        assertEquals(name, before.getName());
        assertEquals(count, before.getCount());
        assertEquals(name + "_after", after.getName());
        assertEquals(count, after.getCount());

        var remainingTicket = sellerMapper.getRemainingTicket();
        var remainingInsert = remainingTicket.get(0);
        assertEquals(name, remainingInsert.getName());
        assertEquals(count, remainingInsert.getCount());
    }

    @Test
    @DisplayName("Mandatory 테스트, 트랜잭션이 없는 곳에서 호출하면 에러")
    void mandatoryNoTransaction() {
        //given
        String name = "mandatory";
        int count = 3; // 정상 동작

        //when
        //then
        assertThrows(IllegalTransactionStateException.class,
                ()->controller.buyTicketMandatoryNoTransaction(name, count));
    }

    @Test
    @DisplayName("never 테스트, 트랜잭션 내에서 호출하면 에러")
    void never() {
        //given
        String name = "never";
        int count = 3; // 정상 동작

        //when
        //then
        assertThrows(IllegalTransactionStateException.class,
                ()->controller.buyTicketNever(name, count));
        var list = buyerMapper.getBuyTicket();
        var insertTicket = list.get(0);
        assertEquals(1, list.size());
        assertEquals(name, insertTicket.getName());
        assertEquals(count, insertTicket.getCount());
    }

}
