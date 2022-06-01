package com.example.transactionpropagationtest.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TestDTO {
    private final String name;
    private final int count;
}
