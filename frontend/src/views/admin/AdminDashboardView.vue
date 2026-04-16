<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  createCarouselImage,
  createActivity,
  createMember,
  createNews,
  createPublication,
  createResearchArea,
  deleteCarouselImage,
  deleteActivity,
  deleteMember,
  deleteNews,
  deletePublication,
  deleteResearchArea,
  getAdminLabProfile,
  listAdminActivities,
  listAdminCarouselImages,
  listAdminMembers,
  listAdminNews,
  listAdminPublications,
  listAdminResearchAreas,
  uploadLocalFile,
  updateActivity,
  updateCarouselImage,
  updateAdminLabProfile,
  updateResearchArea,
  updateMember,
  updateNews,
  updatePublication,
  type ActivityPayload,
  type CarouselImagePayload,
  type LabProfilePayload,
  type MemberPayload,
  type NewsPayload,
  type PublicationPayload,
  type ResearchAreaPayload,
} from '../../api/admin'
import type { ActivityItem, CarouselImageItem, MemberItem, NewsItem, PublicationItem, ResearchArea } from '../../api/public'
import { clearToken } from '../../utils/auth'

const router = useRouter()
const loading = ref(false)
const editingAreaId = ref<number | null>(null)
const editingCarouselId = ref<number | null>(null)
const editingPublicationId = ref<number | null>(null)
const editingActivityId = ref<number | null>(null)
const editingNewsId = ref<number | null>(null)
const editingMemberId = ref<number | null>(null)
const areas = ref<ResearchArea[]>([])
const carouselImages = ref<CarouselImageItem[]>([])
const newsList = ref<NewsItem[]>([])
const publications = ref<PublicationItem[]>([])
const activities = ref<ActivityItem[]>([])
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
  introZh: '',
  introEn: '',
})

function createAreaForm(): ResearchAreaPayload {
  return {
    key: '',
    displayOrder: 10,
    active: true,
    nameZh: '',
    summaryZh: '',
    nameEn: '',
    summaryEn: '',
  }
}

const areaForm = ref<ResearchAreaPayload>(createAreaForm())

function createCarouselForm(): CarouselImagePayload {
  return {
    imageUrl: '',
    displayOrder: 1,
    active: true,
  }
}

const carouselForm = ref<CarouselImagePayload>(createCarouselForm())

function createNewsForm(): NewsPayload {
  return {
    slug: '',
    status: 'PUBLISHED',
    publishedAt: new Date().toISOString().slice(0, 19),
    imageUrl: '',
    titleZh: '',
    contentZh: '',
    titleEn: '',
    contentEn: '',
  }
}

const newsForm = ref<NewsPayload>(createNewsForm())

function createPublicationForm(): PublicationPayload {
  return {
    slug: '',
    pubYear: new Date().getFullYear(),
    pubDate: new Date().toISOString().slice(0, 10),
    category: 'Journal',
    externalUrl: '',
    imageUrl: '',
    active: true,
    titleZh: '',
    authorsZh: '',
    abstractZh: '',
    titleEn: '',
    authorsEn: '',
    abstractEn: '',
  }
}

const publicationForm = ref<PublicationPayload>(createPublicationForm())

function createActivityForm(): ActivityPayload {
  return {
    slug: '',
    startTime: new Date().toISOString().slice(0, 19),
    endTime: new Date().toISOString().slice(0, 19),
    location: '',
    imageUrl: '',
    active: true,
    titleZh: '',
    descriptionZh: '',
    titleEn: '',
    descriptionEn: '',
  }
}

const activityForm = ref<ActivityPayload>(createActivityForm())

function createMemberForm(): MemberPayload {
  return {
    code: '',
    roleType: 'Master',
    email: '',
    cvPdfUrl: '',
    enrollmentYear: new Date().getFullYear(),
    active: true,
    fullNameZh: '',
    bioZh: '',
    fullNameEn: '',
    bioEn: '',
  }
}

const memberForm = ref<MemberPayload>(createMemberForm())

function getErrorMessage(error: unknown) {
  const maybeAxiosError = error as { response?: { data?: { message?: string } }; message?: string }
  return maybeAxiosError?.response?.data?.message ?? maybeAxiosError?.message ?? '保存失败，请检查输入后重试。'
}

async function loadData() {
  loading.value = true
  try {
    const [profileData, areaData, carouselData, newsData, publicationData, activityData, memberData] = await Promise.all([
      getAdminLabProfile(),
      listAdminResearchAreas(),
      listAdminCarouselImages(),
      listAdminNews(),
      listAdminPublications(),
      listAdminActivities(),
      listAdminMembers(),
    ])
    labProfile.value = profileData
    areas.value = areaData
    carouselImages.value = carouselData
    newsList.value = newsData
    publications.value = publicationData
    activities.value = activityData
    members.value = memberData
  } finally {
    loading.value = false
  }
}

function resetAreaForm() {
  editingAreaId.value = null
  areaForm.value = createAreaForm()
}

function editArea(item: ResearchArea) {
  editingAreaId.value = item.id
  areaForm.value = {
    key: item.key,
    displayOrder: item.displayOrder,
    active: true,
    nameZh: item.nameZh,
    summaryZh: item.summaryZh,
    nameEn: item.nameEn,
    summaryEn: item.summaryEn,
  }
}

async function addArea() {
  if (editingAreaId.value) {
    await updateResearchArea(editingAreaId.value, areaForm.value)
  } else {
    await createResearchArea(areaForm.value)
  }
  await loadData()
  resetAreaForm()
}

async function removeArea(id: number) {
  await deleteResearchArea(id)
  await loadData()
  if (editingAreaId.value === id) {
    resetAreaForm()
  }
}

function resetCarouselForm() {
  editingCarouselId.value = null
  carouselForm.value = createCarouselForm()
}

function editCarousel(item: CarouselImageItem) {
  editingCarouselId.value = item.id
  carouselForm.value = {
    imageUrl: item.imageUrl,
    displayOrder: item.displayOrder,
    active: item.active,
  }
}

async function saveCarousel() {
  try {
    if (editingCarouselId.value) {
      await updateCarouselImage(editingCarouselId.value, carouselForm.value)
    } else {
      await createCarouselImage(carouselForm.value)
    }
    await loadData()
    resetCarouselForm()
    alert('轮播图保存成功')
  } catch (error) {
    alert(getErrorMessage(error))
  }
}

async function removeCarousel(id: number) {
  try {
    await deleteCarouselImage(id)
    await loadData()
    if (editingCarouselId.value === id) {
      resetCarouselForm()
    }
  } catch (error) {
    alert(getErrorMessage(error))
  }
}

function resetNewsForm() {
  editingNewsId.value = null
  newsForm.value = createNewsForm()
}

function editNews(item: NewsItem) {
  editingNewsId.value = item.id
  newsForm.value = {
    slug: item.slug,
    status: 'PUBLISHED',
    publishedAt: item.publishedAt,
    imageUrl: item.imageUrl ?? '',
    titleZh: item.titleZh,
    contentZh: item.contentZh,
    titleEn: item.titleEn,
    contentEn: item.contentEn,
  }
}

async function addNews() {
  if (editingNewsId.value) {
    await updateNews(editingNewsId.value, newsForm.value)
  } else {
    await createNews(newsForm.value)
  }
  await loadData()
  resetNewsForm()
}

async function removeNews(id: number) {
  await deleteNews(id)
  await loadData()
  if (editingNewsId.value === id) {
    resetNewsForm()
  }
}

function resetPublicationForm() {
  editingPublicationId.value = null
  publicationForm.value = createPublicationForm()
}

function editPublication(item: PublicationItem) {
  editingPublicationId.value = item.id
  publicationForm.value = {
    slug: item.slug,
    pubYear: item.pubYear,
    pubDate: item.pubDate,
    category: item.category,
    externalUrl: item.externalUrl ?? '',
    imageUrl: item.imageUrl ?? '',
    active: true,
    titleZh: item.titleZh,
    authorsZh: item.authorsZh,
    abstractZh: item.abstractZh,
    titleEn: item.titleEn,
    authorsEn: item.authorsEn,
    abstractEn: item.abstractEn,
  }
}

async function addPublication() {
  if (editingPublicationId.value) {
    await updatePublication(editingPublicationId.value, publicationForm.value)
  } else {
    await createPublication(publicationForm.value)
  }
  await loadData()
  resetPublicationForm()
}

async function removePublication(id: number) {
  await deletePublication(id)
  await loadData()
  if (editingPublicationId.value === id) {
    resetPublicationForm()
  }
}

function resetActivityForm() {
  editingActivityId.value = null
  activityForm.value = createActivityForm()
}

function editActivity(item: ActivityItem) {
  editingActivityId.value = item.id
  activityForm.value = {
    slug: item.slug,
    startTime: item.startTime,
    endTime: item.endTime,
    location: item.location,
    imageUrl: item.imageUrl ?? '',
    active: true,
    titleZh: item.titleZh,
    descriptionZh: item.descriptionZh,
    titleEn: item.titleEn,
    descriptionEn: item.descriptionEn,
  }
}

async function addActivity() {
  if (editingActivityId.value) {
    await updateActivity(editingActivityId.value, activityForm.value)
  } else {
    await createActivity(activityForm.value)
  }
  await loadData()
  resetActivityForm()
}

async function removeActivity(id: number) {
  await deleteActivity(id)
  await loadData()
  if (editingActivityId.value === id) {
    resetActivityForm()
  }
}

function resetMemberForm() {
  editingMemberId.value = null
  memberForm.value = createMemberForm()
}

function editMember(item: MemberItem) {
  editingMemberId.value = item.id
  memberForm.value = {
    code: item.code,
    roleType: item.roleType,
    email: item.email ?? '',
    cvPdfUrl: item.cvPdfUrl ?? '',
    enrollmentYear: item.enrollmentYear,
    active: true,
    fullNameZh: item.fullNameZh,
    bioZh: item.bioZh,
    fullNameEn: item.fullNameEn,
    bioEn: item.bioEn,
  }
}

async function addMember() {
  try {
    if (editingMemberId.value) {
      await updateMember(editingMemberId.value, memberForm.value)
    } else {
      await createMember(memberForm.value)
    }
    await loadData()
    resetMemberForm()
  } catch (error) {
    alert(getErrorMessage(error))
  }
}

async function removeMember(id: number) {
  await deleteMember(id)
  await loadData()
  if (editingMemberId.value === id) {
    resetMemberForm()
  }
}

async function saveLabProfile() {
  await updateAdminLabProfile(labProfile.value)
  await loadData()
}

async function uploadCarouselImage(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (!file) return
  try {
    const result = await uploadLocalFile(file, 'image')
    carouselForm.value.imageUrl = result.url
  } catch (error) {
    alert(getErrorMessage(error))
  }
}

async function uploadNewsImage(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (!file) return
  const result = await uploadLocalFile(file, 'image')
  newsForm.value.imageUrl = result.url
}

async function uploadActivityImage(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (!file) return
  const result = await uploadLocalFile(file, 'image')
  activityForm.value.imageUrl = result.url
}

async function uploadPublicationImage(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (!file) return
  const result = await uploadLocalFile(file, 'image')
  publicationForm.value.imageUrl = result.url
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
      <div class="actions">
        <router-link class="lang-btn" to="/">返回主页</router-link>
        <button class="lang-btn" @click="logout">退出登录</button>
      </div>
    </header>

    <section class="section-card">
      <h2>实验室信息 / 联系方式</h2>
      <div class="admin-grid">
        <input v-model="labProfile.nameZh" placeholder="实验室名称（中文）" />
        <input v-model="labProfile.nameEn" placeholder="实验室名称（英文）" />
        <input v-model="labProfile.sloganZh" placeholder="口号（中文）" />
        <input v-model="labProfile.sloganEn" placeholder="口号（英文）" />
        <input v-model="labProfile.email" placeholder="联系邮箱" />
        <input v-model="labProfile.phone" placeholder="联系电话" />
        <input v-model="labProfile.addressZh" placeholder="地址（中文）" />
        <input v-model="labProfile.addressEn" placeholder="地址（英文）" />
      </div>
      <textarea v-model="labProfile.introZh" rows="4" placeholder="实验室简介（中文）"></textarea>
      <textarea v-model="labProfile.introEn" rows="4" placeholder="实验室简介（英文）"></textarea>
      <button class="lang-btn dark" @click="saveLabProfile">保存</button>
    </section>

    <section class="section-card">
      <h2>首页轮播图</h2>
      <div class="admin-grid">
        <input v-model="carouselForm.imageUrl" placeholder="图片 URL（可自动上传填充）" />
        <input type="file" accept="image/*" @change="uploadCarouselImage" />
        <input v-model.number="carouselForm.displayOrder" placeholder="排序（数字越小越靠前）" type="number" />
        <label><input v-model="carouselForm.active" type="checkbox" /> 启用</label>
      </div>
      <button class="lang-btn dark" @click="saveCarousel">{{ editingCarouselId ? '更新' : '保存' }}</button>
      <button v-if="editingCarouselId" class="ghost-btn" @click="resetCarouselForm">取消编辑</button>
      <ul class="news-list">
        <li v-for="item in carouselImages" :key="item.id">
          #{{ item.displayOrder }} - {{ item.imageUrl }}
          <button class="edit-btn" @click="editCarousel(item)">编辑</button>
          <button class="danger-btn" @click="removeCarousel(item.id)">删除</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>研究方向</h2>
      <p v-if="loading">加载中...</p>
      <div class="admin-grid">
        <input v-model="areaForm.key" placeholder="唯一标识（key）" />
        <input v-model="areaForm.nameZh" placeholder="方向名称（中文）" />
        <input v-model="areaForm.nameEn" placeholder="方向名称（英文）" />
        <input v-model.number="areaForm.displayOrder" placeholder="排序（数字越小越靠前）" type="number" />
        <input v-model="areaForm.summaryZh" placeholder="简介（中文）" />
        <input v-model="areaForm.summaryEn" placeholder="简介（英文）" />
      </div>
      <button class="lang-btn dark" @click="addArea">{{ editingAreaId ? '更新' : '保存' }}</button>
      <button v-if="editingAreaId" class="ghost-btn" @click="resetAreaForm">取消编辑</button>
      <ul class="news-list">
        <li v-for="item in areas" :key="item.id">
          {{ item.nameZh }} / {{ item.nameEn }}
          <button class="edit-btn" @click="editArea(item)">编辑</button>
          <button class="danger-btn" @click="removeArea(item.id)">删除</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>成果</h2>
      <div class="admin-grid">
        <input v-model="publicationForm.slug" placeholder="唯一标识（slug）" />
        <input v-model.number="publicationForm.pubYear" placeholder="发表年份" type="number" />
        <input v-model="publicationForm.pubDate" placeholder="发表日期（YYYY-MM-DD）" />
        <input v-model="publicationForm.category" placeholder="类型（Journal/Conference）" />
        <input v-model="publicationForm.externalUrl" placeholder="外部链接 URL" />
        <input v-model="publicationForm.imageUrl" placeholder="图片 URL（可自动上传填充）" />
        <input type="file" accept="image/*" @change="uploadPublicationImage" />
        <input v-model="publicationForm.titleZh" placeholder="标题（中文）" />
        <input v-model="publicationForm.titleEn" placeholder="标题（英文）" />
      </div>
      <textarea v-model="publicationForm.authorsZh" rows="2" placeholder="作者（中文）"></textarea>
      <textarea v-model="publicationForm.authorsEn" rows="2" placeholder="作者（英文）"></textarea>
      <textarea v-model="publicationForm.abstractZh" rows="3" placeholder="摘要（中文）"></textarea>
      <textarea v-model="publicationForm.abstractEn" rows="3" placeholder="摘要（英文）"></textarea>
      <button class="lang-btn dark" @click="addPublication">{{ editingPublicationId ? '更新' : '保存' }}</button>
      <button v-if="editingPublicationId" class="ghost-btn" @click="resetPublicationForm">取消编辑</button>
      <ul class="news-list">
        <li v-for="item in publications" :key="item.id">
          {{ item.titleZh }} / {{ item.titleEn }} ({{ item.pubYear }})
          <button class="edit-btn" @click="editPublication(item)">编辑</button>
          <button class="danger-btn" @click="removePublication(item.id)">删除</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>活动</h2>
      <div class="admin-grid">
        <input v-model="activityForm.slug" placeholder="唯一标识（slug）" />
        <input v-model="activityForm.startTime" placeholder="开始时间（YYYY-MM-DDTHH:mm:ss）" />
        <input v-model="activityForm.endTime" placeholder="结束时间（YYYY-MM-DDTHH:mm:ss）" />
        <input v-model="activityForm.location" placeholder="地点" />
        <input v-model="activityForm.imageUrl" placeholder="图片 URL（可自动上传填充）" />
        <input type="file" accept="image/*" @change="uploadActivityImage" />
        <input v-model="activityForm.titleZh" placeholder="标题（中文）" />
        <input v-model="activityForm.titleEn" placeholder="标题（英文）" />
      </div>
      <textarea v-model="activityForm.descriptionZh" rows="3" placeholder="描述（中文）"></textarea>
      <textarea v-model="activityForm.descriptionEn" rows="3" placeholder="描述（英文）"></textarea>
      <button class="lang-btn dark" @click="addActivity">{{ editingActivityId ? '更新' : '保存' }}</button>
      <button v-if="editingActivityId" class="ghost-btn" @click="resetActivityForm">取消编辑</button>
      <ul class="news-list">
        <li v-for="item in activities" :key="item.id">
          {{ item.titleZh }} / {{ item.titleEn }} ({{ item.startTime }})
          <button class="edit-btn" @click="editActivity(item)">编辑</button>
          <button class="danger-btn" @click="removeActivity(item.id)">删除</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>新闻</h2>
      <div class="admin-grid">
        <input v-model="newsForm.slug" placeholder="唯一标识（slug）" />
        <input v-model="newsForm.status" placeholder="状态（PUBLISHED/DRAFT）" />
        <input v-model="newsForm.publishedAt" placeholder="发布时间（YYYY-MM-DDTHH:mm:ss）" />
        <input v-model="newsForm.imageUrl" placeholder="图片 URL（可自动上传填充）" />
        <input type="file" accept="image/*" @change="uploadNewsImage" />
        <input v-model="newsForm.titleZh" placeholder="标题（中文）" />
        <input v-model="newsForm.titleEn" placeholder="标题（英文）" />
      </div>
      <textarea v-model="newsForm.contentZh" rows="4" placeholder="正文（中文）"></textarea>
      <textarea v-model="newsForm.contentEn" rows="4" placeholder="正文（英文）"></textarea>
      <button class="lang-btn dark" @click="addNews">{{ editingNewsId ? '更新' : '保存' }}</button>
      <button v-if="editingNewsId" class="ghost-btn" @click="resetNewsForm">取消编辑</button>
      <ul class="news-list">
        <li v-for="item in newsList" :key="item.id">
          {{ item.titleZh }} / {{ item.titleEn }}
          <button class="edit-btn" @click="editNews(item)">编辑</button>
          <button class="danger-btn" @click="removeNews(item.id)">删除</button>
        </li>
      </ul>
    </section>

    <section class="section-card">
      <h2>成员</h2>
      <div class="admin-grid">
        <input v-model="memberForm.code" placeholder="唯一标识（code）" />
        <input v-model="memberForm.roleType" placeholder="角色类型（Master/PhD等）" />
        <input v-model="memberForm.email" placeholder="邮箱" />
        <input v-model="memberForm.cvPdfUrl" placeholder="简历链接（支持 PDF/HTM/HTML）" />
        <input v-model.number="memberForm.enrollmentYear" placeholder="入学年份" type="number" />
        <input v-model="memberForm.fullNameZh" placeholder="姓名（中文）" />
        <input v-model="memberForm.fullNameEn" placeholder="姓名（英文）" />
        <input v-model="memberForm.bioZh" placeholder="简介（中文）" />
        <input v-model="memberForm.bioEn" placeholder="简介（英文）" />
      </div>
      <button class="lang-btn dark" @click="addMember">{{ editingMemberId ? '更新' : '保存' }}</button>
      <button v-if="editingMemberId" class="ghost-btn" @click="resetMemberForm">取消编辑</button>
      <ul class="news-list">
        <li v-for="item in members" :key="item.id">
          {{ item.fullNameZh }} / {{ item.fullNameEn }} - {{ item.roleType }}
          <button class="edit-btn" @click="editMember(item)">编辑</button>
          <button class="danger-btn" @click="removeMember(item.id)">删除</button>
        </li>
      </ul>
    </section>
  </div>
</template>
