<template>
  <div className="e404">
    <el-image src="Forbidden.png" />
    <div>
      <h1>403</h1>
      <span>没有访问权限</span>
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped lang="scss">
.e404 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;

  el-icon {
    font-size: 260px;
  }

  h1 {
    text-align: center;
  }
}

</style>