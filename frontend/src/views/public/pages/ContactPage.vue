<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import PublicHeader from '../../../components/public/PublicHeader.vue'
import { fetchProfile, type LabProfile } from '../../../api/public'

const { t, locale } = useI18n()
const profile = ref<LabProfile | null>(null)

const address = computed(() => {
  if (!profile.value) return ''
  return locale.value === 'zh-CN' ? profile.value.addressZh : profile.value.addressEn
})

onMounted(async () => {
  profile.value = await fetchProfile()
})
</script>

<template>
  <div class="layout">
    <PublicHeader />
    <section class="section-card">
      <h2>{{ t('nav.contact') }}</h2>
      <p>{{ profile?.email }}</p>
      <p>{{ profile?.phone }}</p>
      <p>{{ address }}</p>
    </section>
  </div>
</template>
