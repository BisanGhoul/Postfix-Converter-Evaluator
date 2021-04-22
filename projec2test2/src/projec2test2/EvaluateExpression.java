package projec2test2;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class EvaluateExpression {
	@FXML Label ValidLabel;
	@FXML TextField eq1TextField;
	
	
	String eq1;
	String eq2;
	
	
	public EvaluateExpression(String eq1, String eq2) {
		
		this.eq1 = eq1;
		this.eq2 = eq2;
	}

	String[] lines;
	
	public static String insertBlanks(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '[' || s.charAt(i) == ']' || s.charAt(i) == '(' || s.charAt(i) == ')'
					|| s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/')
				result += " " + s.charAt(i) + " ";
			else
				result += s.charAt(i);
		}
		return result;
	}




	public static void readfileEquations() throws IOException {

		File file = new File("output.txt");
		File file2 = new File("equations.txt");
		Scanner scan = new Scanner(file2);


		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		String expressions[] = new String[20];
		for(int i=0;i<20;i++) {
			expressions[i]="";
		}
		int i = 0;

		while (scan.hasNextLine()) { // scan line by line from the file and send each to handleequations
			String line = scan.nextLine();

			expressions[i] += processLines(line);
			writer.write(expressions[i]);
			writer.write("\n");

			i++;
		}
		
		writer.flush();
		writer.close();
		scan.close();//?
	}

	
	
	//process file line by line
	public static String processLines(String line) throws IOException {
		String[] details = line.split("=");
		String eq1 = details[0];
		String eq2 = details[1];
	
		if (isValid(eq1) && isValid(eq2)) {
			if(isEqual(evaluateExpression2(eq1),evaluateExpression2(eq2))) {
				return("[postfix(" + infixToPostfix(eq1) + ")=result(" + evaluateExpression2((eq1)) + ")]=?" + "[postfix("
				+ infixToPostfix(eq2) + ")=result(" + evaluateExpression2((eq2)) + ")]->True");
			}else
				if(!isEqual(evaluateExpression2((eq1)),evaluateExpression2((eq2)))) {
					return("[postfix(" + infixToPostfix(eq1) + ")=result(" + evaluateExpression2((eq1)) + ")]=?" + "[postfix("
					+ infixToPostfix(eq2) + ")=result(" + evaluateExpression2((eq2)) + ")]->False");
				
		}


		}
		if(isValid(eq1) && !(isValid(eq2))) {
			return("equation 2 is not valid| ->equation 2: "+ "[postfix("
				+ infixToPostfix(eq1) + ")=result(" + evaluateExpression2((eq1)) + ")]");
		
		}
		if(!(isValid(eq1)) && isValid(eq2)) {
			return("equation 1 is not valid| ->equation 2: "+ "[postfix("
					+ infixToPostfix(eq2) + 
					")=result(" + evaluateExpression2((eq2)) + ")]");
			
		}
		if(!(isValid(eq1)) && !(isValid(eq2))){
			return("equation 1 and 2 are both invalid");
			
		}


	
		return null;
		
	}
	
	


	public static boolean isEqual(Double double1, Double double2) {
		 if(double1.compareTo(double2)==0)
		 return true;
		 else 
			 return false;
		 
		 
	}
		
	public static boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%')
			return true;
		return false;
	}

	public static boolean isOperand(char c) {
		return ((int) c) >= 48 && ((int) c) <= 57;
	}
	
public static boolean isOperator(String c) {
	if (c == "+" || c == "-"|| c == "*" || c == "/" )
		return true;
	return false;
}

public static boolean isOperand(String c) {
	return (Integer.parseInt(c) ) >= 48 && (Integer.parseInt(c)) <= 57;
}




	public static boolean isValid(String equation) {
		equation = " " + equation + " ";
		if ((isOperator(equation.charAt(0)) && (equation.charAt(0) != '-'))
				|| (isOperator(equation.charAt(equation.length() - 1)))) {
			return false;
		}

		int openParentheses = 0;
		int openSqBracket = 0;

		for (int i = 0; i < equation.length(); i++) {
			if (equation.charAt(i) == ' ')
				continue;

			if ((equation.charAt(i) == '(')) {
				openParentheses++;
				if (isOperand(equation.charAt(i - 1))) {
					return false;

				}

			} else if ((equation.charAt(i) == ')')) {

				if ((openParentheses < 1) || isOperand(equation.charAt(i + 1)) || equation.charAt(i + 1) == '(')

					return false;

				else
					openParentheses--;

			} else if ((equation.charAt(i) == '[')) {
				openSqBracket++;
			} else if ((equation.charAt(i) == ']')) {
				if (openSqBracket < 1 || isOperand(equation.charAt(i + 1)) || equation.charAt(i + 1) == '[')
					return false;
				else
					openSqBracket--;
			} else if (isOperator(equation.charAt(i))
					&& ((isOperator(equation.charAt(i + 1))) && (equation.charAt(i + 1) != '-'))) {
				return false;
			}

		}
		return (openSqBracket == 0 && openParentheses == 0);
	}



	public static int priority(char x) {


		switch(x) {
		case '*':
		case '/':
			return 2;
		case '+':
		case '-':
			return 1;
		default:
			return 0;
			
		}
		
	

	}


	
	public static String infixToPostfix(String equation) {
		StackLinkedList<Character> stack = new StackLinkedList<Character>();
		String postfix = "";

		for (int i = 0; i < equation.length(); i++) {
			if (Character.isDigit(equation.charAt(i))){
				postfix = postfix+equation.charAt(i);
				if(i+1 >= equation.length() ||(!Character.isDigit(equation.charAt(i+1)) ))
					postfix +=" ";
			}
			
			else if (equation.charAt(i) == '['||equation.charAt(i) == '(' )
				stack.push(equation.charAt(i));

			else if (equation.charAt(i) == ']') {
				while (!stack.isEmpty() && stack.peek() != '[')
					postfix += stack.pop();
				if (!stack.isEmpty() && stack.peek() == '[')
					stack.pop();
				else {
					return null;
				}
			} 
			else if (equation.charAt(i) == ')') {
				while (!stack.isEmpty() && stack.peek() != '(')
					postfix += stack.pop();
				if (!stack.isEmpty() && stack.peek() == '(')
					stack.pop();
				else {
					return null;
				}
			} 
	
			else if(equation.charAt(i) =='-'){
				if( i==0 || equation.charAt(i-1)=='('||isOperator(equation.charAt(i-1))) 
					postfix = postfix+'#';
				else {
					if (!stack.isEmpty()&& priority(equation.charAt(i)) <= priority(stack.peek()))
					postfix = postfix+stack.pop();
				    stack.push(equation.charAt(i));
				}
			}

			else if ((isOperator(equation.charAt(i))&&equation.charAt(i)!='-')|| equation.charAt(i) == '[' || equation.charAt(i) == ']'
					|| equation.charAt(i) == '(' || equation.charAt(i) == ')' ) {
				if (!stack.isEmpty()&& priority(equation.charAt(i)) <= priority(stack.peek()))
					postfix = postfix+stack.pop();
				stack.push(equation.charAt(i));
			}
		}

		while (!stack.isEmpty()) {
			if (stack.peek() == '(')
				System.out.println("exp envalid");
			postfix += stack.pop();
		}
		return postfix;
	}


	public static double evaluateExpression(String postfix){
		StackLinkedList<Double> stack =new StackLinkedList<Double>();
		String s;

		//insertBlanks(postfix);
		String[] details=postfix.split(" ");
		for(int i=0; i<details.length; i++){
			
			if(details[i].equals("#")){
				stack.push(Double.parseDouble("-"+postfix.substring(i+1, i+2)));
				i++;
			}
			else if(details[i].equals("+")){
				stack.push(calculate('+',stack.pop(),stack.pop()));
			} 

			else if(details[i].equals("-")){

				stack.push(calculate('-',stack.pop(),stack.pop()));
			} 

			else if(details[i].equals("*")){
				stack.push(calculate('*',stack.pop(),stack.pop()));
			} 
	
			else if(details[i].equals("/")){
				stack.push(calculate('/',stack.pop(),stack.pop()));
			} 
			else if(!isOperand(details[i]))
				stack.push(Double.parseDouble(details[i]));
		}

		return stack.pop();
	}
	
	
	public static Double evaluateExpression2(String equation) {

		StackLinkedList<Double> operandStack = new StackLinkedList<>(); // stack to store operands
		StackLinkedList<Character> operatorStack = new StackLinkedList<>(); // stack to store operators

		equation = insertBlanks(equation); // to insert spaces between operators & between '('')' & ']' '['

		String[] details = equation.split(" ");

		
		for (String token : details) {
			if (token.length() == 0) // -> space
				continue;
			else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
			
				while (!operatorStack.isEmpty() && (operatorStack.peek() == '+' || operatorStack.peek() == '-'
						|| operatorStack.peek() == '*' || operatorStack.peek() == '/')) {
					double a=operandStack.pop();
					double b=operandStack.pop();
					operandStack.push(calculate(operatorStack.pop(),a , b));
				}
				
				operatorStack.push(token.charAt(0));
			} else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
				
				while (!operatorStack.isEmpty() && (operatorStack.peek() == '*' || operatorStack.peek() == '/')) {
					operandStack.push(calculate(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
				}

				
				operatorStack.push(token.charAt(0));
			} else if (token.trim().charAt(0) == '(') {
				operatorStack.push('('); 
			} else if (token.trim().charAt(0) == ')') {
				
				while (operatorStack.peek() != '(') {
					operandStack.push(calculate(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
				}
				operatorStack.pop(); 
				
			} else if (token.trim().charAt(0) == '[') {
				operatorStack.push('['); 
			} else if (token.trim().charAt(0) == ']') {
				
				while (operatorStack.peek() != '[') {
					operandStack.push(calculate(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
				}
				operatorStack.pop(); 
			} else { 
				
				operandStack.push(new Double(token));
			}
		}

		while (!operatorStack.isEmpty()) {
			operandStack.push(calculate(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
		}

		return operandStack.pop(); // return the final result
	}

	public static Double calculate(Character op, Double a, Double b) {
//		char op = operatorStack.pop();
//		int op1 = operandStack.pop();
//		int op2 = operandStack.pop();
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return b - a;
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				throw new UnsupportedOperationException("Cannot divide by zero");
			return b / a;
		}
		return 0.0;
	}
	
	public static  String replace(String postfix) {

		String[] details = postfix.split(" ");

		
		for (String token : details) {
			if(token.charAt(0)=='#')
				token.replace('#', '-');
		}
		return postfix;

	}




	public static boolean isEqual(String text, String text2) {
		if(text.compareTo(text2)==0)
		return true;
		
		else 
			return false;
	}
}