package com.myworkflow.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/6/2017
 * email: code.crosser@gmail.com
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.myworkflow.server")
public class WebConfig extends WebMvcConfigurerAdapter{
}
