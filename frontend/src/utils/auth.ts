const TOKEN_KEY = 'fraslab_admin_token'

export function saveToken(token: string) {
  // Keep admin auth in current browser session only.
  sessionStorage.setItem(TOKEN_KEY, token)
  localStorage.removeItem(TOKEN_KEY)
}

export function getToken() {
  const sessionToken = sessionStorage.getItem(TOKEN_KEY)
  if (sessionToken) {
    return sessionToken
  }
  // Clean up legacy persistent token to avoid auto-login.
  if (localStorage.getItem(TOKEN_KEY)) {
    localStorage.removeItem(TOKEN_KEY)
  }
  return null
}

export function clearToken() {
  sessionStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(TOKEN_KEY)
}
