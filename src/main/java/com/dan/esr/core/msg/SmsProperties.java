package com.dan.esr.core.msg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("danfood.sms")
public class SmsProperties {

    private ImplSms impl = ImplSms.CLARO;

    public enum ImplSms {
        CLARO, VIVO
    }
}