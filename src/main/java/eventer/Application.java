package eventer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailSender mailSender;

	@Bean
	public EmailService emailService(@Value("${eventer.send-emails}") boolean sendEmails) {
		return new EmailService(mailSender, sendEmails);
	}

	@Bean
	public EventService eventService(EmailService emailService) {
		return new EventService(eventRepository, userRepository, emailService);
	}

}
