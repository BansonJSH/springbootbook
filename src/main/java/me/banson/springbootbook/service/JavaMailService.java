package me.banson.springbootbook.service;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.dto.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavaMailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Async
    public Boolean sendMail(AddUserRequest dto) throws Exception {
        boolean msg = false;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(dto.getEmail());
        simpleMailMessage.setSubject("회원가입이 완료됨.");
        simpleMailMessage.setText("회원가입 축하드려용" + dto.getNickname() + " 님");
        javaMailSender.send(simpleMailMessage);
        return true;
    }
}
