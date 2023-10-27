package tech.bonda.sufest2023.services;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    @SuppressWarnings("FieldCanBeLocal")
    private final JwtDecoder jwtDecoder;

    private final Environment env;


    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, Environment env) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.env = env;
    }
    public String generateJwt(Authentication auth) {

        Instant now = Instant.now();

        String scope = auth.
                getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.joining(" "));

        //noinspection DataFlowIssue
        JwtClaimsSet claims = JwtClaimsSet.
                builder().
                issuer(env.getProperty("spring.datasource.username")).
                issuedAt(now).
                subject(auth.getName()).
                claim("roles", scope).
                build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


}
