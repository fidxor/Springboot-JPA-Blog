package com.cos.blog.test;

import com.cos.blog.model.Member;
import com.cos.blog.model.RoleType;
import com.cos.blog.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController // html 파일이 아닌 data를 리턴해주는 controller
@Transactional(readOnly = true)
public class DummyControllerTest {

    @Autowired // 의존성 주입
    private MemberRepository memberRepository;

    /**
     * 회원 정보 insert
     */
    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    public String join(String username, String password, String email) { // key, value (약속된 규칙)
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("email : " + email);
        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("/dummy/join2")
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

    /**
     * 회원 정보 조회
     */
    // {id} 주소로 파라미터를 전달 받을 수 있음.
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public Member detail(@PathVariable Long id) {
        // id 값이 데이터베이스에 없으며 null이 되기 때문에
        // Optional로 객체를 감싸서 넘겨주게 된다.

        // 람다식
//        Member member = memberRepository.findById(id).orElseThrow(() -> {
//            return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//        });
        Member member = memberRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });

        // 요청 : 웹브라우저
        // member 객체 : 자바 오브젝트
        // 변환 (웹브라우서가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
        // 스프링부트 = MessageConverter가 응답시에 자동으로 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // member오브젝트를 json으로 변환해서 웹브라으저에 리턴하게 된다.
        return member;
    }

    /**
     * 회원조회 페이징
     */
    @GetMapping("/dummy/users")
    public List<Member> list() {
        return memberRepository.findAll();
    }

    @GetMapping("/dummy/user/page")
    public List<Member> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Member> pagingMember = memberRepository.findAll(pageable);

        List<Member> members = pagingMember.getContent();

        return members;
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member request) {
        System.out.println("id : " + id);
        System.out.println("password : " + request.getPassword());
        System.out.println("email : " + request.getEmail());

        Member member = memberRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        member.setPassword(request.getPassword());
        member.setEmail(request.getEmail());
        return member;
    }

    /**
     * 회원 삭제
     */
    @Transactional
    @DeleteMapping("/dummy/user/{id}")
    public String deleteMember(@PathVariable Long id) {
//        try {
//            memberRepository.deleteById(id); // deleteById는 내부에서 null 체크까지 진행
//        } catch (IllegalArgumentException e) {
//            return "삭제 할수 없습니다. 해당 id가 DB에 없습니다.";
//        }

        Member member = memberRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 id가 DB에 존재하지 않습니다.");
            }
        });

        memberRepository.delete(member);
        return "삭제되었습니다 id : " + id;
    }
}
