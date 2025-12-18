<template>
  <div class="page-background">
    <header class="navbar">
      <router-link to="/" class="logo">LaporDesa</router-link>
      <nav class="nav-links">
        <router-link to="/lapor" class="btn btn-primary">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M12 5v14"></path>
            <path d="M5 12h14"></path>
          </svg>
          Buat Laporan Baru
        </router-link>
        <button class="btn btn-secondary" disabled>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <line x1="8" y1="6" x2="21" y2="6"></line>
            <line x1="8" y1="12" x2="21" y2="12"></line>
            <line x1="8" y1="18" x2="21" y2="18"></line>
            <line x1="3" y1="6" x2="3.01" y2="6"></line>
            <line x1="3" y1="12" x2="3.01" y2="12"></line>
            <line x1="3" y1="18" x2="3.01" y2="18"></line>
          </svg>
          Daftar Laporan
        </button>
      </nav>
    </header>

    <main class="main-content">
      <div class="list-container">
        <h1>Daftar Laporan Anda</h1>
        <p class="subtitle">Berikut adalah riwayat laporan yang telah Anda kirimkan.</p>

        <div class="tab-nav">
          <button
            :class="['tab-button', { active: tabAktif === 'Semua' }]"
            @click="tabAktif = 'Semua'"
          >
            Semua ({{ laporanList.length }})
          </button>
          <button
            :class="['tab-button', { active: tabAktif === 'Terkirim' }]"
            @click="tabAktif = 'Terkirim'"
          >
            Terkirim ({{ listTerkirim.length }})
          </button>
          <button
            :class="['tab-button', { active: tabAktif === 'Sedang Diproses' }]"
            @click="tabAktif = 'Sedang Diproses'"
          >
            Diproses ({{ listDiproses.length }})
          </button>
          <button
            :class="['tab-button', { active: tabAktif === 'Selesai' }]"
            @click="tabAktif = 'Selesai'"
          >
            Selesai ({{ listSelesai.length }})
          </button>
        </div>

        <div v-if="isLoading" class="empty-state">
          <h2>Memuat Laporan...</h2>
          <p>Menghubungi server...</p>
        </div>

        <div v-else-if="laporanDisaring.length === 0" class="empty-state">
          <h2>Belum Ada Laporan</h2>
          <p>Belum ada laporan dengan status '{{ tabAktif }}'.</p>
        </div>

        <div v-else class="report-list">
          <div v-for="laporan in laporanDisaring" :key="laporan.id" class="report-card">
            <div class="card-header">
              <h3>{{ laporan.judul }}</h3>
              <span
                :class="[
                  'status-badge',
                  { 'status-proses': laporan.status === 'Sedang Diproses' },
                  { 'status-selesai': laporan.status === 'Selesai' },
                ]"
              >
                {{ laporan.status }}
              </span>
            </div>
            <small class="tanggal-laporan">
              Dibuat: {{ formatTanggal(laporan.tanggalLaporan) }}
              <span v-if="laporan.tanggalUpdate !== laporan.tanggalLaporan">
                | Diperbarui: {{ formatTanggal(laporan.tanggalUpdate) }}
              </span>
            </small>

            <p class="category">{{ laporan.kategori }}</p>
            <p class="details">{{ laporan.isi }}</p>

            <img
              v-if="laporan.fotoUrl"
              :src="`http://localhost:8080/api/uploads/${laporan.fotoUrl}`"
              alt="Foto Laporan Warga"
              class="laporan-foto"
            />

            <small class="name">Dikirim oleh: {{ laporan.nama }} ({{ laporan.telepon }})</small>

            <div v-if="laporan.tanggapanAdmin || laporan.fotoTanggapanUrl" class="tanggapan-admin">
              <strong>Tanggapan Admin:</strong>
              <p v-if="laporan.tanggapanAdmin">{{ laporan.tanggapanAdmin }}</p>
              <img
                v-if="laporan.fotoTanggapanUrl"
                :src="`http://localhost:8080/api/uploads/${laporan.fotoTanggapanUrl}`"
                alt="Foto Tanggapan Admin"
                class="laporan-foto"
              />
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

const laporanList = ref([])
const isLoading = ref(true)
const tabAktif = ref('Semua')

// FUNGSI BARU: FORMAT TANGGAL
function formatTanggal(tanggalISO) {
  if (!tanggalISO) return '-'
  const options = { day: 'numeric', month: 'short', year: 'numeric' }
  return new Date(tanggalISO).toLocaleDateString('id-ID', options)
}

// --- Logika TAB ---
const listTerkirim = computed(() => laporanList.value.filter((l) => l.status === 'Terkirim'))
const listDiproses = computed(() => laporanList.value.filter((l) => l.status === 'Sedang Diproses'))
const listSelesai = computed(() => laporanList.value.filter((l) => l.status === 'Selesai'))
const laporanDisaring = computed(() => {
  if (tabAktif.value === 'Terkirim') return listTerkirim.value
  if (tabAktif.value === 'Sedang Diproses') return listDiproses.value
  if (tabAktif.value === 'Selesai') return listSelesai.value
  return laporanList.value
})

// --- Logika Fetch Data ---
onMounted(async () => {
  isLoading.value = true
  try {
    const response = await fetch('http://localhost:8080/api/laporan')
    if (response.ok) {
      const data = await response.json()
      laporanList.value = data
    } else {
      alert('Gagal mengambil data laporan dari server.')
    }
  } catch (error) {
    // =============================================
    // PERBAIKAN DI SINI: Kita tambahkan console.error
    // =============================================
    console.error('Error mengambil data:', error) // <-- Variabel 'error' dipakai di sini
    alert('Tidak bisa terhubung ke server backend.')
  } finally {
    isLoading.value = false
  }
})
</script>

<style scoped>
.page-background {
  background-color: #f8f9fa;
  min-height: 100vh;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
}
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 40px;
  background-color: #ffffff;
  border-bottom: 1px solid #e5e7eb;
}
.logo {
  font-weight: 600;
  font-size: 1.5rem;
  color: #1f2937;
  text-decoration: none;
}
.nav-links {
  display: flex;
  gap: 16px;
}
.btn {
  padding: 10px 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 500;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.2s ease;
  text-decoration: none;
}
.btn-primary {
  background-color: #3b82f6;
  color: white;
}
.btn-primary:hover {
  background-color: #2563eb;
}
.btn-secondary {
  background-color: transparent;
  color: #374151;
  border: 1px solid #d1d5db;
}
.btn-secondary:hover:not(:disabled) {
  background-color: #f3f4f6;
}
.btn-secondary:disabled {
  background-color: #f3f4f6;
  color: #9ca3af;
  cursor: not-allowed;
}
.main-content {
  padding: 32px 20px;
}
.list-container {
  max-width: 900px;
  margin: 0 auto;
}
.list-container h1 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #1f2937;
  margin-top: 0;
  margin-bottom: 8px;
}
.list-container .subtitle {
  font-size: 1rem;
  color: #6b7280;
  margin-bottom: 32px;
}
.tab-nav {
  display: flex;
  gap: 8px;
  border-bottom: 2px solid #e5e7eb;
  margin-bottom: 32px;
}
.tab-button {
  padding: 10px 16px;
  font-size: 0.95rem;
  font-weight: 500;
  color: #6b7280;
  background-color: transparent;
  border: none;
  cursor: pointer;
  border-bottom: 3px solid transparent;
  transform: translateY(2px);
}
.tab-button:hover {
  color: #1f2937;
}
.tab-button.active {
  color: #3b82f6;
  border-bottom-color: #3b82f6;
}
.report-list {
  display: grid;
  gap: 20px;
}
.report-card {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #e5e7eb;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 8px;
}
.card-header h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0;
}
.status-badge {
  font-weight: 500;
  font-size: 0.8rem;
  padding: 4px 10px;
  border-radius: 99px;
  flex-shrink: 0;
  background-color: #e0f2fe; /* Terkirim (Biru) */
  color: #0284c7;
}
.status-badge.status-proses {
  background-color: #fef9c3; /* Sedang Diproses (Kuning) */
  color: #a16207;
}
.status-badge.status-selesai {
  background-color: #dcfce7; /* Selesai (Hijau) */
  color: #166534;
}
.category {
  font-weight: 500;
  color: #6b7280;
  margin-bottom: 12px;
}
.details {
  color: #374151;
  line-height: 1.6;
  margin-bottom: 16px;
}
.name {
  font-size: 0.85rem;
  color: #6b7280;
}
.laporan-foto {
  width: 100%;
  max-width: 400px;
  height: auto;
  border-radius: 8px;
  margin-top: 12px;
  margin-bottom: 16px;
  border: 1px solid #e5e7eb;
}
.tanggapan-admin {
  background-color: #f9fafb;
  border-radius: 8px;
  padding: 16px;
  margin-top: 20px;
  border: 1px solid #e5e7eb;
}
.tanggapan-admin strong {
  display: block;
  font-size: 0.9rem;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 8px;
}
.tanggapan-admin p {
  font-size: 1rem;
  color: #374151;
  line-height: 1.6;
  white-space: pre-wrap;
  margin: 0;
  margin-bottom: 12px; /* Beri jarak jika ada foto di bawahnya */
}
.empty-state {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 64px 40px;
  text-align: center;
  border: 1px solid #e5e7eb;
}
.empty-state h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: #1f2937;
  margin-top: 16px;
  margin-bottom: 8px;
}
.empty-state p {
  color: #6b7280;
  margin-bottom: 24px;
}
.tanggal-laporan {
  font-size: 0.8rem;
  color: #9ca3af;
  margin-bottom: 12px;
  display: block;
}
</style>
