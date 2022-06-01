package com.example.transactionpropagationtest.mapper;

import com.example.transactionpropagationtest.model.BuyTicketDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyerMapper {
    void buyTicket(BuyTicketDAO ticket);
}
