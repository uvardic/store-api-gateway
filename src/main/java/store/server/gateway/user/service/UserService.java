package store.server.gateway.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import store.server.gateway.exception.UserServiceGraphQLError;
import store.server.gateway.exception.handler.CustomResponseErrorHandler;
import store.server.gateway.mapper.CustomObjectMapper;
import store.server.gateway.user.dto.TokenRequest;
import store.server.gateway.user.dto.TokenResponse;
import store.server.gateway.user.dto.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_ENDPOINT = "http://store-user-service/store/server/user";

    private final RestTemplate restTemplate;

    private final CustomResponseErrorHandler responseErrorHandler;

    private final CustomObjectMapper objectMapper;

    public Long deleteById(Long existingId) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/delete/existingId=%d", USER_ENDPOINT, existingId),
                HttpMethod.DELETE,
                null,
                Object.class
        );

        responseErrorHandler.handleError(response, UserServiceGraphQLError.class);

        return existingId;
    }

    public User save(User userRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/save", USER_ENDPOINT),
                HttpMethod.POST,
                new HttpEntity<>(userRequest),
                Object.class
        );

        responseErrorHandler.handleError(response, UserServiceGraphQLError.class);

        return objectMapper.mapResponse(response, User.class);
    }

    public User update(Long existingId, User userRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/update/existingId=%d", USER_ENDPOINT, existingId),
                HttpMethod.PUT,
                new HttpEntity<>(userRequest),
                Object.class
        );

        responseErrorHandler.handleError(response, UserServiceGraphQLError.class);

        return objectMapper.mapResponse(response, User.class);
    }

    public User findById(Long id) {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/id=%d", USER_ENDPOINT, id), Object.class
        );

        responseErrorHandler.handleError(response, UserServiceGraphQLError.class);

        return objectMapper.mapResponse(response, User.class);
    }

    public List<User> findAll() {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/all", USER_ENDPOINT), Object.class
        );

        responseErrorHandler.handleError(response, UserServiceGraphQLError.class);

        return objectMapper.mapResponseToList(response, User.class);
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        ResponseEntity<?> response = restTemplate.postForEntity(
                String.format("%s/login", USER_ENDPOINT), tokenRequest, Object.class
        );

        responseErrorHandler.handleError(response, UserServiceGraphQLError.class);

        return objectMapper.mapResponse(response, TokenResponse.class);
    }

}
