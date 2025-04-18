package com.scaler.emailservicecapstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.emailservicecapstone.dtos.SendEmailDto;
import com.scaler.emailservicecapstone.util.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEmailService {

    @KafkaListener(topics = "sendUserEmail", groupId = "emailService")
    public void sendUserEmail(String message)    {
        ObjectMapper mapper = new ObjectMapper();
        SendEmailDto sendEmailDto = null;
        try {
            sendEmailDto = mapper.readValue(message, SendEmailDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        EmailUtil.sendUserEmail(sendEmailDto.getFrom(), sendEmailDto.getTo(), sendEmailDto.getSubject(), sendEmailDto.getBody());
    }
}
