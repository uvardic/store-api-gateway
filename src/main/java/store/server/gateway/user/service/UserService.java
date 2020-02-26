package store.server.gateway.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import store.server.gateway.exception.UserServiceGraphQLError;
import store.server.gateway.exception.handler.CustomResponseErrorHandler;
import store.server.gateway.user.dto.TokenRequest;
import store.server.gateway.user.dto.TokenResponse;
import store.server.gateway.user.dto.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_ENDPOINT = "http://store-user-service/store/server/user";

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public Long deleteById(Long existingId) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/delete/existingId=%d", USER_ENDPOINT, existingId),
                HttpMethod.DELETE,
                null,
                Object.class
        );

        if (isError(response))
            throw new UserServiceGraphQLError(getMessage(response));

        return existingId;
    }

    private boolean isError(ResponseEntity<?> response) {
        return CustomResponseErrorHandler.HANDLED_HTTP_ERROR_CODES.contains(response.getStatusCode());
    }

    private String getMessage(ResponseEntity<?> response) {
        LinkedHashMap<?, ?> responseBody = (LinkedHashMap<?, ?>) response.getBody();

        return (String) (responseBody != null ? responseBody.get("message") : "No message provided!");
    }

    public User save(User userRequest) {
        ResponseEntity<?> response = restTemplate.postForEntity(
                String.format("%s/save", USER_ENDPOINT), userRequest, Object.class
        );

        if (isError(response))
            throw new UserServiceGraphQLError(getMessage(response));

        return objectMapper.convertValue(response.getBody(), User.class);
    }

    public User update(Long existingId, User userRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/update/existingId=%d", USER_ENDPOINT, existingId),
                HttpMethod.PUT,
                new HttpEntity<>(userRequest),
                Object.class
        );

        if (isError(response))
            throw new UserServiceGraphQLError(getMessage(response));

        return objectMapper.convertValue(response.getBody(), User.class);
    }

    public User findById(Long id) {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/id=%d", USER_ENDPOINT, id), Object.class
        );

        if (isError(response))
            throw new UserServiceGraphQLError(getMessage(response));

        return objectMapper.convertValue(response.getBody(), User.class);
    }

    public List<User> findAll() {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/all", USER_ENDPOINT), Object.class
        );

        if (isError(response))
            throw new UserServiceGraphQLError(getMessage(response));

        return mapToUserList(response);
    }

    private List<User> mapToUserList(ResponseEntity<?> response) {
        return ((List<?>) Objects.requireNonNull(response.getBody()))
                .stream()
                .map(user -> objectMapper.convertValue(user, User.class))
                .collect(Collectors.toList());
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        ResponseEntity<?> response = restTemplate.postForEntity(
                String.format("%s/login", USER_ENDPOINT), tokenRequest, Object.class
        );

        if (isError(response))
            throw new UserServiceGraphQLError(getMessage(response));

        return objectMapper.convertValue(response.getBody(), TokenResponse.class);
    }

}
