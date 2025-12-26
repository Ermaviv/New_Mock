package com.example.New_Mock.controller;

import com.example.New_Mock.model.RequestDTO;
import com.example.New_Mock.model.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try{
            String clientId = requestDTO.getClientId();
            char first_Digit = clientId.charAt(0);
            BigDecimal maxLimit;
            String RqUID = requestDTO.getRqUID();
            String currency = "RUB";
            if (first_Digit == '8' ) {
                maxLimit = new BigDecimal(2000);
                currency = "US";
            } else if (first_Digit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            }
            else {
                maxLimit = new BigDecimal(10000);
            }
            ResponseDTO responseDTO = new ResponseDTO();
//            ResponseDTO responseDTO1 = new ResponseDTO();
            responseDTO.setRqUID(RqUID);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(new BigDecimal(777));
            responseDTO.setMaxLimit(maxLimit);
            responseDTO.setClientId(clientId);

//            ResponseDTO responseDTO1 = new ResponseDTO(
//                    RqUID,
//                    clientID,
//                    requestDTO.getAccount(),
//                    "US",
//                    new BigDecimal(777),
//                    maxLimit
//            );

            log.info(
                    "************ Request DTO ************" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO)
            );
            log.info(
                    "************ Response DTO ************" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO)
            );

            long pacing = ThreadLocalRandom.current().nextLong(1000, 2500);
            Thread.sleep(pacing);
            return responseDTO;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
        }
    }
}
