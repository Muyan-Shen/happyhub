<template>
    <div class="form-container">
        <el-form ref="userForm" :model="form" :rules="rules" @submit.native.prevent="submitForm">
            <!-- 头像 -->
            <div class="form-group">
                <label for="photo">头像:</label>
                <div style="margin-left: 50px">
                    <!-- 头像，点击时触发 fileInput 的点击事件 -->
                    <el-avatar :size="96" :src="form.picPath" @click="triggerFileInput"/>
                    <!-- 隐藏的文件输入元素 -->
                    <input type="file" ref="fileInput" @change="handleFileChange" style="display: none;"/>
                </div>
            </div>
            <!-- 用户名 -->
            <el-form-item class="form-group" prop="username">
                <label for="username">用户名:</label>
                <el-input v-model.trim="form.username" type="text"/>
            </el-form-item>

            <!-- 邮箱 -->
            <el-form-item class="form-group" prop="email">
                <label for="email">邮箱:</label>
                <el-input v-model.trim="form.email" type="email"/>
            </el-form-item>

            <!-- 密码 -->
            <el-form-item class="form-group" prop="passwordHash">
                <label for="password">密码:</label>
                <el-input v-model.trim="form.passwordHash" type="password"/>
            </el-form-item>

            <!-- 提交按钮的容器 -->
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
        // { email: true, message: , trigger: "blur" }
    ],
    passwordHash: [
        {required: true, message: "请输入密码", trigger: "blur"},
        {min: 6, message: "密码长度不能小于 6 个字符", trigger: "blur"}
    ]
};


// 更新模型绑定部分


const userForm = ref(null); // 获取表单引用
const fileInput = ref();

// 触发文件输入元素的点击事件
const triggerFileInput = () => {
    fileInput.value.click();
};


// 提交表单，注册用户信息
const submitForm = async () => {
    console.log(11111111)
    if (!userForm.value) {
        console.log("表单引用未获得");
        return;
    }
    // 验证表单的所有字段
    await userForm.value.validate().then(async (valid) => {
        if (valid) {
            try {
                // 发送POST请求到后端注册账号的URL
                const accountResponse = await $http.post('/user/register', form);
                console.log(222222)
                // 假设后端返回的accountResponse包含用户信息和账户创建的状态
                if (accountResponse && accountResponse.data) {
                    ElMessage.success('账户创建成功，头像上传中...');
                    // 账号创建成功，继续上传头像
                    const fileEl = fileInput.value;
                    if (fileEl && fileEl.files.length > 0) {
                        await handleFileUpload(fileEl.files[0], accountResponse.data.userId);
                    }
                } else {
                    // 账户创建失败，输出错误信息
                    ElMessage.error('账户创建失败，请检查提交的资料。');
                }
            } catch (error) {
                ElMessage.error('提交失败，请稍后再试。');
            }
        }
    })
};

// 单独的头像上传函数
const handleFileUpload = async (file, userId) => {
    const formData = new FormData();
    formData.append('avatar', file);
    formData.append('userId', userId); // 将userId添加到formData中，以便后端关联用户和头像

    try {
        // 发送POST请求到另一个后端URL上传头像
        const response = await $http.post('/upload-avatar', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        ElMessage.success('头像上传成功！');
        // 处理上传头像后的逻辑，例如更新头像显示
        form.picPath = response.url; // 假设后端返回新头像的URL
    } catch (uploadError) {
        ElMessage.error('上传头像失败，请稍后再试。');
    }
};

// 提交文件的函数留给 input 的 change 事件处理
const handleFileChange = async (event) => {
    // 目前我们不在这里上传文件，因为我们会在提交表单后上传头像
    const file = event.target.files[0];
    if (file) {
        // 预设图片路径为本地临时URL，等待上传
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
