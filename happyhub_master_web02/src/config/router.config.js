import {createRouter, createWebHashHistory} from "vue-router";
import {useProfileStore} from "../stores/useProfile.js";

const routes = [
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
        component:() => import('../pages/layout/Layout.vue'),
        children:[
            {
                path:'account',
                name:'account',
                component:() => import('../pages/account/Account.vue'),
            },
            {
                path:'role',
                name:'role',
                component:() => import('../pages/role/Role.vue'),
            },
            {
                path:'permission',
                name:'permission',
                component:() => import('../pages/permission/Permission.vue'),
            },
        ]
    },
    {
        path:'/:patchMatch(.*)*',
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
    routes
})

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