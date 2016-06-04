package calculator;

import java.util.Stack;

import lexer.Lexer;
import lexer.Token;
import lexer.Token.Type;

/**
 * "3 * ( 3 + 2) - 5" 
 * =>"10"
 * @author anto5710
 *
 */




public class Calculator {
	private final Lexer lexer = new Lexer();
	private Stack<Token>Nstack = new Stack<>();
	private Stack<Token>OPstack = new Stack<>();
	
	public String calculate ( String exp ) {
		try {
			Token [] tokens = lexer.parseTokens(exp);
			a(tokens);
			String a, b, op;
			
			while (!OPstack.isEmpty()){
				op = OPstack.pop().value();
				a = Nstack.pop().value();
				b = Nstack.pop().value();
				
				String r = caculate(a, op, b);
				
				Nstack.add(new Token(Type.NUM, r));
			} 
			
			return Nstack.pop().value();
			
		} finally {
			Nstack.clear();
			OPstack.clear();
		}
	}
	
	
	
	private void a(final Token [] tokens) {
		int t_level;
		Token last_op;
		
		for ( Token t : tokens ) {
			t_level = t.level();
			
			
			if ( t.typeOf(Type.NUM) ) {
				Nstack.push(t);
				
			}else if(t.typeOf(Type.OP)){
				
				while(!OPstack.isEmpty() && (last_op = OPstack.peek()).level() >= t_level){
				
						String r = caculate(Nstack.pop().value(), last_op.value(), Nstack.pop().value());
						Nstack.push(new Token(Type.NUM, r));
						OPstack.pop();
					
				}
				OPstack.push(t);
			
			
			} else if(t.typeOf(Type.LPAREN)){
				OPstack.push(t);
				
			}else if(t.typeOf(Type.RPAREN)){
				
				while ((last_op = OPstack.pop()).type() != Type.LPAREN){
					
					Token a = Nstack.pop();
					Token b = Nstack.pop();
					
					String r = caculate(a.value(), last_op.value(), b.value());
					Token push = new Token(Type.NUM, r);
					
					Nstack.push(push);
				}
			}
			
			else {
				System.out.println("숫자도 연산자도 아님: " + t);
			}
		}
	}



	private String caculate(String b, String op, String a){
		double result = 0;
		
		double na = Double.parseDouble(a);
		double nb = Double.parseDouble(b);
		
		switch (op) {
		case "+":
			result = na + nb;
			break;
		case "-":
			result = na - nb;
			break;
		case "*":
			result = na * nb;
			break;
		case "/":	
			result = na / nb;
			break;
		case "%":	
			result = na % nb;
			break;
		case "^":
			result = Math.pow(na, nb);
			break;
		}
		
		return String.valueOf(result);
	}
}
