<template>
    <div class="role-permissions-container">
        <div class="header">权限管理</div>
        <div class="search-area">
            <el-input v-model="searchPermissionId" placeholder="请输入权限ID"/>
            <el-input v-model="searchPermissionName" placeholder="请输入权限名称"/>
            <el-input v-model="searchPermissionCode" placeholder="请输入权限编码"/>
            <el-button icon="Search" @click="fetchRolePermissions">查询</el-button>
            <el-button icon="Plus" @click="openAddDialog">添加</el-button>
        </div>
        <div class="table-pagination-container">
            <el-table :data="PermissionsData" style="width: 100%" :stripe="true">
                <el-table-column prop="id" label="权限ID" width="180"/>
                <el-table-column prop="name" label="权限名称" width="200"/>
                <el-table-column prop="code" label="权限编码"/>
                <el-table-column prop="description" label="权限描述"/>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small" @click="openEditDialog(scope.row)">修改</el-button>
                        <el-button size="small" type="danger" @click="deletePermission(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalRoles"
            >
            </el-pagination>
        </div>
        <el-dialog title="修改角色权限" v-model="editDialogVisible">
            <el-form ref="updateForm" v-model="editDialogData">
                <el-form-item label="权限名称" prop="name">
                    <el-input v-model="editDialogData.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限编码" prop="code">
                    <el-input v-model="editDialogData.code" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限描述" prop="description">
                    <el-input v-model="editDialogData.description" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="updatePermission">确认修改</el-button>
            </div>
        </el-dialog>

    </div>
    <el-dialog title="添加新权限" v-model="addDialogVisible">
        <div class="add-dialog-container">
            <el-form ref="addForm" :model="addDialogData">
                <el-form-item label="权限名称" prop="name">
                    <el-input v-model="addDialogData.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限编码" prop="code">
                    <el-input v-model="addDialogData.code" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限描述" prop="description">
                    <el-input v-model="addDialogData.description" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addPermission">确认添加</el-button>
            </div>
        </div>
    </el-dialog>
</template>


<script setup>
import {getCurrentInstance, reactive, ref, watch} from 'vue';
import {ElMessage} from 'element-plus';

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const PermissionsData = reactive([]);
const searchPermissionId = ref('');
const searchPermissionName = ref('');
const searchPermissionCode = ref('');
const totalRoles = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const editDialogVisible = ref(false);

const editDialogData = ref({
    name: '',
    code: '',
    description: ''
});
const id = ref('');

const fetchRolePermissions = async () => {
    try {
        let form = {
            id: searchPermissionId.value,
            name: searchPermissionName.value,
            code: searchPermissionCode.value,
            page: currentPage.value,
            limit: pageSize.value
        }
        $http.get("/permission", {params: form}).then(response => {
            if (!response.code === 200) throw new Error('Network response was not ok');
            const data = response.data;
            PermissionsData.splice(0, PermissionsData.length, ...data);
            totalRoles.value = data.count;
        })
    } catch (error) {
        console.error('Fetch error:', error);
        ElMessage.error('获取数据失败');
    }
};

const openEditDialog = (role) => {
    id.value = role.id
    editDialogData.value = {
        name: role.name,
        code: role.code,
        description: role.description
    }
    editDialogVisible.value = true;
};
const deletePermission = async (role) => {
    try {

        const response = await $http.del(`/permission?id=${role.id}`);

        if (!response.msg === "success") {
            throw new Error('Network response was not ok');
        }

        ElMessage.success('权限删除成功');
        await fetchRolePermissions();
    } catch (error) {
        console.error('Delete error:', error);
        ElMessage.error('删除权限失败');
    }
};

const handleSizeChange = (newSize) => {
    pageSize.value = newSize;
    fetchRolePermissions();
};

const handleCurrentChange = (newPage) => {
    currentPage.value = newPage;
    fetchRolePermissions();
};
//----------------------------------------------------------添加弹框
const addDialogVisible = ref(false);
const addDialogData = reactive({
    name: '',
    code: '',
    description: ''
});


const openAddDialog = () => {
    addDialogData.name = '';
    addDialogData.code = '';
    addDialogData.description = '';
    addDialogVisible.value = true;
};
const updatePermission = async () => {

    try {
        const form = editDialogData;
        await $http.post("/permission/"+id.value, form.value).then(response => {
            if (!response.msg==="success") throw new Error('Network response was not ok');
            ElMessage.success('权限添加成功');
            editDialogVisible.value = false;
            fetchRolePermissions();
        })
    } catch (error) {
        console.error('Update error:', error);
        ElMessage.error('修改权限失败');
    }
};
const addPermission = async () => {
    try {
        const form = addDialogData;
        await $http.put("/permission", form).then(response => {
            if (!response.msg==="success") throw new Error('Network response was not ok');
            ElMessage.success('权限添加成功');
            addDialogVisible.value = false;
            fetchRolePermissions();
        })
    } catch (error) {
        console.error('Add error:', error);
        ElMessage.error('添加权限失败');
    }
};

watch([currentPage, pageSize], () => {
    fetchRolePermissions();
});

// 初始加载
fetchRolePermissions();
</script>


<style scoped lang="scss">
$primary-color: #3498db; // 考虑使用一种更柔和的蓝色调
$hover-color: darken($primary-color, 10%); // 通过调整主色实现悬停色
$success-color: #2ecc71;
$success-hover-color: darken($success-color, 10%);
$error-color: #e74c3c;
$error-hover-color: darken($error-color, 10%);
$background-color: #ecf0f1;
$font-color: #2c3e50; // 深色字体以提高可读性
// 创建了一个 mixin 来复用过渡效果
@mixin transition-default {
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
}

.role-permissions-container {
  background: $background-color;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
  font-family: 'Helvetica Neue', Arial, sans-serif;
  transition: all 0.3s ease;

  .header {
    font-size: 1.75rem;
    font-weight: 600;
    color: $font-color;
    margin-bottom: 2rem;
  }

  .search-area {
    margin-bottom: 2rem;
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;

    .el-button {
      box-shadow: 0 2px 6px rgb(0 0 0 / 10%);
      border-radius: 4px;

      @include transition-default;

      &:active {
        transform: translateY(1px);
        box-shadow: none;
      }
    }


    .el-input__inner {
      border-radius: 4px;
      @include transition-default;

      &:focus {
        border-color: $primary-color;
        box-shadow: 0 0 0 2px rgba($primary-color, 0.2);
      }
    }

  }

  .el-table {
    thead {
      th {
        background-color: lighten($background-color, 5%);
        color: $font-color;
        font-weight: 600;

        .ascending, .descending {
          color: $primary-color;
        }
      }
    }

    tbody {
      tr {
        transition: background-color 0.3s ease;

        &:hover {
          background-color: lighten($background-color, 3%);
        }
      }
    }
  }

  .el-pagination {
    margin-top: 1rem;

    .el-pagination__total {
      color: $font-color;
    }

    .el-pager li:not(.disabled).active {
      background-color: $primary-color;
    }
  }

  .el-table, .el-pagination {
    .el-pager li:not(.disabled).active,
    .el-pager li:hover {
      transform: scale(1.1);
      @include transition-default;
    }
  }

  .el-dialog {
    .el-dialog__header {
      background-color: lighten($background-color, 10%);
      color: $font-color;
    }

    .add-dialog-container {
      background-color: $background-color;

      .el-button {
        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
      }
    }

    &.fade-enter-active, &.fade-leave-active {
      transition: opacity 0.5s, transform 0.5s;
    }

    &.fade-enter, &.fade-leave-to
    {
      opacity: 0;
      transform: translateY(-20px);
    }
  }
}


</style>