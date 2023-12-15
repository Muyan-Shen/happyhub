<template>
    <div class="add">
        <el-dialog title="新增角色" v-model="visible" :before-close="handleClose">
            <el-form ref="roleFormRef" :model="roleInfo" :rules="rules" label-width="120px">
                <el-form-item label="角色名称" prop="name">
                    <el-input v-model.trim="roleInfo.name" placeholder="请输入角色名称" clearable/>
                </el-form-item>
                <el-form-item label="角色编码" prop="code">
                    <el-input v-model.trim="roleInfo.code" placeholder="请输入角色编码" clearable/>
                </el-form-item>

                <el-form-item label="角色描述" prop="description">
                    <el-input v-model.trim="roleInfo.description" placeholder="请输入角色描述" clearable/>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                  <el-button @click="handleClose">取消</el-button>
                  <el-button type="primary" @click="onSave">提交</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import {computed, getCurrentInstance, reactive, ref, watch} from "vue";
import {ElMessage} from "element-plus";

const emit = defineEmits(['update:modelValue']);
const {$http} = getCurrentInstance().appContext.config.globalProperties;

const roleInfo = reactive({
    name: '',
    code: '',
    description: ''
});

const rules = {
    name: [
        {required: true, message: '请输入角色名称', trigger: 'blur'},
    ],
    code: [
        {required: true, message: '请输入角色编码', trigger: 'blur'},

        {pattern: /^[a-zA-Z0-9_]{4,20}$/, message: '角色编码只能为4-20位的字母数字下划线', trigger: 'blur'}
    ],
    description: [
        {required: false, message: '请输入角色描述', trigger: 'blur'}
    ]
};

const roleFormRef = ref(null);


const onSave = async () => {

    if (!roleFormRef.value) return;

    await roleFormRef.value.validate(async (valid) => {
        if (valid) {
            try {
                const response = await $http.put('/role', roleInfo);
                if (response.status === 200) {
                    ElMessage.success({
                        message: '新增角色成功',
                        duration: 1000,
                        onClose: () => emit('update:modelValue', false),
                    });
                } else {
                    ElMessage.error({
                        message: '新增角色失败',
                        duration: 1000,
                        onClose: () => emit('update:modelValue', true),
                    });
                }
            } catch (error) {
                // 如果请求失败，捕获错误并显示消息
                ElMessage.error({
                    message: error.message || '新增角色失败',
                    duration: 1000,
                });
            }
        }
    });
};

// Handle the dialog close event
const handleClose = () => {
    emit('update:modelValue', false);
};


const props = defineProps({
    modelValue: {
        type: Boolean,
        default: false
    }
});

const visible = computed(() => props.modelValue);

watch(visible, (newValue) => {
    if (!newValue && roleInfo.value) {
        roleInfo.value.resetFields();
    }
});
</script>

<style scoped lang="scss">
.dialog-footer {
  text-align: right;
  padding: 10px 0;

  .el-button {
    margin-left: 10px;
  }
}
</style>