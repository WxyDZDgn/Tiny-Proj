package polynomialCalculation;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		Polynomial p = new Polynomial(
				Arrays.asList(
						new Fraction(4),
						new Fraction(4),
						new Fraction(-3),
						new Fraction(0)
				)
		);
		Polynomial q = new Polynomial(
				Arrays.asList(
						new Fraction(2),
						new Fraction(-7)
				)
		);
		System.out.printf("p = %s%n", p);
		System.out.printf("q = %s%n", q);
		System.out.printf("p + q = %s%n", p.add(q));
		System.out.printf("p - q = %s%n", p.subtract(q));
		System.out.printf("p * q = %s%n", p.multiply(q));
		System.out.printf("p / q = %s%n", p.divide(q));
		System.out.printf("p %% q = %s%n", p.remainder(q));
		System.out.printf("deg(p), deg(q) = %s, %s%n", p.degree(), q.degree());
		System.out.printf("p(3) = %s%n", p.valueOf(new Fraction(3)));
		System.out.printf("ratio roots of p: %s%n", p.getRatioRoots());
	}
}
