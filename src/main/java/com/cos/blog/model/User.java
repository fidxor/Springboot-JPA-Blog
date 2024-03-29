package com.cos.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
@Entity // Member클래스가 자동으로 MySQL에 테이블이 생성이 된다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    @Column(name = "user_id")
    private Long id;    // auto_increment

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100) // 123456 -> 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

//    @ColumnDefault("user")
    //DB는 RoleType이라는게 없다.
    @Enumerated(EnumType.STRING)
    private RoleType role; // USER, ADMIN

    @CreationTimestamp // 시간이 자동 입력
    private LocalDateTime createDate;
}
