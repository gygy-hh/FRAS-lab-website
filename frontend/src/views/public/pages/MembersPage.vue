<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import PublicHeader from '../../../components/public/PublicHeader.vue'
import { fetchMembers, type MemberItem } from '../../../api/public'

const { t, locale } = useI18n()
const list = ref<MemberItem[]>([])

function nameOf(item: MemberItem) {
  return locale.value === 'zh-CN' ? item.fullNameZh : item.fullNameEn
}

onMounted(async () => {
  list.value = await fetchMembers()
})
</script>

<template>
  <div class="layout">
    <PublicHeader />
    <section class="section-card">
      <h2>{{ t('nav.member') }}</h2>
      <ul class="news-list">
        <li v-for="item in list" :key="item.id">
          {{ nameOf(item) }} - {{ item.roleType }}
          <a v-if="item.cvPdfUrl" :href="item.cvPdfUrl" target="_blank" rel="noreferrer">简历/CV</a>
        </li>
      </ul>
    </section>
  </div>
</template>
