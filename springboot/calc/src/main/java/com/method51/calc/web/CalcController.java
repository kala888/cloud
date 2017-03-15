package com.method51.calc.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.method51.calc.service.RemoteCalcService;
import com.method51.swagger.api.CalcApi;
import com.method51.swagger.model.CalcResult;

@RestController
public class CalcController implements CalcApi {

    @Autowired
    private RemoteCalcService remoteCalcService;



    /**
     * @see com.method51.swagger.api.CalcApi#calcMultiplyGet(java.lang.Double,
     *      java.lang.Double)
     */
    @Override
    public ResponseEntity<CalcResult> multiply(BigDecimal pA, BigDecimal pB) {
        CalcResult result = new CalcResult();
        result.setResult(pA.multiply(pB).toString());
        return ResponseEntity.ok(result);
    }



    /**
     * @see com.method51.swagger.api.CalcApi#calcPlusGet(java.lang.Double,
     *      java.lang.Double)
     */
    @Override
    public ResponseEntity<CalcResult> plus(BigDecimal pA, BigDecimal pB) {

        CalcResult result = new CalcResult();
        result.setResult(remoteCalcService.plus(pA.intValue(), pB.intValue()));
        return ResponseEntity.ok(result);

    }
}