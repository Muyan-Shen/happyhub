<template>
    <div class="form-container">
        <el-form ref="userForm" :model="form" :rules="rules" @submit.native.prevent="submitForm">
            <div class="form-group">
                <label for="photo">头像:</label>
                <div style="margin-left: 50px">
                    <el-avatar :size="96" :src="form.picPath" @click="triggerFileInput"/>
                    <input type="file" ref="fileInput" @change="handleFileChange" style="display: none;"/>
                </div>
            </div>
            <el-form-item class="form-group" prop="username">
                <label for="username">用户名:</label>
                <el-input v-model.trim="form.username" type="text"/>
            </el-form-item>
            <el-form-item class="form-group" prop="email">
                <label for="email">邮箱:</label>
                <el-input v-model.trim="form.email" type="email"/>
            </el-form-item>
            <el-form-item class="form-group" prop="passwordHash">
                <label for="password">密码:</label>
                <el-input v-model.trim="form.passwordHash" type="password"/>
            </el-form-item>
            <div class="submit-button-container">
                <el-button @click="submitForm" class="submit-button">添加</el-button>
            </div>
        </el-form>
    </div>
</template>

<script setup>

import {getCurrentInstance, reactive, ref} from "vue";

import {ElMessage} from "element-plus";


// 表单模型
const form = reactive({
    username: '',
    email: '',
    passwordHash: '',
    picPath: ''
});
const $http  = getCurrentInstance().appContext.config.globalProperties.$http
const rules = {
    username: [
        {required: true, message: "请输入用户名", trigger: "blur"},
        {min: 3, message: "用户名长度不能小于 3 个字符", trigger: "blur"}
    ],
    email: [
        {required: true, message: "请输入邮箱地址", trigger: "blur"},
        {pattern: /^[a-zA-Z0-9]+@.+$/, message: "请输入正确的邮箱格式", trigger: 'blur'}
    ],
    passwordHash: [
        {required: true, message: "请输入密码", trigger: "blur"},
        {min: 6, message: "密码长度不能小于 6 个字符", trigger: "blur"}
    ]
};

const userForm = ref(null);
const fileInput = ref();

const triggerFileInput = () => {
    fileInput.value.click();
};


const submitForm = async () => {
    if (!userForm.value) {
        console.log("表单引用未获得");
        return;
    }
    await userForm.value.validate().then(async (valid) => {
        if (valid) {
            try {
                const accountResponse = await $http.post('/user/register', form);
                if (accountResponse && accountResponse.data) {
                    ElMessage.success('账户创建成功，头像上传中...');
                    const fileEl = fileInput.value;
                    if (fileEl && fileEl.files.length > 0) {
                        await handleFileUpload(fileEl.files[0], accountResponse.data.userId);
                    }
                } else {
                    ElMessage.error('账户创建失败，请检查提交的资料。');
                }
            } catch (error) {
                ElMessage.error('提交失败，请稍后再试。');
            }
        }
    })
};

const handleFileUpload = async (file, userId) => {
    const formData = new FormData();
    formData.append('avatar', file);
    formData.append('userId', userId);

    try {
        const response = await $http.post('/upload-avatar', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        ElMessage.success('头像上传成功！');
        form.picPath = response.url;
    } catch (uploadError) {
        ElMessage.error('上传头像失败，请稍后再试。');
    }
};

const handleFileChange = async (event) => {
    const file = event.target.files[0];
    if (file) {
        form.picPath = URL.createObjectURL(file);
    }
};
</script>


<style scoped lang="scss">
.form-container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin: 20px auto;
  padding: 30px;
  max-width: 500px;
  transition: box-shadow 0.3s ease;

  &:hover {
    box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
  }
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;

  label {
    color: #303133;
    font-weight: bold;
    margin-bottom: 8px;
  }

  .el-input {
    border-radius: 4px;
  }
}

.submit-button-container {
  display: flex;
  justify-content: center;
}

.submit-button {
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.2s;

  &:hover {
    background-color: #66b1ff;
    transform: translateY(-2px);
  }
}

.el-avatar {
  cursor: pointer;
  transition: transform 0.2s;

  &:hover {
    transform: scale(1.05);
  }
}

input[type="file"] {
  display: none;
}

.error {
  color: #f56c6c;
  margin-top: 5px;
}
</style>
