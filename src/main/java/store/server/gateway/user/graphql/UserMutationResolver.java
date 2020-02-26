package store.server.gateway.user.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import store.server.gateway.user.dto.TokenRequest;
import store.server.gateway.user.dto.TokenResponse;
import store.server.gateway.user.dto.User;
import store.server.gateway.user.service.UserService;

@Component
@RequiredArgsConstructor
public class UserMutationResolver implements GraphQLMutationResolver {

    private final UserService userService;

    public TokenResponse login(TokenRequest tokenRequest) {
        return userService.login(tokenRequest);
    }

    public User saveUser(User userRequest) {
        return userService.save(userRequest);
    }

    public User updateUser(Long existingId, User userRequest) {
        return userService.update(existingId, userRequest);
    }

    public Long deleteUserById(Long existingId) {
        return userService.deleteById(existingId);
    }

}
