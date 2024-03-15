package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/api/user")
    public ResponseDto<Long> save(@RequestBody User user) {
        System.out.println("MemberApiController : save 호출됨");
        user.setRole(RoleType.USER);
        Long result = userService.userJoin(user);
        return new ResponseDto<Long>(HttpStatus.OK.value(), result);
    }

    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user) {
        System.out.println("MemberApiController : login 호출됨");
        User principal = userService.login(user); // principal (접근주체)

        // session 만들기
        if (principal != null) {
            session.setAttribute("principal", principal);
        }

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
