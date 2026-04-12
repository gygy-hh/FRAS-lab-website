<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { fetchProfile, fetchResearchAreas, type LabProfile, type ResearchArea } from './api/public'

const { t, locale } = useI18n()
const profile = ref<LabProfile | null>(null)
const researchAreas = ref<ResearchArea[]>([])
const loading = ref(true)

const navItems = computed(() => [
  t('nav.home'),
  t('nav.advisor'),
  t('nav.member'),
  t('nav.research'),
  t('nav.news'),
  t('nav.activity'),
  t('nav.contact'),
])

const profileName = computed(() => {
  if (!profile.value) return t('hero.title')
  return locale.value === 'zh-CN' ? profile.value.nameZh : profile.value.nameEn
})

const profileSlogan = computed(() => {
  if (!profile.value) return t('hero.subtitle')
  return locale.value === 'zh-CN' ? profile.value.sloganZh : profile.value.sloganEn
})

function areaName(area: ResearchArea) {
  return locale.value === 'zh-CN' ? area.nameZh : area.nameEn
}

function toggleLanguage() {
  locale.value = locale.value === 'zh-CN' ? 'en' : 'zh-CN'
}

onMounted(async () => {
  try {
    const [profileData, areaData] = await Promise.all([fetchProfile(), fetchResearchAreas()])
    profile.value = profileData
    researchAreas.value = areaData
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="layout">
    <header class="topbar">
      <div class="brand">FRAS-lab</div>
      <nav class="nav">
        <a v-for="item in navItems" :key="item" href="#">{{ item }}</a>
      </nav>
      <button class="lang-btn" @click="toggleLanguage">{{ t('common.switchLang') }}</button>
    </header>

    <section class="hero">
      <h1>{{ profileName }}</h1>
      <p>{{ profileSlogan }}</p>
    </section>

    <section class="section-card">
      <h2>{{ t('section.researchArea') }}</h2>
      <p v-if="loading">{{ t('common.loading') }}</p>
      <div v-else class="grid">
        <article v-for="area in researchAreas" :key="area.key" class="card">
          <h3>{{ areaName(area) }}</h3>
        </article>
      </div>
    </section>

    <section class="section-card">
      <h2>{{ t('section.contact') }}</h2>
      <p>{{ profile?.email ?? 'contact@fras-lab.example' }}</p>
      <p>{{ profile?.phone ?? '+86-000-00000000' }}</p>
    </section>

    <footer class="footer">{{ t('footer.copyright') }}</footer>
  </div>
</template>
