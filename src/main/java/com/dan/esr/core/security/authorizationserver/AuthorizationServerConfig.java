package com.dan.esr.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

import static java.util.Arrays.asList;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /*@Autowired
    private PasswordEncoder passwordEncoder;
*/
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    private DataSource dataSource;

    /*@Autowired
    private RedisConnectionFactory redisConnectionFactory;*/

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //clientes na base de dados
                .jdbc(dataSource);

                /*
                //clientes em mémoria
                .inMemory()
                //fluxo de autenticacao com password credentials grant type
                .withClient("danfood-web")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("WRITE", "READ")
                .accessTokenValiditySeconds(60 * 60 * 6) // 6 horas (padrão é 12 horas) tempo de expiração do access token
                .refreshTokenValiditySeconds(60 * 60 * 24) // 24 horas (padrão é 30 dias) tempo de expiração do refresh token

                //fluxo de autenticacao com client credentials grant type
                .and()
                .withClient("faturamento")
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials") // refresh token não funciona com client_credentials
                .scopes("READ") // scope apenas de leitura

                //fluxo de autenticacao com authorization_code
                .and()
                .withClient("danfood-analytics")
                .secret(passwordEncoder.encode("")) // secret vazio para não haver necessidade do Client informar o secret na requisição
                .authorizedGrantTypes("authorization_code")
                .scopes("READ", "WRITE")
                .redirectUris("http://www.danfoodanalytics.local:8082")

                //fluxo de autenticacao implicit grant type
                .and()
                .withClient("webadmin")
                .authorizedGrantTypes("implicit")// não funciona com refresh token
                .scopes("WRITE", "READ")
                .redirectUris("http://aplicacao-cliente")

                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("checktoken123"));
                */
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //security.checkTokenAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false) // definindo para não reutilizar o refresh token
                //.tokenStore(redisTokenStore()) // definindo o armazenamento de tokens no Redis
                .accessTokenConverter(jwtAccessTokenConverter()) //definindo o token de acesso com o JWT
                .tokenEnhancer(enhancerChain) //definindo informações complementares ao token JWT
                .approvalStore(getApprovalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
    }

    //Configura um handler http para aprovação de leitura e/ou escrita
    private ApprovalStore getApprovalStore(TokenStore tokenStore) {
        var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);
        return approvalStore;
    }

    /*
     * Configurando a geração de tokens transparentes com o JWT
     * */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtTokenConverter = new JwtAccessTokenConverter();
        //jwtTokenConverter.setSigningKey("ds41f60as587f1afs1d3.SF748A13-1GD1A31GKa3s184q9");//usando o algoritmo HS256 simétrico (assinatura simétrica)
        var keyStorePass = jwtKeyStoreProperties.getPassword();
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
        var keyStoreFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());
        var keyPair = keyStoreFactory.getKeyPair(keyPairAlias);
        jwtTokenConverter.setKeyPair(keyPair);
        return jwtTokenConverter;
    }

    /*private TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }*/

    // configurando o tokenGranter
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()
        );

        var granters = asList(pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
        return new CompositeTokenGranter(granters);
    }
}