package com.cos.blog.repository;

import com.cos.blog.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository  생략 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
}