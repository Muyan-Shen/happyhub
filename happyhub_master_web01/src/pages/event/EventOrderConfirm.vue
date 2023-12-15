<template>
  <el-container>
    <el-header>
      <top-header/>
    </el-header>
    <el-main>
      <div class="paymentShow">
        <el-card class="paymentMessage">
          <div class="paymentTitle">
            <el-icon>
              <Clock/>
            </el-icon>
            支付信息
          </div>
          <div>订单编号：{{payment.orderId}}</div>
          <div>总价格：{{ payment.amount }}元</div>
          <div>支付状态：{{ payment.status }}</div>
          <div>创建时间：{{ payment.processedAt }}</div>
          <el-button @click="paymentConfirm1">确认</el-button>
          <el-button @click="dialogFormVisible = true">使用优惠券</el-button>
          <el-button @click="paymentCancel1(payment.id)">取消</el-button>
        </el-card>
      </div>
    </el-main>
  </el-container>


  <el-dialog v-model="dialogFormVisible" title="使用优惠券" class="couponsSelectAndUse" id="dialogCoupons">
    <el-select v-model="couponsId" placeholder="请选择优惠券">
      <el-option
          v-for="coupon in coupons"
          :key="coupon.id"
          :value="coupon.code"
      ><span v-if="coupon.discountAmount!=null"><el-icon><Paperclip/></el-icon>优惠券编号: {{ coupon.id }}       ￥折扣额:{{ coupon.discountAmount }}元</span>
        <span v-else><el-icon><Paperclip/></el-icon>优惠券编号: {{ coupon.id }}       ￥折扣额:{{ coupon.discount}}折</span>
      </el-option>
    </el-select>
    <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确认</el-button>
      </span>
  </el-dialog>
</template>

<script setup>
import router from "../../config/router.config.js";
import {onBeforeMount, onMounted, reactive, ref, watch} from "vue";
import {useRoute} from "vue-router";
import {getCurrentInstance} from "vue";
import {Clock, Paperclip} from "@element-plus/icons-vue";
import topHeader from '../component/header.vue'


const dialogFormVisible = ref(false)
const $http = getCurrentInstance().appContext.config.globalProperties.$http

const orderId = ref('');
const route = useRoute();

const couponsId = ref()
orderId.value = route.params.orderId;

const payment = ref({
  "orderId":"",
  "amount":"",
  "status":"",
  "processedAt":""
})
const getPayment =  (orderId) => {
  console.log(orderId)
  $http.get("/pay/orderConfirmed/" + orderId).then(res => {
    console.log(res)
    payment.value = res.data;
    console.log(payment.value)
  })
}
const paymentCancel1 = (paymentId) => {
  $http.get("/pay/paymentCanceled/" + paymentId).then((res) => {
    console.log(res.msg)
  })
}
const paymentConfirm1 = () => {
  router.push({
    name: "home"
  })
}

watch(
    () => dialogFormVisible.value,
    (newValue, oldValue) => {
      // 在这里执行对话框状态变化后的逻辑
      if (newValue === true) {
        couponsSelect()
      } else {
        console.log('对话框已关闭');
      }
    }
);
let coupons = ref()
const couponsSelect = () => {
  $http.get("/pay/selectCoupons").then((res) => {
    coupons.value = res.data;
  })
}
onMounted(() => {
  getPayment(orderId.value)
})
</script>

<style scoped lang="scss">
.paymentShow {
  justify-content: center;
  align-items: center;
  margin-right: 200px;
  margin-left: 200px;
  padding: 0;
  height: 100%;

  .paymentMessage {
    .paymentTitle {
      background-color: hotpink;
      margin-bottom: 10px;
      font-size: 25px;
      font-weight: bold;
      text-align: center;
    }
  }
}

.couponsSelectAndUse {
  .el-button--text {
    margin-right: 15px;
  }

  .el-select {
    width: 300px;
  }

  .dialog-footer button:first-child {
    margin-right: 10px;
  }
}

</style>