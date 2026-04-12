<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { createNews, createResearchArea, deleteNews, deleteResearchArea, listAdminNews, listAdminResearchAreas, type NewsPayload, type ResearchAreaPayload } from '../../api/admin'
import type { NewsItem, ResearchArea } from '../../api/public'
import { clearToken } from '../../utils/auth'

const router = useRouter()
const loading = ref(false)
const areas = ref<ResearchArea[]>([])
const newsList = ref<NewsItem[]>([])

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

async function loadData() {
  loading.value = true
  try {
    const [areaData, newsData] = await Promise.all([listAdminResearchAreas(), listAdminNews()])
    areas.value = areaData
    newsList.value = newsData
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
  </div>
</template>
