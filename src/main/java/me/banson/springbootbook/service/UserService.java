package me.banson.springbootbook.service;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.User;
import me.banson.springbootbook.dto.AddUserRequest;
import me.banson.springbootbook.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailService javaMailService;

    @Transactional
    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Long id = userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build()).getId();

        return id;
    }

    @Async
    public void sendMail(AddUserRequest dto) {
        try {
            javaMailService.sendMail(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String sendValidNumber(AddUserRequest user) throws Exception {
        Random random = new Random();
        String validNumber = "";
        for (int i = 0; i < 6; i++) {
            validNumber += Integer.toString(random.nextInt(10));
        }
        javaMailService.sendValidNumber(user, validNumber);
        return validNumber;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId)
                .orElseThrow();
    }
}

