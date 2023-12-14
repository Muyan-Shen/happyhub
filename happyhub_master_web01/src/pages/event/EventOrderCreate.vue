<template>
  显示订单  和确认订单按钮还有取消订单按钮
  {{order}}
  <el-button @click="orderConfirm1">确认</el-button>
  <el-button @click="orderCancel1">取消</el-button>
</template>

<script setup>
import {ElMessage} from "element-plus";
import router from "../../config/router.config.js";
import {getCurrentInstance, onMounted, ref} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const order=ref({
  "id":'',
  "userId":'',
  "eventId":'',
  "status":'',
  "totalPrice":'',
  "createdAt":'',
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
    order.value=res.data;
    console.log(order.value);
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
}


onMounted(()=>{
  getOrder(eventId,price);
})
</script>

<style scoped lang="scss">

</style>