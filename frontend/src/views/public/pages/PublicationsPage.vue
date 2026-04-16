<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import PublicHeader from '../../../components/public/PublicHeader.vue'
import { fetchPublications, type PublicationItem } from '../../../api/public'

const { t, locale } = useI18n()
const list = ref<PublicationItem[]>([])

function titleOf(item: PublicationItem) {
  return locale.value === 'zh-CN' ? item.titleZh : item.titleEn
}

onMounted(async () => {
  list.value = await fetchPublications()
})
</script>

<template>
  <div class="layout">
    <PublicHeader />
    <section class="section-card">
      <h2>{{ t('nav.publications') }}</h2>
      <ul class="news-list">
        <li v-for="item in list" :key="item.id">
          {{ titleOf(item) }} ({{ item.pubYear }})
          <img v-if="item.imageUrl" :src="item.imageUrl" alt="publication image" class="thumb" />
          <a v-if="item.externalUrl" :href="item.externalUrl" target="_blank" rel="noreferrer">{{ t('common.link') }}</a>
        </li>
      </ul>
    </section>
  </div>
</template>
