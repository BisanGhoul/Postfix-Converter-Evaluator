package projec2test2;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	@FXML TextArea validTextArea;
	@FXML TextField eq1TextField;
	@FXML TextField eq2TextField;
	@FXML Label vaildLabel;
	@FXML Label resultLabel;
	@FXML Label postfixLabel;
	@FXML Pane pane;
	@FXML Label compareLabel;
	
	public  void valid() {
		boolean x=EvaluateExpression.isValid(eq1TextField.getText());
		if(x) {
			vaildLabel.setText("equation is valid");
		}else {
			vaildLabel.setText("equation is not valid");
		}
			
	}
	
	public void result() {
		
		if(EvaluateExpression.isValid(eq1TextField.getText())) {
			double x =EvaluateExpression.evaluateExpression2((eq1TextField.getText()));
			resultLabel.setText(String.valueOf(x));
		}else {
			resultLabel.setText("equation is not valid");
		}
		
	}
	
	public void toPostfix() {
		
		if(EvaluateExpression.isValid(eq1TextField.getText())) {
			String x =EvaluateExpression.infixToPostfix(eq1TextField.getText());
			postfixLabel.setText(x);
		}else {
			postfixLabel.setText("equation is not valid");
		}
		
	}
	
	public void compare() {
		
		double eq1= EvaluateExpression.evaluateExpression2((eq1TextField.getText()));
		double eq2= EvaluateExpression.evaluateExpression2((eq2TextField.getText()));
		if(EvaluateExpression.isValid(eq1TextField.getText())) {
			boolean x =EvaluateExpression.isEqual(eq1, eq2);
			if(x==true) {
				compareLabel.setText("equation 1 equals equation 2");
			}else if(x==false) {
				compareLabel.setText("equation 1 doesnt equal equation 2");
			}
		}else {
			postfixLabel.setText("equation is not valid");
		}
		
	}
	
	public void printFile() throws IOException {
		EvaluateExpression.readfileEquations();
	}
	
	
	@FXML 
	private void handleButtonAction(ActionEvent event) {
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("choose file");
		Stage primaryStage = (Stage)pane.getScene().getWindow();
		filechooser.showOpenDialog(primaryStage);
	}
	
	
	
	
	
	

}