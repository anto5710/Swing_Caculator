package lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lexer.Token.Type;

public class Lexer {
	private static final Map<String, Integer> PRIORITY = new HashMap<>();
	
	static {
		PRIORITY.put("+", 1);
		PRIORITY.put("-", 1);
		PRIORITY.put("*", 2);
		PRIORITY.put("/", 2);
		PRIORITY.put("%", 3);
		PRIORITY.put("^", 4);
	}
	
	
	
	public Token [] parseTokens ( String exp ) {
		String [] sa = exp.split("\\s"); // 모든 공백으로 잘라냄
		String input = String.join("", sa);
		
		Input chars = new Input ( input );
		ArrayList<Token> tokens = new ArrayList<>();
		Token t = null;
		
		while ( chars.notEmpty()) {
			chars.forawrd();
			Type type = chars.nextCharType();
			switch ( type ) {
				case LPAREN :
				case RPAREN:
				case OP:
					/*
					 * 괄호나 연산자가 모두 length가 1이므로 가능한 구현
					 */
					t = new Token(chars.currentTokenType(), chars.consume());
					type ( t );
					tokens.add(t);
					break;
				case NUM:
				case DOT:
					if ( chars.currentTokenType() != Type.NUM && chars.currentTokenType() != Type.DOT) {
						t = new Token ( chars.currentTokenType(), chars.consume());
						type ( t );
						tokens.add(t);
					}
					break;
				case EOT:
					t = new Token( chars.currentTokenType(), chars.consume());
					type ( t );
					tokens.add(t);
					break;
				default :
					break;
			}
			
			
		}
		
		return tokens.toArray(new Token[tokens.size()]);
	}

	private void type(Token t) {
		if(Token.isOP(t)){
			t.level(PRIORITY.get(t.value()));
		}
	}
}
