package polynomialCalculation;

import java.math.*;

public class Fraction implements Comparable<Fraction> {
	public static Fraction ZERO = new Fraction();
	public static Fraction ONE = new Fraction(1);
	private BigInteger numerator;
	private BigInteger denominator;

	public Fraction(String numerator, String denominator) {
		this(new BigInteger(numerator), new BigInteger(denominator));
	}

	public Fraction(BigInteger numerator, BigInteger denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		BigInteger m = this.denominator.gcd(this.numerator);
		this.numerator = this.numerator.divide(m);
		this.denominator = this.denominator.divide(m);
		if(this.denominator.compareTo(BigInteger.ZERO) < 0) {
			this.denominator = this.denominator.negate();
			this.numerator = this.numerator.negate();
		}
	}

	public Fraction() {
		this(0);
	}

	public Fraction(int numerator) {
		this(String.valueOf(numerator));
	}

	public Fraction(String numerator) {
		this(new BigInteger(numerator), BigInteger.ONE);
	}

	public Fraction(int numerator, int denominator) {
		this(new BigInteger(String.valueOf(numerator)), new BigInteger(String.valueOf(denominator)));
	}

	public Fraction add(Fraction fraction) {
		BigInteger gcd = this.denominator.gcd(fraction.denominator);
		BigInteger lcm = this.denominator.multiply(fraction.denominator).divide(gcd);
		BigInteger numerator1 = this.numerator.multiply(lcm.divide(this.denominator));
		BigInteger numerator2 = fraction.numerator.multiply(lcm.divide(fraction.denominator));
		return new Fraction(numerator1.add(numerator2).toString(), lcm.toString());
	}

	public Fraction multiply(Fraction fraction) {
		return new Fraction(
				this.numerator.multiply(fraction.numerator).toString(),
				this.denominator.multiply(fraction.denominator).toString()
		);
	}

	public Fraction divide(Fraction fraction) {
		return new Fraction(
				this.numerator.multiply(fraction.denominator).toString(),
				this.denominator.multiply(fraction.numerator).toString()
		);
	}

	@Override
	public String toString() {
		if(this.denominator.equals(BigInteger.ONE)) return this.numerator.toString();
		return String.format("\\frac{%s}{%s}", this.numerator.toString(), this.denominator.toString());
	}

	@Override
	public int compareTo(Fraction o) {
		Fraction delta = this.sub(o);
		return delta.numerator.compareTo(BigInteger.ZERO);
	}

	public Fraction sub(Fraction fraction) {
		BigInteger gcd = this.denominator.gcd(fraction.denominator);
		BigInteger lcm = this.denominator.multiply(fraction.denominator).divide(gcd);
		BigInteger numerator1 = this.numerator.multiply(lcm.divide(this.denominator));
		BigInteger numerator2 = fraction.numerator.multiply(lcm.divide(fraction.denominator));
		return new Fraction(numerator1.subtract(numerator2).toString(), lcm.toString());
	}

	public BigInteger getNumerator() {
		return this.numerator;
	}

	public BigInteger getDenominator() {
		return this.denominator;
	}

	public Fraction abs() {
		return new Fraction(numerator.abs(), denominator);
	}
}
