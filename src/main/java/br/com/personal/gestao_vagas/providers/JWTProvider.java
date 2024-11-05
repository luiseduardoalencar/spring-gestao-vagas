package br.com.personal.gestao_vagas.providers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JWTProvider {


    @Value("${security.token.secret}")
    private String secretKey;

    public String validateToken(String token){
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

    try{
        var subject = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
        return subject;
    }
    catch(JWTVerificationException ex){
        ex.printStackTrace();
        return "";
    }
    }
}
