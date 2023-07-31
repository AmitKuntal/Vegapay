package com.vegapay.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreateOfferDto {
    private String accountId;

    private String limitType;

    private Long limit;

    private Timestamp offerActivationTime;

    private Timestamp offerExpiryTime;
}
