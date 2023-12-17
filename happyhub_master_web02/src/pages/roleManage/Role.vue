<template>
  <div class="account">
      <div class="search">
          <!-- 对搜索表单加上阴影效果，增加立体感 -->
          <el-form @submit.native.prevent="onSearch" inline class="search-form">
              <!-- 将label和input放在同一行显示，以节省空间 -->
              <el-form-item>
                  <el-input v-model="searchForm.name" placeholder="请输入角色名称" prefix-icon="Search"></el-input>
              </el-form-item>
              <el-form-item>
                  <el-input v-model="searchForm.code" placeholder="请输入角色编码" prefix-icon="Operation"></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" native-type="submit" icon="Search">搜索</el-button>
                  <!-- 增加了一些左边距 -->
                  <el-button type="success" @click="addRoleDialog = true" icon="CirclePlus" style="margin-left: 8px;">添加</el-button>
              </el-form-item>
          </el-form>
      </div>
      <el-table :data="roles" border stripe>
      <el-table-column label="角色名称" prop="name"></el-table-column>
      <el-table-column label="角色编码" prop="code"></el-table-column>
      <el-table-column label="角色描述" prop="description"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="操作" fixed="right">
        <template #default="{row}">
          <el-button type="primary" size="small">编辑</el-button>
          <el-button type="danger" size="small">删除</el-button>
          <el-button type="primary" size="small" @click="onAssociatedPermission(row)">关联权限</el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="暂无账号数据"/>
      </template>
      <template #append>
        <el-pagination background
                       layout="prev,pager,next"
                       v-model:page-size="pageInfo.limit"
                       v-model:current-page="pageInfo.page"
                       :total="pageInfo.total"/>
      </template>
    </el-table>
    <add v-model="addRoleDialog" @update:modelValue="loadRoles"/>
    <associated-permissions v-model:visible="associatedPermissionDialog.visible" :role="associatedPermissionDialog.role"/>
  </div>
</template>

<script setup>
import {reactive,onMounted,onActivated,watch,ref} from "vue";
import {getCurrentInstance} from "vue";
import {CirclePlus, Search} from "@element-plus/icons-vue";
import AssociatedPermissions from "@/pages/roleManage/AssociatedPermissions.vue";
import Add from "@/pages/roleManage/Add.vue";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const roles = reactive([])
const searchForm = reactive({
  name: '',
  code: ''
})
const pageInfo = reactive({
  page: 1,
  limit: 10,
  total: 0
})
const associatedPermissionDialog = reactive({
  visible: false,
  role: {}
})
const addRoleDialog = ref(false);
const onSearch = (e)=>{
  e.preventDefault();
  loadRoles();
}
const onStatusChange = (row)=>{
  // TODO:发送请求，修改状态
}

const onAssociatedPermission = (role)=>{
  associatedPermissionDialog.role = role;
  associatedPermissionDialog.visible = true;
}

const loadRoles = () => {
  let params = {
    page: pageInfo.page,
    limit: pageInfo.limit,
    name: searchForm.name,
    code: searchForm.code,
  }
  $http.get('/role', {params}).then(res => {
    roles.length = 0;
    roles.push(...res.data);
    pageInfo.total = res.count;
  })
}
watch(()=>searchForm.name,loadRoles)
watch(()=>associatedPermissionDialog.visible,(val)=>{
  if (!val){
    loadRoles();
  }
})
onMounted(loadRoles);
onActivated(loadRoles);
</script>

<style scoped lang="scss">
.search {
  padding: 20px; // 增加padding
  background-color: #f8f8f8; // 更改背景色
  border-radius: 8px; // 增加圆角
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); // 增加阴影效果

  .search-form {
    .el-form-item {
      margin-right: 12px; // 增加间距
    }
  }
}

.el-table {
  &:not(.el-table--border) {
    .el-table__body tr td,
    .el-table__header-wrapper tr th {
      border-bottom: 1px solid #ebeef5; // 加底部边框线
    }
  }
}
</style>