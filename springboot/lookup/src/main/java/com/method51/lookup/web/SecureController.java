package com.method51.lookup.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/secure")
/**
protected  resource, resource owner
**/
public class SecureController {

    @RequestMapping(method = RequestMethod.GET,value = "/me")
    public Map<String,Object> me(Authentication authentication) {
        Map<String,Object> result = new HashMap<>();
        result.put("authentication",authentication);
        return result;
    }
}
