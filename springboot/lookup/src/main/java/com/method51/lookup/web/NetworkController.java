package com.method51.lookup.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.method51.swagger.api.IplookupApi;
import com.method51.swagger.model.IPAddress;

@RestController
public class NetworkController implements IplookupApi {

    private final static String UNKNOWN = "unknown";
    
    @Autowired(required=true)
    private HttpServletRequest request;


//    @Context
//     private HttpServletRequest request;
    // /**
    // *
    // * Lookup IP Address
    // *
    // * @param request
    // * @return
    // */
    // @CrossOrigin(maxAge = 3600)
    // @RequestMapping(value = "/iplookup", method = RequestMethod.GET)
    // public Map<String, String> ipLookup(HttpServletRequest request) {
    // Map<String, String> map = new HashMap<String, String>();
    // String ip = calculateIPAddress(request);
    // map.put("ip", ip);
    // return map;
    // }
    //

    /**
     * @see com.method51.swagger.api.IplookupApi#iplookupGet()
     */
    @Override
    public ResponseEntity<IPAddress> iplookup() {
        String ip = calculateIPAddress(request);
        IPAddress result = new IPAddress();
        result.setIp(ip);
        return ResponseEntity.ok(result);
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
