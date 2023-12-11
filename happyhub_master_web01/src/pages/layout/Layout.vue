<template>
  <div class="layout">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        我来组成身体
        <el-image :src="eventImage" @click="jumpToEventInfo"/>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import router from '../../config/router.config.js'

const eventImage = "piglin.gif"
const jumpToEventInfo = ()=>{
  router.push("/eventInfo")
}
</script>

<style scoped lang="scss">
.layout{
  height: 100%;
  .el-main{
    display: flex;
    justify-content: center;
    align-items: center;
    border: red 1px solid;
    height: 100%;
    margin-left: 200px;
    margin-right: 200px;
    .el-image{
      width: 100px;
    }
  }
}
</style>