package com.platform.payment.service;

import com.alipay.api.internal.util.AlipaySignature;
import com.platform.config.AlipayConfig;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AlipayCallbackService {
    
    private final AlipayConfig alipayConfig;
    
    public AlipayCallbackService(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }
    
    public boolean verifyNotification(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            
            boolean flag = AlipaySignature.rsaCheckV1(
                params, 
                alipayConfig.getAlipayPublicKey(), 
                alipayConfig.getCharset(), 
                "RSA2"
            );
            
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verifyCertNotification(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            
            boolean flag = AlipaySignature.rsaCertCheckV1(
                params,
                alipayConfig.getAlipayPublicCertPath(),
                alipayConfig.getCharset(),
                "RSA2"
            );
            
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}