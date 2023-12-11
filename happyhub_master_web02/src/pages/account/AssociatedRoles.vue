<template>
  <el-dialog v-model="visibleDialog" @close="$emit('update:visible',false)">
    <el-form>
      <el-form-item label="账号">
        <el-input v-model="account.username" disabled/>
      </el-form-item>
      <el-form-item label="角色列表">
        <el-space wrap>
          <el-checkbox v-for="role in roles"
                       :key="role.id"
                       v-model="role.checked"
                       :label="role.name"
                       border>
            {{ role.name }}
          </el-checkbox>
        </el-space>
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
  account: {
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
// 查询出所有角色额
const roles = reactive([]);
// 获取当前账号已关联的角色
const loadRoles = () => {
  $http.get('/role').then(res => {
    roles.length = 0;
    for (let role of res.data) {
      roles.push({
        id: role.id,
        name: role.name,
        code: role.code,
        checked: false
      })
    }
    // 查询当前账号已关联的角色
    // 问号达标roles存在就执行循环
    props.account.roles?.forEach(role => {
      //将已关联的角色设置为选中状态
      let index = roles.findIndex(item => item.id === role.id)
      if (index !== -1) {
        roles[index].checked = true
      }
    })
  })
}
const onSave = () => {
//   获取所有选中的角色
  const roleIds = roles.filter(role => role.checked).map(role => role.id);
//   发送请求，保存关联关系；
//   直接传值（后端用requestBody接收）防止浏览器吧这个请求变成复杂请求，导致不能跨域
  $http.post(`/account/${props.account.id}/role`, roleIds).then(res => {
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
onMounted(loadRoles);
onActivated(loadRoles);
</script>

<style scoped lang="scss">

</style>