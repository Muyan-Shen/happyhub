import {createApp} from "vue";
import config from "./config/index.js";
import './style.css'
import App from './App.vue'

const app = createApp(App);
app.use(config)
app.mount('#app');