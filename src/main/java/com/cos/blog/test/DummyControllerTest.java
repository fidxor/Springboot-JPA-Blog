package com.cos.blog.test;

import com.cos.blog.model.Member;
import com.cos.blog.model.RoleType;
import com.cos.blog.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(readOnly = true)
public class DummyControllerTest {

    @Autowired // 의존성 주입
    private MemberRepository memberRepository;

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    public String join(String username, String password, String email) { // key, value (약속된 규칙)
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("email : " + email);
        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("dummy/join2")
    @Transactional
    public String join2(Member member) { // key, value (약속된 규칙)
        System.out.println("id : " + member.getId());
        System.out.println("username : " + member.getUsername());
        System.out.println("password : " + member.getPassword());
        System.out.println("email : " + member.getEmail());
        System.out.println("role : " + member.getRole());
        System.out.println("reateDate + " + member.getCreateDate());

        member.setRole(RoleType.USER);
        memberRepository.save(member);

        return "회원가입이 완료되었습니다.";
    }
}
