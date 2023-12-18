import {createRouter, createWebHashHistory} from "vue-router";
import {useProfileStore} from "../stores/useProfile.js";
import {useStorage} from "@vueuse/core";

const clientRoutes = [
    {
        path:'/login',
        name:'login',
        component:() => import('../pages/login/Login.vue'),
        meta:{
            noLogin:true
        }
    },
    {
        path:'/home',
        name:'home',
        component:()=> import('../pages/layout/Layout.vue'),
    },
    {
        path:'/:patchMatch(.*)*',
        name:'remaining',
        redirect: '/404'
    },
    {
        path:'/404',
        name:'404',
        component:() => import('../pages/errors/404.vue')
    },
    {
        path:'/403',
        name:'403',
        component:() => import('../pages/errors/403.vue')
    }
]

const router = createRouter({
    history:createWebHashHistory(),
    routes:clientRoutes
})

export const serverMenus = useStorage("serverMenus",[])
const serverRoutes = useStorage("serverRoutes",[])
addServerRoutes(serverRoutes.value)
//重置路由
export function resetRouters() {
    for(const route of clientRoutes){
        router.addRoute(route)
    }
    serverRoutes.value = null;
}


export function addServerRoutes(routerList) {
    for(const r of routerList){
        router.addRoute(r.parentName,{
            path:r.path,
            name:r.name,
            component:()/* @vite-ignore */=>import(r.component),
            meta:{
                parentName:r.parentName,
                title:r.title
            }
        })
        serverRoutes.value=routerList
    }
}

router.beforeEach((to,from)=>{
    const profileStore = useProfileStore();
    if(to.meta && 'noLogin' in to.meta){
        if (to.meta.noLogin){
            return true;
        }
    }
    if (!profileStore.IsLogin){
        return {name:'login'}
    }
})

export default router;