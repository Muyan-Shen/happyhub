<template>
    <div class="notification-container">
        <div class="title">发布通知</div>
      <el-tabs v-model="activeName" type="card" class="demo-tabs">
        <el-tab-pane label="根据用户id发布" name="first">
          <el-form :model="form" label-width="120px" class="notification-form">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="用户id">
                  <el-select v-model="notification.userId" placeholder="请选择用户id">
                    <el-option :value="userId" v-for="(userId,index) in userIds" :key="userId">{{ userId }}</el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="通知标题">
                  <el-input v-model="notification.title"/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="通知内容">
                  <el-input :rows="8" type="textarea" v-model="notification.message"/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item>
                  <el-button type="primary" @click="onSubmit">发布</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="根据角色名发布" name="second">
          <el-form :model="form" label-width="120px" class="notification-form">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="角色名">
                  <el-select v-model="role" placeholder="请选择角色名">
                    <el-option :value="role" v-for="role in roles" :key="role">{{ role }}</el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="通知标题">
                  <el-input v-model="notification.title"/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="通知内容">
                  <el-input :rows="8" type="textarea" v-model="notification.message"/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item>
                  <el-button type="primary" @click="onSubmit">发布</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
</template>

<script setup>
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import {ElLoading, ElMessage, ElMessageBox} from "element-plus";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const userIds=ref([]);
const roles=ref([]);
const role=ref([]);
const notification=reactive({
  userId:'',
  title:'',
  message:'',
})
const activeName = ref('first')

const getUserIds=()=>{
  $http.get('/notification/getAllUserId').then(resp=>{
    userIds.value=resp.userIds
  })
}
const getRoles=()=>{
  $http.get('/notification/getAllRole').then(resp=>{
    roles.value=resp.roles
    console.log(roles.value)
  })
}

const onSubmit = () => {
    const loadingInstance = ElLoading.service({ fullscreen: true, text: '正在处理...' });
    ElMessageBox.confirm(
        '确认添加此通知?',
        '提示',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
          if (activeName.value==='first'){
            $http.post('/notification/addNotification', notification).then(resp => {
              console.log(resp);
              ElMessage({
                type: 'success',
                message: '添加成功',
              });
            })
                .catch(error => {
                  console.error("Error:", error);
                  ElMessage.error('添加失败');
                })
                .finally(() => {
                  loadingInstance.close(); // 关闭加载指示器
                });
          }
          if (activeName.value==='second'){
            $http.post('/notification/addNotificationByRole?role='+role.value,notification).then(resp => {
              console.log(resp);
              ElMessage({
                type: 'success',
                message: '添加成功',
              });
            })
                .catch(error => {
                  console.error("Error:", error);
                  ElMessage.error('添加失败');
                })
                .finally(() => {
                  loadingInstance.close(); // 关闭加载指示器
                });
          }
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '请确认信息无误后再提交',
            });
        })
        .finally(() => {
            loadingInstance.close(); // 确保在任何情况下关闭加载指示器
        });
}

onMounted(()=>{
  getUserIds();
  getRoles();
})
</script>

<style scoped lang="scss">
.notification-container {
    max-width: 600px;
    margin: 40px auto;
    padding: 30px;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);

    .title {
        font-size: 26px;
        color: #303133;
        margin-bottom: 25px;
        font-weight: 500;
    }

    .notification-form {
        .el-form-item {
            margin-bottom: 20px;
        }

        .el-button {
            background-color: #409eff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;

            &:hover {
                background-color: #66b1ff;
                transform: translateY(-3px);
                box-shadow: 0 6px 10px rgba(0,0,0,0.15);
            }
        }

        .el-input,
        .el-select {
            border-radius: 4px;
        }

        .el-input,
        .el-select .el-input__inner {
            transition: border-color 0.3s;
        }

        .el-input:focus,
        .el-select .el-input__inner:focus {
            border-color: #409eff;
            box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
        }
    }
}
</style>