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
        //参数1：路由对象，（原始的路由对象）
        router.addRoute(route)//替换掉同名的路由对象
    }
    serverRoutes.value = null;
}


export function addServerRoutes(routerList) {
    for(const r of routerList){
        //参数1:父路由的名字
        //参数2:路由对象
        router.addRoute(r.parentName,{
            path:r.path,
            name:r.name,
            component:()=>import(r.component),
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