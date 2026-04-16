import axios from 'axios'
import { getToken } from '../utils/auth'

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  requestId: string
}

export const apiClient = axios.create({
  baseURL: '/api/v1',
  timeout: 10000,
})

apiClient.interceptors.request.use((config) => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})
