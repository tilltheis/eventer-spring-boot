package eventer;

import org.springframework.beans.factory.annotation.Autowired;

public class EventService {

	private EventRepository eventRepository;

	public EventService(@Autowired EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	void create(Event event) {
		eventRepository.save(event);
		// send email
	}

}
