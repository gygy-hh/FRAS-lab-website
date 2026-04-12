import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  timeout: 5000,
})

export interface LabProfile {
  nameZh: string
  nameEn: string
  sloganZh: string
  sloganEn: string
  email: string
  phone: string
}

export interface ResearchArea {
  key: string
  nameZh: string
  nameEn: string
}

interface ApiResponse<T> {
  code: number
  message: string
  data: T
  requestId: string
}

export async function fetchProfile() {
  const response = await api.get<ApiResponse<LabProfile>>('/public/profile')
  return response.data.data
}

export async function fetchResearchAreas() {
  const response = await api.get<ApiResponse<ResearchArea[]>>('/public/research-areas')
  return response.data.data
}
