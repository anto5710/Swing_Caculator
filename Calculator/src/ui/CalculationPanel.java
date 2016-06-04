package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.JButton;

import calculator.Calculator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lexer.Token;

/**
 *    상태를 관찰합시다. 
 *    3! = 1 x 2 x 3   3! +  2
 *     
 *     상태표라고 합니다.
 *     
 *      \
 *       \   NUM    OP   (    )    AC    C
 *        +-------------------------------
 *    NUM |    Y     Y   N    ?     Y    Y 
 *    OP2 |    Y     N   Y    N     Y    Y
 *     (  |    Y     N   Y    N     Y    Y
 *    AC  |    Y     N   Y    N     Y    Y
 *     C  |    Y     N   Y    N     Y    Y
 *        +--------------------------------
 *
 *
 * 
 * 
 * 
 * 
 *
 *
 *
 * @author anto5710
 *
 */
public class CalculationPanel extends JFrame {

	private final Calculator calculator = new Calculator();
	
	
	private String exp = ""; 

	private JPanel contentPane;
	private JTextArea equtation = new JTextArea(" \n ", 2, 50);
	private JTextArea result = new JTextArea(" \n", 2, 50);

	private InputValidator validator = new InputValidator();

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculationPanel frame = new CalculationPanel();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalculationPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);
		
		JPanel operationPanel = createOperationPanel();
		JPanel numPanel = createNumPanel();
		JPanel displayPanel = craeteDisplayPanel();
		
		contentPane.add(displayPanel, BorderLayout.NORTH);
		contentPane.add(numPanel, BorderLayout.CENTER);
		contentPane.add(operationPanel, BorderLayout.EAST);
		
		bindKeys();
	}
	
	
	private void bindKeys() {
		InputMap inmap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actions = contentPane.getActionMap();
		
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String paren = e.getActionCommand(); // "(", ")"
				addToken(paren);
			}
		};
		
		inmap.put(KeyStroke.getKeyStroke("shift 9"), "LPAREN");
		actions.put("LPAREN", action);
		inmap.put(KeyStroke.getKeyStroke("shift 0"), "RPAREN");
		actions.put("RPAREN", action);
		
	}

	private void printEquation(){
		equtation.setText( exp );
	}
	
	private void addToken(String token){
		if ( validator.isAcceptable(token) ) {
			exp += token;
			printEquation();
		}
	}

		
	private void clearEquation(){
		if(validator.isAcceptable("AC")){
		
		exp = "\n";
		printEquation();
		clearResult();
		}
	}
	
	private void clearResult(){
		result.setText("\n");
	}
	
	private void printResult(){
		if(validator.isAcceptable("=")){
		
		String result;
		
		try {
			result = calculator.calculate(exp);
		} catch (Exception e) {
			result = "오류";
		}
		
		this.result.setText(result);
		
		}
	}
	
	
	public JPanel createNumPanel(){
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new GridLayout(4, 3, 1, 2));
		
		for(JButton button : createNumButtons()){
			numPanel.add(button);
		}
		
		return numPanel;
	}
	
	public JPanel createOperationPanel(){
		JPanel operationPanel = new JPanel();
		operationPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		for(JButton buttons : createOperationButtons()){
			operationPanel.add(buttons);
		}
		
		return operationPanel;
	}
	
	public JPanel craeteDisplayPanel(){
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JScrollPane eq = new JScrollPane(equtation, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane result = new JScrollPane(this.result, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		equtation.setEditable(false);
		this.result.setEditable(false);
		
		displayPanel.add(eq);
		displayPanel.add(result);
		
		return displayPanel;
	}
	
	private List<JButton> createNumButtons() {
		JButton num1 = new JButton("1");
		JButton num2 = new JButton("2");
		JButton num3 = new JButton("3");
		JButton num4 = new JButton("4");
		JButton num5 = new JButton("5");
		JButton num6 = new JButton("6");
		JButton num7 = new JButton("7");
		JButton num8 = new JButton("8");
		JButton num9 = new JButton("9");
		JButton nothing = new JButton("");
		JButton num0 = new JButton("0");
		JButton point = new JButton(".");
		
		List<JButton>buttons = Arrays.asList(num1, num2, num3, num4, num5, num6, num7, num8, num9, nothing, num0, point);
		
		for(final JButton button : buttons){
			ActionListener listener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					addToken(button.getText());						
				}
			};
			
			button.addActionListener(listener);
		}
		
		return buttons;
	}
	
	private List<JButton> createOperationButtons(){
		
		String operators = "+-*/%^";
		
		List<JButton>buttons = new ArrayList<>();
		
		JButton clearAll = new JButton("AC");
		clearAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearEquation();
			}
		});
		
		JButton answer = new JButton("=");
		answer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				printResult();
			}
		});
		

		buttons.add(clearAll);
		buttons.add(answer);
		
		for(char charop : operators.toCharArray()){
			final String operator = String.valueOf(charop); 
			
			JButton button = new JButton(operator);
			
			ActionListener listener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					addToken(operator);
				}
			};
			button.addActionListener(listener);
			buttons.add(button);
		}
		
		
		return buttons;
	}
}
