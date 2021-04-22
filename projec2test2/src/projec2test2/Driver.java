package projec2test2;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Driver 
extends Application
{

	public static void main(String[] args) throws IOException {
		Application.launch(args);
System.out.println(EvaluateExpression.evaluateExpression2("2*(2-5)+4-[9*3*(7-2)] "));
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Driver.class.getResource("GUIp2.fxml"));
			Pane root = (Pane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("equations");
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
