package com.fraslab.site.service;

import com.fraslab.site.dto.admin.ActivityUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicActivityDto;
import com.fraslab.site.entity.Activity;
import com.fraslab.site.entity.ActivityI18n;
import com.fraslab.site.repository.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<PublicActivityDto> getPublicList() {
        return activityRepository.findAllByActiveTrueOrderByStartTimeDescIdDesc().stream().map(this::toDto).toList();
    }

    public List<PublicActivityDto> getAdminList() {
        return activityRepository.findAllByOrderByStartTimeDescIdDesc().stream().map(this::toDto).toList();
    }

    @Transactional
    public PublicActivityDto create(ActivityUpsertRequest request) {
        Activity activity = new Activity();
        apply(activity, request);
        return toDto(activityRepository.save(activity));
    }

    @Transactional
    public PublicActivityDto update(Long id, ActivityUpsertRequest request) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("活动不存在"));
        apply(activity, request);
        return toDto(activity);
    }

    @Transactional
    public void delete(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new EntityNotFoundException("活动不存在");
        }
        activityRepository.deleteById(id);
    }

    private void apply(Activity activity, ActivityUpsertRequest request) {
        activity.setSlug(request.slug());
        activity.setStartTime(request.startTime());
        activity.setEndTime(request.endTime());
        activity.setLocation(request.location());
        activity.setActive(request.active());
        activity.getTranslations().clear();
        activity.getTranslations().add(buildI18n(activity, "zh-CN", request.titleZh(), request.descriptionZh()));
        activity.getTranslations().add(buildI18n(activity, "en", request.titleEn(), request.descriptionEn()));
    }

    private ActivityI18n buildI18n(Activity activity, String lang, String title, String description) {
        ActivityI18n i18n = new ActivityI18n();
        i18n.setActivity(activity);
        i18n.setLangCode(lang);
        i18n.setTitle(title);
        i18n.setDescription(description);
        return i18n;
    }

    private PublicActivityDto toDto(Activity activity) {
        ActivityI18n zh = findLang(activity, "zh-CN");
        ActivityI18n en = findLang(activity, "en");
        return new PublicActivityDto(
                activity.getId(),
                activity.getSlug(),
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getLocation(),
                zh != null ? zh.getTitle() : "",
                zh != null ? zh.getDescription() : "",
                en != null ? en.getTitle() : "",
                en != null ? en.getDescription() : ""
        );
    }

    private ActivityI18n findLang(Activity activity, String lang) {
        return activity.getTranslations().stream().filter(t -> lang.equals(t.getLangCode())).findFirst().orElse(null);
    }
}
