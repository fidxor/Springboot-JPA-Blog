package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long userJoin(User user) {
        try {
            userRepository.save(user);
            return 1L;
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("UserService : userJoin() : " + e.getMessage());
        }

        return -1L;
    }
}
