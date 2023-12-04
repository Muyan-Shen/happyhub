package cn.shenmuyan.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import cn.shenmuyan.bean.Permission;
import cn.shenmuyan.bean.Role;
import cn.shenmuyan.bean.Users;
import cn.shenmuyan.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

/**
 * @Title: SaTokenConfigure
 * @Author ShenMuyan
 * @Package cn.shenmuyan.config
 * @Date 2023/11/28 14:12
 * @description:
 */

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }

    @Bean
    public StpInterface getStpInterface(UserService userService) {
        return new StpInterface() {
            /**
             * 返回一个账号所拥有的权限码集合
             * @param o 当前的账号id（或账号实体对象）
             * @param s 当前账号类型标识
             * @return
             */
            @Override
            public List<String> getPermissionList(Object o, String s) {
                //得到当前登录用户的权限列表
                Integer id = Convert.toInt(o);
                if (id != null) {
                    Users user = userService.findById(id);
                    Set<Role> roles = user.getRoles();
                    Set<String> permissions = new HashSet<>();
                    Set<Permission> permissions1 = new HashSet<>();
                    for (Role role : roles) {
                        permissions1 = role.getPermissions();
                    }
                    for (Permission permission : permissions1) {
                        permissions.add(permission.getCode());
                    }
                    return new ArrayList<>(permissions);
                }
                return Collections.emptyList();
            }

            @Override
            public List<String> getRoleList(Object o, String s) {
                //得到当前登录用户的权限列表
                Integer id = Convert.toInt(o);
                if (id != null) {
                    Users user = userService.findById(id);
                    Set<Role> roles = user.getRoles();
                    Set<String> roleCodes = new HashSet<>();
                    for (Role role : roles) {
                        roleCodes.add(role.getCode());
                    }
                    return new ArrayList<>(roleCodes);
                }
                return Collections.emptyList();
            }
        };
    }
}
