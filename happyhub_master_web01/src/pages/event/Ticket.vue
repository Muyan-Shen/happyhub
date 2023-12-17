<template>
  <el-container>
    <el-header>
      <top-header/>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";

const route = useRoute();
const ticket=defineProps({
   ticket:''
})


const getTicket=()=>{
  console.log(ticket)
}
onMounted(()=>{
  getTicket()
})
</script>

<style scoped lang="scss">

</style>