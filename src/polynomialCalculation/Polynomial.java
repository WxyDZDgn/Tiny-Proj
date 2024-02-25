package polynomialCalculation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Polynomial {

	public static final Polynomial ZERO = new Polynomial();
	private final List<Fraction> formula;

	public Polynomial() {
		this(List.of(Fraction.ZERO));
	}

	public Polynomial(List<Fraction> fractions) {
		this.formula = new ArrayList<>();
		List<Fraction> tmp = fractions.reversed();
		int size = fractions.size();
		while(size > 0 && tmp.get(size - 1).compareTo(Fraction.ZERO) == 0) --size;
		for(int i = 0; i < size; ++i) {
			formula.add(tmp.get(i));
		}
		if(formula.isEmpty()) formula.add(Fraction.ZERO);
	}

	public Polynomial(Fraction f, int degree) {
		List<Fraction> ls;
		if(f.compareTo(Fraction.ZERO) == 0) ls = Polynomial.ZERO.formula;
		else {
			ls = new ArrayList<>();
			for(int i = 0; i < degree; ++i) {
				ls.add(Fraction.ZERO);
			}
			ls.add(f);
		}
		this.formula = ls;
	}

	public Polynomial divide(Polynomial coefficientPolynomial) {
		return this.divideAndRemainder(coefficientPolynomial)[0];
	}

	public Polynomial[] divideAndRemainder(Polynomial coefficientPolynomial) {
		Polynomial remainder = this;
		Polynomial divide = Polynomial.ZERO;
		while(remainder.degree() >= coefficientPolynomial.degree()) {
			Fraction lambda = remainder.getCoefficient(remainder.degree())
									   .divide(coefficientPolynomial.getCoefficient(coefficientPolynomial.degree()));
			divide = divide.add(new Polynomial(lambda, remainder.degree() - coefficientPolynomial.degree()));
			remainder = remainder.subtract(coefficientPolynomial.multiply(
					new Polynomial(lambda, remainder.degree() - coefficientPolynomial.degree())));
		}
		return new Polynomial[]{divide, remainder};
	}

	public Polynomial add(Polynomial coefficientPolynomial) {
		List<Fraction> res = new ArrayList<>();
		int i = 0, j = 0;
		while(i <= this.degree() && j <= coefficientPolynomial.degree()) {
			res.add(this.getCoefficient(i).add(coefficientPolynomial.getCoefficient(j)));
			++i;
			++j;
		}
		return getCoefficientPolynomial(coefficientPolynomial, res, i, j);
	}

	public Polynomial subtract(Polynomial coefficientPolynomial) {
		List<Fraction> res = new ArrayList<>();
		int i = 0, j = 0;
		while(i <= this.degree() && j <= coefficientPolynomial.degree()) {
			res.add(this.getCoefficient(i).sub(coefficientPolynomial.getCoefficient(j)));
			++i;
			++j;
		}
		return getCoefficientPolynomial(coefficientPolynomial, res, i, j);
	}

	private Polynomial getCoefficientPolynomial(
			Polynomial coefficientPolynomial, List<Fraction> res, int i, int j
	) {
		while(i <= this.degree()) {
			res.add(this.getCoefficient(i));
			++i;
		}
		while(j <= coefficientPolynomial.degree()) {
			res.add(coefficientPolynomial.getCoefficient(j));
			++j;
		}
		return new Polynomial(res.reversed());
	}

	public Polynomial remainder(Polynomial coefficientPolynomial) {
		return this.divideAndRemainder(coefficientPolynomial)[1];
	}

	@Deprecated
	public Polynomial gcd(Polynomial coefficientPolynomial) {

		return coefficientPolynomial.degree() != 0 ?
				coefficientPolynomial.gcd(this.remainder(coefficientPolynomial)).monic()[1] :
				this;
	}

	public Polynomial[] monic() {
		Polynomial lambda = new Polynomial(Fraction.ONE.divide(this.getCoefficient(this.degree())), 0);
		return new Polynomial[]{lambda, lambda.multiply(this)};
	}

	public Polynomial multiply(Polynomial coefficientPolynomial) {
		List<Fraction> newFormula = new ArrayList<>();
		int sizeA = this.degree(), sizeB = coefficientPolynomial.degree();
		for(int i = 0; i <= sizeA + sizeB; ++i) {
			newFormula.add(Fraction.ZERO);
		}
		for(int a = 0; a <= sizeA; ++a) {
			for(int b = 0; b <= sizeB; ++b) {
				newFormula.set(
						a + b,
						newFormula.get(a + b)
								  .add(this.getCoefficient(a).multiply(coefficientPolynomial.getCoefficient(b)))
				);
			}
		}
		return new Polynomial(newFormula.reversed());
	}

	public List<Fraction> getRatioRoots() {
		BigInteger gcd = null;
		int size = this.degree();
		for(int i = 0; i <= size; ++i) {
			if(i == 0) gcd = this.getCoefficient(i).getDenominator();
			else gcd = gcd.gcd(this.getCoefficient(i).getDenominator());
		}
		BigInteger lcm = BigInteger.ONE;
		for(int i = 0; i <= size; ++i) {
			if(i == 0) lcm = this.getCoefficient(i).getDenominator().divide(gcd);
			else lcm = lcm.multiply(this.getCoefficient(i).getDenominator().divide(gcd));
		}
		lcm = lcm.multiply(gcd);
		List<BigInteger> newFormula = new ArrayList<>();
		for(Fraction fraction : this.formula) {
			newFormula.add(fraction.getNumerator().multiply(lcm.divide(fraction.getDenominator())).abs());
		}
		List<BigInteger> pow0 = new ArrayList<>();
		List<BigInteger> powN = new ArrayList<>();
		int index = 0;
		while(index < size && newFormula.get(index).compareTo(BigInteger.ZERO) == 0) ++index;
		getFactors(pow0, newFormula.get(index));
		getFactors(powN, newFormula.get(size));

		HashSet<Fraction> hash = new HashSet<>();
		if(newFormula.get(0).compareTo(BigInteger.ZERO) == 0) hash.add(Fraction.ZERO);
		for(BigInteger p : pow0) {
			for(BigInteger q : powN) {
				if(this.valueOf(new Fraction(p, q)).compareTo(Fraction.ZERO) == 0)
					hash.add(new Fraction(p, q));
				if(this.valueOf(new Fraction(p.negate(), q)).compareTo(Fraction.ZERO) == 0)
					hash.add(new Fraction(p.negate(), q));
			}
		}
		return new ArrayList<>(hash);
	}

	public int degree() {
		return this.formula.size() - 1;
	}

	public Fraction getCoefficient(int i) {
		if(i < 0 || i > this.degree()) return Fraction.ZERO;
		return this.formula.get(i);
	}

	private void getFactors(List<BigInteger> powN, BigInteger q) {
		for(BigInteger i = BigInteger.ONE; i.compareTo(q.sqrt()) <= 0; i = i.add(BigInteger.ONE)) {
			if(q.remainder(i).equals(BigInteger.ZERO)) {
				powN.add(i);
				BigInteger t = q.divide(i);
				if(!t.equals(i)) powN.add(t);
			}
		}
	}

	public Fraction valueOf(Fraction x) {
		Fraction res = Fraction.ZERO;
		Fraction tmp = Fraction.ONE;
		for(Fraction fraction : this.formula) {
			res = res.add(fraction.multiply(tmp));
			tmp = tmp.multiply(x);
		}
		return res;
	}

	@Override
	public String toString() {
		int size = this.degree();
		if(size <= 0) return this.getCoefficient(0).toString();
		StringBuilder result = new StringBuilder();
		for(int i = size; i >= 0; --i) {
			if(this.getCoefficient(i).compareTo(Fraction.ZERO) == 0) continue;
			StringBuilder s = new StringBuilder();
			if(i < size) s.append(this.getCoefficient(i).compareTo(Fraction.ZERO) <= 0 ? "-" : "+");
			Fraction cur = this.getCoefficient(i).abs();
			if(cur.compareTo(Fraction.ONE) != 0) s.append(cur);
			else s.append(i == 0 ? "1" : "");
			if(i > 0) s.append("x");
			if(i > 1) s.append(String.format("^{%s}", i));
			result.append(s);
		}
		return result.toString();
	}
}
