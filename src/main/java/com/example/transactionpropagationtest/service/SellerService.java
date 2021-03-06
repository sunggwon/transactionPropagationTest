package com.example.transactionpropagationtest.service;

import com.example.transactionpropagationtest.mapper.SellerMapper;
import com.example.transactionpropagationtest.model.RemainingTicketDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
    private final SellerMapper sellerMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRemainingTicketRequired(String name, long count){
        log.info("SellerService saveRemainingTicketRequired Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        sellerMapper.saveRemainingTicket(makeDAO(name, count));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRemainingTicketRequiredNew(String name, long count){
        log.info("SellerService saveRemainingTicketRequiredNew Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        sellerMapper.saveRemainingTicket(makeDAO(name, count));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveRemainingTicketSupports(String name, long count){
        log.info("SellerService saveRemainingTicketSupports Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        sellerMapper.saveRemainingTicket(makeDAO(name, count));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveRemainingTicketNotSupported(String name, long count){
        log.info("SellerService saveRemainingTicketNotSupported Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        sellerMapper.saveRemainingTicket(makeDAO(name, count));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveRemainingTicketMandatory(String name, long count){
        log.info("SellerService saveRemainingTicketMandatory Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        sellerMapper.saveRemainingTicket(makeDAO(name, count));
    }

    @Transactional(propagation = Propagation.NEVER)
    public void saveRemainingTicketNever(String name, long count){
        log.info("SellerService saveRemainingTicketNever Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        sellerMapper.saveRemainingTicket(makeDAO(name, count));
    }

    private RemainingTicketDAO makeDAO(String name, long count){
        return RemainingTicketDAO.builder()
                .name(name)
                .count(count)
                .build();
    }
}
