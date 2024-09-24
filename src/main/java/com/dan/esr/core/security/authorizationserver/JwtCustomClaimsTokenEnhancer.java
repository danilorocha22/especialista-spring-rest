package com.dan.esr.core.security.authorizationserver;


import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/*
 * Classe responsável por incluir informações adicionais (id e nome completo do usuário) ao token JWT
 * */
public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication.getPrincipal() instanceof AuthUser authUser) {
            var info = new HashMap<String, Object>();
            info.put("nome_completo", authUser.getFullName());
            info.put("id_usuario", authUser.getUserId());

            var oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            oAuth2AccessToken.setAdditionalInformation(info);
        }
        return accessToken;
    }
}