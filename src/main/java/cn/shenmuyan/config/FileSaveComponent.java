package cn.shenmuyan.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @className: FileSaveComponent
 * @author: 叶宝谦
 * @date: 2023/12/13 11:50
 **/
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileSaveComponent implements InitializingBean {
    private Resource uploadPath = new PathResource("./event");
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!uploadPath.exists()){
            uploadPath.getFile().mkdirs();
        }
    }
}
