import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.js'

export default {
    install(app){
        app.use(ElementPlus,{local:zhCn})
    }
}