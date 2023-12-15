import {defineStore} from "pinia";

export const useProfileStore = defineStore("profile", {
    state:()=>({
        token:'',
        roles:[],
        permissions:[],
        profile:{},
        eventList:[]
    }),
    getters:{
        IsLogin(){
            return this.token !== ''
        },
        // 返回一个函数：判断是否包含某个角色
        hasRole(){
            return (role)=>{
                return this.roles.includes(role)
            }
        },
        // 返回一个函数：判断是否包含某个权限
        hasPermission(){
            return (permission)=>{
                return this.permissions.includes(permission)
            }
        }
    },
    actions:{
        login(token,roles,permissions,profile){
            this.token = token;
            this.roles = roles;
            this.permissions = permissions;
            this.profile = profile;
        },
        saveEventList(eventList){
            this.eventList = eventList;
        }
    },
    persist:{
        enabled:true, //开启持久化
    }
})