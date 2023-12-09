<template>
  <div class="eventDiv">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <div class="eventInfoDiv">
          <div class="eventPhotoDiv">活动图片</div>
          <div>
            <el-form class="event">
              <el-form-item label="活动标题" class="changeItem"></el-form-item>
              <el-form-item label="活动时间"></el-form-item>
              <el-form-item label="活动地点"></el-form-item>
              <el-form-item label="活动档位"></el-form-item>
            </el-form>
          </div>
        </div>
        <div class="eventHtmlDiv">
          <h1>活动富文本</h1>
        </div>
        <div class="eventNoticeDiv">
          <div class="notice">
            <h1 class="content">注意事项</h1>
            <el-icon color="red">
              <CircleClose/>
            </el-icon>
            <b>不支持退</b><br>
            票品为有价票券，非普通商品，其背后承载的文化服务具有时效性，稀缺性等特征，不支持退换。<br><br>
            <el-icon color="green">
              <CircleCheck/>
            </el-icon>
            <b>电子票</b><br>
            电子票：通过票夹中的二维码或身份证，可以直接验票入场（具体以下单后票夹信息为准。<br><br>
            <el-icon color="green">
              <CircleCheck/>
            </el-icon>
            <b>电子发票</b><br>
            发票开具方：湖南小麦文化
            该项目支持开具电子发票。该发票由第三方主办提供，需要在演出开始前提交发票申请，一般演出结束后1个月左右开具<br>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {CircleClose} from "@element-plus/icons-vue";
import {CircleCheck} from "@element-plus/icons-vue";
import {reactive} from "vue";

const eventInfo=reactive([

])
</script>

<style scoped lang="scss">
.eventDiv {
  height: 100%;

  .el-main {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-right: 200px;
    margin-left: 200px;
    padding: 0;
    //background-color: #13ce66;
    .eventInfoDiv {
      display: flex;
      margin-top: 30px;
      height: 50%;
      width: 100%;
      background-color: #ff4949;
      flex-direction: row;
      border: solid 1px black;
      .eventPhotoDiv {
        width: 40%;
      }
      .changeItem .el-form-item__label {
          font-size: 80px;
      }
    }

    .eventHtmlDiv {
      height: 50%;
      width: 100%;
      background-color: blue;
      border: solid 1px black;
    }

    .eventNoticeDiv {
      top: 90px;
      padding: 10px;
      right: 0;
      width: 180px;
      height: auto;
      background-color: #f8f4f4;
      position: absolute;
      border: solid 1px black;
      .notice{
        padding:10px;
        border: solid 1px black;
      }
      .content {
        text-align: center;
      }
    }
  }
}
</style>