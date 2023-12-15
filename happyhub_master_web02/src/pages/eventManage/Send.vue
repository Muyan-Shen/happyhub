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
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="eventInfo.title" placeholder="请输入标题">
          </el-input>
        </el-form-item>
        <el-form-item label="举办地点" prop="location">
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
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="eventInfo.startTime"
                          type="datetime"
                          format="YYYY年MM月DD日 HH:mm"
                          value-format="YYYY-MM-DD HH:mm:ss"
                          placeholder="开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="eventInfo.endTime"
                          type="datetime"
                          format="YYYY年MM月DD日 HH:mm"
                          value-format="YYYY-MM-DD HH:mm:ss"
                          placeholder="结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="座位最高档次" prop="topGear">
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
    <!--    创建座位弹窗-->
    <div class="dialog">
      <el-dialog
          v-model="dialogVisible"
          width="30%"
          title="Tips"
          :show-close="false"
          :close-on-click-modal="false"
          @open="getSeatInfo"
      >
        <template #header>
          <a>座位创建</a>
        </template>
        <el-form ref="seatForm"
                 :model="seatInfo"
                 :rules="seatRules"
                 @submit="generateSeat($event,$refs.eventForm)">
          <el-form-item label="座位分为多少块">
            <el-input-number v-model="seatInfo.directionNum"
                             :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="座位方位名称" prop="direction">
            <el-input v-for="(num,index) of seatInfo.directionNum"
                      :key="seatInfo.directionNum"
                      v-model="seatInfo.direction[index]"
                      placeholder="方位名称"/>
          </el-form-item>
          <el-form-item label="每个块各个档次价格" prop="gearPrice">
            <el-input v-for="(gear,index) in eventInfo.topGear"
                      v-model="seatInfo.gearPrice[index]"
                      :placeholder="index+1+`档价格`"/>
          </el-form-item>
          <el-form-item label="每个块各个档次座位数" prop="gearSum">
            <el-input v-for="(gear,index) in eventInfo.topGear"
                      v-model="seatInfo.gearSum[index]"
                      :placeholder="index+1+`档座位数`"/>
          </el-form-item>
          <el-form-item label="每个块的最大列数" prop="maxCol">
            <el-input-number v-model="seatInfo.maxCol"
                             :min="1"></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button native-type="submit">生成</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
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
import router from "../../config/route.config.js";

const $http = getCurrentInstance().appContext.config.globalProperties.$http
const profileStore = useProfileStore();
const dialogVisible = ref(false)
const seatInfo = reactive({
  eventId: -1,
  topGear: -1,
  directionNum: 1,
  direction: [],
  maxCol: -1,
  gearSum: [],
  gearPrice: []
})
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
const seatRules = reactive({
  direction: [{required: true, message: "座位方位不能为空", trigger: "blur"}],
  gearPrice: [{required: true, message: "档次价格不能为空", trigger: "blur"}],
  gearSum: [{required: true, message: "档次座位数不能为空", trigger: "blur"}],
  maxCol: [{required: true, message: "最大列数不能为空", trigger: "blur"}],
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
      $http.post('event/create', eventInfo).then(resp => {
        console.log(resp)
        if (resp.code === 200) {
          eventId.value = resp.eventId;
          ElMessage.success({
            message: "创建成功",
            duration: 750
          })
          // 打开创建座位的弹窗
          getSeatInfo()
        } else {
          ElMessage.error({
            message: "创建失败:"+resp.msg,
            duration: 1250
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
const triggerFileInput = () => {
  fileInput.value.click();
}
const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    const formData = new FormData();
    formData.append('photo', file);
    // 发送 POST 请求到后端
    $http.post('/upload-event', formData, {headers: {'Content-Type': 'multipart/form-data'}}).then(resp => {
      eventInfo.photoUrl = resp.url
    });
  }
}
const getSeatInfo = () => {
  if (eventId.value > 0) {
    seatInfo.eventId = eventId.value;
    dialogVisible.value = true;
  }
  seatInfo.eventId = eventId.value;
  seatInfo.topGear = eventInfo.topGear;
}
const generateSeat = (e, form) => {
  e.preventDefault();
  form.validate((vali) => {
    if (vali) {
      $http.post('/event/generateSeats', seatInfo).then(resp => {
        if (resp.code === 200) {
          ElMessage.success({
            message: "生成成功",
            duration: 1000
          })
          dialogVisible.value = false;
          router.push("/home")
        } else {
          ElMessage.error({
            message: "生成失败:"+resp.message,
            duration: 1250
          })
        }
      })
    }
  })

  console.log(seatInfo)
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
  $http.get('/event/getCity').then(resp => {
    locations.value = resp.data;
  })
})
</script>

<style scoped lang="scss">
.send {
  display: flex;
  align-items: center;
  justify-content: center;

  .pic {
    width: 240px;
    align-items: center;

    span {
    }

    .el-image {
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

  .dialog {
    .el-dialog {
      .el-form {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        justify-content: flex-start;

        .el-form-item {
          .el-input {
            width: 90px;
            margin-bottom: 4px;
            margin-right: 4px;
          }
        }
      }
    }
  }
}
</style>