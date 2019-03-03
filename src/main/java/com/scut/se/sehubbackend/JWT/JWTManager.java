package com.scut.se.sehubbackend.JWT;

import com.scut.se.sehubbackend.Config.JWTConfig;
import com.scut.se.sehubbackend.Domain.User;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTManager {

    @Autowired
    RsaJsonWebKey rsaJsonWebKey;//密钥对

    @Autowired
    JsonWebSignature jsonWebSignature;//签名

    @Autowired
    JwtConsumer jwtConsumer;//反向构造器

    @Autowired
    JWTConfig jwtConfig;//配置类

    public String encode(User user) throws JoseException {
        JwtClaims jwtClaims=new JwtClaims();//创建一个jwt
        jwtClaims.setIssuer(jwtConfig.getIssuer());//发布组织
        jwtClaims.setSubject(user.getStudentNO());//以学号为验证信息
        jwtClaims.setExpirationTimeMinutesInTheFuture(jwtConfig.getExpired());//过期时间
        jwtClaims.setIssuedAtToNow();//发布时间

        jsonWebSignature.setPayload(jwtClaims.toJson());//设置Payload
        jsonWebSignature.setKey(rsaJsonWebKey.getPrivateKey());//私钥签署
        jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);//设置Header签名算法

        return jsonWebSignature.getCompactSerialization();
    }

    public User decode(String jwt) throws InvalidJwtException, MalformedClaimException {
        User user=new User();
        user.setStudentNO(jwtConsumer.processToClaims(jwt).getSubject());
        return user;
    }
}