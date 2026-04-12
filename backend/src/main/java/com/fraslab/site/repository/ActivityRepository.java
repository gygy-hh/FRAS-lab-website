package com.fraslab.site.repository;

import com.fraslab.site.entity.Activity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @EntityGraph(attributePaths = "translations")
    List<Activity> findAllByOrderByStartTimeDescIdDesc();

    @EntityGraph(attributePaths = "translations")
    List<Activity> findAllByActiveTrueOrderByStartTimeDescIdDesc();
}
