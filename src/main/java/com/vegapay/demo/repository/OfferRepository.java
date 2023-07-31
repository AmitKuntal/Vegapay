package com.vegapay.demo.repository;

import com.vegapay.demo.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, String> {

    @Query("select o from Offer o where accountId = :accountId and status = 'PENDING' and offerActivationTime <= :time and offerExpiryTime >=:time")
    List<Offer> getAllActiveOfferByAccountIdAndDate(@Param("accountId") String accountId, @Param("time")Timestamp times);

    @Transactional
    @Query("update Offer set status=:status where id=:id")
    void updateOfferStatus(@Param("status") String status, @Param("id") String id);
}
