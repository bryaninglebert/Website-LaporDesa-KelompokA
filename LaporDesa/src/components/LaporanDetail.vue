<template>
  <div class="admin-layout">
    <nav class="sidebar">
      <div class="sidebar-header">
        <h2>Admin LaporDesa</h2>
      </div>
      <ul class="nav-menu">
        <li class="nav-item" @click="goBack">
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
        <a @click="goBack" class="back-link">
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
            <line x1="19" y1="12" x2="5" y2="12"></line>
            <polyline points="12 19 5 12 12 5"></polyline>
          </svg>
          Kembali ke Dashboard
        </a>
        <h1>Detail Laporan</h1>
      </header>

      <div class="content-body">
        <div v-if="isLoading" class="loading-state">
          <p>Memuat data laporan...</p>
        </div>

        <div v-else class="detail-layout">
          <div class="detail-card">
            <h2>{{ laporan.judul }}</h2>
            <span class="status-badge">{{ laporan.status }}</span>
            <div class="info-grup">
              <strong>Pelapor:</strong>
              <p>{{ laporan.nama }} ({{ laporan.telepon }})</p>
            </div>
            <div class="info-grup">
              <strong>Kategori:</strong>
              <p>{{ laporan.kategori }}</p>
            </div>
            <div class="info-grup">
              <strong>Isi Laporan:</strong>
              <p class="isi-laporan">{{ laporan.isi }}</p>
            </div>
            <div class="info-grup" v-if="laporan.fotoUrl">
              <strong>Foto Lampiran Warga:</strong>
              <img
                :src="`http://localhost:8080/api/uploads/${laporan.fotoUrl}`"
                alt="Foto Laporan"
                class="laporan-foto-penuh"
              />
            </div>
            <div class="info-grup" v-else>
              <strong>Foto Lampiran Warga:</strong>
              <p class="info-placeholder">Tidak ada foto dilampirkan</p>
            </div>
          </div>

          <div class="action-card">
            <h3>Tindak Lanjut Laporan</h3>
            <form @submit.prevent="kirimTanggapan">
              <div class="form-group">
                <label for="status">Ubah Status Laporan</label>
                <select id="status" v-model="formTanggapan.status">
                  <option value="Terkirim">Terkirim (Baru Masuk)</option>
                  <option value="Sedang Diproses">Sedang Diproses</option>
                  <option value="Selesai">Selesai</option>
                </select>
              </div>
              <div class="form-group">
                <label for="tanggapan">Tanggapan Admin (Teks)</label>
                <textarea
                  id="tanggapan"
                  rows="5"
                  placeholder="Tulis tanggapan untuk warga di sini..."
                  v-model="formTanggapan.tanggapan"
                ></textarea>
              </div>

              <div class="form-group">
                <label for="foto">Foto Hasil Kerja (Opsional)</label>
                <label for="foto-upload" class="upload-box">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="32"
                    height="32"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="#6b7280"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  >
                    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                    <polyline points="17 8 12 3 7 8"></polyline>
                    <line x1="12" y1="3" x2="12" y2="15"></line>
                  </svg>
                  <p>Klik untuk mengunggah file</p>
                  <small>PNG, JPG, GIF hingga 10MB</small>
                  <span v-if="formTanggapan.file">{{ formTanggapan.file.name }}</span>
                </label>
                <input
                  id="foto-upload"
                  type="file"
                  @change="handleFileUpload"
                  class="hidden-input"
                />
              </div>

              <button type="submit" class="btn-aksi-kirim" :disabled="isSubmitting">
                {{ isSubmitting ? 'Menyimpan...' : 'Simpan Perubahan' }}
              </button>
            </form>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const laporan = ref({})
const isLoading = ref(true)
const isSubmitting = ref(false)

const formTanggapan = reactive({
  status: '',
  tanggapan: '',
  file: null,
})

const idLaporan = route.params.id

onMounted(async () => {
  if (!idLaporan || idLaporan === 'undefined') {
    alert('ID Laporan tidak valid. Kembali ke dashboard.')
    router.push('/admin/dashboard')
    return
  }
  try {
    const response = await fetch(`http://localhost:8080/api/laporan/${idLaporan}`)
    if (response.ok) {
      const data = await response.json()
      laporan.value = data
      formTanggapan.status = data.status
      formTanggapan.tanggapan = data.tanggapanAdmin || ''
    } else {
      alert('Gagal mengambil detail laporan.')
    }
  } catch (error) {
    console.error(error)
    alert('Error koneksi backend')
  } finally {
    isLoading.value = false
  }
})

function handleFileUpload(event) {
  const file = event.target.files[0]
  if (file) {
    formTanggapan.file = file
  }
}

async function kirimTanggapan() {
  isSubmitting.value = true
  const dataTeks = JSON.stringify({
    status: formTanggapan.status,
    tanggapan: formTanggapan.tanggapan,
  })
  const dataKirim = new FormData()
  dataKirim.append('data', dataTeks)
  if (formTanggapan.file) {
    dataKirim.append('file', formTanggapan.file)
  }
  try {
    const response = await fetch(`http://localhost:8080/api/laporan/${idLaporan}`, {
      method: 'PUT',
      body: dataKirim,
    })
    if (response.ok) {
      alert('Perubahan berhasil disimpan!')
      router.push('/admin/dashboard')
    } else {
      alert('Gagal menyimpan perubahan ke server.')
    }
  } catch (error) {
    console.error('Error saat update:', error)
    alert('Tidak bisa terhubung ke server backend.')
  } finally {
    isSubmitting.value = false
  }
}

function goBack() {
  router.push('/admin/dashboard')
}

function logout() {
  alert('Logout berhasil!')
  router.push('/admin-login')
}
</script>

<style scoped>
/* CSS LENGKAP UNTUK HALAMAN DETAIL */
.admin-layout {
  display: flex;
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
}
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
.main-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
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
  margin: 0;
}
.content-body {
  padding: 32px;
  flex-grow: 1;
}

.back-link {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
  color: #6b7280;
  font-weight: 500;
  margin-bottom: 16px;
  font-size: 1rem;
  cursor: pointer;
}
.back-link:hover {
  color: #1f2937;
}

.detail-layout {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 32px;
}

.detail-card {
  background-color: #fff;
  border-radius: 8px;
  padding: 32px;
  border: 1px solid #e5e7eb;
}
.detail-card h2 {
  font-size: 1.75rem;
  font-weight: 600;
  color: #111827;
  margin-top: 0;
  margin-bottom: 12px;
}

.status-badge {
  background-color: #e0f2fe;
  color: #0284c7;
  font-weight: 500;
  font-size: 0.9rem;
  padding: 6px 12px;
  border-radius: 99px;
  display: inline-block;
  margin-bottom: 24px;
}

.info-grup {
  margin-bottom: 20px;
}
.info-grup strong {
  display: block;
  font-weight: 600;
  font-size: 0.9rem;
  color: #6b7280;
  margin-bottom: 4px;
  text-transform: uppercase;
}
.info-grup p {
  font-size: 1rem;
  color: #1f2937;
  margin: 0;
}
.info-grup .isi-laporan {
  font-size: 1.1rem;
  line-height: 1.7;
  white-space: pre-wrap;
}
.laporan-foto-penuh {
  width: 100%;
  height: auto;
  border-radius: 8px;
  margin-top: 8px;
  border: 1px solid #e5e7eb;
}
.info-placeholder {
  font-style: italic;
  color: #9ca3af;
}

.action-card {
  background-color: #fff;
  border-radius: 8px;
  padding: 32px;
  border: 1px solid #e5e7eb;
  height: fit-content;
}
.action-card h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: #111827;
  margin-top: 0;
  margin-bottom: 24px;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 16px;
}

.form-group {
  margin-bottom: 20px;
}
.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  font-size: 0.9rem;
  color: #374151;
}
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  box-sizing: border-box;
}
.form-group textarea {
  font-family: inherit;
  resize: vertical;
}

.btn-aksi-kirim {
  width: 100%;
  padding: 14px;
  font-size: 1rem;
  font-weight: 600;
  background-color: #16a34a;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.btn-aksi-kirim:hover:not(:disabled) {
  background-color: #15803d;
}
.btn-aksi-kirim:disabled {
  background-color: #86efac;
  cursor: not-allowed;
}

.loading-state {
  text-align: center;
  padding: 40px;
  color: #6b7280;
}

.upload-box {
  border: 2px dashed #d1d5db;
  border-radius: 8px;
  padding: 24px;
  text-align: center;
  cursor: pointer;
  background-color: #f9fafb;
}
.upload-box:hover {
  background-color: #f3f4f6;
}
.upload-box p {
  margin: 8px 0 4px 0;
  font-weight: 500;
  font-size: 0.9rem;
  color: #3b82f6;
}
.upload-box small {
  color: #6b7280;
  font-size: 0.8rem;
}
.upload-box span {
  display: block;
  margin-top: 12px;
  font-weight: 500;
  font-size: 0.9rem;
  color: #16a34a;
}
.hidden-input {
  display: none;
}

@media (max-width: 900px) {
  .detail-layout {
    grid-template-columns: 1fr;
  }
}
</style>
