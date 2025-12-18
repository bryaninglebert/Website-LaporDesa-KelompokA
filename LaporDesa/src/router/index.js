import { createRouter, createWebHistory } from 'vue-router'

// 1. Impor semua komponen halaman Anda
import LandingPage from '../components/LandingPage.vue'
import FormLaporan from '../components/FormLaporan.vue'
import DaftarLaporan from '../components/DaftarLaporan.vue'
import AdminLogin from '../components/AdminLogin.vue'
import AdminDashboard from '../components/AdminDashboard.vue'
import LaporanDetail from '../components/LaporanDetail.vue'
import UbahPassword from '../components/UbahPassword.vue' // Halaman Keamanan

// 2. Tentukan "rute" atau "link" Anda
const routes = [
  {
    path: '/',
    name: 'Landing',
    component: LandingPage,
  },
  {
    path: '/lapor',
    name: 'FormLaporan',
    component: FormLaporan,
  },
  {
    path: '/daftar',
    name: 'DaftarLaporan',
    component: DaftarLaporan,
  },
  {
    path: '/admin-login',
    name: 'AdminLogin',
    component: AdminLogin,
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: AdminDashboard,
  },
  {
    // Rute dinamis untuk detail laporan (misal: /admin/laporan/1, /admin/laporan/2)
    path: '/admin/laporan/:id',
    name: 'LaporanDetail',
    component: LaporanDetail,
  },
  {
    // Rute baru untuk halaman ganti password
    path: '/admin/keamanan',
    name: 'UbahPassword',
    component: UbahPassword,
  },
]

// 3. Buat instansi router
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes, // Gunakan array routes di atas
})

export default router
