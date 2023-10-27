package tech.bonda.sufest2023.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Companies", uniqueConstraints = {
        @UniqueConstraint(name = "uc_business_email", columnNames = {"email", "company_name"})
})
public class Company implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    int id;

    @Column(name = "username", nullable = false)
    String username;

    @Column(name = "company_name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "date_of_registration")
    String date_of_registration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_company_junction",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
