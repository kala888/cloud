package com.method51.serviceb.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkController {

    private final static String UNKNOWN = "unknown";



    /**
     *
     * Lookup IP Address
     *
     * @param request
     * @return
     */
    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/iplookup", method = RequestMethod.GET)
    public Map<String, String> ipLookup(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        String ip = calculateIPAddress(request);
        map.put("ip", ip);
        return map;
    }



    /**
     *
     * get IP from header
     *
     * @param request
     * @return
     */
    private String calculateIPAddress(HttpServletRequest request) {
        String[] ipHeads = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR" };
        for (String header : ipHeads) {
            String ip = extractValidateIP(request.getHeader(header));
            if (ip != null) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }



    /**
     *
     *
     *
     * @param ip
     * @return
     */
    protected String extractValidateIP(String ip) {
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            return null;
        }
        if (ip.contains(",")) {
            for (String subIP : ip.split(",")) {
                String finalIP = extractValidateIP(subIP);
                if (finalIP != null) {
                    return finalIP;
                }
            }
        }
        return ip;
    }

}
