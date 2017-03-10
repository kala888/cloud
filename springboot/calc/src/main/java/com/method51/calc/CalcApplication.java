package com.method51.calc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class CalcApplication {

    public static void main(String[] args) {
         SpringApplication.run(new Object[] {CalcApplication.class }, args);
    }

}
