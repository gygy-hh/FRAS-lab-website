package com.fraslab.site.repository;

import com.fraslab.site.entity.News;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    @EntityGraph(attributePaths = "translations")
    List<News> findAllByStatusOrderByPublishedAtDesc(String status);

    @EntityGraph(attributePaths = "translations")
    List<News> findAllByOrderByPublishedAtDesc();
}
