package it.uniba.example.utils.math;

/**
 * This class and package are for demo purpose only and must be deleted.
 */
public class MathUtils {
	/**
	 * Returns the arithmetic sum of two integers.
	 * 
	 * @param addend1
	 *            First integer to sum.
	 * @param addend2
	 *            Second integer to sum.
	 * @return The arithmetic sum of <code>a</code> and <code>b</code>.
	 */
	public int add(final int addend1, final int addend2) {
		return addend1 + addend2;
	}

	/**
	 * Returns the arithmetic division of two integers.
	 * 
	 * @param num
	 *            The division numerator.
	 * @param div
	 *            The division denominator.
	 * @return The result of the division as a <code>float</code> number.
	 * @throws ArithmeticException
	 *             If <code>div</code> is zero.
	 */
	public float divide(final int num, final int div) throws ArithmeticException {
		return new Float(num / div);
	}
}
