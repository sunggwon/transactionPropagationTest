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

@Service
@RequiredArgsConstructor
public class BuyerService {
    private final BuyerMapper mapper;
    private final SellerService seller;

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketRequired(String name, long count){
        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketRequired(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketRequiredNew(String name, long count){
        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketRequiredNew(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketSupports(String name, long count){
        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketSupports(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketNotSupported(String name, long count){
        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketNotSupported(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    public void buyTicketNotSupportedNotTransaction(String name, long count){
        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketNotSupported(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketMandatoryInTransaction(String name, long count){
        seller.saveRemainingTicketMandatory(name, count);
        try {
            mapper.buyTicket(makeDAO(name, count));
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    public void buyTicketMandatoryNoTransaction(String name, long count){
        seller.saveRemainingTicketMandatory(name, count);
        try {
            mapper.buyTicket(makeDAO(name, count));
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    @Transactional(noRollbackFor = Exception.class)
    public void buyTicketNever(String name, long count){
        mapper.buyTicket(makeDAO(name, count));
        try {
            seller.saveRemainingTicketNever(name, count);
        }catch (Exception e){}
        mapper.buyTicket(makeDAO(name + "test", count));
    }

    private BuyTicketDAO makeDAO(String name, long count){
        return BuyTicketDAO.builder()
                .name(name)
                .count(count)
                .build();
    }
}
