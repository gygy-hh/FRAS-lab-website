package com.fraslab.site.repository;

import com.fraslab.site.entity.Advisor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {

    @EntityGraph(attributePaths = "translations")
    List<Advisor> findAllByOrderByIdAsc();

    @EntityGraph(attributePaths = "translations")
    List<Advisor> findAllByActiveTrueOrderByIdAsc();
}
