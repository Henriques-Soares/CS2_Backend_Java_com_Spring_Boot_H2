package br.com.digitaltwin.sensores.config;

import br.com.digitaltwin.sensores.user.AppUser;
import br.com.digitaltwin.sensores.user.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedConfig {

    @Bean
    public CommandLineRunner seedAdminRunner(AppUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            String email = "admin@dtwin.io";
            if (!repo.existsByEmail(email)) {
                AppUser u = new AppUser();
                u.setEmail(email);
                u.setPassword(encoder.encode("admin123"));
                u.setRole("ADMIN");
                repo.save(u);
                System.out.println("[seed] admin@dtwin.io criado (senha: admin123)");
            }
        };
    }
}
