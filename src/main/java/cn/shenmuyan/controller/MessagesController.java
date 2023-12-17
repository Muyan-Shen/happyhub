package cn.shenmuyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.shenmuyan.Xfun.AiManager;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.Xfun.XfunListener;
import cn.shenmuyan.vo.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ApplicationContext context;


    private AiManager getOrCreateAiManager(Integer sessionId) {
        String aiManagerKey = "aiManager_" + sessionId;
        AiManager ai = (AiManager) httpSession.getAttribute(aiManagerKey);
        if (ai == null) {
            ai = new AiManager();
            XfunListener listener = XfunListener.builder()
                    .apiKey("23c5e09fe931746d2691698913eb25a8")
                    .apiSecret("MjU2Njk2NDQ5Y2MxZDBmZGRiNGVmNGMw")
                    .appid("640f368d")
                    .hostUrl("https://spark-api.xf-yun.com/v3.1/chat")
                    .build();
            ai.setXfunListener(listener);
            httpSession.setAttribute(aiManagerKey, ai);
        }
        return ai;
    }
    @GetMapping("/get")
    public SaResult getMessage() {
        Integer currentSessionId = StpUtil.getLoginIdAsInt();
        AiManager ai = getOrCreateAiManager(currentSessionId);
        List<MsgDTO> messages = ai.getMessages();
        return SaResult.ok().setData(messages);
    }

    @PostMapping("/send")
    public SaResult sendMessage(@RequestParam("msg") String msg) {
        Integer currentSessionId = StpUtil.getLoginIdAsInt();
        AiManager ai = getOrCreateAiManager(currentSessionId);

        ai.userAddMessage(msg);

        List<MsgDTO> response = ai.chat();

        return SaResult.ok().setData(response);
    }

}