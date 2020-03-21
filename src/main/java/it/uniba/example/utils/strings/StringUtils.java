package it.uniba.example.utils.strings;

/**
 * This class is demo purpose only and must be deleted.
 */
public final class StringUtils {

	/**
	 * Default empty constructor.
	 */
	private StringUtils() {

	}

	/**
	 * Converts the parameter from <code>String</code> to <code>Double</code>.
	 * 
	 * @param str
	 *            The string to convert to number.
	 * @return The corresponding number.
	 */
	public static Double convertToDouble(final String str) {
		Double res = null;
		if (str != null) {
			res = Double.valueOf(str);
		}
		return res;
	}

	/**
	 * Checks whether the parameter is <code>null</code> or empty (blank).
	 * 
	 * @param str
	 *            The string to check.
	 * @return <code>True</code> is the string is <code>null</code> or has length
	 *         zero.
	 */
	public static boolean isNullOrBlank(final String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * Merges strings together. If any of the strings passed as parameter is
	 * <code>null</code> or has length zero, it is ignored.
	 * 
	 * @param strings
	 *            The list of strings to concatenate.
	 * @return The concatenated string.
	 */
	public static String concat(final String... strings) {
		String concatStr = null;

		if (strings != null && strings.length > 0) {
			final StringBuilder stringBuilder = new StringBuilder();
			for (final String str : strings) {
				if (!isNullOrBlank(str)) {
					stringBuilder.append(str);
				}
			}
			concatStr = stringBuilder.toString();
		}
		return concatStr;
	}

}
