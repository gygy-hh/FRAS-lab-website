package com.fraslab.site.repository;

import com.fraslab.site.entity.CarouselImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarouselImageRepository extends JpaRepository<CarouselImage, Long> {
    List<CarouselImage> findAllByActiveTrueOrderByDisplayOrderAscIdAsc();

    List<CarouselImage> findAllByOrderByDisplayOrderAscIdAsc();
}
