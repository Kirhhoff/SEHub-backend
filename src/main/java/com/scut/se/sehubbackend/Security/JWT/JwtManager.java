package com.scut.se.sehubbackend.Security.JWT;

import com.scut.se.sehubbackend.Config.JwtConfig;
import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Repository.user.UserAuthenticationRepository;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 提供将一个用户编码为{@code jwt}的{@link #encode(UserAuthentication)}、将一个{@code jwt}解码为用户的{@link #decode(String)}功能<br/>
 */
@Service
public class JwtManager {

    @Autowired RsaJsonWebKey rsaJsonWebKey;//密钥对
    @Autowired JsonWebSignature jsonWebSignature;//签名
    @Autowired JwtConsumer jwtConsumer;//反向构造器
    @Autowired
    JwtConfig jwtConfig;//配置类
    @Autowired UserAuthenticationRepository userRepository;//用户dao

    public String encode(UserDetails userDetails) throws JoseException {
        JwtClaims jwtClaims=new JwtClaims();//创建一个jwt
        jwtClaims.setIssuer(jwtConfig.getIssuer());//发布组织
//        jwtClaims.setSubject(userDetails.getStudentNO());//以学号为验证信息
        jwtClaims.setExpirationTimeMinutesInTheFuture(jwtConfig.getExpired());//过期时间
        jwtClaims.setIssuedAtToNow();//发布时间

        jsonWebSignature.setPayload(jwtClaims.toJson());//设置Payload
        jsonWebSignature.setKey(rsaJsonWebKey.getPrivateKey());//私钥签署
        jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);//设置Header签名算法

        return jsonWebSignature.getCompactSerialization();
    }

    /**
     * 对一个{@code jwt}解码得到用户<br/>
     * 此处是根据{@code jwt}内的学号对数据库查询得到用户
     * @param jwt 提供的jwt
     * @return 解码后得到的用户，无效的{@code jwt}返回null
     */
    public UserAuthentication decode(String jwt) throws MalformedClaimException {
        try {
            // 从 Java 8 引入的一个很有趣的特性是 Optional  类。
            // Optional 类主要解决的问题是臭名昭著的空指针异常（NullPointerException） —— 每个 Java 程序员都非常了解的异常。
            // 本质上，这是一个包含有可选值的包装类，这意味着 Optional 类既可以含有对象也可以为空。
            Optional<UserAuthentication> user=userRepository.findById(jwtConsumer.processToClaims(jwt).getSubject());
            if(user.isPresent())
                return user.get();
            return null;
        } catch (InvalidJwtException e) {
            return null;
        }
    }
}
