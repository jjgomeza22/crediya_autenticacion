package co.com.crediya.model.password.gateways;

public interface PasswordEncoderPort {
    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
