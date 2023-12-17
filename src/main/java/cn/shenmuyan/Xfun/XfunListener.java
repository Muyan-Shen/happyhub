package cn.shenmuyan.Xfun;

import cn.shenmuyan.vo.MsgDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.resources.UtilMessages;
import lombok.Builder;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Title: XfunListener
 * @Author ShenMuyan
 * @Package cn.shenmuyan.Xfun
 * @Date 2023/12/17 7:51
 */
@Builder
public class XfunListener extends WebSocketListener {
    private String hostUrl;
    private String appid;
    private String apiSecret;
    private String apiKey;

    @Builder.Default
    public boolean is_finished = false;

    @Builder.Default
    private String answer = "";

    public String getAnswer() {
        return answer;
    }
    public boolean isFinished() {
        return is_finished;
    }
    public void setFinished() {
        this.is_finished=false;
    }

    public List<MsgDTO> getHistoryList() {
        return historyList;
    }

    @Builder.Default
    public List<MsgDTO> historyList = new ArrayList<>();
    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
    }

    public  void deleteHistory(){
        historyList = new ArrayList<>();
    }
    public void init_chat(){
        is_finished = false;
    }
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);

         System.out.println("接收到消息：" + text);

        XfunReceieveRequest xfunReceieveRequest = JSONObject.parseObject(text, XfunReceieveRequest.class);

        if(xfunReceieveRequest.getHeader().getCode() == 0) {
            XfunReceieveRequest.PayloadDTO payload = xfunReceieveRequest.getPayload();
            XfunReceieveRequest.PayloadDTO.ChoicesDTO choices = payload.getChoices();
            List<MsgDTO> msgs = choices.getText();
            for(int i = 0; i < msgs.size(); i++){
                MsgDTO msg =msgs.get(i);
                msg.setIndex(historyList.size()+i);
                historyList.add(msg);
            }

            if(xfunReceieveRequest.getHeader().getStatus() == 2){
                XfunReceieveRequest.PayloadDTO.UsageDTO.TextDTO text1 = payload.getUsage().getText();
                System.out.println("PromptTokecn：" + text1.getPromptTokens());
                System.out.println("QuestionToken：" + text1.getQuestionTokens());
                System.out.println("CompletionToken：" + text1.getCompletionTokens());
                System.out.println("TotalToken"+text1.getTotalTokens());

                StringBuilder message = new StringBuilder();
                for(MsgDTO msg: historyList){
                    message.append(msg.getContent());
                }
                deleteHistory();
                answer = message.toString();

                is_finished = true;

            }
        }
    }

    // 获得鉴权地址
    public static String getAuthUrl(String hostUrl, String apiSecret, String apiKey) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }

    public XfunSendRequest getSendRequest(String uid, List<MsgDTO> msgs) {
        XfunSendRequest xfunSendRequest = new XfunSendRequest();
        XfunSendRequest.Header header = new XfunSendRequest.Header();
        header.setAppId(appid);
        header.setUid(uid);
        xfunSendRequest.setHeader(header);
        XfunSendRequest.ParameterDTO parameterDTO = new XfunSendRequest.ParameterDTO();
        XfunSendRequest.ParameterDTO.ChatDTO chatDTO = new XfunSendRequest.ParameterDTO.ChatDTO();
        parameterDTO.setChat(chatDTO);
        xfunSendRequest.setParameter(parameterDTO);
        XfunSendRequest.PayloadDTO payloadDTO = new XfunSendRequest.PayloadDTO();
        XfunSendRequest.PayloadDTO.MessageDTO messageDTO = new XfunSendRequest.PayloadDTO.MessageDTO();
        messageDTO.setText(msgs);
        payloadDTO.setMessage(messageDTO);
        xfunSendRequest.setPayload(payloadDTO);
        return xfunSendRequest;
    }


    public XfunListener sendMsg(String uid, List<MsgDTO> msgs, XfunListener webSocketListener) throws Exception {

        String url = getAuthUrl(hostUrl,apiSecret,apiKey);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();

        WebSocket webSocket = okHttpClient.newWebSocket(request, webSocketListener);

        XfunSendRequest xfunSendRequest = this.getSendRequest(uid, msgs);
        System.out.println("params:" + JSONObject.toJSONString(xfunSendRequest));


        webSocket.send(JSONObject.toJSONString(xfunSendRequest));
        return webSocketListener;
    }
}


