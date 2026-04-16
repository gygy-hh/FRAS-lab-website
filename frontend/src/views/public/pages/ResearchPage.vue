<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import PublicHeader from '../../../components/public/PublicHeader.vue'
import { fetchResearchAreas, type ResearchArea } from '../../../api/public'

const { t, locale } = useI18n()
const list = ref<ResearchArea[]>([])

function nameOf(item: ResearchArea) {
  return locale.value === 'zh-CN' ? item.nameZh : item.nameEn
}

function summaryOf(item: ResearchArea) {
  return locale.value === 'zh-CN' ? item.summaryZh : item.summaryEn
}

onMounted(async () => {
  list.value = await fetchResearchAreas()
})
</script>

<template>
  <div class="layout">
    <PublicHeader />
    <section class="section-card">
      <h2>{{ t('nav.research') }}</h2>
      <div class="grid">
        <article v-for="item in list" :key="item.id" class="card">
          <h3>{{ nameOf(item) }}</h3>
          <p>{{ summaryOf(item) }}</p>
        </article>
      </div>
    </section>
  </div>
</template>
