package codecheck.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerUtil {
	public static Integer convert(String target) {
		return Optional
				.ofNullable(Integer.valueOf(target))
				.orElse(null);
	}
	
	private static Pattern integerPattern = Pattern.compile("-?\\d+");
	public static boolean isInteger(String target) {
		Matcher integerMatcher = integerPattern.matcher(target);
		return integerMatcher.find();
	}
}
