package com.fraslab.site.service;

import com.fraslab.site.dto.admin.LabProfileUpdateRequest;
import com.fraslab.site.entity.LabProfileEntity;
import com.fraslab.site.model.LabProfile;
import com.fraslab.site.repository.LabProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LabProfileService {

    private static final long SINGLETON_ID = 1L;

    private final LabProfileRepository labProfileRepository;

    public LabProfileService(LabProfileRepository labProfileRepository) {
        this.labProfileRepository = labProfileRepository;
    }

    public LabProfile getProfile() {
        LabProfileEntity entity = getOrThrow();
        return toModel(entity);
    }

    @Transactional
    public LabProfile update(LabProfileUpdateRequest request) {
        LabProfileEntity entity = getOrThrow();
        entity.setNameZh(request.nameZh());
        entity.setNameEn(request.nameEn());
        entity.setSloganZh(request.sloganZh());
        entity.setSloganEn(request.sloganEn());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setAddressZh(request.addressZh());
        entity.setAddressEn(request.addressEn());
        entity.setIntroZh(request.introZh());
        entity.setIntroEn(request.introEn());
        return toModel(entity);
    }

    private LabProfileEntity getOrThrow() {
        return labProfileRepository.findById(SINGLETON_ID)
                .orElseThrow(() -> new EntityNotFoundException("实验室信息不存在"));
    }

    private LabProfile toModel(LabProfileEntity entity) {
        return new LabProfile(
                entity.getNameZh(),
                entity.getNameEn(),
                entity.getSloganZh(),
                entity.getSloganEn(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddressZh(),
                entity.getAddressEn(),
                entity.getIntroZh(),
                entity.getIntroEn()
        );
    }
}
