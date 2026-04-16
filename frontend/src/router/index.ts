import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/public/pages/HomePage.vue'
import ResearchPage from '../views/public/pages/ResearchPage.vue'
import PublicationsPage from '../views/public/pages/PublicationsPage.vue'
import NewsPage from '../views/public/pages/NewsPage.vue'
import ActivitiesPage from '../views/public/pages/ActivitiesPage.vue'
import MembersPage from '../views/public/pages/MembersPage.vue'
import ContactPage from '../views/public/pages/ContactPage.vue'
import AdminLoginView from '../views/admin/AdminLoginView.vue'
import AdminDashboardView from '../views/admin/AdminDashboardView.vue'
import { getToken } from '../utils/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: HomePage },
    { path: '/research', component: ResearchPage },
    { path: '/publications', component: PublicationsPage },
    { path: '/news', component: NewsPage },
    { path: '/activities', component: ActivitiesPage },
    { path: '/members', component: MembersPage },
    { path: '/contact', component: ContactPage },
    { path: '/admin/login', component: AdminLoginView },
    { path: '/admin', component: AdminDashboardView },
  ],
})

router.beforeEach((to) => {
  if (to.path.startsWith('/admin') && to.path !== '/admin/login' && !getToken()) {
    return '/admin/login'
  }
  return true
})

export default router
