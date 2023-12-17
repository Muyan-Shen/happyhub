<template>
    <div class="robot-input">
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
        <div class="text1" v-html="info"></div>

    </div>
</template>

<script setup>

import {toRefs} from "vue";

const props = defineProps({
    info: String,
})

const {info} =toRefs(props)

</script>

<style scoped lang="scss">
.robot-input {
    width: 95%;
    padding: 10px;
    align-items: center;
    display: flex;
    background: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

    .text1 {
        background: white;
        border: solid 1px #eaeaea;
        border-radius: 15px;
        padding: 5px 10px;
        margin-left: 10px;
        color: #333;
        font-size: 0.9rem;
        display: flex;
        align-items: center;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        max-width: 80%;
        overflow: visible;
        white-space: normal;
    }
}
</style>