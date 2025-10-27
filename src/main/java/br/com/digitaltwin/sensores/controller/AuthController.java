package br.com.digitaltwin.sensores.controller;

import br.com.digitaltwin.sensores.security.JwtService;
import br.com.digitaltwin.sensores.user.AppUser;
import br.com.digitaltwin.sensores.user.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

record LoginRequest(String email, String password) {}
record SignupRequest(String email, String password) {}
record TokenResponse(String token) {}

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(AppUserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var user = users.findByEmail(req.email()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
        String token = jwt.generate(user.getEmail(), List.of(user.getRole()));
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (users.existsByEmail(req.email())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }
        AppUser u = new AppUser();
        u.setEmail(req.email());
        u.setPassword(encoder.encode(req.password()));
        u.setRole("USER");
        users.save(u);

        String token = jwt.generate(u.getEmail(), List.of(u.getRole()));
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
