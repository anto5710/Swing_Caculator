package lexer;

import lexer.Token.Type;
/**
 * 연산자, 피연산자(숫자) 또는 괄호를 나타내는 클래스입니다.
 * @author anto5710
 *
 */
public class Token {
	public enum Type { LPAREN, RPAREN, OP, NUM, EOT, DOT};
	private String value;
	private Type type;
	private int level;
	
	public Token(Type type, String val) {
		this.value = val;
		this.type = type;
	}

	public String value() {
		return value;
	}

	public Type type() {
		return type;
	}

	public static boolean isNum(Token token) {
		return token.type == Type.NUM;
	}

	public static boolean isOP(Token token) {
		return token.type == Type.OP;
	}

	public static boolean isLParen(Token token) {
		return token.type == Type.LPAREN;
	}
	
	public static boolean isRParen(Token token) {
		return token.type == Type.RPAREN;
	}

	public void level(int level) {
		this.level = level;
	}
	
	public int level() {
		return level;
	}

	@Override
	public String toString() {
		return "" + value + "(" + type + ")";
	}

	public boolean typeOf(Type t) {
		return this.type == t;
	}
	
	
}
