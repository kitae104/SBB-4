package com.mysite.sbb.member.repository;

import com.mysite.sbb.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long>{
}
