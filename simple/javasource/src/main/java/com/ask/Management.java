package com.ask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Management {
    public static void main(String[] args) {
        SpringApplication.run(Management.class, args);
    }
}

//@EnableJpaAuditing
//@SpringBootApplication
//@ComponentScan(basePackages = {"com.ask.**"})
//// 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
//public class Management extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // 注意这里一定要指向原先用main方法执行的Application启动类
//        return builder.sources(Management.class);
//    }
//}