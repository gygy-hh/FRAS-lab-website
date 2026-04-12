import { apiClient, type ApiResponse } from './client'

export interface LabProfile {
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

export interface PublicationItem {
  id: number
  slug: string
  pubYear: number
  pubDate: string
  category: string
  externalUrl: string
  titleZh: string
  authorsZh: string
  abstractZh: string
  titleEn: string
  authorsEn: string
  abstractEn: string
}

export interface ActivityItem {
  id: number
  slug: string
  startTime: string
  endTime: string
  location: string
  titleZh: string
  descriptionZh: string
  titleEn: string
  descriptionEn: string
}

export interface AdvisorItem {
  id: number
  code: string
  email: string
  phone: string
  fullNameZh: string
  titleZh: string
  summaryZh: string
  fullNameEn: string
  titleEn: string
  summaryEn: string
}

export interface MemberItem {
  id: number
  code: string
  roleType: string
  email: string
  enrollmentYear: number
  fullNameZh: string
  bioZh: string
  fullNameEn: string
  bioEn: string
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

export async function fetchPublications() {
  const response = await apiClient.get<ApiResponse<PublicationItem[]>>('/public/publications')
  return response.data.data
}

export async function fetchAdvisors() {
  const response = await apiClient.get<ApiResponse<AdvisorItem[]>>('/public/advisors')
  return response.data.data
}

export async function fetchMembers() {
  const response = await apiClient.get<ApiResponse<MemberItem[]>>('/public/members')
  return response.data.data
}

export async function fetchActivities() {
  const response = await apiClient.get<ApiResponse<ActivityItem[]>>('/public/activities')
  return response.data.data
}
