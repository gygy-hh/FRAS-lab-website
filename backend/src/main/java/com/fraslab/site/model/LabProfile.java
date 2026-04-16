package com.fraslab.site.model;

public record LabProfile(
        String nameZh,
        String nameEn,
        String sloganZh,
        String sloganEn,
        String email,
        String phone,
        String addressZh,
        String addressEn,
        String introZh,
        String introEn
) {
}
