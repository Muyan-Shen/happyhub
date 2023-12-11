<template>
  <div class="e404">
    <el-image src="Forbidden.png" />
    <div>
      <h1>404</h1>
      <span>页面不存在</span>
    </div>
  </div>
</template>

<script setup>

import {CircleCloseFilled} from "@element-plus/icons-vue";
</script>

<style scoped lang="scss">
.e404 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  i.iconfont{
    font-size: 260px;
  }
  h1{
    text-align: center;
  }
}

</style>