package br.com.digitaltwin.sensores.security;

import br.com.digitaltwin.sensores.user.AppUser;
import br.com.digitaltwin.sensores.user.AppUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final AppUserRepository users;

    public DbUserDetailsService(AppUserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = users.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Converte o papel salvo ("ADMIN"/"USER") em GrantedAuthority
        var authorities = List.of(new SimpleGrantedAuthority(u.getRole()));
        return new User(u.getEmail(), u.getPassword(), authorities);
    }
}
