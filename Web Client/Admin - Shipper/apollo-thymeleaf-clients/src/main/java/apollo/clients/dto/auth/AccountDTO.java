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

    public AccountDTO(String email, String password, String shipperName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.shipperName = shipperName;
        this.phoneNumber = phoneNumber;
    }

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private String shipperName;
}
