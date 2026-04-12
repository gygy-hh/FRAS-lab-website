<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  createAdvisor,
  createActivity,
  createMember,
  createNews,
  createPublication,
  createResearchArea,
  deleteAdvisor,
  deleteActivity,
  deleteMember,
  deleteNews,
  deletePublication,
  deleteResearchArea,
  getAdminLabProfile,
  listAdminAdvisors,
  listAdminActivities,
  listAdminMembers,
  listAdminNews,
  listAdminPublications,
  listAdminResearchAreas,
  updateAdminLabProfile,
  type ActivityPayload,
  type AdvisorPayload,
  type LabProfilePayload,
  type MemberPayload,
  type NewsPayload,
  type PublicationPayload,
  type ResearchAreaPayload,
} from '../../api/admin'
import type { ActivityItem, AdvisorItem, MemberItem, NewsItem, PublicationItem, ResearchArea } from '../../api/public'
import { clearToken } from '../../utils/auth'

const router = useRouter()
const loading = ref(false)
const areas = ref<ResearchArea[]>([])
const newsList = ref<NewsItem[]>([])
const publications = ref<PublicationItem[]>([])
const activities = ref<ActivityItem[]>([])
const advisors = ref<AdvisorItem[]>([])
const members = ref<MemberItem[]>([])
const labProfile = ref<LabProfilePayload>({
  nameZh: '',
  nameEn: '',
  sloganZh: '',
  sloganEn: '',
  email: '',
  phone: '',
  addressZh: '',
  addressEn: '',
  mapLink: '',
})

const areaForm = ref<ResearchAreaPayload>({
  key: '',
  displayOrder: 10,
  active: true,
  nameZh: '',
  summaryZh: '',
  nameEn: '',
  summaryEn: '',
})

const newsForm = ref<NewsPayload>({
  slug: '',
  status: 'PUBLISHED',
  publishedAt: new Date().toISOString().slice(0, 19),
  titleZh: '',
  contentZh: '',
  titleEn: '',
  contentEn: '',
})

const advisorForm = ref<AdvisorPayload>({
  code: '',
  email: '',
  phone: '',
  active: true,
  fullNameZh: '',
  titleZh: '',
  summaryZh: '',
  fullNameEn: '',
  titleEn: '',
  summaryEn: '',
})

const publicationForm = ref<PublicationPayload>({
  slug: '',
  pubYear: new Date().getFullYear(),
  pubDate: new Date().toISOString().slice(0, 10),
  category: 'Journal',
  externalUrl: '',
  active: true,
  titleZh: '',
  authorsZh: '',
  abstractZh: '',
  titleEn: '',
  authorsEn: '',
  abstractEn: '',
})

const activityForm = ref<ActivityPayload>({
  slug: '',
  startTime: new Date().toISOString().slice(0, 19),
  endTime: new Date().toISOString().slice(0, 19),
  location: '',
  active: true,
  titleZh: '',
  descriptionZh: '',
  titleEn: '',
  descriptionEn: '',
})

const memberForm = ref<MemberPayload>({
  code: '',
  roleType: 'Master',
  email: '',
  enrollmentYear: new Date().getFullYear(),
  active: true,
  fullNameZh: '',
  bioZh: '',
  fullNameEn: '',
  bioEn: '',
})

async function loadData() {
  loading.value = true
  try {
    const [profileData, areaData, newsData, publicationData, activityData, advisorData, memberData] = await Promise.all([
      getAdminLabProfile(),
      listAdminResearchAreas(),
      listAdminNews(),
      listAdminPublications(),
      listAdminActivities(),
      listAdminAdvisors(),
      listAdminMembers(),
    ])
    labProfile.value = profileData
    areas.value = areaData
    newsList.value = newsData
    publications.value = publicationData
    activities.value = activityData
    advisors.value = advisorData
    members.value = memberData
  } finally {
    loading.value = false
  }
}

async function addArea() {
  await createResearchArea(areaForm.value)
  await loadData()
}

async function removeArea(id: number) {
  await deleteResearchArea(id)
  await loadData()
}

async function addNews() {
  await createNews(newsForm.value)
  await loadData()
}

async function removeNews(id: number) {
  await deleteNews(id)
  await loadData()
}

async function addPublication() {
  await createPublication(publicationForm.value)
  await loadData()
}

async function removePublication(id: number) {
  await deletePublication(id)
  await loadData()
}

async function addActivity() {
  await createActivity(activityForm.value)
  await loadData()
}

async function removeActivity(id: number) {
  await deleteActivity(id)
  await loadData()
}

async function addAdvisor() {
  await createAdvisor(advisorForm.value)
  await loadData()
}

async function removeAdvisor(id: number) {
  await deleteAdvisor(id)
  await loadData()
}

async function addMember() {
  await createMember(memberForm.value)
  await loadData()
}

async function removeMember(id: number) {
  await deleteMember(id)
  await loadData()
}

async function saveLabProfile() {
  await updateAdminLabProfile(labProfile.value)
  await loadData()
}

function logout() {
  clearToken()
  router.push('/admin/login')
}

onMounted(loadData)
</script>

<template>
  <div class="layout">
    <header class="topbar">
      <div class="brand">FRAS-lab Admin</div>
      <button class="lang-btn" @click="logout">Logout</button>
    </header>

    <section class="section-card">
      <h2>Lab Profile / Contact</h2>
      <div class="admin-grid">
        <input v-model="labProfile.nameZh" placeholder="nameZh" />
        <input v-model="labProfile.nameEn" placeholder="nameEn" />
        <input v-model="labProfile.sloganZh" placeholder="sloganZh" />
        <input v-model="labProfile.sloganEn" placeholder="sloganEn" />
        <input v-model="labProfile.email" placeholder="email" />
        <input v-model="labProfile.phone" placeholder="phone" />
        <input v-model="labProfile.addressZh" placeholder="addressZh" />
        <input v-model="labProfile.addressEn" placeholder="addressEn" />
        <input v-model="labProfile.mapLink" placeholder="mapLink" />
      </div>
      <button class="lang-btn dark" @click="saveLabProfile">Save</button>
    </section>

    <section class="section-card">
      <h2>Research Areas</h2>
      <p v-if="loading">Loading...</p>
      <div class="admin-grid">
        <input v-model="areaForm.key" placeholder="key" />
        <input v-model="areaForm.nameZh" placeholder="nameZh" />
        <input v-model="areaForm.nameEn" placeholder="nameEn" />
        <input v-model.number="areaForm.displayOrder" placeholder="displayOrder" type="number" />
        <input v-model="areaForm.summaryZh" placeholder="summaryZh" />
        <input v-model="areaForm.summaryEn" placeholder="summaryEn" />
      </div>
      <button class="lang-btn dark" @click="addArea">Save</button>
      <ul class="news-list">
        <li v-for="item in areas" :key="item.id">
          {{ item.nameZh }} / {{ item.nameEn }}
          <button class="danger-btn" @click="removeArea(item.id)">Delete</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>Publications</h2>
      <div class="admin-grid">
        <input v-model="publicationForm.slug" placeholder="slug" />
        <input v-model.number="publicationForm.pubYear" placeholder="pubYear" type="number" />
        <input v-model="publicationForm.pubDate" placeholder="YYYY-MM-DD" />
        <input v-model="publicationForm.category" placeholder="category" />
        <input v-model="publicationForm.externalUrl" placeholder="externalUrl" />
        <input v-model="publicationForm.titleZh" placeholder="titleZh" />
        <input v-model="publicationForm.titleEn" placeholder="titleEn" />
      </div>
      <textarea v-model="publicationForm.authorsZh" rows="2" placeholder="authorsZh"></textarea>
      <textarea v-model="publicationForm.authorsEn" rows="2" placeholder="authorsEn"></textarea>
      <textarea v-model="publicationForm.abstractZh" rows="3" placeholder="abstractZh"></textarea>
      <textarea v-model="publicationForm.abstractEn" rows="3" placeholder="abstractEn"></textarea>
      <button class="lang-btn dark" @click="addPublication">Save</button>
      <ul class="news-list">
        <li v-for="item in publications" :key="item.id">
          {{ item.titleZh }} / {{ item.titleEn }} ({{ item.pubYear }})
          <button class="danger-btn" @click="removePublication(item.id)">Delete</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>Activities</h2>
      <div class="admin-grid">
        <input v-model="activityForm.slug" placeholder="slug" />
        <input v-model="activityForm.startTime" placeholder="YYYY-MM-DDTHH:mm:ss" />
        <input v-model="activityForm.endTime" placeholder="YYYY-MM-DDTHH:mm:ss" />
        <input v-model="activityForm.location" placeholder="location" />
        <input v-model="activityForm.titleZh" placeholder="titleZh" />
        <input v-model="activityForm.titleEn" placeholder="titleEn" />
      </div>
      <textarea v-model="activityForm.descriptionZh" rows="3" placeholder="descriptionZh"></textarea>
      <textarea v-model="activityForm.descriptionEn" rows="3" placeholder="descriptionEn"></textarea>
      <button class="lang-btn dark" @click="addActivity">Save</button>
      <ul class="news-list">
        <li v-for="item in activities" :key="item.id">
          {{ item.titleZh }} / {{ item.titleEn }} ({{ item.startTime }})
          <button class="danger-btn" @click="removeActivity(item.id)">Delete</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>News</h2>
      <div class="admin-grid">
        <input v-model="newsForm.slug" placeholder="slug" />
        <input v-model="newsForm.status" placeholder="status" />
        <input v-model="newsForm.publishedAt" placeholder="YYYY-MM-DDTHH:mm:ss" />
        <input v-model="newsForm.titleZh" placeholder="titleZh" />
        <input v-model="newsForm.titleEn" placeholder="titleEn" />
      </div>
      <textarea v-model="newsForm.contentZh" rows="4" placeholder="contentZh"></textarea>
      <textarea v-model="newsForm.contentEn" rows="4" placeholder="contentEn"></textarea>
      <button class="lang-btn dark" @click="addNews">Save</button>
      <ul class="news-list">
        <li v-for="item in newsList" :key="item.id">
          {{ item.titleZh }} / {{ item.titleEn }}
          <button class="danger-btn" @click="removeNews(item.id)">Delete</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>Advisors</h2>
      <div class="admin-grid">
        <input v-model="advisorForm.code" placeholder="code" />
        <input v-model="advisorForm.email" placeholder="email" />
        <input v-model="advisorForm.phone" placeholder="phone" />
        <input v-model="advisorForm.fullNameZh" placeholder="fullNameZh" />
        <input v-model="advisorForm.fullNameEn" placeholder="fullNameEn" />
        <input v-model="advisorForm.titleZh" placeholder="titleZh" />
        <input v-model="advisorForm.titleEn" placeholder="titleEn" />
        <input v-model="advisorForm.summaryZh" placeholder="summaryZh" />
        <input v-model="advisorForm.summaryEn" placeholder="summaryEn" />
      </div>
      <button class="lang-btn dark" @click="addAdvisor">Save</button>
      <ul class="news-list">
        <li v-for="item in advisors" :key="item.id">
          {{ item.fullNameZh }} / {{ item.fullNameEn }}
          <button class="danger-btn" @click="removeAdvisor(item.id)">Delete</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>Members</h2>
      <div class="admin-grid">
        <input v-model="memberForm.code" placeholder="code" />
        <input v-model="memberForm.roleType" placeholder="roleType" />
        <input v-model="memberForm.email" placeholder="email" />
        <input v-model.number="memberForm.enrollmentYear" placeholder="enrollmentYear" type="number" />
        <input v-model="memberForm.fullNameZh" placeholder="fullNameZh" />
        <input v-model="memberForm.fullNameEn" placeholder="fullNameEn" />
        <input v-model="memberForm.bioZh" placeholder="bioZh" />
        <input v-model="memberForm.bioEn" placeholder="bioEn" />
      </div>
      <button class="lang-btn dark" @click="addMember">Save</button>
      <ul class="news-list">
        <li v-for="item in members" :key="item.id">
          {{ item.fullNameZh }} / {{ item.fullNameEn }} - {{ item.roleType }}
          <button class="danger-btn" @click="removeMember(item.id)">Delete</button>
        </li>
      </ul>
    </section>
  </div>
</template>
