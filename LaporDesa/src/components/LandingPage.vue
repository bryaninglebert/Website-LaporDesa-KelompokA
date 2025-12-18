<template>
  <div class="landing-container">
    <header class="header">
      <svg
        class="logo"
        width="36"
        height="36"
        viewBox="0 0 24 24"
        fill="#3b82f6"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M2.78064 13.3134L11.0664 6.68652C11.6288 6.22388 12.3712 6.22388 12.9336 6.68652L21.2194 13.3134C21.7818 13.7761 21.7818 14.5373 21.2194 15L12.9336 21.6269C12.3712 22.0895 11.6288 22.0895 11.0664 21.6269L2.78064 15C2.2182 14.5373 2.2182 13.7761 2.78064 13.3134Z"
          opacity="0.4"
        />
        <path
          d="M2.78064 6.68652L11.0664 0.0596486C11.6288 -0.403022 12.3712 -0.403022 12.9336 0.0596486L21.2194 6.68652C21.7818 7.14919 21.7818 7.91041 21.2194 8.37308L12.9336 15C12.3712 15.4627 11.6288 15.4627 11.0664 15L2.78064 8.37308C2.2182 7.91041 2.2182 7.14919 2.78064 6.68652Z"
        />
      </svg>
      <h1 class="judul">Selamat Datang di LaporDesa</h1>
    </header>

    <p class="subjudul">
      Platform terpadu untuk pengaduan dan pelayanan masyarakat. Pilih peran Anda untuk memulai.
    </p>

    <div class="card-container">
      <div class="card" @click="pilihPeran('warga')">
        <div class="icon-wrapper">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="28"
            height="28"
            viewBox="0 0 24 24"
            fill="none"
            stroke="#3b82f6"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M4 13.5V4a2 2 0 0 1 2-2h8.5L20 7.5V20a2 2 0 0 1-2 2h-5.5"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <path
              d="M10.42 12.61a2.1 2.1 0 1 1 2.97 2.97L7.95 21l-2.83 1.13L6.25 19.3l4.17-6.69z"
            ></path>
          </svg>
        </div>
        <h2 class="card-judul">Laporan/Keluhan Warga</h2>
        <p class="card-deskripsi">Buat laporan baru dan lihat status progres laporan Anda.</p>
      </div>

      <div class="card" @click="pilihPeran('admin')">
        <div class="icon-wrapper">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="28"
            height="28"
            viewBox="0 0 24 24"
            fill="none"
            stroke="#3b82f6"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            <path d="m9 12 2 2 4-4"></path>
          </svg>
        </div>
        <h2 class="card-judul">Login Admin</h2>
        <p class="card-deskripsi">Masuk untuk mengelola dan menindaklanjuti laporan warga.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
// =============================================
//  INI ADALAH BAGIAN SCRIPT YANG DIPERBARUI
// =============================================
import { useRouter } from 'vue-router'

const router = useRouter()

function pilihPeran(peran) {
  if (peran === 'warga') {
    router.push('/lapor') // Mengarah ke formulir laporan
  } else if (peran === 'admin') {
    // BARIS INI DIUBAH DARI 'alert' KE 'router.push'
    router.push('/admin-login') // Mengarah ke halaman login admin
  }
}
</script>

<style scoped>
/* Bagian CSS (UI/UX) - Tidak ada perubahan */
.landing-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  width: 100%;
  background-color: #f8f9fa;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  padding: 20px;
  box-sizing: border-box;
}
.header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}
.logo {
  flex-shrink: 0;
}
.judul {
  font-size: 2rem;
  font-weight: 700;
  color: #1f2937;
  text-align: center;
}
.subjudul {
  font-size: 1rem;
  color: #6b7280;
  max-width: 450px;
  text-align: center;
  margin-bottom: 40px;
  line-height: 1.5;
}
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 30px;
}
.card {
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  padding: 32px;
  width: 300px;
  text-align: center;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  border: 1px solid #e5e7eb;
}
.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
}
.icon-wrapper {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background-color: #e0f2fe;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 24px auto;
}
.card-judul {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 8px;
}
.card-deskripsi {
  font-size: 0.9rem;
  color: #6b7280;
  line-height: 1.5;
}
</style>
