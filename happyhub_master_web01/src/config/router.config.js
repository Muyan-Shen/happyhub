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
        path:'/register',
        name:'register',
        component:() => import('../pages/login/Login.vue'),
        meta:{
            noLogin:true
        }
    },
    {
        path:'/home',
        name:'home',
        component:() => import('../pages/layout/Layout.vue'),
        meta: {
            noLogin: true
        }
    },
    {
        path:'/service',
        name:'service',
        component:() => import('../pages/customerService/service.vue'),
    },
    {
        path:'/userInfo',
        name:'userInfo',
        component:() => import('../pages/user/userInfo.vue'),
    },
    {
        path:'/eventList',
        name:'eventList',
        component:() => import('../pages/event/eventList.vue'),
    },
    {
        path:'/eventInfo/:eventId?',
        name:'eventInfo',
        component:() => import('../pages/event/EventInfo.vue'),
    },
    {
        path:'/ticketPage',
        name:'ticketPage',
        component:() => import('../pages/event/Ticket.vue'),
    },
    {
        path:'/eventOrderCreate/:eventId?/:price?',
        name:'eventOrderCreate',
        component:() => import('../pages/event/EventOrderCreate.vue'),
    },
    {
        path:'/eventOrderConfirm/:orderId',
        name:'eventOrderConfirm',
        component:() => import('../pages/event/eventOrderConfirm.vue'),
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