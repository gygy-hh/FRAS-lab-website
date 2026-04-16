<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { login } from '../../api/admin'
import { saveToken } from '../../utils/auth'

const { t } = useI18n()
const router = useRouter()
const username = ref('')
const password = ref('')
const loading = ref(false)
const errorMsg = ref('')

async function submit() {
  errorMsg.value = ''
  loading.value = true
  try {
    const result = await login(username.value, password.value)
    saveToken(result.token)
    router.push('/admin')
  } catch (error: any) {
    errorMsg.value = error?.response?.data?.message ?? 'Login failed'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="admin-login">
    <h1>{{ t('admin.login') }}</h1>
    <div class="form-item">
      <label>{{ t('admin.username') }}</label>
      <input v-model="username" />
    </div>
    <div class="form-item">
      <label>{{ t('admin.password') }}</label>
      <input v-model="password" type="password" />
    </div>
    <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
    <button class="lang-btn dark" :disabled="loading" @click="submit">{{ t('admin.login') }}</button>
  </div>
</template>
