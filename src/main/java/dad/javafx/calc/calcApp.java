package dad.javafx.calc;

import javafx.application.Application;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class calcApp extends Application {

	private Label arriba, medio, abajo;
	private TextField operando1text, operando2text, operando3text, operando4text, res1text, res2text;
	private ComboBox<String> selector;
	// cambiar esto por variables del bean Complejo
	private DoubleProperty operando1 = new SimpleDoubleProperty();
	private DoubleProperty operando2 = new SimpleDoubleProperty();
	private DoubleProperty operando3 = new SimpleDoubleProperty();
	private DoubleProperty operando4 = new SimpleDoubleProperty();
	private StringProperty operador = new SimpleStringProperty();
	private DoubleProperty res1 = new SimpleDoubleProperty();
	private DoubleProperty res2 = new SimpleDoubleProperty();
	
	private Complejo oper1;
	private Complejo oper2;
	private Complejo result;
	
	
	

	public void start(Stage primaryStage) throws Exception {
		// los campos estan ordenados desde arriba izq hacia abajo derecha
		oper1 = new Complejo();
		oper2 = new Complejo();
		result = new Complejo();
		
		
		operando1text = new TextField();
		operando1text.setPromptText("0");
		operando1text.setPrefColumnCount(4);

		operando2text = new TextField();
		operando2text.setPromptText("0");
		operando2text.setPrefColumnCount(4);

		operando3text = new TextField();
		operando3text.setPromptText("0");
		operando3text.setPrefColumnCount(4);

		operando4text = new TextField();
		operando4text.setPromptText("0");
		operando4text.setPrefColumnCount(4);

		res1text = new TextField();
		res1text.setPromptText("0");
		res1text.setPrefColumnCount(4);
		res1text.setDisable(false);

		res2text = new TextField();
		res2text.setPromptText("0");
		res2text.setPrefColumnCount(4);
		res2text.setDisable(false);

		arriba = new Label(",");
		medio = new Label(",");
		abajo = new Label(",");

		selector = new ComboBox<String>();
		selector.getItems().addAll("+", "-", "*", "/");

		// Bindings

		Bindings.bindBidirectional(operando1text.textProperty(), operando1, new NumberStringConverter());
		Bindings.bindBidirectional(operando2text.textProperty(), operando2, new NumberStringConverter());
		Bindings.bindBidirectional(operando3text.textProperty(), operando3, new NumberStringConverter());
		Bindings.bindBidirectional(operando4text.textProperty(), operando4, new NumberStringConverter());
		Bindings.bindBidirectional(res1text.textProperty(), res1, new NumberStringConverter());
		Bindings.bindBidirectional(res2text.textProperty(), res2, new NumberStringConverter());
		operador.bind(selector.getSelectionModel().selectedItemProperty());

		// Listener
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));

		//necesario para evitar que el selector apunte a null y de nullpointerexception
		selector.getSelectionModel().selectFirst();

		// Vista

		VBox izq = new VBox(5, selector);
		izq.setAlignment(Pos.CENTER);

		HBox centroarriba = new HBox(5, operando1text, arriba, operando2text);

		HBox centromedio = new HBox(5, operando3text, medio, operando4text);

		Separator sep = new Separator();

		HBox centroabajo = new HBox(5, res1text, abajo, res2text);

		VBox centro = new VBox(5, centroarriba, centromedio, sep, centroabajo);
		centro.setAlignment(Pos.CENTER);

		HBox root = new HBox(5, izq, centro);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Calculadora compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void onOperadorChanged(String nv) {
		// TODO Auto-generated method stub
		switch (nv) {
		case "+":
			res1.bind(operando1.add(operando3));
			res2.bind(operando2.add(operando4));
			break;
		case "-":
			res1.bind(operando1.subtract(operando3));
			res2.bind(operando2.subtract(operando4));
			break;
		case "*":
			res1.bind((operando1.multiply(operando3)).subtract(operando2.multiply(operando4)));
			res2.bind((operando1.multiply(operando4)).add(operando2.multiply(operando3)));
			break;
		case "/":
			res1.bind((operando1.multiply(operando3).add(operando2.multiply(operando4)))
					.divide((operando3.multiply(operando3).add(operando4.multiply(operando4)))));
			res2.bind((operando2.multiply(operando3).subtract(operando1.multiply(operando4)))
					.divide((operando3.multiply(operando3).add(operando4.multiply(operando4)))));
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
