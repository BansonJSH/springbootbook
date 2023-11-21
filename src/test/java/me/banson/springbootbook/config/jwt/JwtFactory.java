package me.banson.springbootbook.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Getter
public class JwtFactory {

    private String subject = "test@eamil.com";
    private Date issuerAt = new Date();
    private Date expriation = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String , Object> claims;

    @Builder
    public JwtFactory(String subject, Date issuerAt, Date expriation, Map<String ,Object> claims) {
        this.subject = subject != null ? subject: this.subject;
        this.issuerAt = issuerAt != null ? issuerAt: this.issuerAt;
        this.expriation = expriation != null ? expriation: this.expriation;
        this.claims = claims != null ? claims: this.claims;
    }

    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuerAt)
                .setExpiration(expriation)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
