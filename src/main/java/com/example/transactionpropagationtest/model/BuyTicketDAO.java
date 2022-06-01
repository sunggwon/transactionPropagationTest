package com.example.transactionpropagationtest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyTicketDAO {
    private final long id;
    private final String name;
    private final long count;
}
