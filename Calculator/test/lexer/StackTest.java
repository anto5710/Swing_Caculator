package lexer;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class StackTest {

	@Test
	public void test() {
		Stack<String> stack = new Stack<>();
		stack.push("12");
		stack.push("33");
		stack.push("17");
		
		assertEquals ( "17", stack.pop());
		assertEquals ( 2, stack.size());
		assertEquals ( "33", stack.pop());
		assertEquals ( 1, stack.size());
		assertEquals ( "12", stack.pop());
		assertEquals ( 0, stack.size());
		
		stack.push("3333");
		assertEquals ( "3333", stack.peek());
		assertEquals ( 1, stack.size());
		stack.peek();
		stack.peek();
		stack.peek();
		stack.peek();
		stack.peek();
		stack.peek();
		assertEquals ( 1, stack.size());
		
		
	}

}
