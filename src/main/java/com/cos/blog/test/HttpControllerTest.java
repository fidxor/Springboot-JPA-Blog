package com.cos.blog.test;

import com.cos.blog.model.Member;
import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(Data)

@RestController
public class HttpControllerTest {

    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member member) {
        return "get 요청 : " + member.getId() + ", " + member.getUsername() + ", " + member.getPassword() + ", " + member.getEmail();
    }

    //http://localhost:8080/http/post (insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member member) { // MessageConverter(스프링부트)
        return "post 요청 : " + member.getId() + ", " + member.getUsername() + ", " + member.getPassword() + ", " + member.getEmail();
    }

    //http://localhost:8080/http/put (udpate)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member member) {
        return "put 요청 : " + member.getId() + ", " + member.getUsername() + ", " + member.getPassword() + ", " + member.getEmail();
    }

    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
