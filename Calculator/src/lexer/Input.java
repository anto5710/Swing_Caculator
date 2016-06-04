package lexer;

import lexer.Token.Type;

class Input {
	
	private int p0;
	private int p1;
	private StringBuilder chars ;
	
	private static String op = "+-*/%^";
	
	
	public Input(String str) {
		this.chars = new StringBuilder(str);
	}

	public String consume() {
		String tk = chars.substring(p0, p1);
		p0 = p1;
		return tk;
	}

	public Type currentTokenType() {
		String tk = chars.substring(p0, p1);
		return type ( tk );
	}
	
	public Type nextCharType() {
		String tk = chars.substring(p1, Math.min(p1+1, chars.length()) );
		return type ( tk );
	}
	
	public Type type( String tk ) {
		if ( tk.equals("(")){
			return Type.LPAREN;
		} else if ( tk.equals(")") ) {
			return Type.RPAREN;
		} else if (  anyOf ( op, tk)) {
			return Type.OP;
		} else if ( isNum(tk)) {
			return Type.NUM;
		} else if ( "".equals(tk) ){
			return Type.EOT;
		} else if ( ".".equals(tk) ) {
			return Type.DOT;
		}
		
		else {
			throw new RuntimeException("what is this?: " + tk);				
		}
	}

	private boolean isNum(String tk) {
		return tk.matches("\\d*\\.*\\d+\\.*");
	}

	private boolean anyOf(String op, String tk) {
		return op.indexOf(tk) >= 0 ;
	}

	public void forawrd() {
		p1 = Math.min(p1+1, chars.length()) ;
	}

	public boolean notEmpty() {
		return p0 < p1 || p1 < chars.length() ;
	}

	@Override
	public String toString() {
		int s = Math.max(p0, p0-10);
		int e = Math.min(p1 +10, chars.length() );
		
		return "... " + chars.substring(s, e) + "... [" + s + ", " + e + "]" + "\n" + 
		       "..."  + "cur\"" + chars.substring(p0, p1) + "\"";
	}
}