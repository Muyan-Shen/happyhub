package cn.shenmuyan.controller;

import cn.shenmuyan.Xfun.AiManager;
import cn.shenmuyan.Xfun.XfunListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 信息Controller
 *
 * @Title: MessagesController
 * @Author ShenMuyan
 * @Package cn.shenmuyan.controller
 * @Date 2023/12/17 7:31
 */
@RestController
@Slf4j
@RequestMapping("/message")
public class MessagesController {


    @Resource

    public void init(){
        AiManager ai = new AiManager();
        ai.xfunListener =  XfunListener.builder()
                .apiKey("23c5e09fe931746d2691698913eb25a8")
                .apiSecret("MjU2Njk2NDQ5Y2MxZDBmZGRiNGVmNGMw")
                .appid("640f368d")
                .hostUrl("https://spark-api.xf-yun.com/v3.1/chat")
                .build();
        ai.init();
    }

    public void send(String msg){
        ai.testChat(msg)
        System.out.println(ai.testChat("你好啊！"));
        System.out.println(ai.testChat("vue组件中的ref是什么作用"));
    }


}
