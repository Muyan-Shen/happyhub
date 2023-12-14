<template>
<div class="eventInfo">
  <el-container class="eventContainer">
    <el-header>
      <top-header/>
    </el-header>
    <el-main >
       <div class="eventBody">

       </div>
    </el-main>
  </el-container>
</div>
</template>

<script setup>
import topHeader from '../component/header.vue'
</script>

<style scoped lang="scss">
.eventInfo {
  height: 100%;
  .eventContainer{
    height: 100%;
    .el-main{
      height: 100%;
      background-color: red;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-left: 200px;
      margin-right: 200px;

    }
  }
}
</style>