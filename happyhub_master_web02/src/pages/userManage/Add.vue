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
            <div class="form-group">
                <label for="username">用户名:</label>
                <input v-model="form.username" name="username" type="text" @blur="validate" />
                <span v-if="usernameError" class="error">{{ usernameError }}</span>
            </div>

            <!-- 邮箱 -->
            <div class="form-group">
                <label for="email">邮箱:</label>
                <input v-model="form.email" name="email" type="email" @blur="validate" />
                <span v-if="emailError" class="error" :class="{ active: emailError }">{{ emailError }}</span>
            </div>

            <!-- 密码 -->
            <div class="form-group">
                <label for="password">密码:</label>
                <input v-model="form.passwordHash" name="password" type="password" @blur="validate" />
                <span v-if="passwordError" class="error" :class="{ active: passwordError }">{{ passwordError }}</span>
            </div>

            <!-- 提交按钮 -->
            <button type="submit" class="submit-button">添加</button>
        </el-form>
    </div>
</template>

<script setup>

import {reactive, ref} from "vue";
import * as $http from "../../config/http.config.js";
import {useField, validate} from "vee-validate";


// 表单模型
const form = reactive({
    username: '',
    email: '',
    passwordHash:'' ,
    picPath:''
});

const rules = {
    username: [
        { required: true, message: "请输入用户名", trigger: "blur" },
        { min: 3, message: "用户名长度不能小于 3 个字符", trigger: "blur" }
    ],
    email: [
        { required: true, message: "请输入邮箱地址", trigger: "blur" },
        { email: true, message: "请输入正确的邮箱格式", trigger: "blur" }
    ],
    password: [
        { required: true, message: "请输入密码", trigger: "blur" },
        { min: 6, message: "密码长度不能小于 6 个字符", trigger: "blur" }
    ]
};
// 初始化错误对象
const errors = reactive({});

// 在使用 useField 之前这些名字是未定义的，所以无法将它们作为参数传递
// 改为如下使用，第一个参数是字段名，第二个参数是验证规则，初始值可以作为第三个参数传递

const { value: usernameValue, errorMessage: usernameError } = useField('username', rules.username);
const { value: emailValue, errorMessage: emailError } = useField('email', rules.email);
const { value: passwordValue, errorMessage: passwordError } = useField('password', rules.password);

// 更新模型绑定部分
form.username = usernameValue;
form.email = emailValue;
form.passwordHash = passwordValue;

const userForm = ref(null); // 获取表单引用
const fileInput = ref();

// 触发文件输入元素的点击事件
const triggerFileInput = () => {
    fileInput.value.click();
};


// 提交表单，注册用户信息
const submitForm = async () => {


    if (!userForm.value) {
        console.log("表单引用未获得");
        return;
    }
    // 验证表单的所有字段
    userForm.value.validate().then(async (valid) => {
        if (valid) {
            try {
                // 发送POST请求到后端注册账号的URL
                const accountResponse = await $http.post('/user/register', form);
                console.log(accountResponse)
                // 假设后端返回的accountResponse包含用户信息和账户创建的状态
                if (accountResponse && accountResponse.data) {
                    // 账号创建成功，继续上传头像
                    const fileEl = fileInput.value;
                    if (fileEl && fileEl.files.length > 0) {
                        await handleFileUpload(fileEl.files[0], accountResponse.data.userId);
                    }
                } else {
                    // 账户创建失败，输出错误信息
                    console.error('账户创建失败', accountResponse.data.message);
                }
            } catch (error) {
                console.error('提交失败', error);
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

        // 处理上传头像后的逻辑，例如更新头像显示
        form.picPath = response.url; // 假设后端返回新头像的URL
    } catch (uploadError) {
        console.error('上传头像失败', uploadError);
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
.layout {
    // ...其它样式保持不变...

    // 新增表单样式
    .form-container {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        margin: 20px;
        padding: 20px;
    }

    .form {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .form-group {
        display: flex;
        flex-direction: column;
        margin-bottom: 15px;
        width: 100%;
    }

    .form-group label {
        margin-bottom: 5px;
        color: #303133;
        font-weight: bold;
    }

    .form-group input[type="text"],
    .form-group input[type="email"],
    .form-group input[type="password"],
    .form-group input[type="file"] {
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        padding: 10px;
        width: 100%;
    }

    .error {
        color: #f56c6c;
        font-size: 0.8em;
        margin-top: 5px;
    }

    .submit-button {
        background-color: #409eff;
        border: none;
        border-radius: 4px;
        color: white;
        cursor: pointer;
        padding: 10px 20px;
        margin-top: 10px;
    }

    .submit-button:hover {
        background-color: #66b1ff;
    }

    // ...其他样式保持不变...

    .form-container {
        transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        &:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }
    }

    .form-group input[type="text"],
    .form-group input[type="email"],
    .form-group input[type="password"] {
        // 输入框在获取焦点时的动画和样式
        transition: border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        &:focus {
            border-color: #409eff;
            box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
        }
    }

    // 悬停在提交按钮上时的动画和样式
    .submit-button {
        transition: background-color 0.3s ease-in-out;
        &:hover {
            background-color: #66b1ff;
            transform: scale(1.05); // 按钮放大效果
        }
    }

    // 错误信息的淡入动画
    .error {
        transition: opacity 0.3s ease-in-out;
        opacity: 0;
        &.active {
            opacity: 1; // 当有错误时显示
        }
    }

    // 头像的悬停效果
    .el-avatar {
        cursor: pointer;
        transition: transform 0.3s ease;
        &:hover {
            transform: scale(1.1); // 头像放大效果
        }
    }
}
</style>