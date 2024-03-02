package com.cos.blog.test;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private int id;
    private String username;
    private String password;
    private String email;

    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
