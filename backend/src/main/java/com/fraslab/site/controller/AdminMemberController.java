package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.MemberUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicMemberDto;
import com.fraslab.site.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/members")
public class AdminMemberController {

    private final MemberService memberService;

    public AdminMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ApiResponse<List<PublicMemberDto>> list() {
        return ApiResponse.ok(memberService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicMemberDto> create(@Valid @RequestBody MemberUpsertRequest request) {
        return ApiResponse.ok(memberService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicMemberDto> update(@PathVariable Long id, @Valid @RequestBody MemberUpsertRequest request) {
        return ApiResponse.ok(memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ApiResponse.ok(true);
    }
}
