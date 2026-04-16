package com.fraslab.site.service;

import com.fraslab.site.dto.admin.NewsUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicNewsDto;
import com.fraslab.site.entity.News;
import com.fraslab.site.entity.NewsI18n;
import com.fraslab.site.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<PublicNewsDto> getPublicList() {
        return newsRepository.findAllByStatusOrderByPublishedAtDesc("PUBLISHED")
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PublicNewsDto> getAdminList() {
        return newsRepository.findAllByOrderByPublishedAtDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public PublicNewsDto create(NewsUpsertRequest request) {
        News news = new News();
        apply(news, request);
        return toDto(newsRepository.save(news));
    }

    @Transactional
    public PublicNewsDto update(Long id, NewsUpsertRequest request) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("新闻不存在"));
        apply(news, request);
        return toDto(news);
    }

    @Transactional
    public void delete(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityNotFoundException("新闻不存在");
        }
        newsRepository.deleteById(id);
    }

    private void apply(News news, NewsUpsertRequest request) {
        news.setSlug(request.slug());
        news.setStatus(request.status());
        news.setPublishedAt(request.publishedAt());
        news.setImageUrl(request.imageUrl());
        news.getTranslations().clear();
        news.getTranslations().add(buildI18n(news, "zh-CN", request.titleZh(), request.contentZh()));
        news.getTranslations().add(buildI18n(news, "en", request.titleEn(), request.contentEn()));
    }

    private NewsI18n buildI18n(News news, String lang, String title, String content) {
        NewsI18n i18n = new NewsI18n();
        i18n.setNews(news);
        i18n.setLangCode(lang);
        i18n.setTitle(title);
        i18n.setContent(content);
        return i18n;
    }

    private PublicNewsDto toDto(News news) {
        NewsI18n zh = findLang(news, "zh-CN");
        NewsI18n en = findLang(news, "en");
        return new PublicNewsDto(
                news.getId(),
                news.getSlug(),
                news.getStatus(),
                news.getPublishedAt(),
                news.getImageUrl(),
                zh != null ? zh.getTitle() : "",
                zh != null ? zh.getContent() : "",
                en != null ? en.getTitle() : "",
                en != null ? en.getContent() : ""
        );
    }

    private NewsI18n findLang(News news, String lang) {
        return news.getTranslations().stream()
                .filter(item -> lang.equals(item.getLangCode()))
                .findFirst()
                .orElse(null);
    }
}
