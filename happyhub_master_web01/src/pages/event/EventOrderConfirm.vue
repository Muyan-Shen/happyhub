<template>
  支付信息，和确认支付按钮还有取消支付(后端暂时没写)按钮
 {{payment}}
  <el-button @click="paymentConfirm1">确认</el-button>
  <el-button @click="">取消</el-button>
</template>

<script setup>
import router from "../../config/router.config.js";
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import {getCurrentInstance} from "vue";

const $http=getCurrentInstance().appContext.config.globalProperties.$http

const orderId = ref('');
const route = useRoute();
orderId.value=route.params.orderId;

const payment=ref({
  "id":'',
   "orderId":'',
  "amount":'',
  "status":'',
  "paymentMethod":'',
  "processedAt":'',
})
const getOrder = (orderId) => {
  $http.get("/pay/orderConfirmed/"+orderId).then((res)=>{
     payment.value=res.data;
    console.log(payment.value)
  })
}
onMounted(()=>{
    getOrder(orderId.value)
})
</script>

<style scoped lang="scss">

</style>