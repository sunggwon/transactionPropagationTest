package com.example.transactionpropagationtest.mapper;

import com.example.transactionpropagationtest.model.RemainingTicketDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellerMapper {
    void saveRemainingTicket(RemainingTicketDAO ticket);
}
