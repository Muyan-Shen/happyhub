package cn.shenmuyan;

import cn.shenmuyan.config.XfunConfig;
import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Title: App
 * @Author ShenMuyan
 * @Package cn.shenmuyan
 * @Date 2023/12/3 14:44
 * @description:
 */

@MapperScan("cn.shenmuyan.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
