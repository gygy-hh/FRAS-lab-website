package com.fraslab.site.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "advisors")
public class Advisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advisor_code", nullable = false, unique = true, length = 64)
    private String advisorCode;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(length = 64)
    private String phone;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdvisorI18n> translations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getAdvisorCode() {
        return advisorCode;
    }

    public void setAdvisorCode(String advisorCode) {
        this.advisorCode = advisorCode;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<AdvisorI18n> getTranslations() {
        return translations;
    }
}
