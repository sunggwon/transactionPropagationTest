package com.example.transactionpropagationtest.mapper;

import com.example.transactionpropagationtest.model.RemainingTicketDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerMapper {
    void saveRemainingTicket(RemainingTicketDAO ticket);
    List<RemainingTicketDAO> getRemainingTicket();
    void deleteAllRows();
}
