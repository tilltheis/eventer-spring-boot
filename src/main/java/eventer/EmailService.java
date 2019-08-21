package eventer;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailService extends Logging {

	private MailSender mailSender;

	private boolean sendEmails;

	public EmailService(MailSender mailSender, boolean sendEmails) {
		this.mailSender = mailSender;
		this.sendEmails = sendEmails;
	}

	public void sendEmail(String recipient, String subject, String body) {
		logger.info("Sending email about '{}' to '<anonymized>@{}'...", subject, recipient.split("@")[1]);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("mail.eventer.local");
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(body);

		if (sendEmails) {
			mailSender.send(message);
		}
		else {
			logger.info("Nothing has been sent because email sending is disabled.");
		}
	}

}
