<template>
    <div class="user-input">
        <el-avatar :src="userSrc"/>
        <div class="text1"><el-text>{{info}}</el-text></div>
    </div>
</template>

<script setup>
import {toRefs} from "vue";

const props = defineProps({
    userSrc:String,
    info: String,
})

const {info,userSrc} =toRefs(props)
</script>

<style scoped lang="scss">
    .user-input {
        width: 95%;
        padding: 10px; /* 增加内边距以增加呼吸空间 */
        align-items: center;
        direction: rtl;
        display: flex;
        background: #f9f9f9; /* 轻微的背景色以与页面背景进行对比 */
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 给予轻微的阴影以增加层次感 */

        .text1 {
            background: white; /* 明亮的背景色 */
            border: solid 1px #eaeaea; /* 更轻微的边框色 */
            border-radius: 15px; /* 增加边框半径以获得更圆润的外观 */
            padding: 5px 10px; /* 文本周围的空间 */
            margin-right: 10px; /* 与头像之间的间距 */
            color: #333; /* 较深的文字颜色提高可读性 */
            font-size: 0.9rem; /* 适当的文字大小 */
            display: flex; /* 用于垂直居中文本 */
            align-items: center;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1); /* 文本框也应用轻微的阴影 */
            max-width: 80%; /* 防止文本框过宽 */
            overflow: visible; // 允许内容扩展
            white-space: normal; // 允许换行
        }
    }
</style>