package store.server.gateway.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import store.server.gateway.user.dto.TokenRequest;
import store.server.gateway.user.dto.TokenResponse;
import store.server.gateway.user.dto.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public TokenResponse login(TokenRequest tokenRequest) {
        return null;
    }

    public User save(User userRequest) {
        return null;
    }

    public User update(Long existingId, User userRequest) {
        return null;
    }

    public Long deleteById(Long existingId) {
        return null;
    }
}
