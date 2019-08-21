package eventer;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class CreateOrUpdateEventRequest {

	public static class ExternalGuest {

		public String name;

		public String email;

		public ExternalGuest(String name, String email) {
			this.name = name;
			this.email = email;
		}

	}

	public Optional<UUID> maybeId;

	public String title;

	public String description;

	public UUID hostId;

	public Set<UUID> internalGuestIds;

	public Set<ExternalGuest> externalGuests;

	public LocalDateTime dateTime;

	public CreateOrUpdateEventRequest(Optional<UUID> maybeId, String title, String description, UUID hostId,
			Set<UUID> internalGuestIds, Set<ExternalGuest> externalGuests, LocalDateTime dateTime) {
		this.maybeId = maybeId;
		this.title = title;
		this.description = description;
		this.hostId = hostId;
		this.internalGuestIds = internalGuestIds;
		this.externalGuests = externalGuests;
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "CreateOrUpdateEventRequest{" + "maybeId=" + maybeId + ", title='" + title + '\'' + ", description='"
				+ description + '\'' + ", hostId=" + hostId + ", internalGuestIds=" + internalGuestIds
				+ ", externalGuests=" + externalGuests + ", dateTime=" + dateTime + '}';
	}

}
