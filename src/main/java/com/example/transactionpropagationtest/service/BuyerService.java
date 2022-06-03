package com.example.transactionpropagationtest.service;

import com.example.transactionpropagationtest.mapper.BuyerMapper;
import com.example.transactionpropagationtest.model.BuyTicketDAO;
import com.example.transactionpropagationtest.model.RemainingTicketDAO;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerService {
    private final BuyerMapper mapper;
    private final SellerService seller;

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketRequired(String name, long count){
        log.info("BuyerService buyTicketRequired Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketRequired(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketRequiredNew(String name, long count){
        log.info("BuyerService buyTicketRequiredNew Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketRequiredNew(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketSupports(String name, long count){
        log.info("BuyerService buyTicketSupports Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketSupports(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketNotSupported(String name, long count){
        log.info("BuyerService buyTicketNotSupported Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketNotSupported(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    public void buyTicketNotSupportedNoTransaction(String name, long count){
        log.info("BuyerService buyTicketNotSupportedNotTransaction Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketNotSupported(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketMandatoryInTransaction(String name, long count){
        log.info("BuyerService buyTicketMandatoryInTransaction Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        seller.saveRemainingTicketMandatory(name, count);
        try {
            mapper.buyTicket(makeDAO(name, count));
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    public void buyTicketMandatoryNoTransaction(String name, long count){
        log.info("BuyerService buyTicketMandatoryNoTransaction Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        seller.saveRemainingTicketMandatory(name, count);
        try {
            mapper.buyTicket(makeDAO(name, count));
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketNever(String name, long count){
        log.info("BuyerService buyTicketNever Transaction - "
                + TransactionSynchronizationManager.getCurrentTransactionName());

        mapper.buyTicket(makeDAO(name, count));
        seller.saveRemainingTicketNever(name, count);
        mapper.buyTicket(makeDAO(name + "_after", count));
    }

    private BuyTicketDAO makeDAO(String name, long count){
        return BuyTicketDAO.builder()
                .name(name)
                .count(count)
                .build();
    }
}
