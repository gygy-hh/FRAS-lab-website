<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import PublicHeader from '../../../components/public/PublicHeader.vue'
import { fetchActivities, type ActivityItem } from '../../../api/public'

const { t, locale } = useI18n()
const list = ref<ActivityItem[]>([])

function titleOf(item: ActivityItem) {
  return locale.value === 'zh-CN' ? item.titleZh : item.titleEn
}

onMounted(async () => {
  list.value = await fetchActivities()
})
</script>

<template>
  <div class="layout">
    <PublicHeader />
    <section class="section-card">
      <h2>{{ t('nav.activity') }}</h2>
      <ul class="news-list">
        <li v-for="item in list" :key="item.id">
          {{ titleOf(item) }} ({{ item.startTime }} @ {{ item.location }})
          <img v-if="item.imageUrl" :src="item.imageUrl" alt="activity image" class="thumb" />
        </li>
      </ul>
    </section>
  </div>
</template>
