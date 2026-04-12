import { apiClient, type ApiResponse } from './client'
import type { NewsItem, ResearchArea } from './public'

export interface LoginResponse {
  token: string
  expireSeconds: number
  username: string
}

export interface ResearchAreaPayload {
  key: string
  displayOrder: number
  active: boolean
  nameZh: string
  summaryZh: string
  nameEn: string
  summaryEn: string
}

export interface NewsPayload {
  slug: string
  status: string
  publishedAt: string
  titleZh: string
  contentZh: string
  titleEn: string
  contentEn: string
}

export async function login(username: string, password: string) {
  const response = await apiClient.post<ApiResponse<LoginResponse>>('/auth/login', { username, password })
  return response.data.data
}

export async function listAdminResearchAreas() {
  const response = await apiClient.get<ApiResponse<ResearchArea[]>>('/admin/research-areas')
  return response.data.data
}

export async function createResearchArea(payload: ResearchAreaPayload) {
  const response = await apiClient.post<ApiResponse<ResearchArea>>('/admin/research-areas', payload)
  return response.data.data
}

export async function updateResearchArea(id: number, payload: ResearchAreaPayload) {
  const response = await apiClient.put<ApiResponse<ResearchArea>>(`/admin/research-areas/${id}`, payload)
  return response.data.data
}

export async function deleteResearchArea(id: number) {
  await apiClient.delete(`/admin/research-areas/${id}`)
}

export async function listAdminNews() {
  const response = await apiClient.get<ApiResponse<NewsItem[]>>('/admin/news')
  return response.data.data
}

export async function createNews(payload: NewsPayload) {
  const response = await apiClient.post<ApiResponse<NewsItem>>('/admin/news', payload)
  return response.data.data
}

export async function deleteNews(id: number) {
  await apiClient.delete(`/admin/news/${id}`)
}
