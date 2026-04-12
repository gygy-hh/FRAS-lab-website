import { apiClient, type ApiResponse } from './client'
import type { ActivityItem, AdvisorItem, LabProfile, MemberItem, NewsItem, PublicationItem, ResearchArea } from './public'

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

export interface AdvisorPayload {
  code: string
  email: string
  phone: string
  active: boolean
  fullNameZh: string
  titleZh: string
  summaryZh: string
  fullNameEn: string
  titleEn: string
  summaryEn: string
}

export interface MemberPayload {
  code: string
  roleType: string
  email: string
  enrollmentYear: number
  active: boolean
  fullNameZh: string
  bioZh: string
  fullNameEn: string
  bioEn: string
}

export interface PublicationPayload {
  slug: string
  pubYear: number
  pubDate: string
  category: string
  externalUrl: string
  active: boolean
  titleZh: string
  authorsZh: string
  abstractZh: string
  titleEn: string
  authorsEn: string
  abstractEn: string
}

export interface ActivityPayload {
  slug: string
  startTime: string
  endTime: string
  location: string
  active: boolean
  titleZh: string
  descriptionZh: string
  titleEn: string
  descriptionEn: string
}

export interface LabProfilePayload {
  nameZh: string
  nameEn: string
  sloganZh: string
  sloganEn: string
  email: string
  phone: string
  addressZh: string
  addressEn: string
  mapLink: string
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

export async function listAdminAdvisors() {
  const response = await apiClient.get<ApiResponse<AdvisorItem[]>>('/admin/advisors')
  return response.data.data
}

export async function createAdvisor(payload: AdvisorPayload) {
  const response = await apiClient.post<ApiResponse<AdvisorItem>>('/admin/advisors', payload)
  return response.data.data
}

export async function deleteAdvisor(id: number) {
  await apiClient.delete(`/admin/advisors/${id}`)
}

export async function listAdminMembers() {
  const response = await apiClient.get<ApiResponse<MemberItem[]>>('/admin/members')
  return response.data.data
}

export async function createMember(payload: MemberPayload) {
  const response = await apiClient.post<ApiResponse<MemberItem>>('/admin/members', payload)
  return response.data.data
}

export async function deleteMember(id: number) {
  await apiClient.delete(`/admin/members/${id}`)
}

export async function listAdminPublications() {
  const response = await apiClient.get<ApiResponse<PublicationItem[]>>('/admin/publications')
  return response.data.data
}

export async function createPublication(payload: PublicationPayload) {
  const response = await apiClient.post<ApiResponse<PublicationItem>>('/admin/publications', payload)
  return response.data.data
}

export async function deletePublication(id: number) {
  await apiClient.delete(`/admin/publications/${id}`)
}

export async function listAdminActivities() {
  const response = await apiClient.get<ApiResponse<ActivityItem[]>>('/admin/activities')
  return response.data.data
}

export async function createActivity(payload: ActivityPayload) {
  const response = await apiClient.post<ApiResponse<ActivityItem>>('/admin/activities', payload)
  return response.data.data
}

export async function deleteActivity(id: number) {
  await apiClient.delete(`/admin/activities/${id}`)
}

export async function getAdminLabProfile() {
  const response = await apiClient.get<ApiResponse<LabProfile>>('/admin/lab-profile')
  return response.data.data
}

export async function updateAdminLabProfile(payload: LabProfilePayload) {
  const response = await apiClient.put<ApiResponse<LabProfile>>('/admin/lab-profile', payload)
  return response.data.data
}
