package com.scut.se.sehubbackend.Config;

import lombok.Data;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
@Component
public class JwtConfig {

    Integer rsaKeySize;
    Integer expired;
    String issuer;

    @Bean
    RsaJsonWebKey rsaJsonWebKey(JwtConfig jwtConfig) throws JoseException {
        return RsaJwkGenerator.generateJwk(jwtConfig.getRsaKeySize());
    }

    @Bean
    JsonWebSignature jsonWebSignature(){
        return new JsonWebSignature();
    }

    @Bean
    JwtConsumer jwtConsumer(RsaJsonWebKey rsaJsonWebKey, JwtConfig jwtConfig){
        return new JwtConsumerBuilder()
                .setRequireSubject()//要求有被发行对象
                .setExpectedIssuer(jwtConfig.getIssuer())//检查发行者
                .setRequireExpirationTime()//要求设置了过期时间
                .setVerificationKey(rsaJsonWebKey.getKey())//公钥验证签名
                .setJwsAlgorithmConstraints(//加密算法限制
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                                AlgorithmIdentifiers.RSA_USING_SHA256))
                .build();
    }
}
