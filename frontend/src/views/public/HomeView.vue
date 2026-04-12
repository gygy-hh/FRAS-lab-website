<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import {
  fetchActivities,
  fetchAdvisors,
  fetchMembers,
  fetchNews,
  fetchProfile,
  fetchPublications,
  fetchResearchAreas,
  type ActivityItem,
  type AdvisorItem,
  type LabProfile,
  type MemberItem,
  type NewsItem,
  type PublicationItem,
  type ResearchArea,
} from '../../api/public'

const { t, locale } = useI18n()
const profile = ref<LabProfile | null>(null)
const researchAreas = ref<ResearchArea[]>([])
const newsList = ref<NewsItem[]>([])
const publications = ref<PublicationItem[]>([])
const activities = ref<ActivityItem[]>([])
const advisors = ref<AdvisorItem[]>([])
const members = ref<MemberItem[]>([])
const loading = ref(true)

const navItems = computed(() => [
  t('nav.home'),
  t('nav.advisor'),
  t('nav.member'),
  t('nav.research'),
  t('nav.publications'),
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

function newsTitle(item: NewsItem) {
  return locale.value === 'zh-CN' ? item.titleZh : item.titleEn
}

function publicationTitle(item: PublicationItem) {
  return locale.value === 'zh-CN' ? item.titleZh : item.titleEn
}

function advisorName(item: AdvisorItem) {
  return locale.value === 'zh-CN' ? item.fullNameZh : item.fullNameEn
}

function memberName(item: MemberItem) {
  return locale.value === 'zh-CN' ? item.fullNameZh : item.fullNameEn
}

function activityTitle(item: ActivityItem) {
  return locale.value === 'zh-CN' ? item.titleZh : item.titleEn
}

function toggleLanguage() {
  locale.value = locale.value === 'zh-CN' ? 'en' : 'zh-CN'
}

onMounted(async () => {
  try {
    const [profileData, areaData, publicationData, newsData, activityData, advisorData, memberData] = await Promise.all([
      fetchProfile(),
      fetchResearchAreas(),
      fetchPublications(),
      fetchNews(),
      fetchActivities(),
      fetchAdvisors(),
      fetchMembers(),
    ])
    profile.value = profileData
    researchAreas.value = areaData
    publications.value = publicationData
    newsList.value = newsData
    activities.value = activityData
    advisors.value = advisorData
    members.value = memberData
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
      <div class="actions">
        <a class="lang-btn" href="/admin/login">{{ t('admin.enter') }}</a>
        <button class="lang-btn" @click="toggleLanguage">{{ t('common.switchLang') }}</button>
      </div>
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
      <h2>{{ t('nav.publications') }}</h2>
      <p v-if="loading">{{ t('common.loading') }}</p>
      <ul v-else class="news-list">
        <li v-for="item in publications" :key="item.id">
          {{ publicationTitle(item) }} <small>({{ item.pubYear }})</small>
          <a v-if="item.externalUrl" :href="item.externalUrl" target="_blank" rel="noreferrer"> Link </a>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>{{ t('nav.news') }}</h2>
      <p v-if="loading">{{ t('common.loading') }}</p>
      <ul v-else class="news-list">
        <li v-for="item in newsList" :key="item.id">
          {{ newsTitle(item) }} <small>({{ item.publishedAt ?? '-' }})</small>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>{{ t('nav.activity') }}</h2>
      <p v-if="loading">{{ t('common.loading') }}</p>
      <ul v-else class="news-list">
        <li v-for="item in activities" :key="item.id">
          {{ activityTitle(item) }} <small>({{ item.startTime }} @ {{ item.location }})</small>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>{{ t('nav.advisor') }}</h2>
      <p v-if="loading">{{ t('common.loading') }}</p>
      <ul v-else class="news-list">
        <li v-for="item in advisors" :key="item.id">{{ advisorName(item) }} ({{ item.email }})</li>
      </ul>
    </section>

    <section class="section-card">
      <h2>{{ t('nav.member') }}</h2>
      <p v-if="loading">{{ t('common.loading') }}</p>
      <ul v-else class="news-list">
        <li v-for="item in members" :key="item.id">{{ memberName(item) }} - {{ item.roleType }}</li>
      </ul>
    </section>

    <section class="section-card">
      <h2>{{ t('section.contact') }}</h2>
      <p>{{ profile?.email ?? 'contact@fras-lab.example' }}</p>
      <p>{{ profile?.phone ?? '+86-000-00000000' }}</p>
      <p>{{ locale === 'zh-CN' ? profile?.addressZh : profile?.addressEn }}</p>
      <p v-if="profile?.mapLink"><a :href="profile.mapLink" target="_blank" rel="noreferrer">Map</a></p>
    </section>

    <footer class="footer">{{ t('footer.copyright') }}</footer>
  </div>
</template>
