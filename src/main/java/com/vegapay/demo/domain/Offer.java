package com.vegapay.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "limit_type")
    private String limitType;

    @Column(name = "limit_value")
    private Long limitValue;

    @Column(name = "status")
    private String status;

    @Column(name = "offer_activation_time")
    private Timestamp offerActivationTime;

    @Column(name = "offer_expiry_time")
    private Timestamp offerExpiryTime;

}
