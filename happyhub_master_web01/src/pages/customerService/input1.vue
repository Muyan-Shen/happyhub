<template>
    <div class="imput1">
        <el-input v-model="input" placeholder="请输入你的问题" />
        <el-button @click="send">发送</el-button>
    </div>
</template>

<script setup>
import {ref} from "vue";

const  input = ref('')

const emit = defineEmits(['sendData'])
const send = ()=>{
    let param={
        input:input.value
    }
    //传递给父组件
    emit('sendData',param)
}
</script>

<style scoped lang="scss">
    .imput1{
        width: 100%;
        align-items: center;
        display: flex;
        .el-input{
            margin-left: 10px;
        }
        .el-button{
            margin-left: 10px;
            border-radius: 10px;
        }
    }
</style>