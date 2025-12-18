<script setup>
// Bagian <script setup> ini sengaja kosong.
// App.vue tidak lagi menyimpan "database sementara".
// Semua data sekarang ditangani oleh halaman masing-masing
// yang berkomunikasi dengan backend.
</script>

<template>
  <router-view />
</template>

<style>
/* Style global (tanpa 'scoped') ini berlaku untuk seluruh website.
  Kita gunakan untuk mereset margin/padding bawaan browser.
*/
body,
html {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>
