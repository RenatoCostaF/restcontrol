package entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public class Person implements UserDetails {
        private Long id;
        private String name;
        private String email;
        private String login;
        private String password;
        private Date lastModifiedDate;
        private String address;
        private UserRole role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if (this.role == UserRole.DONO_RESTAURANTE) {
                return List.of(new SimpleGrantedAuthority("ROLE_DONO_RESTAURANTE"), new SimpleGrantedAuthority("ROLE_CLIENTE"));
            } else {
                return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
            }
        }

        @Override
        public String getUsername() {
            return login;
        }
    }
}
