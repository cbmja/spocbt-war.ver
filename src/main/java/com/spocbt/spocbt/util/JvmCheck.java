package com.spocbt.spocbt.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
@Component
@RequiredArgsConstructor
public class JvmCheck {

    private static final Logger logger = LoggerFactory.getLogger(JvmCheck.class);


    public void check(ApplicationContext applicationContext , String uri){

        ////////////////////////////////////////// jvm 메모리 체크용 코드 ////////////////////////////////////////////////////
        logger.error("---------------------------------------- jvm check ----------------------------------------");
        if (!uri.endsWith(".js") && !uri.endsWith(".css")) {
            logger.info("Request URI: {}", uri);
        }
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
            // Metaspace 영역인지 확인
            if ("Metaspace".equals(memoryPoolMXBean.getName())) {
                MemoryUsage usage = memoryPoolMXBean.getUsage();
                long used = usage.getUsed();
                long max = usage.getMax();
                long init = usage.getInit();

                logger.error("Used: " + used / 1024 / 1024 + " MB");
                logger.error("Max: " + (max != -1 ? max / 1024 / 1024 + " MB" : "Unlimited"));
                logger.error("Init: " + init / 1024 / 1024 + " MB");
            }
        }
        logger.error("---------------------------------------- jvm check ----------------------------------------");
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames); // 알파벳 순 정렬
        int i = 0;
        for (String beanName : beanNames) {
            i++;
            //logger.error("Bean"+i+": " + beanName);
        }
        logger.error("---------------------------------------- bean cnt : "+i+" ----------------------------------------");

        ////////////////////////////////////////// jvm 메모리 체크용 코드 ////////////////////////////////////////////////////


    }




}
