package eventer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
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

	@NotNull
	private boolean registered;

	protected User() {
	}

	public User(@NotNull UUID id, @NotNull String name, @NotNull String email, @NotNull boolean registered) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.registered = registered;
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

	public boolean isRegistered() {
		return registered;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email)
				&& Objects.equals(registered, user.registered);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, email, registered);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", registered='"
				+ registered + '\'' + '}';
	}

}
