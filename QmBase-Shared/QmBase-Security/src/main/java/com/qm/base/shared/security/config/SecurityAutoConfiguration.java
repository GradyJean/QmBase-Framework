package com.qm.base.shared.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.qm.base.shared.security")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

}
