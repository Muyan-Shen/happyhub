<template>
  <div class="account">
    <div class="search">
      <el-form @submit="onSearch" inline>
        <el-form-item>
          <el-form-item label="rolename">
            <el-input v-model="searchForm.name" placeholder="请输入角色名称"></el-input>
          </el-form-item>
          <el-form-item label="角色编码" :label-width="120">
            <el-input v-model="searchForm.code" placeholder="请输入角色编码"></el-input>
          </el-form-item>
        </el-form-item>
        <el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit">
              <el-icon :size="24"><Search/></el-icon>
            </el-button>
            <el-button type="primary" @click="addRoleDialog = true">
              <el-icon :size="24"><CirclePlus/></el-icon>
            </el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="roles">
      <el-table-column label="角色名称" prop="name"></el-table-column>
      <el-table-column label="角色编码" prop="cide"></el-table-column>
      <el-table-column label="角色编码" prop="description"></el-table-column>
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
    <add v-model="addRoleDialog"/>
    <associated-permissions v-model:visible="associatedPermissionDialog.visible" :role="associatedPermissionDialog.role"/>
  </div>
</template>

<script setup>
import {reactive,onMounted,onActivated,watch,ref} from "vue";
import {getCurrentInstance} from "vue";
import {CirclePlus, Search} from "@element-plus/icons-vue";
import AssociatedPermissions from "@/pages/role/AssociatedPermissions.vue";
import Add from "@/pages/role/Add.vue";

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
.account{
  width: 100%;
  height: 100%;
  .el-form-item {
    .el-button{
      margin-left: 10px;
    }
  }
  .el-pagination{
    margin: 10px;
  }
}
</style>