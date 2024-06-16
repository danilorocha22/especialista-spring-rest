package com.dan.esr.core.email;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("danfood.email")
public class EmailProperties {

    @NotNull
    private String remetente;
    private Sandbox sandbox = new Sandbox();
    private ImplEmail impl = ImplEmail.FAKE;

    public String destinatarioSandbox() {
        return getSandbox().getDestinatario();
    }

    public enum ImplEmail {
        SMTP_ASW, FAKE, SANDBOX
    }

    @Getter
    @Setter
    public static class Sandbox {
        private String destinatario;
    }
}