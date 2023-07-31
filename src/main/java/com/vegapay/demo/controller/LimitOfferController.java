package com.vegapay.demo.controller;

import com.vegapay.demo.dto.CreateOfferDto;
import com.vegapay.demo.dto.GenericResponse;
import com.vegapay.demo.dto.UpdateOfferDto;
import com.vegapay.demo.services.OfferHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offer")
public class LimitOfferController {

    @Autowired
    OfferHelperService offerService;

    @PostMapping
    public ResponseEntity<GenericResponse> createOffer(@RequestBody CreateOfferDto body){
        try{
            return ResponseEntity.ok(offerService.createOffer(body));
        } catch (Exception exception){
            GenericResponse response = new GenericResponse();
            response.setStatus("error");
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/account/{id}/active")
    public Object getActiveOfferForAccount(@PathVariable("id") String accountId, @RequestParam(value = "date", required = false) String date){
        return ResponseEntity.ok(offerService.getActiveOffer(accountId, date));
    }

    @PutMapping
    public Object updateOffer(@RequestBody UpdateOfferDto body){
        try{
            return ResponseEntity.ok(offerService.updateOffer(body));
        } catch (Exception exception){
            exception.printStackTrace();
            GenericResponse response = new GenericResponse();
            response.setStatus("error");
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
