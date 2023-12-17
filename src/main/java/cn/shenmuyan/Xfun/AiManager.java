package cn.shenmuyan.Xfun;

import cn.shenmuyan.vo.MsgDTO;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan(basePackages = {"cn.shenmuyan.Xfun", "其他需要扫描的包"})
public class AiManager {


    @Resource
    private XfunListener xfunListener;  // 实现发送接受消息的websockect

    public String testChat(String question){

        //8位随机数
        String random = String.valueOf((int)((Math.random()*9+1)*10000000));
        List<MsgDTO> msgs = new ArrayList<>();
        MsgDTO msgDTO = new MsgDTO( );
        msgDTO.setRole("user");
        msgDTO.setContent(question);
        msgDTO.setIndex(0);
        msgs.add(msgDTO);

        xfunListener.init_chat();
        try {
            // 获取接受消息的webSoeckt
            XfunListener webSocket = xfunListener.sendMsg(random, msgs, xfunListener);
            //等待weSocked返回消息 , 这是一个笨笨的处理方法。
            int cnt = 30;
            //最长等待30S
            while (!webSocket.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }

            String answer = webSocket.getAnswer();
            //返回答案
            return answer;
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
    }


}
