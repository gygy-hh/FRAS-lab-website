package com.fraslab.site.service;

import com.fraslab.site.dto.admin.PublicationUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicPublicationDto;
import com.fraslab.site.entity.Publication;
import com.fraslab.site.entity.PublicationI18n;
import com.fraslab.site.repository.PublicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<PublicPublicationDto> getPublicList() {
        return publicationRepository.findAllByActiveTrueOrderByPubYearDescPubDateDescIdDesc().stream().map(this::toDto).toList();
    }

    public List<PublicPublicationDto> getAdminList() {
        return publicationRepository.findAllByOrderByPubYearDescPubDateDescIdDesc().stream().map(this::toDto).toList();
    }

    @Transactional
    public PublicPublicationDto create(PublicationUpsertRequest request) {
        Publication publication = new Publication();
        apply(publication, request);
        return toDto(publicationRepository.save(publication));
    }

    @Transactional
    public PublicPublicationDto update(Long id, PublicationUpsertRequest request) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("成果不存在"));
        apply(publication, request);
        return toDto(publication);
    }

    @Transactional
    public void delete(Long id) {
        if (!publicationRepository.existsById(id)) {
            throw new EntityNotFoundException("成果不存在");
        }
        publicationRepository.deleteById(id);
    }

    private void apply(Publication publication, PublicationUpsertRequest request) {
        publication.setSlug(request.slug());
        publication.setPubYear(request.pubYear());
        publication.setPubDate(request.pubDate());
        publication.setCategory(request.category());
        publication.setExternalUrl(request.externalUrl());
        publication.setActive(request.active());
        publication.getTranslations().clear();
        publication.getTranslations().add(buildI18n(publication, "zh-CN", request.titleZh(), request.authorsZh(), request.abstractZh()));
        publication.getTranslations().add(buildI18n(publication, "en", request.titleEn(), request.authorsEn(), request.abstractEn()));
    }

    private PublicationI18n buildI18n(Publication publication, String lang, String title, String authors, String abstractText) {
        PublicationI18n i18n = new PublicationI18n();
        i18n.setPublication(publication);
        i18n.setLangCode(lang);
        i18n.setTitle(title);
        i18n.setAuthors(authors);
        i18n.setAbstractText(abstractText);
        return i18n;
    }

    private PublicPublicationDto toDto(Publication publication) {
        PublicationI18n zh = findLang(publication, "zh-CN");
        PublicationI18n en = findLang(publication, "en");
        return new PublicPublicationDto(
                publication.getId(),
                publication.getSlug(),
                publication.getPubYear(),
                publication.getPubDate(),
                publication.getCategory(),
                publication.getExternalUrl(),
                zh != null ? zh.getTitle() : "",
                zh != null ? zh.getAuthors() : "",
                zh != null ? zh.getAbstractText() : "",
                en != null ? en.getTitle() : "",
                en != null ? en.getAuthors() : "",
                en != null ? en.getAbstractText() : ""
        );
    }

    private PublicationI18n findLang(Publication publication, String lang) {
        return publication.getTranslations().stream().filter(t -> lang.equals(t.getLangCode())).findFirst().orElse(null);
    }
}
