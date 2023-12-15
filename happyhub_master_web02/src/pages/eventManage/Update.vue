<template>
  <div class="update">
    <div class="table">
      <el-table :data="eventInfos">
        <el-table-column fixed prop="title" label="标题">
          <template #default="{row}">
            <el-input v-model="row.title"/>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="举办地点" class="locationColumn" width="100px">
          <template #default="{row}">
            <el-select v-model="row.location"
                       filterable
                       allow-create
                       default-first-option
                       placeholder="举办地点">
              <el-option v-for="city in locations"
                         :label="city"
                         :value="city"/>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间">
          <template #default="{row}">
            <el-date-picker v-model="row.startTime"
                            type="datetime"
                            format="YYYY年MM月DD日 HH:mm"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            placeholder="开始时间">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="结束时间">
          <template #default="{row}">
            <el-date-picker v-model="row.endTime"
                            type="datetime"
                            format="YYYY年MM月DD日 HH:mm"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            placeholder="结束时间">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="90px">
          <template #default="{row}">
            <el-switch v-model="row.status"
                       inline-prompt
                       style="--el-switch-off-color:#939393"
                       :active-value="`正常`"
                       :inactive-value="`异常`"
                       active-text="正常"
                       inactive-text="停止"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100px" fixed="right">
          <template #default="{row}">
            <el-button @click="updateEvent(row)" type="success">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import {useProfileStore} from "../../stores/useProfile.js";
import {onMounted, reactive, getCurrentInstance, ref} from "vue";
import {ElMessage} from "element-plus";

const $http = getCurrentInstance().appContext.config.globalProperties.$http
const profileStore = useProfileStore();
const userId = profileStore.profile.id;
const eventInfos = ref([]);
const locations = ref([]);

const updateEvent = (event)=>{
  $http.post("/event/update",event).then(resp=>{
    if (resp.code === 200){
      ElMessage.success({
        message:resp.msg,
        duration:1000
      })
    }else {
      ElMessage.error({
        message:"修改失败",
        duration:1250
      })
    }
  })
}

onMounted(() => {
  $http.post("/event/getAll", {"id": userId}).then(resp => {
    eventInfos.value = resp.data
    console.log(eventInfos);
  })
  $http.get('/event/getCity').then(resp => {
    locations.value = resp.data;
  })
})
</script>

<style scoped lang="scss">
.update{
  .table{
    .el-table{
      .el-table-column{
      }
    }
  }
}
</style>