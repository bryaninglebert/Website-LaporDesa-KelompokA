<template>
  <div class="admin-layout">
    <nav class="sidebar">
      <div class="sidebar-header">
        <h2>Admin LaporDesa</h2>
      </div>
      <ul class="nav-menu">
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
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
            <polyline points="9 22 9 12 15 12 15 22"></polyline>
          </svg>
          <span>Dashboard</span>
        </li>
        <li class="nav-item" @click="$router.push('/admin/keamanan')">
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
        <h1>Dashboard Laporan Masuk</h1>
        <p>Kelola semua laporan yang dikirimkan oleh warga.</p>
      </header>

      <div class="content-body">
        <div v_if="isLoading" class="loading-state">
          <p>Memuat data laporan dari server...</p>
        </div>

        <div v-if="!isLoading && laporanList.length === 0" class="empty-state">
          <h2>Belum Ada Laporan</h2>
          <p>Database masih kosong. Belum ada warga yang mengirimkan laporan.</p>
        </div>

        <table v-if="!isLoading && laporanList.length > 0" class="laporan-table">
          <thead>
            <tr>
              <th>Foto</th>
              <th>Judul</th>
              <th>Kategori</th>
              <th>Pelapor</th>
              <th>Status</th>
              <th>Aksi</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="laporan in laporanList" :key="laporan.id">
              <td>
                <img
                  v-if="laporan.fotoUrl"
                  :src="`http://localhost:8080/api/uploads/${laporan.fotoUrl}`"
                  alt="Foto Laporan"
                  class="foto-thumbnail"
                />
                <span v-else class="foto-placeholder">Tidak ada foto</span>
              </td>
              <td>
                <strong>{{ laporan.judul }}</strong>
                <p class="detail-text">{{ laporan.isi.substring(0, 50) }}...</p>
              </td>
              <td>{{ laporan.kategori }}</td>
              <td>
                {{ laporan.nama }}
                <p class="detail-text">{{ laporan.telepon }}</p>
              </td>
              <td>
                <span
                  :class="[
                    'status-badge',
                    { 'status-proses': laporan.status === 'Sedang Diproses' },
                    { 'status-selesai': laporan.status === 'Selesai' },
                  ]"
                >
                  {{ laporan.status }}
                </span>
              </td>
              <td>
                <div class="aksi-container">
                  <router-link :to="`/admin/laporan/${laporan.id}`" class="btn-aksi">
                    Detail
                  </router-link>

                  <button @click="hapusLaporan(laporan.id)" class="btn-aksi btn-hapus">
                    Hapus
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const laporanList = ref([])
const isLoading = ref(true)

async function fetchLaporan() {
  isLoading.value = true
  try {
    const response = await fetch('http://localhost:8080/api/laporan')
    if (response.ok) {
      const data = await response.json()
      laporanList.value = data
      console.log('Data laporan admin berhasil diambil:', data)
    } else {
      alert('Gagal mengambil data laporan dari server.')
    }
  } catch (error) {
    console.error('Error mengambil data:', error) // <-- Variabel 'error' dipakai di sini
    alert('Tidak bisa terhubung ke server backend.')
  } finally {
    isLoading.value = false
  }
}

onMounted(fetchLaporan)

async function hapusLaporan(id) {
  if (
    !confirm(
      'Apakah Anda yakin ingin menghapus laporan ini? Data yang dihapus tidak bisa dikembalikan.',
    )
  ) {
    return
  }
  try {
    const response = await fetch(`http://localhost:8080/api/laporan/${id}`, {
      method: 'DELETE',
    })
    const responseData = await response.json()
    if (response.ok) {
      alert(responseData.message)
      laporanList.value = laporanList.value.filter((l) => l.id !== id)
    } else {
      alert('Gagal menghapus laporan: ' + responseData.message)
    }
  } catch (error) {
    // =============================================
    // PERBAIKAN DI SINI: Kita tambahkan console.error
    // =============================================
    console.error('Error saat menghapus:', error) // <-- Variabel 'error' dipakai di sini
    alert('Tidak bisa terhubung ke server backend.')
  }
}

function logout() {
  alert('Logout berhasil!')
  router.push('/admin-login')
}
</script>

<style scoped>
/* Style untuk layout admin */
.admin-layout {
  display: flex;
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
}

/* Sidebar */
.sidebar {
  width: 260px;
  background-color: #1f2937;
  color: #e5e7eb;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
}
.sidebar-header {
  padding: 24px;
  border-bottom: 1px solid #374151;
}
.sidebar-header h2 {
  margin: 0;
  font-size: 1.5rem;
  color: #ffffff;
}

/* Menu Navigasi */
.nav-menu {
  list-style: none;
  margin: 0;
  padding: 16px 0;
}
.nav-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 14px 24px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  border-left: 4px solid transparent;
  transition: all 0.2s ease;
}
.nav-item:hover {
  background-color: #374151;
  color: #ffffff;
}
.nav-item.active {
  background-color: #4b5563;
  color: #ffffff;
  border-left-color: #3b82f6;
}
.nav-item svg {
  flex-shrink: 0;
}

/* Konten Utama */
.main-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  overflow-x: auto;
}
.main-header {
  padding: 24px 32px;
  background-color: #ffffff;
  border-bottom: 1px solid #e5e7eb;
}
.main-header h1 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #111827;
  margin: 0 0 4px 0;
}
.main-header p {
  font-size: 1rem;
  color: #6b7280;
  margin: 0;
}

.content-body {
  padding: 32px;
  flex-grow: 1;
}

/* STYLE TABEL ADMIN */
.loading-state,
.empty-state {
  text-align: center;
  padding: 40px;
  background-color: #fff;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}
.empty-state h2 {
  color: #1f2937;
}
.empty-state p {
  color: #6b7280;
}

.laporan-table {
  width: 100%;
  border-collapse: collapse;
  background-color: #ffffff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.laporan-table th,
.laporan-table td {
  padding: 16px;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
  vertical-align: middle;
}

.laporan-table th {
  background-color: #f9fafb;
  font-size: 0.8rem;
  font-weight: 600;
  color: #6b7280;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.foto-thumbnail {
  width: 100px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}
.foto-placeholder {
  font-size: 0.8rem;
  color: #9ca3af;
}

.detail-text {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 4px 0 0 0;
  white-space: normal;
}

/* Badge Status Berwarna */
.status-badge {
  font-weight: 500;
  font-size: 0.8rem;
  padding: 4px 10px;
  border-radius: 99px;
  flex-shrink: 0;
  background-color: #e0f2fe; /* Biru */
  color: #0284c7;
}
.status-badge.status-proses {
  background-color: #fef9c3; /* Kuning */
  color: #a16207;
}
.status-badge.status-selesai {
  background-color: #dcfce7; /* Hijau */
  color: #166534;
}

/* Tombol Aksi */
.btn-aksi {
  background-color: #3b82f6; /* Biru */
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  text-decoration: none;
  font-size: 0.9rem;
  transition: background-color 0.2s ease;
  white-space: nowrap;
}
.btn-aksi:hover {
  background-color: #2563eb;
}

.btn-hapus {
  background-color: #ef4444; /* Merah */
}
.btn-hapus:hover {
  background-color: #dc2626; /* Merah lebih gelap */
}

.aksi-container {
  display: flex;
  align-items: center;
  gap: 8px; /* Memberi jarak antar tombol */
}

.laporan-table td {
  white-space: nowrap;
}
.laporan-table td:nth-child(2) {
  white-space: normal;
}
</style>
