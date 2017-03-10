package com.method51.calc.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @FeignClient(name = "calc")
@FeignClient(name = "plus",url="http://plus:4000")
public interface RemoteCalcService {

    @RequestMapping(method = RequestMethod.GET, value = "/plus?a={a}&b={b}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String plus(@PathVariable("a") Integer a, @PathVariable("b") Integer b);

}
