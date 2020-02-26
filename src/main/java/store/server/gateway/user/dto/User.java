package store.server.gateway.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private boolean active;

}
