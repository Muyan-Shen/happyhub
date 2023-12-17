package cn.shenmuyan.Xfun;

import cn.dev33.satoken.stp.StpUtil;
import cn.shenmuyan.vo.MsgDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
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

    private Integer time = 5;
    private Integer sessionId;
    public XfunListener xfunListener;
    private List<MsgDTO> messageHistory = new ArrayList<>();
    private LocalDateTime lastInteraction;
    public AiManager(){
        init();
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public void setXfunListener(XfunListener xfunListener) {
        this.xfunListener = xfunListener;
    }
    public void init(){
        messageHistory.clear();
        String presupposedAnswer = "您好，我是小M，有什么可以帮到您的吗？";
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setRole("bot");
        msgDTO.setContent(presupposedAnswer);
        msgDTO.setIndex(messageHistory.size());
        msgDTO.setSendAt(LocalDateTime.now());
        messageHistory.add(msgDTO);
        lastInteraction = LocalDateTime.now();
    }

    public void updateLastInteractionTime() {
        lastInteraction = LocalDateTime.now();
    }

    public List<MsgDTO> getMessages(){
        return messageHistory;
    }

    public void userAddMessage(String question){
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setRole("user");
        msgDTO.setContent(question);
        msgDTO.setIndex(messageHistory.size());
        msgDTO.setSendAt(LocalDateTime.now());
        messageHistory.add(msgDTO);
        updateLastInteractionTime();
    }

    public List<MsgDTO> chat(){
        String random = String.valueOf(StpUtil.getLoginIdAsInt());
        try {
            XfunListener webSocket = xfunListener.sendMsg(random, messageHistory, xfunListener);
            int cnt = 300;
            while (!webSocket.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }
            webSocket.setFinished();
            String answer = webSocket.getAnswer();
            System.out.println("answer:"+answer);
            MsgDTO replyDTO = new MsgDTO();
            replyDTO.setRole("bot");
            replyDTO.setContent(answer);
            replyDTO.setIndex(messageHistory.size());
            replyDTO.setSendAt(LocalDateTime.now());
            messageHistory.add(replyDTO);
            return messageHistory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void cleanUpExpiredSessions() {
        if (lastInteraction != null && Duration.between(lastInteraction, LocalDateTime.now()).toMinutes() >= time) {

            saveMessagesToDatabase();
            resetSession();
        }
    }

    // 保存消息到数据库
    private void saveMessagesToDatabase() {
        // 使用JDBC或Spring Data JPA将 messageHistory 保存到数据库
        // ...
    }

    // 清理会话
    private void resetSession() {
        init();
        lastInteraction = null;

    }




}
