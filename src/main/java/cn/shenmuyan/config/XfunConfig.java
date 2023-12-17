package cn.shenmuyan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: XfunConfig
 * @Author ShenMuyan
 * @Package cn.shenmuyan.config
 * @Date 2023/12/17 9:12
 */

@Configuration
@Data
@ConfigurationProperties(prefix = "xfun.open")
public class XfunConfig {
    private String appid;
    private String apiSecret;
    private String hostUrl;
    private String apiKey;
}