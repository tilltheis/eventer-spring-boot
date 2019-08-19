package eventer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class EventController {

	private User tillUser = new User(UUID.fromString("32ec5ce0-5173-4a52-8fc8-62356bd26cc5"), "Till",
			"example@example.org");

	private User janniUser = new User(UUID.fromString("fae95a3e-7e9c-4bcc-8903-87305b055bfe"), "Janni",
			"example@example.org");

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String index(Model model) {
		seedDatabaseIfNeeded();

		model.addAttribute("events", eventRepository.findAllByOrderByDateTimeAsc());
		return "listEvents";
	}

	@GetMapping("/events/{id}")
	public String showCreateOrEdit(@PathVariable("id") String id, Model model) {
		Event event;
		String action;

		if (id.equals("new")) {
			event = new Event(UUID.randomUUID(), "", "", tillUser, new HashSet<>(0), LocalDateTime.now());
			action = "create";
		}
		else {
			Optional<Event> maybeEvent = eventRepository.findById(UUID.fromString(id));
			if (!maybeEvent.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			event = maybeEvent.get();
			action = "edit";
		}

		model.addAttribute("event", event);
		model.addAttribute("action", action);
		model.addAttribute("users", userRepository.findAll());

		return "editEvent";
	}

	@PostMapping("/events/{id}")
	public ModelAndView doCreateOrEdit(@PathVariable("id") String id, @RequestParam String title,
			@RequestParam String description, @RequestParam UUID hostId,
			@RequestParam(value = "guestIds[]", required = false) UUID[] guestIds, @RequestParam String date,
			RedirectAttributes redirectAttributes) {
		UUID parsedId = id.equals("new") ? UUID.randomUUID() : UUID.fromString(id);
		User host = new User(hostId);
		Set<User> guests = new HashSet<>();
		if (guestIds != null) {
			for (UUID guestId : guestIds) {
				guests.add(new User(guestId));
			}
		}
		LocalDate parsedLocalDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
		LocalDateTime parsedDateTime = parsedLocalDate.atStartOfDay();
		Event event = new Event(parsedId, title, description, host, guests, parsedDateTime);
		eventRepository.save(event);
		String flashMessage = id.equals("new") ? "Event has been created." : "Event has been updated.";
		redirectAttributes.addFlashAttribute("status", flashMessage);
		return new ModelAndView("redirect:/");
	}

	private void seedDatabaseIfNeeded() {
		userRepository.save(tillUser);
		userRepository.save(janniUser);
	}

}
