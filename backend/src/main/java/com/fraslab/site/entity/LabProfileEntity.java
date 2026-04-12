package com.fraslab.site.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lab_profile")
public class LabProfileEntity {

    @Id
    private Long id;

    @Column(name = "name_zh", nullable = false, length = 200)
    private String nameZh;

    @Column(name = "name_en", nullable = false, length = 200)
    private String nameEn;

    @Column(name = "slogan_zh", nullable = false, length = 500)
    private String sloganZh;

    @Column(name = "slogan_en", nullable = false, length = 500)
    private String sloganEn;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 64)
    private String phone;

    @Column(name = "address_zh", nullable = false, length = 500)
    private String addressZh;

    @Column(name = "address_en", nullable = false, length = 500)
    private String addressEn;

    @Column(name = "map_link", length = 512)
    private String mapLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getSloganZh() {
        return sloganZh;
    }

    public void setSloganZh(String sloganZh) {
        this.sloganZh = sloganZh;
    }

    public String getSloganEn() {
        return sloganEn;
    }

    public void setSloganEn(String sloganEn) {
        this.sloganEn = sloganEn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressZh() {
        return addressZh;
    }

    public void setAddressZh(String addressZh) {
        this.addressZh = addressZh;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }
}
