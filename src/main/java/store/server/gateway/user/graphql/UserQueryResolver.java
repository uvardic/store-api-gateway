package store.server.gateway.user.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import store.server.gateway.user.dto.User;
import store.server.gateway.user.service.UserService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserService userService;

    public User findUserById(Long id) {
        return userService.findById(id);
    }

    public List<User> findAllUsers() {
        return userService.findAll();
    }

}
