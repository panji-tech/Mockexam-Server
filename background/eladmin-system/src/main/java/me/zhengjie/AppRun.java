/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie;

import com.alibaba.druid.support.json.JSONUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 开启审计功能 -> @EnableJpaAuditing
 *
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@RestController
@Api(hidden = true)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@MapperScan("me.zhengjie.modules.mockexam.mapper")
@SpringBootApplication
@Slf4j
public class AppRun {

    public static void main(String[] args) throws UnknownHostException {
        log.info("服务开始启动~");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppRun.class, args);
        ConfigurableEnvironment env = applicationContext.getEnvironment();

        log.info("\n---------------- 关注ITSource每日分享,每天分享一个 IT 资源------------------------------------------\n\t" +
                        "Application: '{}' is running! Access URLs:\n\t" +
                        "后端地址: \t\thttp://127.0.0.1:{}\n\t" +
                        "API Doc: \thttp://127.0.0.1:{}/swagger-ui.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                //InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                //InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
        log.info("-------服务启动完成:{}-------", InetAddress.getLocalHost().getHostAddress());
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
