package eventer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Event {

	@Id
	@NotNull
	private UUID id;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@ManyToOne
	@NotNull
	private User host;

	@ManyToMany
	@NotNull
	private Set<User> guests;

	@NotNull
	private LocalDateTime dateTime;

	protected Event() {
	}

	public Event(@NotNull UUID id, @NotNull String title, @NotNull String description, @NotNull User host,
			@NotNull Set<User> guests, @NotNull LocalDateTime dateTime) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.host = host;
		this.guests = guests;
		this.dateTime = dateTime;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public User getHost() {
		return host;
	}

	public Set<User> getGuests() {
		return guests;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Event event = (Event) o;
		return Objects.equals(id, event.id) && Objects.equals(title, event.title)
				&& Objects.equals(description, event.description) && Objects.equals(host, event.host)
				&& Objects.equals(guests, event.guests) && Objects.equals(dateTime, event.dateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, host, guests, dateTime);
	}

	@Override
	public String toString() {
		return "Event{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", host="
				+ host + ", guests=" + guests + ", dateTime=" + dateTime + '}';
	}

}
