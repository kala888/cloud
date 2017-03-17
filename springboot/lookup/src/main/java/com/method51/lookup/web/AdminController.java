package com.method51.lookup.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peter Schneider-Manzell
 */
@RestController
@RequestMapping("/admin")
/**
protected  resource, resource owner, with role 
**/
public class AdminController {

    @RequestMapping("/supersecure")
    public String superSecureMessage(){
        return "42";
    }
}
