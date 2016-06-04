package lexer;

import static org.junit.Assert.*;
import lexer.Token.Type;

import org.junit.Test;

public class LexerTest {

	@Test
	public void test() {
		Lexer lexer = new Lexer();
		Token [] tokens = lexer.parseTokens("1.4 + 22");
		assertEquals ( 3, tokens.length);
		assertEquals ( "1.4", tokens[0].value());
		assertEquals ( "+", tokens[1].value());
		assertEquals ( "22", tokens[2].value());
		
		// type
		
		assertEquals ( Type.NUM, tokens[0].type());
		assertEquals ( Type.OP, tokens[1].type());
		assertEquals ( Type.NUM, tokens[2].type());
	}

}
