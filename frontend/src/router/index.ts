import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/public/HomeView.vue'
import AdminLoginView from '../views/admin/AdminLoginView.vue'
import AdminDashboardView from '../views/admin/AdminDashboardView.vue'
import { getToken } from '../utils/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: HomeView },
    { path: '/admin/login', component: AdminLoginView },
    { path: '/admin', component: AdminDashboardView },
  ],
})

router.beforeEach((to) => {
  if (to.path.startsWith('/admin') && to.path !== '/admin/login' && !getToken()) {
    return '/admin/login'
  }
  if (to.path === '/admin/login' && getToken()) {
    return '/admin'
  }
  return true
})

export default router
