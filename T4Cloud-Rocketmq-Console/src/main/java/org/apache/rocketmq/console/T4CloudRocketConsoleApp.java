/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.rocketmq.console;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan
public class T4CloudRocketConsoleApp {

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(T4CloudRocketConsoleApp.class, args);
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
