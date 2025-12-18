<template>
  <div class="login-page-container">
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

    <div class="login-card">
      <h1>PORTAL ADMIN</h1>
      <p class="subtitle">Layanan Pengaduan Fasilitas Umum</p>

      <form @submit.prevent="handleLogin">
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>

        <div class="form-group">
          <label for="username">Username</label>
          <div class="input-wrapper">
            <svg
              class="input-icon"
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
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
              <circle cx="12" cy="7" r="4"></circle>
            </svg>
            <input
              type="text"
              id="username"
              v-model="username"
              placeholder="masukkan username"
              required
            />
          </div>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <div class="input-wrapper">
            <svg
              class="input-icon"
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
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
            <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              v-model="password"
              placeholder="masukkan password"
              required
            />
            <button type="button" @click="togglePassword" class="password-toggle">
              <svg
                v-if="!showPassword"
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
                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                <circle cx="12" cy="12" r="3"></circle>
              </svg>
              <svg
                v-else
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
                <path
                  d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"
                ></path>
                <line x1="1" y1="1" x2="23" y2="23"></line>
              </svg>
            </button>
          </div>
        </div>

        <a href="#" class="forgot-link">Lupa Password?</a>

        <button type="submit" class="login-button" :disabled="isLoading">
          {{ isLoading ? 'Mencoba Masuk...' : 'Login' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const username = ref('')
const password = ref('')
const showPassword = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')

function togglePassword() {
  showPassword.value = !showPassword.value
}

async function handleLogin() {
  isLoading.value = true
  errorMessage.value = ''

  try {
    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: username.value,
        password: password.value,
      }),
    })

    const responseData = await response.json()

    if (response.ok) {
      console.log('Login berhasil:', responseData.message)
      router.push('/admin/dashboard')
    } else {
      errorMessage.value = responseData.message
    }
  } catch (error) {
    console.error('Koneksi ke server gagal:', error)
    errorMessage.value = 'Tidak dapat terhubung ke server. Coba lagi nanti.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.login-page-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  padding: 20px;
  box-sizing: border-box;
}
.back-link {
  position: absolute;
  top: 30px;
  left: 30px;
  text-decoration: none;
  color: #6b7280;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}
.back-link:hover {
  color: #1f2937;
}
.login-card {
  position: relative;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.07);
  padding: 40px;
  width: 100%;
  max-width: 420px;
  box-sizing: border-box;
  overflow: hidden;
}
.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 8px;
  background-color: #d90429;
}
.login-card h1 {
  text-align: center;
  color: #1f2937;
  font-size: 1.8rem;
  font-weight: 700;
  margin-top: 20px;
  margin-bottom: 8px;
}
.login-card .subtitle {
  text-align: center;
  color: #6b7280;
  margin-bottom: 32px;
  font-size: 1rem;
}
.error-message {
  background-color: #fee2e2;
  color: #b91c1c;
  border: 1px solid #fca5a5;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 0.9rem;
  font-weight: 500;
  text-align: center;
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
.input-wrapper {
  position: relative;
}
.input-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
  pointer-events: none;
}
.input-wrapper input {
  width: 100%;
  padding: 12px 12px 12px 40px;
  font-size: 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  box-sizing: border-box;
  color: #1f2937;
}
.input-wrapper input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.3);
}
.password-toggle {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  color: #9ca3af;
}
.forgot-link {
  display: block;
  text-align: right;
  margin-top: -10px;
  margin-bottom: 24px;
  font-size: 0.9rem;
  color: #3b82f6;
  text-decoration: none;
}
.forgot-link:hover {
  text-decoration: underline;
}
.login-button {
  width: 100%;
  padding: 14px;
  font-size: 1rem;
  font-weight: 600;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.login-button:hover:not(:disabled) {
  background-color: #2563eb;
}
.login-button:disabled {
  background-color: #93c5fd;
  cursor: not-allowed;
}
</style>
