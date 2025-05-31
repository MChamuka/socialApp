import com.social.socialapp.model.LoginRequest;
import com.social.socialapp.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

// Add:
@Autowired
private JwtUtil jwtUtil;

@Autowired
private PasswordEncoder passwordEncoder;

@PostMapping("/login")
public String login(@RequestBody LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        return jwtUtil.generateToken(user.getEmail());
    } else {
        return "Invalid credentials";
    }
}
