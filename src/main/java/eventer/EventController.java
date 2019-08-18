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
    public String showEdit(@PathVariable("id") UUID id, Model model) {
        Optional<Event> maybeEvent = eventRepository.findById(id);
        if (maybeEvent.isPresent()) {
            model.addAttribute("event", maybeEvent.get());
            model.addAttribute("users", userRepository.findAll());
            return "editEvent";
        } else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/events/{id}")
    public ModelAndView doEdit(@PathVariable("id") UUID id, @RequestParam String title, @RequestParam String description, @RequestParam UUID hostId, @RequestParam(value = "guestIds[]", required = false) UUID[] guestIds, @RequestParam String date, RedirectAttributes redirectAttributes) {
        User host = new User(hostId);
        Set<User> guests = new HashSet<>();
        if (guestIds != null) {
            for (UUID guestId : guestIds) {
                guests.add(new User(guestId));
            }
        }
        LocalDate parsedLocalDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        LocalDateTime parsedDateTime = parsedLocalDate.atStartOfDay();
        Event event = new Event(id, title, description, host, guests, parsedDateTime);
        eventRepository.save(event);
        redirectAttributes.addFlashAttribute("status", "Event has been updated.");
        return new ModelAndView("redirect:/");
    }

    @GetMapping(path = "/create")
    public String showCreate(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "createEvent";
    }

    @PostMapping(path = "/create")
    public ModelAndView doCreate(@RequestParam String title, @RequestParam String description, @RequestParam UUID hostId, @RequestParam(value = "guestIds[]", required = false) UUID[] guestIds, @RequestParam String date, RedirectAttributes redirectAttributes) {
        UUID id = UUID.randomUUID();
        User host = new User(hostId);
        Set<User> guests = new HashSet<>();
        if (guestIds != null) {
            for (UUID guestId : guestIds) {
                guests.add(new User(guestId));
            }
        }
        LocalDate parsedLocalDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        LocalDateTime parsedDateTime = parsedLocalDate.atStartOfDay();
        Event event = new Event(id, title, description, host, guests, parsedDateTime);
        eventRepository.save(event);
        redirectAttributes.addFlashAttribute("status", "Event has been created.");
        return new ModelAndView("redirect:/");
    }

    private void seedDatabaseIfNeeded() {
        userRepository.save(new User(UUID.fromString("32ec5ce0-5173-4a52-8fc8-62356bd26cc5"), "Till", "example@example.org"));
        userRepository.save(new User(UUID.fromString("fae95a3e-7e9c-4bcc-8903-87305b055bfe"), "Janni", "example@example.org"));
    }
}
