package com.example.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

//@PropertySource("classpath:resources/application.yml")
@Component
public class commonData {
    public static final int pageSize = 10;
    //public static   String RootPath="src/main/resources/static/";
    public static   String RootPath="/usr/local/pic/";
}
