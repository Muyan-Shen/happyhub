<template>
  <div class="eventList">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <el-table :data="eventList">
          <el-table-column prop="title" label="活动标题"/>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {onMounted, reactive, ref} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";

const profileStore = useProfileStore();
const eventList = ref([]);

// 在渲染时加载活动列表，从pinia中获得
onMounted(()=>{
  eventList.value = profileStore.eventList;
  console.log(eventList.value)
})
</script>

<style scoped lang="scss">

</style>