package com.method51.lookup.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.method51.swagger.api.FromApi;

@RefreshScope
@RestController
public class TestController implements FromApi {
    @Value("${from}")
    private String from;



    /**
     * @see com.method51.swagger.api.FromApi#fromGet()
     */
    @Override
    public ResponseEntity<String> getFromConfig() {
        return ResponseEntity.ok(this.from);
    }

}