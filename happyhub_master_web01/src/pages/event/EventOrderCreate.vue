<template>
  <el-container>
  <el-header>
    <top-header/>
  </el-header>
  <el-main>
  <div class="orderShow">
    <el-card class="orderMessage" v-model="order">
      <div class="orderTitle"><el-icon><Clock /></el-icon>发起订单</div>
      <div>订单编号：{{order.id}}</div>
      <div>用户号：{{order.userId}}</div>
      <div>总价格：{{order.totalPrice}}元</div>
      <div>活动编号：{{order.eventId}}</div>
      <div>活动状态：{{order.status}}</div>
      <div>订单创建时间：{{order.createAt}}</div>
      <el-button @click="orderConfirm1">确认</el-button>
      <el-button @click="orderCancel1">取消</el-button>
    </el-card>

  </div>
  </el-main>
    </el-container>
</template>

<script setup>
import {ElAlert, ElMessage} from "element-plus";
import router from "../../config/router.config.js";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import {Clock} from   "@element-plus/icons-vue";
import topHeader from '../component/header.vue'
import {useProfileStore} from "../../stores/useProfile.js";

const useProfileStore1 = useProfileStore();

const route = useRoute();
const order= ref({
  "id":'',
  "userId":'',
  "eventId":'',
  "status":'',
  "totalPrice":'',
  "createAt":'',
  "updatedAt":'',
})
const $http = getCurrentInstance().appContext.config.globalProperties.$http;

const eventId = route.params.eventId;
const price=route.params.price;
const orderId = ref('');
const getOrder=(eventId,price)=> {
  $http.post('/pay/beforePaid/', {
    "eventId": eventId, "price": price
  }).then(res => {

    ElMessage(res.msg);
    orderId.value = res.data.id;
    order.value = res.data;
    console.log(order);
  })
}

const orderConfirm1=()=>{
  router.push({
    name: "eventOrderConfirm",
    params: {
      "orderId": orderId.value
    }
  })
}
const orderCancel1=()=>{
  //异步请求
  $http.get("/pay/orderCancel/"+orderId.value).then((res)=>{
    ElMessage(res.msg);
    useProfileStore1.gear=''
    router.push({
      name:"home"
    })
  }).catch(err=>{
    console.error('取消订单时出错：', err);
    router.push({
      name:"home"
    })
  })
}


onMounted(()=>{
 getOrder(eventId,price);
})
</script>

<style scoped lang="scss">
.orderShow{
  justify-content: center;
  align-items: center;
  margin-right: 200px;
  margin-left: 200px;
  padding: 0;
  height: 100%;
  .orderMessage {
    .orderTitle {
      background-color:hotpink ;
      margin-bottom: 10px;
      font-size: 25px;
      font-weight: bold;
      text-align: center;
    }
  }
}
</style>