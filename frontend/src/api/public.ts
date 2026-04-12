import { apiClient, type ApiResponse } from './client'

export interface LabProfile {
  nameZh: string
  nameEn: string
  sloganZh: string
  sloganEn: string
  email: string
  phone: string
}

export interface ResearchArea {
  id: number
  key: string
  displayOrder: number
  nameZh: string
  summaryZh: string
  nameEn: string
  summaryEn: string
}

export interface NewsItem {
  id: number
  slug: string
  publishedAt: string
  titleZh: string
  titleEn: string
}

export async function fetchProfile() {
  const response = await apiClient.get<ApiResponse<LabProfile>>('/public/profile')
  return response.data.data
}

export async function fetchResearchAreas() {
  const response = await apiClient.get<ApiResponse<ResearchArea[]>>('/public/research-areas')
  return response.data.data
}

export async function fetchNews() {
  const response = await apiClient.get<ApiResponse<NewsItem[]>>('/public/news')
  return response.data.data
}
