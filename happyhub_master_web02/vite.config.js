import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    server:{
        port:80,
        host: '0.0.0.0',
        open:false
    },
    resolve:{
        alias:{
            '@':'/src'
        }
    }
})
