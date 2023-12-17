<template>
  <div class="side">
    <div class="title">
      <h3>我的小麦</h3>
    </div>
    <div class="subheading1">
      <span class="a">交易中心</span>
      <div>
      <a href="/#/orderInfo">
        <span class="a1">订单管理</span>
      </a>
      </div>
    </div>
    <div class="subheading2">
      <span class="a">账户中心</span>
      <div>
        <a href="/#/userInfo">
          <span class="a1">个人信息</span>
        </a>
      </div>
    </div>
  </div>
</template>
<script setup>

</script>

<style scoped lang="scss">
.side{
    margin-top: 10px;
    margin-left: 200px;
    width: 203px;
}
div {
  background-color: #f5f5f5;
  border: 1px solid #e5e5e5;
  .title {
    width: 203px;
    height: 33px;
    align-items: center;
    display: flex;
    background-color: #ff1c74;

    h3 {
      font-size: 14px;
      color: #fff;
      padding: 0 0 0 11px;
    }
  }

  .subheading1{
    width: 199.6px;
    height: 40px;
    div{
      a{
        text-decoration: none;
      }
    }
  }
  .subheading2{
    width: 199.6px;
    height: 40px;
    div{
      a{
        text-decoration: none;
      }
    }
  }
  .a{
      font-size: 14px;
      font-weight:  bolder;
      padding: 0 0 0 20px;
  }
  .a1{
    font-size: 14px;
    padding: 0 0 0 20px;
  }
}
</style>