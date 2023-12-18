<template>
  <el-dialog v-model="visibleDialog" @close="$emit('update:visible',false)">
    <el-form>
      <el-form-item label="账号">
        <el-input v-model="user.username" disabled/>
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
import {computed, getCurrentInstance, reactive, ref, onMounted, onActivated, watch} from 'vue'
import {ElMessage} from "element-plus";


const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const emit = defineEmits(['update:visible']);

const props = defineProps({
  /**
   * @type {Object} user
   * @property {String} user.username
   * @property {number} user.id
   * @property {array} user.roles
   * @property {number} user.roles.id
   * @property {string} user.roles.name
   * @property {String} user.roles.code
   */
  user: {
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
const roles = reactive([]);
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
    props.user.roles?.forEach(role => {
      let index = roles.findIndex(item => item.id === role.id)
      if (index !== -1) {
        roles[index].checked = true
      }
    })
  })
}
const onSave = () => {
  const roleIds = roles.filter(role => role.checked).map(role => role.id);
  $http.post(`/user/${props.user.id}/role`, roleIds).then(res => {
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