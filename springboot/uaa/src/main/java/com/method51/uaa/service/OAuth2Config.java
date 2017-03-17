package com.method51.uaa.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private static final String     DUMMY_RESOURCE_ID = "dummy";

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private Environment             env;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager   authenticationManager;

    @Value("${default.redirect:http://localhost:8080/tonr2/sparklr/redirect}")
    private String                  defaultRedirectUri;

    @Value("${jwt.file:demo_jwt.jks}")
    private String                  jwtFile;
    @Value("${jwt.password:111222}")
    private String                  jwtPassword;



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }



    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }



    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(jwtFile),
                jwtPassword.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt-key"));
        return converter;
    }



    /*
     * curl a-service:111222@localhost:5000/oauth/token -d
     * grant_type=client_credentials -d client_id=a-service
     * 
     * 
     * curl -H "Authorization:Basic YnJvd3Nlcjo=" localhost:5000/oauth/token -d
     * grant_type=password -d scope=ui -d username=demo -d password=111222
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // TODO persist clients details

        // @formatter:off
        clients.inMemory()
                .withClient("browser")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .scopes("ui")
        .and()
                .withClient("app-client")
                .secret(env.getProperty("APP_CLIENT_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
        .and()
                .withClient("admin-service")
                .secret("admin")
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
          .and() 
                  .withClient("my-client-with-registered-redirect")
                  .resourceIds(DUMMY_RESOURCE_ID)
                  .authorizedGrantTypes("authorization_code", "client_credentials")
                  .authorities("ROLE_CLIENT")
                  .scopes("read", "trust")
                  .redirectUris("http://anywhere?key=value");
        // @formatter:on
    }



    // @formatter:off

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("tonr")
//                .resourceIds(DUMMY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//                .and()
//                .withClient("tonr-with-redirect")
//                .resourceIds(DUMMY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//                .redirectUris(defaultRedirectUri)
//                .and()
//                .withClient("my-client-with-registered-redirect")
//                .resourceIds(DUMMY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "client_credentials")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "trust")
//                .redirectUris("http://anywhere?key=value")
//                .and()
//                .withClient("my-trusted-client")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .accessTokenValiditySeconds(60)
//                .and()
//                .withClient("my-trusted-client-with-secret")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit",
//                        "client_credentials").authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust").secret("somesecret").and().withClient("my-less-trusted-client")
//                .authorizedGrantTypes("authorization_code", "implicit").authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust").and().withClient("my-less-trusted-autoapprove-client")
//                .authorizedGrantTypes("implicit").authorities("ROLE_CLIENT").scopes("read", "write", "trust")
//                .autoApprove(true);
//    }

    // @formatter:on

    // @Override
    // public void configure(AuthorizationServerSecurityConfigurer oauthServer)
    // throws Exception {
    // oauthServer.realm("sparklr2/client");
    // }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
}
