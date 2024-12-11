package ar.com.kaijusoftware.lotto_mania.models;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Transient;

import io.jsonwebtoken.Jwts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LP_USER_SESSION")
public class UserSession {

    @Id
    @Column(name = "SID")
    private String sid;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Employee user;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @Column(name = "EXPIRES_AT")
    private Instant expiresAt;

    @Transient
    private transient String token;

    public String getToken(Key signature) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(Date.from(createdAt))
                .expiration(Date.from(expiresAt))
                .claim("sid", sid)
                .signWith(signature)
                .compact();
    }

    public Boolean isValid() {
        return expiresAt.isBefore(Instant.now());
    }

    public static UserSession create(final String sid, final Employee user, final Long ttl)
    {
        UserSession session = new UserSession();
        session.setSid(sid);
        session.setUser(user);
        session.setExpiresAt(Instant.now().plusSeconds(ttl));
        return session;

    }
}
