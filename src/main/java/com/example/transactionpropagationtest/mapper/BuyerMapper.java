package com.example.transactionpropagationtest.mapper;

import com.example.transactionpropagationtest.model.BuyTicketDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuyerMapper {
    void buyTicket(BuyTicketDAO ticket);
    List<BuyTicketDAO> getBuyTicket();
    void deleteAllRows();
}
