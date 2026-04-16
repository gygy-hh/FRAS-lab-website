<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import PublicHeader from '../../../components/public/PublicHeader.vue'
import { fetchCarouselImages, fetchProfile, type CarouselImageItem, type LabProfile } from '../../../api/public'

const { t, locale } = useI18n()
const profile = ref<LabProfile | null>(null)
const carouselImages = ref<CarouselImageItem[]>([])
const currentImageIdx = ref(0)
let timer: number | null = null

const profileName = computed(() => {
  if (!profile.value) return 'FRAS-lab'
  return locale.value === 'zh-CN' ? profile.value.nameZh : profile.value.nameEn
})

const profileSlogan = computed(() => {
  if (!profile.value) return ''
  return locale.value === 'zh-CN' ? profile.value.sloganZh : profile.value.sloganEn
})

const profileIntro = computed(() => {
  if (!profile.value) return ''
  return locale.value === 'zh-CN' ? profile.value.introZh : profile.value.introEn
})

onMounted(async () => {
  const [profileData, carouselData] = await Promise.all([fetchProfile(), fetchCarouselImages()])
  profile.value = profileData
  carouselImages.value = carouselData
  if (carouselImages.value.length > 1) {
    timer = window.setInterval(() => {
      currentImageIdx.value = (currentImageIdx.value + 1) % carouselImages.value.length
    }, 4000)
  }
})

onUnmounted(() => {
  if (timer) {
    window.clearInterval(timer)
  }
})
</script>

<template>
  <div class="layout">
    <PublicHeader />
    <section class="hero">
      <h1>{{ profileName }}</h1>
      <p v-if="profileSlogan">{{ profileSlogan }}</p>
    </section>
    <section v-if="carouselImages.length" class="carousel-wrap">
      <img :src="carouselImages[currentImageIdx].imageUrl" alt="carousel" class="carousel-image" />
      <div class="carousel-dots">
        <button
          v-for="(item, idx) in carouselImages"
          :key="item.id"
          class="carousel-dot"
          :class="{ active: idx === currentImageIdx }"
          @click="currentImageIdx = idx"
        ></button>
      </div>
    </section>
    <section class="section-card">
      <h2>{{ t('page.homeIntroTitle') }}</h2>
      <p>{{ profileIntro || t('page.homeIntroText') }}</p>
    </section>
    <footer class="footer">{{ t('footer.copyright') }}</footer>
  </div>
</template>
