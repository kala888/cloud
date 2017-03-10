package com.method51.calc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.method51.calc.service.RemoteCalcService;

@RestController
public class CalcController {

    @Autowired
    private RemoteCalcService remoteCalcService;

    class CalcResult{
        String result;
        public CalcResult(String pResult){
            result=pResult;
        }
        public String getResult(){
            return result;
        }
         public void setResult(String pResult){
            result=pResult;
        }
    }

    @RequestMapping(value = "/calc/plus", method = RequestMethod.GET)
    public CalcResult warpedPlus(@RequestParam Integer a, @RequestParam Integer b) {
        return new CalcResult(remoteCalcService.plus(a, b));
    }

    @RequestMapping(value = "/calc/multiply", method = RequestMethod.GET)
    public CalcResult multiply(@RequestParam Integer a, @RequestParam Integer b) {
        return  new CalcResult(String.valueOf(a*b));
    }

}