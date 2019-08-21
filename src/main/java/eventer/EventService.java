package eventer;

import java.util.*;

public class EventService extends Logging {

	private EventRepository eventRepository;

	private UserRepository userRepository;

	private EmailService emailService;

	public EventService(EventRepository eventRepository, UserRepository userRepository, EmailService emailService) {
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
	}

	void createOrUpdate(CreateOrUpdateEventRequest request) throws IllegalRequestException {
		logger.info("Creating or updating {}...", request);

		Optional<Event> maybeExistingEvent = request.maybeId.flatMap(eventRepository::findById);
		if (request.maybeId.isPresent() && !maybeExistingEvent.isPresent()) {
			throw new IllegalRequestException("Event ID does not exist.");
		}
		Set<User> existingGuests = new HashSet<>();
		if (maybeExistingEvent.isPresent()) {
			existingGuests.addAll(maybeExistingEvent.get().getGuests());
		}

		UUID id = request.maybeId.isPresent() ? request.maybeId.get() : UUID.randomUUID();
		Optional<User> maybeHost = userRepository.findById(request.hostId);
		if (!maybeHost.isPresent()) {
			throw new IllegalRequestException("Host does not exist.");
		}
		Set<User> guests = new HashSet<>();
		for (User guest : userRepository.findAllById(request.internalGuestIds)) {
			guests.add(guest);
		}
		Set<User> externalGuests = new HashSet<>();
		for (CreateOrUpdateEventRequest.ExternalGuest guest : request.externalGuests) {
			externalGuests.add(new User(UUID.randomUUID(), guest.name, guest.email, false));
		}
		userRepository.saveAll(externalGuests);
		guests.addAll(externalGuests);

		Event event = new Event(id, request.title, request.description, maybeHost.get(), guests, request.dateTime);

		eventRepository.save(event);

		Set<User> newGuests = new HashSet<>(guests);
		newGuests.removeAll(existingGuests);

		logger.info("Inviting {} new guests...", newGuests.size());
		for (User guest : newGuests) {
			String body = "Dear " + guest.getName() + ", you have been invited to " + event.getTitle() + "!";
			emailService.sendEmail(guest.getEmail(), "Invitation for " + event.getTitle(), body);
		}
	}

}
