package eventer;

import com.github.jknack.handlebars.Options;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@HandlebarsHelper
public class HandlebarsHelpers {

	public static CharSequence guestListPreview(Set<User> guests) {
		List<String> names = new ArrayList<>(guests.size());
		for (User guest : guests) {
			names.add(guest.getName());
		}
		Collections.sort(names);
		return String.join(", ", names);
	}

	public static <A> CharSequence ifEquals(A x, A y, Options options) throws IOException {
		return Objects.equals(x, y) ? options.fn(options.context) : options.inverse(options.context);
	};

	public static <A> CharSequence ifContained(A element, Collection<A> collection, Options options)
			throws IOException {
		return collection.contains(element) ? options.fn(options.context) : options.inverse(options.context);
	};

	public static CharSequence formatDate(LocalDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));
	}

}
