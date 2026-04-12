package com.fraslab.site.service;

import com.fraslab.site.dto.admin.AdvisorUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicAdvisorDto;
import com.fraslab.site.entity.Advisor;
import com.fraslab.site.entity.AdvisorI18n;
import com.fraslab.site.repository.AdvisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdvisorService {

    private final AdvisorRepository advisorRepository;

    public AdvisorService(AdvisorRepository advisorRepository) {
        this.advisorRepository = advisorRepository;
    }

    public List<PublicAdvisorDto> getPublicList() {
        return advisorRepository.findAllByActiveTrueOrderByIdAsc().stream().map(this::toDto).toList();
    }

    public List<PublicAdvisorDto> getAdminList() {
        return advisorRepository.findAllByOrderByIdAsc().stream().map(this::toDto).toList();
    }

    @Transactional
    public PublicAdvisorDto create(AdvisorUpsertRequest request) {
        Advisor advisor = new Advisor();
        apply(advisor, request);
        return toDto(advisorRepository.save(advisor));
    }

    @Transactional
    public PublicAdvisorDto update(Long id, AdvisorUpsertRequest request) {
        Advisor advisor = advisorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("导师不存在"));
        apply(advisor, request);
        return toDto(advisor);
    }

    @Transactional
    public void delete(Long id) {
        if (!advisorRepository.existsById(id)) {
            throw new EntityNotFoundException("导师不存在");
        }
        advisorRepository.deleteById(id);
    }

    private void apply(Advisor advisor, AdvisorUpsertRequest request) {
        advisor.setAdvisorCode(request.code());
        advisor.setEmail(request.email());
        advisor.setPhone(request.phone());
        advisor.setActive(request.active());
        advisor.getTranslations().clear();
        advisor.getTranslations().add(buildI18n(advisor, "zh-CN", request.fullNameZh(), request.titleZh(), request.summaryZh()));
        advisor.getTranslations().add(buildI18n(advisor, "en", request.fullNameEn(), request.titleEn(), request.summaryEn()));
    }

    private AdvisorI18n buildI18n(Advisor advisor, String lang, String name, String title, String summary) {
        AdvisorI18n i18n = new AdvisorI18n();
        i18n.setAdvisor(advisor);
        i18n.setLangCode(lang);
        i18n.setFullName(name);
        i18n.setTitle(title);
        i18n.setResearchSummary(summary);
        return i18n;
    }

    private PublicAdvisorDto toDto(Advisor advisor) {
        AdvisorI18n zh = findLang(advisor, "zh-CN");
        AdvisorI18n en = findLang(advisor, "en");
        return new PublicAdvisorDto(
                advisor.getId(),
                advisor.getAdvisorCode(),
                advisor.getEmail(),
                advisor.getPhone(),
                zh != null ? zh.getFullName() : "",
                zh != null ? zh.getTitle() : "",
                zh != null ? zh.getResearchSummary() : "",
                en != null ? en.getFullName() : "",
                en != null ? en.getTitle() : "",
                en != null ? en.getResearchSummary() : ""
        );
    }

    private AdvisorI18n findLang(Advisor advisor, String lang) {
        return advisor.getTranslations().stream().filter(t -> lang.equals(t.getLangCode())).findFirst().orElse(null);
    }
}
