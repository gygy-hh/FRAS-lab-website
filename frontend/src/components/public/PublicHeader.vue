<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'

const { t, locale } = useI18n()

const navItems = computed(() => [
  { label: t('nav.home'), to: '/' },
  { label: t('nav.member'), to: '/members' },
  { label: t('nav.research'), to: '/research' },
  { label: t('nav.publications'), to: '/publications' },
  { label: t('nav.news'), to: '/news' },
  { label: t('nav.activity'), to: '/activities' },
  { label: t('nav.contact'), to: '/contact' },
])

function toggleLanguage() {
  locale.value = locale.value === 'zh-CN' ? 'en' : 'zh-CN'
}
</script>

<template>
  <header class="topbar">
    <div class="brand">FRAS-lab</div>
    <nav class="nav">
      <router-link v-for="item in navItems" :key="item.to" :to="item.to">{{ item.label }}</router-link>
    </nav>
    <div class="actions">
      <router-link class="lang-btn" to="/admin/login">{{ t('admin.enter') }}</router-link>
      <button class="lang-btn" @click="toggleLanguage">{{ t('common.switchLang') }}</button>
    </div>
  </header>
</template>
