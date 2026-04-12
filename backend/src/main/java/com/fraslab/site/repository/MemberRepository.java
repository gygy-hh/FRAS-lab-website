package com.fraslab.site.repository;

import com.fraslab.site.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "translations")
    List<Member> findAllByOrderByEnrollmentYearDescIdAsc();

    @EntityGraph(attributePaths = "translations")
    List<Member> findAllByActiveTrueOrderByEnrollmentYearDescIdAsc();
}
