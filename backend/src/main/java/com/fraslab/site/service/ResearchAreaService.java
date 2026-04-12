package com.fraslab.site.service;

import com.fraslab.site.dto.admin.ResearchAreaUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicResearchAreaDto;
import com.fraslab.site.entity.ResearchArea;
import com.fraslab.site.entity.ResearchAreaI18n;
import com.fraslab.site.repository.ResearchAreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResearchAreaService {

    private final ResearchAreaRepository researchAreaRepository;

    public ResearchAreaService(ResearchAreaRepository researchAreaRepository) {
        this.researchAreaRepository = researchAreaRepository;
    }

    public List<PublicResearchAreaDto> getPublicList() {
        return researchAreaRepository.findAllByActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PublicResearchAreaDto> getAdminList() {
        return researchAreaRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public PublicResearchAreaDto create(ResearchAreaUpsertRequest request) {
        ResearchArea area = new ResearchArea();
        apply(area, request);
        return toDto(researchAreaRepository.save(area));
    }

    @Transactional
    public PublicResearchAreaDto update(Long id, ResearchAreaUpsertRequest request) {
        ResearchArea area = researchAreaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("研究方向不存在"));
        apply(area, request);
        return toDto(area);
    }

    @Transactional
    public void delete(Long id) {
        if (!researchAreaRepository.existsById(id)) {
            throw new EntityNotFoundException("研究方向不存在");
        }
        researchAreaRepository.deleteById(id);
    }

    private void apply(ResearchArea area, ResearchAreaUpsertRequest request) {
        area.setAreaKey(request.key());
        area.setDisplayOrder(request.displayOrder());
        area.setActive(request.active());
        area.getTranslations().clear();
        area.getTranslations().add(buildI18n(area, "zh-CN", request.nameZh(), request.summaryZh()));
        area.getTranslations().add(buildI18n(area, "en", request.nameEn(), request.summaryEn()));
    }

    private ResearchAreaI18n buildI18n(ResearchArea area, String lang, String name, String summary) {
        ResearchAreaI18n i18n = new ResearchAreaI18n();
        i18n.setResearchArea(area);
        i18n.setLangCode(lang);
        i18n.setName(name);
        i18n.setSummary(summary);
        return i18n;
    }

    private PublicResearchAreaDto toDto(ResearchArea area) {
        ResearchAreaI18n zh = findLang(area, "zh-CN");
        ResearchAreaI18n en = findLang(area, "en");
        return new PublicResearchAreaDto(
                area.getId(),
                area.getAreaKey(),
                area.getDisplayOrder(),
                zh != null ? zh.getName() : "",
                zh != null ? zh.getSummary() : "",
                en != null ? en.getName() : "",
                en != null ? en.getSummary() : ""
        );
    }

    private ResearchAreaI18n findLang(ResearchArea area, String lang) {
        return area.getTranslations().stream()
                .filter(item -> lang.equals(item.getLangCode()))
                .findFirst()
                .orElse(null);
    }
}
