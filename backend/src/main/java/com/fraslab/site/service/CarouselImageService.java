package com.fraslab.site.service;

import com.fraslab.site.dto.admin.CarouselImageUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicCarouselImageDto;
import com.fraslab.site.entity.CarouselImage;
import com.fraslab.site.repository.CarouselImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarouselImageService {

    private final CarouselImageRepository carouselImageRepository;

    public CarouselImageService(CarouselImageRepository carouselImageRepository) {
        this.carouselImageRepository = carouselImageRepository;
    }

    public List<PublicCarouselImageDto> getPublicList() {
        return carouselImageRepository.findAllByActiveTrueOrderByDisplayOrderAscIdAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PublicCarouselImageDto> getAdminList() {
        return carouselImageRepository.findAllByOrderByDisplayOrderAscIdAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public PublicCarouselImageDto create(CarouselImageUpsertRequest request) {
        CarouselImage image = new CarouselImage();
        apply(image, request);
        return toDto(carouselImageRepository.save(image));
    }

    @Transactional
    public PublicCarouselImageDto update(Long id, CarouselImageUpsertRequest request) {
        CarouselImage image = carouselImageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("轮播图不存在"));
        apply(image, request);
        return toDto(image);
    }

    @Transactional
    public void delete(Long id) {
        if (!carouselImageRepository.existsById(id)) {
            throw new EntityNotFoundException("轮播图不存在");
        }
        carouselImageRepository.deleteById(id);
    }

    private void apply(CarouselImage image, CarouselImageUpsertRequest request) {
        image.setImageUrl(request.imageUrl());
        image.setDisplayOrder(request.displayOrder());
        image.setActive(request.active());
    }

    private PublicCarouselImageDto toDto(CarouselImage image) {
        return new PublicCarouselImageDto(
                image.getId(),
                image.getImageUrl(),
                image.getDisplayOrder(),
                image.isActive()
        );
    }
}
