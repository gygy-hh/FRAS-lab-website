package com.fraslab.site.service;

import com.fraslab.site.dto.admin.MemberUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicMemberDto;
import com.fraslab.site.entity.Member;
import com.fraslab.site.entity.MemberI18n;
import com.fraslab.site.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<PublicMemberDto> getPublicList() {
        return memberRepository.findAllByActiveTrueOrderByEnrollmentYearDescIdAsc().stream().map(this::toDto).toList();
    }

    public List<PublicMemberDto> getAdminList() {
        return memberRepository.findAllByOrderByEnrollmentYearDescIdAsc().stream().map(this::toDto).toList();
    }

    @Transactional
    public PublicMemberDto create(MemberUpsertRequest request) {
        Member member = new Member();
        apply(member, request);
        return toDto(memberRepository.save(member));
    }

    @Transactional
    public PublicMemberDto update(Long id, MemberUpsertRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("成员不存在"));
        apply(member, request);
        return toDto(member);
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("成员不存在");
        }
        memberRepository.deleteById(id);
    }

    private void apply(Member member, MemberUpsertRequest request) {
        member.setMemberCode(request.code());
        member.setRoleType(request.roleType());
        member.setEmail(request.email());
        member.setCvPdfUrl(request.cvPdfUrl());
        member.setEnrollmentYear(request.enrollmentYear());
        member.setActive(request.active());
        member.getTranslations().clear();
        member.getTranslations().add(buildI18n(member, "zh-CN", request.fullNameZh(), request.bioZh()));
        member.getTranslations().add(buildI18n(member, "en", request.fullNameEn(), request.bioEn()));
    }

    private MemberI18n buildI18n(Member member, String lang, String name, String bio) {
        MemberI18n i18n = new MemberI18n();
        i18n.setMember(member);
        i18n.setLangCode(lang);
        i18n.setFullName(name);
        i18n.setBio(bio);
        return i18n;
    }

    private PublicMemberDto toDto(Member member) {
        MemberI18n zh = findLang(member, "zh-CN");
        MemberI18n en = findLang(member, "en");
        return new PublicMemberDto(
                member.getId(),
                member.getMemberCode(),
                member.getRoleType(),
                member.getEmail(),
                member.getCvPdfUrl(),
                member.getEnrollmentYear(),
                zh != null ? zh.getFullName() : "",
                zh != null ? zh.getBio() : "",
                en != null ? en.getFullName() : "",
                en != null ? en.getBio() : ""
        );
    }

    private MemberI18n findLang(Member member, String lang) {
        return member.getTranslations().stream().filter(t -> lang.equals(t.getLangCode())).findFirst().orElse(null);
    }
}
