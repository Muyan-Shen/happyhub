<template>
  <div class="send">
    <div class="left">
      <el-form @submit="send($event)">
        <el-form-item label="活动标题">
          <el-input v-model="eventInfo.title" placeholder="请输入标题">
          </el-input>
        </el-form-item>
        <el-form-item label="举办地点">
          <el-select v-model="eventInfo.location"
                     filterable
                     allow-create
                     default-first-option
                     placeholder="举办地点">
            <el-option v-for="city in locations"
                       :label="city"
                       :value="city"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker v-model="eventInfo.startTime"
                          type="datetime"
                          format="YYYY年MM月DD日 HH:mm"
                          value-format="YYYY-MM-DD HH:mm:ss"
                          placeholder="开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="eventInfo.endTime"
                          type="datetime"
                          format="YYYY年MM月DD日 HH:mm"
                          value-format="YYYY-MM-DD HH:mm:ss"
                          placeholder="结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="座位最高档次">
          <el-input-number v-model="eventInfo.topGear"
                           :min="1"
                           :max="5">

          </el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="submit">
            提交修改
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="right">
      <span>活动描述</span>
      <div class="joditArea">
        <textarea v-model="eventInfo.description" class="description">
        </textarea>
      </div>
    </div>
  </div>
</template>

<script setup>
import {nextTick, onMounted, ref, getCurrentInstance} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";
import {Jodit} from "jodit";
import 'jodit/es2021/jodit.min.css'
import zhCn from 'jodit/esm/langs/zh_cn.js'

const $http = getCurrentInstance().appContext.config.globalProperties.$http
const profileStore = useProfileStore();
const eventInfo = ref({
  title: '',
  description: '',
  location: '',
  startTime: '',
  endTime: '',
  organizerId: profileStore.profile.id,
  topGear: 1
})
const locations = ref([
  "长沙", "株洲", "New York", "Tokyo"
])
const jodit = ref(null);

const send = (e) => {
  e.preventDefault();
  console.log(eventInfo)
}
onMounted(() => {
  nextTick((() => {
    jodit.value = Jodit.make(".description", {
      language: "zh_cn",
      uploader: {
        headers: {"token": profileStore.token},
        url: `http://localhost:8000/upload`,
        filesVariableName: () => 'file',
        pathVariableName: "url",
        isSuccess(resp) {
          return resp.code === 200;
        },
        process: function (resp) {
          return resp;
        },
        getMessage(resp) {
          return resp.url
        },
        defaultHandlerSuccess: function (resp) {
          this.s.insertImage('http://localhost:80' + resp.url);
        }
      }
    });
  }))
})
</script>

<style scoped lang="scss">
.send {
  display: flex;
  align-items: center;
  justify-content: center;

  .left {
    width: 40%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

  }

  .right {
    width: 50%;

    .joditArea{
    }
  }
}
</style>