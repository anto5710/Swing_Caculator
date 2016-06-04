package calculator;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void test_level0() {
		Calculator c = new Calculator();
		String r = c.calculate("1 + 2");
		assertEquals ( "3.0", r);
		
		r = c.calculate(" 10 - 3");
		assertEquals ( "7.0", r);
		
		r = c.calculate(" 10 - 3 + 2");
		assertEquals ( "9.0", r);
		
		r = c.calculate(" 10 - 3 + 2 - 5 + 2");
		assertEquals ( "6.0", r);
		
		r = c.calculate(" 20 - 3 * 4");
		assertEquals ( "8.0", r);
		
		r = c.calculate(" 20 / 4 * 10");
		assertEquals ( "50.0", r);

		r = c.calculate(" 20 / 4 - 10");
		assertEquals ( "-5.0", r);

		r = c.calculate(" 20/4 - 3 * 2 + 10"); //  5 - 6 + 10
		assertEquals ( "9.0", r);
		
		r = c.calculate("2 * ( 1 + 3)");
		assertEquals ( "8.0", r);
		
		r = c.calculate("(2+7) * ( 1 + 10 ^(1+2))");
		assertEquals ( "9009.0", r);
	}

}
