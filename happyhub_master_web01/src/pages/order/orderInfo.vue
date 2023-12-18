<template>
  <div class="orderInfo">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <div class="q">
        <side/>
        </div>
        <div class="title">
          <h3>我的小麦 > 订单查看</h3>
        </div>
        <br>
        <div class="table">
        <el-table stripe :data="orders">
          <el-table-column prop="id" label="订单号" width="180"></el-table-column>
          <el-table-column prop="eventId" label="活动号" align="center" width="180"></el-table-column>
          <el-table-column prop="status" label="状态" align="center" width="180"></el-table-column>
          <el-table-column prop="totalPrice" align="center" label="总价" width="180"></el-table-column>
          <el-table-column align="center" label="操作" min-width="80">
            <template #default="scope">
            <el-button @click="jumpToPay(scope.row)">查看</el-button>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty description="暂无通知数据"/>
          </template>
        </el-table>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>

import Side from "../component/side.vue";
import  TopHeader from "../component/header.vue";
import {onMounted, ref,getCurrentInstance} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";
import router from "../../config/router.config.js";

const orders=ref([]);
const  profileStore=useProfileStore()
const $http=getCurrentInstance().appContext.config.globalProperties.$http;

const jumpToPay=row=>{
  console.log(row.id)
  router.push({
    name: "eventOrderCreate",
    params: {
      "eventId": row.eventId,
      "price": row.totalPrice,
    }
  })
}
onMounted(()=>{
  $http.get("/pay/getAllOrder?userId="+profileStore.profile.id).then(resp=>{
    orders.value=resp.data
  })
})
</script>

<style scoped lang="scss">
.q{
  width: 403px;
  float: left;
}
.table{
  width: 60%;
  float: left;
}
.title{
  display: flex;
  align-items: center;
  border-bottom: 2px solid black;
  h3{
    font-size: 13px;
    margin-left: 20px;
  }
}
</style>