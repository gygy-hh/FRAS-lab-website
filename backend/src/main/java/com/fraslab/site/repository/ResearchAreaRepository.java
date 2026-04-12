package com.fraslab.site.repository;

import com.fraslab.site.entity.ResearchArea;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResearchAreaRepository extends JpaRepository<ResearchArea, Long> {

    @EntityGraph(attributePaths = "translations")
    List<ResearchArea> findAllByOrderByDisplayOrderAsc();

    @EntityGraph(attributePaths = "translations")
    List<ResearchArea> findAllByActiveTrueOrderByDisplayOrderAsc();
}
