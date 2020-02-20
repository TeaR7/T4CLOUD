package com.xxl.job.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class T4CloudJobAdminApplication {

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(T4CloudJobAdminApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Application T4Cloud-Rocketmq-Console is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + (StringUtils.isEmpty(path) ? "" : path) + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + (StringUtils.isEmpty(path) ? "" : path) + "/\n\t" +
                "Username: \tt4cloud" + "\n\t" +
                "Password: \t123456" + "\n" +
                "----------------------------------------------------------");

    }

}