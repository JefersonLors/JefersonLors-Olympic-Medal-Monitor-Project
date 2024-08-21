import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import AppRoutes from './Components/app-routes'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AppRoutes />
  </StrictMode>,
)
