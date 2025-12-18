<template>
  <div class="admin-layout">
    <nav class="sidebar">
      <div class="sidebar-header"><h2>Admin LaporDesa</h2></div>
      <ul class="nav-menu">
        <li class="nav-item" @click="goToDashboard">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
            <polyline points="9 22 9 12 15 12 15 22"></polyline>
          </svg>
          <span>Dashboard</span>
        </li>
        <li class="nav-item active">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
          </svg>
          <span>Keamanan</span>
        </li>
        <li class="nav-item" @click="logout">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
            <polyline points="16 17 21 12 16 7"></polyline>
            <line x1="21" y1="12" x2="9" y2="12"></line>
          </svg>
          <span>Logout</span>
        </li>
      </ul>
    </nav>

    <main class="main-content">
      <header class="main-header">
        <h1>Pengaturan Keamanan</h1>
      </header>

      <div class="content-body">
        <div class="security-card">
          <h3>Ganti Password</h3>
          <form @submit.prevent="gantiPassword">
            <div class="form-group">
              <label>Username</label>
              <input
                type="text"
                v-model="form.username"
                placeholder="Masukkan username Anda"
                required
                class="input-field"
              />
            </div>

            <div class="form-group">
              <label>Password Lama</label>
              <input
                type="password"
                v-model="form.oldPassword"
                placeholder="Password saat ini"
                required
                class="input-field"
              />
            </div>

            <div class="form-group">
              <label>Password Baru</label>
              <input
                type="password"
                v-model="form.newPassword"
                placeholder="Password baru"
                required
                class="input-field"
              />
            </div>

            <div class="form-group">
              <label>Konfirmasi Password Baru</label>
              <input
                type="password"
                v-model="form.confirmPassword"
                placeholder="Ulangi password baru"
                required
                class="input-field"
              />
            </div>

            <button type="submit" class="btn-save" :disabled="isLoading">
              {{ isLoading ? 'Memproses...' : 'Simpan Password Baru' }}
            </button>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isLoading = ref(false)

const form = reactive({
  username: '',
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})

async function gantiPassword() {
  if (form.newPassword !== form.confirmPassword) {
    alert('Password baru dan konfirmasi tidak cocok!')
    return
  }

  isLoading.value = true
  try {
    const response = await fetch('http://localhost:8080/api/auth/change-password', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: form.username,
        oldPassword: form.oldPassword,
        newPassword: form.newPassword,
      }),
    })

    const data = await response.json()

    if (response.ok) {
      alert('Sukses! Password berhasil diganti. Silakan login ulang.')
      router.push('/admin-login')
    } else {
      alert('Gagal: ' + data.message)
    }
  } catch (error) {
    console.error(error)
    alert('Error koneksi server.')
  } finally {
    isLoading.value = false
  }
}

function goToDashboard() {
  router.push('/admin/dashboard')
}
function logout() {
  alert('Logout berhasil!')
  router.push('/admin-login')
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: sans-serif;
}
.sidebar {
  width: 260px;
  background-color: #1f2937;
  color: #e5e7eb;
  display: flex;
  flex-direction: column;
}
.sidebar-header {
  padding: 24px;
  border-bottom: 1px solid #374151;
}
.sidebar-header h2 {
  margin: 0;
  font-size: 1.5rem;
  color: #fff;
}
.nav-menu {
  list-style: none;
  padding: 16px 0;
}
.nav-item {
  display: flex;
  gap: 16px;
  padding: 14px 24px;
  cursor: pointer;
}
.nav-item:hover {
  background-color: #374151;
}
.nav-item.active {
  background-color: #4b5563;
  color: #fff;
  border-left-color: #3b82f6;
}
.main-content {
  flex-grow: 1;
}
.main-header {
  padding: 24px 32px;
  background-color: #fff;
  border-bottom: 1px solid #e5e7eb;
}
.main-header h1 {
  font-size: 1.8rem;
  margin: 0;
}
.content-body {
  padding: 32px;
}

.security-card {
  background-color: #fff;
  border-radius: 8px;
  padding: 32px;
  max-width: 500px;
  border: 1px solid #e5e7eb;
}
.form-group {
  margin-bottom: 20px;
}
.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
}
.input-field {
  width: 100%;
  padding: 12px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  box-sizing: border-box;
}
.btn-save {
  width: 100%;
  padding: 14px;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}
.btn-save:hover {
  background-color: #2563eb;
}
</style>
