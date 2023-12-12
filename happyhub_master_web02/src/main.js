import {createApp} from "vue";
import config from "./config/index.js";
import './style.css'
import App from './App.vue'
// 统一导入 element 图标
import * as Icons from '@element-plus/icons'

const app = createApp(App);
// 注册全局 element-icons 组件
Object.keys(Icons).forEach((key) => {
    app.component(key, Icons[key])
})


app.use(config)
app.mount('#app');




