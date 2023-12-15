<template>
  <div>
    <div style="display: flex;justify-content: center;align-items: center;" class="title">发布通知</div>
    <br>
    <br>
    <el-form :model="form" label-width="120px">
      <div style="margin-left: 100px">
      <el-form-item label="用户id">
        <el-select v-model="notification.userId" placeholder="请选择用户id">
          <el-option :value="userId" v-for="(userId,index) of userIds" :key="userId">{{
              userId
            }}</el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item  label="通知标题">
        <el-input style="width: 500px" v-model="notification.title"/>
      </el-form-item>
      <br>
      <el-form-item label="通知内容">
        <el-input :rows="8" style="width: 1000px" v-model="notification.message" type="textarea"/>
      </el-form-item>
      </div>
      <br>
      <div style=" display: flex;justify-content: center;align-items: center;">
      <el-form-item>
        <el-button  type="primary" @click="onSubmit">发布</el-button>
      </el-form-item>
      </div>
    </el-form>
  </div>
</template>
<script setup>
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const userIds=ref([]);
const notification=reactive({
  userId:'',
  title:'',
  message:'',
})

const getUserIds=()=>{
  $http.get('/notification/getAllUserId').then(resp=>{
    console.log(resp.userIds)
    userIds.value=resp.userIds
    console.log(userIds.value)
  })
}

const onSubmit=()=>{
  ElMessageBox.confirm(
        '确认添加此通知?',
      'Warning',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        $http.post('/notification/addNotification',notification).then(resp=>{
          console.log(resp);
        })
        ElMessage({
          type: 'success',
          message: '添加成功',
        })
        location.reload();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '请确认信息无误后再提交',
        })
      })

}

onMounted(()=>{
  getUserIds();
})
</script>

<style scoped lang="scss">
.title{
  font-size: 30px;
}
</style>