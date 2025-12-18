<template>
  <div class="page-background">
    <header class="navbar">
      <router-link to="/" class="logo">LaporDesa</router-link>
      <nav class="nav-links">
        <button class="btn btn-primary" disabled>
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
          Buat Laporan
        </button>
        <router-link to="/daftar" class="btn btn-secondary">
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
        </router-link>
      </nav>
    </header>

    <main class="main-content">
      <div class="form-wrapper">
        <router-link to="/" class="back-link">
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
          Kembali
        </router-link>

        <div class="form-card">
          <h1>Buat Laporan Baru</h1>
          <p class="subtitle">Sampaikan keluhan atau masukan Anda agar kami tindak lanjuti.</p>

          <form @submit.prevent="kirimLaporanKeBackend">
            <div class="form-group">
              <label for="nama">Nama Lengkap</label>
              <input id="nama" type="text" v-model="formData.nama" required class="input-dark" />
            </div>

            <div class="form-group">
              <label for="telepon">Nomor Telepon/HP</label>
              <input
                id="telepon"
                type="tel"
                v-model="formData.telepon"
                required
                class="input-dark"
              />
            </div>

            <div class="form-group">
              <label for="kategori">Kategori Laporan</label>
              <select id="kategori" v-model="formData.kategori" required>
                <option value="" disabled selected>Pilih kategori...</option>
                <option value="Infrastruktur">Infrastruktur (Jalan, dll)</option>
                <option value="Kebersihan">Kebersihan</option>
                <option value="Administrasi">Administrasi</option>
                <option value="Lainnya">Lainnya</option>
              </select>
            </div>

            <div class="form-group">
              <label for="judul">Judul Laporan</label>
              <input id="judul" type="text" v-model="formData.judul" required class="input-dark" />
            </div>

            <div class="form-group">
              <label for="isi">Isi Laporan</label>
              <textarea
                id="isi"
                v-model="formData.isi"
                rows="5"
                required
                class="input-dark"
              ></textarea>
            </div>

            <div class="form-group">
              <label for="foto">Lampiran Foto (Opsional)</label>
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
                <p>Klik untuk mengunggah atau seret file</p>
                <small>PNG, JPG, GIF hingga 10MB</small>
                <span v-if="formData.foto">{{ formData.foto.name }}</span>
              </label>
              <input id="foto-upload" type="file" @change="handleFileUpload" class="hidden-input" />
            </div>

            <button type="submit" class="btn btn-submit" :disabled="sedangMengirim">
              {{ sedangMengirim ? 'Mengirim...' : 'Kirim Laporan' }}
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
const sedangMengirim = ref(false)

// Kita tambahkan 'foto' kembali ke data reaktif
const formData = reactive({
  nama: '',
  telepon: '',
  kategori: '',
  judul: '',
  isi: '',
  foto: null, // Ini akan menyimpan objek File
})

// Fungsi untuk menangani saat user memilih file
function handleFileUpload(event) {
  const file = event.target.files[0]
  if (file) {
    formData.foto = file // Simpan objek file ke formData
  }
}

async function kirimLaporanKeBackend() {
  sedangMengirim.value = true

  // 1. Pisahkan data teks dari data file
  const dataTeks = {
    nama: formData.nama,
    telepon: formData.telepon,
    kategori: formData.kategori,
    judul: formData.judul,
    isi: formData.isi,
  }

  // 2. Buat objek FormData (ini WAJIB untuk kirim file)
  const dataKirim = new FormData()

  // 3. Masukkan data teks sebagai JSON String (sesuai permintaan backend)
  //    Nama key "laporan" HARUS SAMA dengan @RequestParam("laporan") di Controller
  dataKirim.append('laporan', JSON.stringify(dataTeks))

  // 4. Masukkan file gambar JIKA ada
  //    Nama key "file" HARUS SAMA dengan @RequestParam("file") di Controller
  if (formData.foto) {
    dataKirim.append('file', formData.foto)
  }

  try {
    // 5. Kirim data FormData ke backend
    const response = await fetch('http://localhost:8080/api/laporan', {
      method: 'POST',
      body: dataKirim,
      // PENTING: JANGAN set 'Content-Type' header.
      // Browser akan otomatis mengaturnya ke 'multipart/form-data'
      // dengan 'boundary' yang benar jika body-nya adalah FormData.
    })

    if (response.ok) {
      router.push('/daftar') // Langsung pindah ke daftar
    } else {
      // Baca pesan error dari backend jika ada
      const errorData = await response.json()
      alert('Terjadi kesalahan: ' + (errorData.message || 'Gagal mengirim laporan'))
    }
  } catch (error) {
    console.error('Error:', error)
    alert('Tidak bisa terhubung ke server backend.')
  } finally {
    sedangMengirim.value = false
  }
}
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
.btn-primary:hover:not(:disabled) {
  background-color: #2563eb;
}
.btn-primary:disabled {
  background-color: #93c5fd;
  cursor: not-allowed;
}
.btn-secondary {
  background-color: transparent;
  color: #374151;
  border: 1px solid #d1d5db;
}
.btn-secondary:hover {
  background-color: #f3f4f6;
}
.main-content {
  padding: 32px 20px;
}
.form-wrapper {
  max-width: 700px;
  margin: 0 auto;
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
}
.back-link:hover {
  color: #1f2937;
}
.form-card {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 32px 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}
.form-card h1 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #1f2937;
  margin-top: 0;
  margin-bottom: 8px;
}
.form-card .subtitle {
  font-size: 1rem;
  color: #6b7280;
  margin-bottom: 32px;
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
.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  box-sizing: border-box;
}
.input-dark {
  background-color: #374151;
  color: #ffffff;
  border-color: #4b5563;
}
.input-dark::placeholder {
  color: #9ca3af;
}
.upload-box {
  border: 2px dashed #d1d5db;
  border-radius: 8px;
  padding: 32px;
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
  color: #16a34a;
}
.hidden-input {
  display: none;
}
.btn-submit {
  width: 100%;
  padding: 14px;
  font-size: 1rem;
  font-weight: 600;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: background-color 0.2s ease;
}
.btn-submit:hover:not(:disabled) {
  background-color: #2563eb;
}
.btn-submit:disabled {
  background-color: #93c5fd;
  cursor: not-allowed;
}
</style>
