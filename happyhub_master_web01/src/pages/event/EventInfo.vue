<template>
  <div class="eventDiv">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <div class="eventInfoDiv" >
          <div class="eventPhotoDiv" >
<!--            <img :src=eventInfo.photoUrl alt="">-->
          </div>
          <div class="eventMessage">
            <el-card class="eventMessage" style="width: 100%">
              <div class="eventTitle" >活动标题：{{eventInfo.title}}</div>
              <div class="eventTime">活动时间:{{eventInfo.startTime}}-{{eventInfo.endTime}}</div>
              <div class="eventLocation">活动地点:{{eventInfo.location}}</div>
              <div class="eventGear">活动档位:
                <el-radio-group v-model="gear1" size="small" @change="change">
                  <el-radio-button v-for="gear of gears" :label="gear"></el-radio-button>
                </el-radio-group>
              </div>
            </el-card>
          </div>
        </div>
        <div class="eventHtmlDiv">
          <h1>活动富文本</h1>
        </div>
        <div class="eventNoticeDiv">
          <div class="notice">
            <h1 class="content">注意事项</h1>
            <el-icon color="red">
              <CircleClose/>
            </el-icon>
            <b>不支持退</b><br>
            票品为有价票券，非普通商品，其背后承载的文化服务具有时效性，稀缺性等特征，不支持退换。<br><br>
            <el-icon color="green">
              <CircleCheck/>
            </el-icon>
            <b>电子票</b><br>
            电子票：通过票夹中的二维码或身份证，可以直接验票入场（具体以下单后票夹信息为准。<br><br>
            <el-icon color="green">
              <CircleCheck/>
            </el-icon>
            <b>电子发票</b><br>
            发票开具方：湖南小麦文化
            该项目支持开具电子发票。该发票由第三方主办提供，需要在演出开始前提交发票申请，一般演出结束后1个月左右开具<br>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {CircleClose, CircleCheck} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import {getCurrentInstance} from "vue";

const eventInfo = ref(
    {title:"",},
    {id:"",},
    {location:"",},
    {startTime:"",},
    {endTime:"",},
    {description:"",},
    {organizerUsername:"",},
    {status:"",},
    {statusDescription:"",},
)

const gear1 = ref({
  value: '',
  visible: false,
})
const gears = ref([])

function change() {
  console.log(gear1)
}

const eventId = ref(1001);
const $http = getCurrentInstance().appContext.config.globalProperties.$http

const getEventInfo = async (eventId) => {
  await $http.get('/event/' + eventId)
      .then(res => {
        // eventId.value = this.$route.params.eventId;获取前端传过来的活动id
        gears.value = res.gearPrices;
        eventInfo.value = res.data;
        console.log(eventInfo.value)
      }).catch(err => {
        console.error('获取活动信息时出错：', err);
      });
}

onMounted(
    () => {
      // eventId.value = this.$route.params.eventId;
      getEventInfo(eventId.value);
    }
)

</script>

<style scoped lang="scss">
.eventDiv {
  height: 100%;

  .el-main {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-right: 200px;
    margin-left: 200px;
    padding: 0;
    //background-color: #13ce66;
    .eventInfoDiv {
      display: flex;
      margin-top: 30px;
      height: 50%;
      width: 100%;
      background-color: #ff4949;
      flex-direction: row;
      border: solid 1px black;

      .eventPhotoDiv {
        width: 40%;
      }

      .eventMessage {
        border: 0px solid black;

        .eventTitle {
          margin-bottom: 10px;
          font-size: 25px;
          font-weight: bold;
        }

        .eventTime {
          margin-bottom: 10px;
          font-size: 14px;
        }

        .eventLocation {
          margin-bottom: 10px;
          font-size: 14px;
        }

        .eventGear {
          margin-bottom: 10px;
          font-size: 14px;
        }
      }
    }

    .eventHtmlDiv {
      height: 50%;
      width: 100%;
      background-color: blue;
      border: solid 1px black;
    }

    .eventNoticeDiv {
      top: 90px;
      padding: 10px;
      right: 0;
      width: 180px;
      height: auto;
      background-color: #f8f4f4;
      position: absolute;
      border: solid 1px black;

      .notice {
        padding: 10px;
        border: solid 1px black;
      }

      .content {
        text-align: center;
      }
    }
  }
}
</style>