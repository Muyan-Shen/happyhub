<template>
  <el-dialog class="associated-permissions" v-model="visibleDialog" @close="$emit('update:visible',false)">
    <el-form>
      <el-form-item label="角色名称">
        <el-input v-model="role.name" disabled/>
      </el-form-item>
      <el-form-item label="角色编码">
        <el-input v-model="role.code" disabled/>
      </el-form-item>
      <el-form-item label="权限列表">
        <el-scrollbar height="300">
          <el-tree
              ref="treeRef"
              :data="permissions"
              show-checkbox
              node-key="id"
              highlight-current
              :props="defaultProps"
              :default-expanded-keys="expandedKeys"
              :default-checked-keys="checkedKeys"
          />
        </el-scrollbar>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="$emit('update:visible',false)">取消</el-button>
      <el-button @click="onSave()" type="primary">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {computed, defineProps, getCurrentInstance, reactive, ref, onMounted, onActivated, watch} from 'vue'
import {ElMessage} from "element-plus";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const emit = defineEmits(['update:visible']);

const props = defineProps({
  /**
   * @type {Object} account
   * @property {String} account.username
   * @property {number} account.id
   * @property {array} account.roles
   * @property {number} account.roles.id
   * @property {string} account.roles.name
   * @property {String} account.roles.code
   */
  role: {
    type: Object,
    required: true
  },
  visible: {
    type: Boolean,
    default: false
  }
})
const visibleDialog = computed(
    () => props.visible,
    val => emit('update:visible', val)
)
const permissions = reactive([]);
const permissionMap = reactive({})
const expandedKeys = computed(()=>{
  const keys =[];
  for (let key in permissionMap) {
    let permission = permissionMap[key];
    if (permission.expanded) {
      keys.push(permission.id);
    }
  }
  return keys;
})
const checkedKeys = computed(()=>{
  const keys =[];
  for (let key in permissionMap) {
    let permission = permissionMap[key];
    if (permission.checked) {
      keys.push(permission.id);
    }
  }
  return keys;
})
const treeRef = ref(null);
const defaultProps = reactive({
  children: 'children',
  label: 'name',
})

// 1.查询出所有权限
// 2.获取当前账号已关联的角色
const loadRoles = () => {
  $http.get('/permission?limit=0').then(res => {
    permissions.length = 0
    // 将所有权限存入permissionObj的map集合中
    res.data.forEach(item => {
      permissionMap[item.id] = {...item,checked:false,expanded:false}
      if (item.pid === 0){
        //构建所有顶级父节点
        permissions.push(permissionMap[item.id])
      }
    })
    // 将所有的子节点添加到父节点的下级中
    res.data.forEach(item => {
      if (item.pid !== 0) {
        console.log(1)
        const permission = permissionMap[item.pid];
        if (!permission.children) {
          permission.children = [permissionMap[item.pid]]
        } else {
          permission.children.push(permissionMap[item.pid]);
        }
      }
    })

    // 3.查询当前账号已关联的角色
    // 问号代表roles存在就执行循环
    props.role.permissions?.forEach(permission => {
      //4.将已关联的角色设置为选中状态
        permissionMap[permission.id].checked = true;
        permissionMap[permission.id].expanded = true;
    })
  })
}
const onSave = () => {
//   获取所有选中的权限
  const permissionIds = treeRef.value?.getCheckedKeys(false);
//   发送请求，保存关联关系；
//   直接传值（后端用requestBody接收）防止浏览器吧这个请求变成复杂请求，导致不能跨域
  $http.post(`/role/${props.role.id}/permission`, permissionIds).then(res => {
    // 关闭对话框
    ElMessage.success(({
      message: '保存成功',
      duration: 750,
      onClose: () => {
        emit('update:visible', false);
      }
    }))
  })
}
watch(() => props.visible, (val) => {
  if (val) {
    loadRoles();
  }
})
// onMounted(loadRoles);
// onActivated(loadRoles);
</script>

<style scoped lang="scss">
.associated-permissions{
  .el-form{
    .el-form-item{
      .el-scrollbar{
        width: 100%;
      }
    }
  }
}
</style>