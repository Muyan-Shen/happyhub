package cn.shenmuyan.Xfun;

import cn.shenmuyan.vo.MsgDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: AiManager
 * @Author ShenMuyan
 * @Package cn.shenmuyan.Xfun
 * @Date 2023/12/17 8:03
 */

@Service
public class AiManager {


    public XfunListener xfunListener; // 实现发送接受消息的websockect
    private List<MsgDTO> messageHistory = new ArrayList<>();

    public void init(){
        messageHistory.clear();
        String presupposedAnswer = "您好，我是小M，有什么可以帮到您的吗？";
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setRole("bot");
        msgDTO.setContent(presupposedAnswer);
        msgDTO.setIndex(messageHistory.size()); // 设置这是第几条消息
        messageHistory.add(msgDTO);
    }

    public List<MsgDTO> getMessages(){
        return messageHistory;
    }

    public void userAddMessage(String question){
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setRole("user");
        msgDTO.setContent(question);
        msgDTO.setIndex(messageHistory.size()); // 设置这是第几条消息
        messageHistory.add(msgDTO);
    }

    public List<MsgDTO> Chat(){
        //8位随机数
        String random = String.valueOf((int)((Math.random()*9+1)*10000000));
        //添加到消息历史


        xfunListener.init_chat();
        try {
            // 获取接受消息的webSoeckt
            XfunListener webSocket = xfunListener.sendMsg(random, messageHistory, xfunListener);
            //等待weSocked返回消息 , 这是一个笨笨的处理方法。
            int cnt = 300;
            //最长等待30S
            while (!webSocket.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }

            String answer = webSocket.getAnswer();
            //把机器人回复也添加到历史记录中
            MsgDTO replyDTO = new MsgDTO();
            replyDTO.setRole("bot");
            replyDTO.setContent(answer);
            replyDTO.setIndex(messageHistory.size());
            messageHistory.add(replyDTO);
            //返回答案
            return messageHistory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        AiManager ai = new AiManager();
        ai.xfunListener =  XfunListener.builder()
                .apiKey("23c5e09fe931746d2691698913eb25a8")
                .apiSecret("MjU2Njk2NDQ5Y2MxZDBmZGRiNGVmNGMw")
                .appid("640f368d")
                .hostUrl("https://spark-api.xf-yun.com/v3.1/chat")
                .build();

        System.out.println(ai.testChat("你好啊！"));
        System.out.println(ai.testChat("vue组件中的ref是什么作用"));
    }


}
