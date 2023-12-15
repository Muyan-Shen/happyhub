<template>
  <div class="eventDiv">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <div class="all_main">
          <el-button class="back" @click="reBack">返回</el-button>
          <div class="left">
            <div class="eventInfoDiv">
              <div class="eventPhotoDiv">
                <el-image :src="eventInfo.photoUrl"/>
              </div>
              <el-card class="eventMessage">
                <div class="eventMessage" style="width: 100%">
                  <div class="eventTitle">
                    <el-icon style="transform:rotate(90deg)" color="hotpink">
                      <PriceTag/>
                    </el-icon>
                    活动标题：{{ eventInfo.title }}
                  </div>
                  <div class="eventTime">活动时间:{{ eventInfo.startTime }}-{{ eventInfo.endTime }}</div>
                  <div class="eventLocation">活动地点:{{ eventInfo.location }}</div>
                  <div class="eventGear">活动档位:
                    <el-radio-group v-model="gear1.value" size="small" @change="change">
                      <el-radio-button v-for="(gear,index) of gears" :label="index1[index]"  >{{gear}}</el-radio-button>
                    </el-radio-group>
                  </div>
                  <el-button  @click="getOrder(eventInfo.id,price)">购买</el-button>
                </div>
              </el-card>
            </div>

            <el-divider>活动详情</el-divider>
            <div class="titles">
              <el-tabs v-model="activeName" class="demo-tabs" style="background-color: white" @tab-click="scrollToLine">
                <el-tab-pane label="活动须知" name="first"  ></el-tab-pane>
                <el-tab-pane label="购票须知" name="second"  ></el-tab-pane>
                <el-tab-pane label="观演须知" name="third"   ></el-tab-pane>
              </el-tabs>
            </div>


            <h1 style="margin-top: 75px" id="line-start1">活动须知</h1>
            <div class="eventHtmlDiv" v-html="eventInfo.description"></div>
            <el-divider></el-divider>
            <h1 id="line-start2">购票须知</h1>
            <div class="words">
              <div>
                <p class="item_title">
                  限购规则</p>
                <ul>
                  <li>每笔订单最多购买6张</li>
                </ul>
              </div>
              <div>
                <p class="item_title">退票/换票规则</p>
                <ul>
                  <li>票品为有价票券，非普通商品，其背后承载的文化服务具有时效性，稀缺性等特征，不支持退换。</li>
                </ul>
              </div>
              <div>
                <p class="item_title">入场规则</p>
                <ul>
                  <li>须打开【票夹】扫码入场，截图无效。</li>
                </ul>
              </div>
              <div><p class="item_title">儿童购票</p>
                <ul>
                  <li>
                    1.2米以上凭成人票入场，1.2米以下谢绝入场
                  </li>
                </ul>
              </div>
              <div><p class="item_title">发票说明</p>
                <ul>
                  <li>演出开始前，去【订单详情页】提交发票申请。我们会将电子发票发送至您的邮箱。</li>
                </ul>
              </div>
              <div><p class="item_title">实名购票规则</p>
                <ul>
                  <li>
                    一张门票对应一个证件；证件支持：身份证/港澳台居民居住证/港澳居民来往内地通行证/外国人永久居留身份证/台湾居民来往大陆通行证/护照
                  </li>
                </ul>
              </div>
              <div><p class="item_title">异常排单说明</p>
                <ul>
                  <li>对于异常订购行为，大麦网有权在订单成立或者生效之后取消相应订单。异常订购行为包括但不限于以下情形：
                    （1）通过同一ID订购超出限购张数的订单。
                    （2）经合理判断认为非真实消费者的下单行为，包括但不限于通过批量相同或虚构的支付账号、收货地址（包括下单时填写及最终实际收货地址）、收件人、电话号码订购超出限购张数的订单。
                  </li>
                </ul>
              </div>
              <div><p class="item_title">温馨提示</p>
                <ul>
                  <li>
                    1.购买前请您提前规划好行程，做好相应准备，以免影响您的正常观演，感谢您的理解和配合。2.若演出受不可抗力影响延期或取消，大麦将对演出票订单按照项目官方通知方案进行处理，其他因观演发生的费用需由您自行承担。
                  </li>
                </ul>
              </div>
            </div>
            <el-divider></el-divider>
            <h1 id="line-start3">观演须知</h1>
            <div class="words">
              <div><p class="item_title" >演出时长</p>
                <ul>
                  <li>约90分钟，仅供参考，最终以现场实际时长为准</li>
                </ul>
              </div>
              <div><p class="item_title">入场时间</p>
                <ul>
                  <li>提前30分钟，仅供参考，最终以现场实际入场时间为准</li>
                </ul>
              </div>
              <div><p class="item_title">最低演出曲目</p>
                <ul>
                  <li>以现场为准</li>
                </ul>
              </div>
              <div><p class="item_title">主要演员</p>
                <ul>
                  <li>张玮玮等，以页面为准</li>
                </ul>
              </div>
              <div><p class="item_title">最低演出时长</p>
                <ul>
                  <li>90分钟</li>
                </ul>
              </div>
              <div><p class="item_title">禁止携带物品</p>
                <ul>
                  <li>
                    由于安保和版权的原因，大多数演出、展览及比赛场所禁止携带食品、饮料、专业摄录设备、打火机等物品，请您注意现场工作人员和广播的提示，予以配合
                  </li>
                </ul>
              </div>
              <div><p class="item_title">寄存说明</p>
                <ul>
                  <li>无寄存处,请自行保管携带物品，谨防贵重物品丢失。</li>
                </ul>
              </div>
              <div><p class="item_title">小麦网初始开售时全场可售门票总张数</p>
                <ul>
                  <li>1000</li>
                </ul>
              </div>
            </div>


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

  </div>
  </el-main>
  </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {CircleClose, CircleCheck, PriceTag} from "@element-plus/icons-vue";
import {onMounted, reactive, ref, computed, nextTick} from "vue";
import {getCurrentInstance} from "vue";
import {ElMessage} from "element-plus";
import  {useRoute} from "vue-router";
import router  from "../../config/router.config.js";
import {useProfileStore} from "../../stores/useProfile.js";
import index from "pinia-plugin-persist";

const route = useRoute();
const index1 = [2,5,8]
const useProfileStore1 = useProfileStore();

const eventInfo = ref(
    {
      title: "",
      id: "",
      location: "",
      startTime: "",
      endTime: "",
      description: "",
      organizerUsername: "",
      status: "",
      photoUrl: "",
      statusDescription: ""
    }
)

const gear1= ref({
  value: '',
  visible: false,
})
const price=ref()
const gears = ref([]);

const activeName = ref('first');

const titleOffsetTop = ref(479)

window.addEventListener('scroll',()=>{
  const value = (499 - window.scrollY);
  titleOffsetTop.value = (value <= 0 ? 0 : value) + "px";
},true)


const scrollToLine = ()=> {
  if(activeName.value == 'first'){
    const targetElement = document.querySelector(`#line-start1`);
    if (targetElement) {
      targetElement.scrollIntoView({ behavior: "smooth" });
    }
  }
  if(activeName.value == 'second'){
    const targetElement = document.querySelector(`#line-start2`);
    if (targetElement) {
      targetElement.scrollIntoView({ behavior: "smooth" });
    }
  }
  if(activeName.value == 'third'){
    const targetElement = document.querySelector(`#line-start3`);
    if (targetElement) {
      targetElement.scrollIntoView({ behavior: "smooth" });
    }
  }
}



function change() {
  price.value=gears.value[gear1.value.value]
  console.log(gear1.value.value)
  // console.log(price.value)
}

const eventId = route.params.eventId;
const $http = getCurrentInstance().appContext.config.globalProperties.$http

const getEventInfo = (eventId) => {
  titleOffsetTop.value=479;
  $http.get('/event/' + eventId)
      .then(res => {
        gears.value = res.gearPrices;
        eventInfo.value = res.data;
        // console.log(gears.value)
      }).catch(err => {
    console.error('获取活动信息时出错：', err);
  });
}

onMounted(
    () => {
      getEventInfo(eventId);
    }
)
const orderId = ref({
});

const reBack= () => {
   router.push({
     name:"home",
   })
}
const getOrder = (eventId, price) => {
  if(price==null||price.value==''){
    ElMessage("档位未选择");
    price.value=''
    return
  }

  useProfileStore1.gear=gear1.value.value
  console.log(price.value)
  // ElMessage("发起订单");
  router.push({
    name:"eventOrderCreate",
    params:{
      "eventId":eventId,
      "price": gears.value[gear1.value.value]
    }
  })
}
</script>

<style  lang="scss">
.detail .words img {
  width: 100% !important; /* 强制宽度为100%并覆盖内联样式 */
  height: auto !important; /* 高度根据宽度调整，并覆盖内联样式 */
  display: block; /* 图片显示为块级元素 */
}
.eventDiv {
  height: 100%;

  .el-main {
    justify-content: center;
    align-items: center;
    margin-right: 200px;
    margin-left: 200px;
    padding: 0;
    //background-color: #13ce66;
    .all_main {
      width: 100%;
      flex-direction: column;
      .back{
        margin-top: 20px;
        margin-left: 50px;
      }
      .left {
        width: 80%;
        float: left;
        flex-direction: row;
        overflow: hidden;
        .titles{
          position: fixed;
          top: v-bind("titleOffsetTop");
        }
        .eventInfoDiv {
          display: flex;
          margin-top: 30px;
          height: 50%;
          width: 100%;
          //background-color: #ff4949;


            .demo-tabs > .el-tabs__content {
              height: 20px;
              width: 100%;
              padding: 32px;
              color: #6b778c;
              font-size: 32px;
              font-weight: 600;
            }


          .eventPhotoDiv {
             width:304.29px;
            height:359.39px;
            .el-image{
              width: 304.29px;
              height: 354.99px;
            }
          }

          .eventMessage {
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
          //background-color: blue;

          //border: solid 1px black;
        }
      }

      .eventNoticeDiv {
        margin-top: 30px;
        width: 20%;
        height: auto;
        float: right;
        background-color: #f8f4f4;


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
}
</style>