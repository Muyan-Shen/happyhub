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
                if (response.code === 200) {
                    ElMessage.success({
                        message: '新增角色成功',
                        duration: 1000,
                        onClose: () => {
                            emit('update:modelValue', false)
                        },
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
.add {
    .el-dialog {
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);

        .el-dialog__header {
            font-size: 18px;
            font-weight: bold;
            color: #303133;
            background-color: #f5f5f5;
            border-bottom: 1px solid #ebeef5;
        }

        .el-dialog__body {
            padding: 20px 30px;
            font-size: 14px;
            color: #606266;

            .el-form-item {
                margin-bottom: 20px;
            }

            .el-input {
                border-radius: 4px;
                &:focus {
                    border-color: #409eff;
                    box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
                }
            }
        }

        .dialog-footer {
            border-top: 1px solid #ebeef5;
            padding: 10px 20px;
            text-align: right;
            background-color: #f5f5f5;

            .el-button {
                margin-left: 10px;
                border-radius: 4px;
                &:hover {
                    transform: translateY(-2px);
                }
            }

            .el-button[type="primary"] {
                background-color: #409eff;
                border-color: #409eff;
                color: #fff;
                &:hover {
                    background-color: #66b1ff;
                    border-color: #66b1ff;
                }
            }
        }
    }
}
</style>