package apollo.clients.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDTO {
    public AccountDTO() {
    }

    public AccountDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private String name;

    private String email;

    private String password;
}
