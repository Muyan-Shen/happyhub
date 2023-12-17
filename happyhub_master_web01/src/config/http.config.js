import axios from 'axios'
import router from "./router.config.js";
import {ElMessage} from "element-plus";
import {useProfileStore} from "../stores/useProfile.js";

const http = axios.create({
    withCredentials: true ,// 确保携带 Cookie
    baseURL: 'http://localhost:8000',
    timeout: 10000,
    headers: {
        'content-type': 'application/json',
    }
})

http.interceptors.request.use(config => {
        const profileStore = useProfileStore()
        // 检查是否登录
        if (profileStore.IsLogin) {
            // 添加登录令牌到请求头
            config.headers['token'] = profileStore.token
        }
        return config
    },
    error => {
        ElMessage.error({
            message:error.message||(window.navigator.onLine?"网络连接失败":"网络连接失败，请检查网络连接"),
            grouping:true
        })
    })
http.interceptors.response.use(resp => {
        if (resp.status === 200) {
            return resp.data;
        }
        return Promise.reject(resp.data)
    },
    error => {
        if (error.response) {
            if (error.response.status == 401) {
                ElMessage.error({
                    message: '登录过期，请重新登录',
                    grouping: true,
                    onClose: () => {
                        router.push({name: "login"})
                    }
                });
            }
        }
        if (error.response) {
            if (error.response.status == 403) {
                ElMessage.error({
                    message: '您暂无权限访问',
                    grouping: true,
                    onClose: () => {
                        router.push({name: "403"})
                    }
                });
            }
        }
        return Promise.reject(error);
    })

export const get = http.get
export const post = http.post
export const put = http.put
export const del = http.delete

export default {
    install(app) {
        app.config.globalProperties.$http = {
            get,
            post,
            put,
            del,
            config:http.defaults
        }
    }
}