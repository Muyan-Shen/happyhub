package cn.shenmuyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
