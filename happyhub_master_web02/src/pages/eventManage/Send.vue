<template>
  <div class="send">
    <div class="pic">
      <span>宣传图</span>
      <el-image :src="eventInfo.photoUrl" @click="triggerFileInput"/>
        <!-- 隐藏的文件输入元素 -->
      <input type="file" ref="fileInput" @change="handleFileChange" style="display: none;"/>
    </div>
    <div class="left">
      <el-form ref="eventForm"
               :model="eventInfo"
               :rules="eventRules"
               @submit="send($event,$refs.eventForm)">
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
          <el-button native-type="submit">
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
import {nextTick, onMounted, ref, getCurrentInstance, reactive} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";
import {Jodit} from "jodit";
import 'jodit/es2021/jodit.min.css'
import zhCn from 'jodit/esm/langs/zh_cn.js'
import {ElMessage} from "element-plus";

const $http = getCurrentInstance().appContext.config.globalProperties.$http
const profileStore = useProfileStore();
const eventInfo = reactive({
  title: "",
  description: "",
  location: "",
  startTime: "",
  endTime: "",
  organizerId: profileStore.profile.id,
  photoUrl: "/event/photo05.jpg",
  topGear: 1
})
const eventRules = reactive({
  title: [{required: true, message: "活动标题不能为空", trigger: "blur"}],
  description: [{required: true, message: "活动描述不能为空", trigger: "blur"}],
  location: [{required: true, message: "举办位置不能为空", trigger: "blur"}],
  startTime: [{required: true, message: "开始时间不能为空", trigger: "blur"}],
  endTime: [{required: true, message: "结束时间不能为空", trigger: "blur"}],
  topGear: [{required: true, message: "最高档位不能为空", trigger: "blur"}],
})
const locations = ref([
  "长沙", "株洲", "New York", "Tokyo"
])
const jodit = ref(null);
const eventId = ref(-1);
const fileInput = ref()
const send = (e, form) => {
  e.preventDefault();
  form.validate((valid) => {
    if (valid) {
      eventInfo.description = jodit.value.value
      console.log(eventInfo)
      $http.post('event/create', eventInfo).then(resp => {
        console.log(resp)
        if (resp.code === 200) {
          eventId.value = resp.eventId;
          ElMessage.success({
            message: "创建成功",
            duration: 750
          })
        } else {
          ElMessage.error({
            message: "创建失败，请按要求填写表单",
            duration: 750
          })
        }
      })
    } else {
      ElMessage.error({
        message: "创建失败，请按要求填写表单",
        duration: 1000
      })
    }
  })
}
const triggerFileInput = ()=>{
  fileInput.value.click();
}
const handleFileChange = (event)=>{
  const file = event.target.files[0];
  if (file) {
    const formData = new FormData();
    formData.append('photo', file);
      // 发送 POST 请求到后端
    $http.post('/upload-event', formData, {headers: {'Content-Type': 'multipart/form-data'}}).then(resp=>{
      eventInfo.photoUrl = resp.url
    });
  }
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
          this.s.insertImage(resp.url);
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
  .pic{
    width: 240px;
    align-items: center;
    span{
    }
    .el-image{
    }
  }
  .left {
    width: 40%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

  }

  .right {
    width: 50%;

    .joditArea {
    }
  }
}
</style>