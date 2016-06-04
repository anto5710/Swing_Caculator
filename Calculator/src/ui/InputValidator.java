package ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexer.Token.Type;

/**
 * 현재 눌리는 버튼의 문자열 값들을 보면서 입력이 올바른지 확인해주는 역할을 합니다.
 * 
 * 25
 * 
 * @author anto5710
 *   
 *   2! + 3* 3^2!   
 *    상태표
 *      \
 *       \   NUM    OP   (    )    AC    C
 *        +-------------------------------
 *    NUM |    Y     Y   N    ?     Y    Y   => ( NUM, [NUM, OP, AC, C] )
 *    OP2 |    Y     N   Y    N     Y    Y   => ( OP, [ NUM, LP, AC, C] )
 *     (  |    Y     N   Y    N     Y    Y   => ( LP, [ NUM, LP, AC, C] )
 *    AC  |    Y     N   Y    N     Y    Y   => ( AC, [ NUM, LP, AC, C] )
 *     C  |    Y     N   Y    N     Y    Y   => (  C, { NUM, LP, AC, C] )
 *        +--------------------------------
 *
 *  25*3+(
 *  
 *  NUM, NUM, OP, NUM, OP, LP
 */
public class InputValidator {
	public enum Types { NUM, OP, LP, RP, AC, EQ};
	
	private static final Map<String, Types> typeMap = new HashMap<>();
	private static final Map<Types, List<Types>> matches = new HashMap<>();
	
	private int LParens_left = 0;
	private Types lastTokenType = Types.AC;
	
	static{
		initMatches();
		initTypeMap();
	}
	
	private static void initTypeMap(){
		String nums = "0123456789";
		for ( int i = 0 ; i < nums.length(); i++ ) {
			typeMap.put(nums.substring(i, i+1), Types.NUM);
		}
		String ops = "+-*/%^";
		for ( int i = 0 ; i < ops.length(); i++ ) {
			typeMap.put(ops.substring(i, i+1), Types.OP);
		}
		
		
		typeMap.put("(", Types.LP);
		typeMap.put(")", Types.RP);
		typeMap.put("AC", Types.AC);
		typeMap.put("=", Types.EQ);
	}
	
	
	private static void initMatches(){
		matches.put(Types.NUM, Arrays.asList(Types.NUM, Types.OP, Types.AC, Types.EQ));
		matches.put(Types.OP, Arrays.asList(Types.NUM, Types.LP, Types.AC));
		matches.put(Types.LP, Arrays.asList(Types.NUM, Types.LP, Types.AC));
		matches.put(Types.RP, Arrays.asList(Types.OP, Types.AC, Types.EQ));
		matches.put(Types.AC, Arrays.asList(Types.NUM, Types.LP, Types.AC));
		matches.put(Types.EQ,  Arrays.asList(Types.AC, Types.LP, Types.NUM, Types.OP, Types.EQ));
	}
	
	public boolean isAcceptable ( String token ) { // "1", "2", ... "0", "*"
		// 12 + 34 )
		token = token.trim();
		
		Types curType = typeMap.get(token);
		Types formerType = lastTokenType;
		
		boolean acceptable = LParens_left > 0 ;
		
		if(curType != Types.RP){
			acceptable = matches.get(formerType).contains(curType);
		}
		
		
		if(acceptable){
			lastTokenType = curType;
			
			if(curType == Types.LP){
				LParens_left++;
			}
		
		}
		
		return acceptable;
	}
}
