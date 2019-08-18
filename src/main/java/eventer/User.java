package eventer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    protected User() {}

    public User(@NotNull UUID id) {
        this.id = id;
    }

    public User(@NotNull UUID id, @NotNull String name, @NotNull String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
