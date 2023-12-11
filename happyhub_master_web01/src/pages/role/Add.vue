<template>
  <el-dialog title="新增角色" v-model="visible" @close="$emit('update:modelValue',false)">
    <el-form ref="roleForm"
             :model="roleInfo"
             :rules="rules">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="roleInfo.name" placeholder="请输入角色名称" clearable/>
      </el-form-item>
      <el-form-item label="角色编码" prop="code">
        <el-input v-model="roleInfo.code" placeholder="请输入角色名称" clearable/>
      </el-form-item>
      <el-form-item label="角色描述">
        <text>

        </text>
      </el-form-item>
      <template #footer>
        <el-button @click="$emit('update:modelValue',false)">取消</el-button>
        <el-button type="primary" @click="onSave($refs.roleForm)">保存</el-button>
      </template>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {computed, reactive, getCurrentInstance} from "vue";
import {ElMessage} from "element-plus";

const $http = getCurrentInstance().appContext.config.globalProperties.$http
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  }
})
const roleInfo = reactive({
  name: '',
  code: '',
  description: ''
})
const visible = computed(
    () => props.modelValue,
    val => emit('update:modelValue', val)
)
const rules = reactive({
  name: [
    {required: true, message: '请输入角色名称', trigger: 'blur'},
  ],
  code: [
    {required: true, message: '请输入角色编码', trigger: 'blur'},
    {pattren: /^[a-zA-Z]+$/, message: '角色编码只能为4-20位的字母数字下划线', trigger: 'blur'}
  ]
})
const onSave = (form) => {
  form.validate((valid) => {
    if (valid) {
      ElMessage.success({
        message: '新增角色成功',
        duration: 1000,
        onClose() {
          emit('update:visible', false)
        }
      }).catch(err=>{
        ElMessage.error({
          message:'inssert failed',
          duration:1000
        })
      })
    }
  })
}
</script>

<style scoped lang="scss">

</style>