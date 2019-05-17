



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreatorClass {
  static String n;
  static TextField tf1, tf2, tf3;
  public static void askdialogue() {
    Scene scene;
    Stage stage=new Stage();
    Button create=new Button("Search");
    Label indexname=new Label("City: ");
    Label typename=new Label("Country: ");
    
    tf1=new TextField("");
    tf1.setPromptText("Dlehi");
    tf2=new TextField("");
    tf2.setPromptText("India");
    
    create.setOnAction(e -> {
      Main.city=tf1.getText();
      Main.country=tf2.getText();
      stage.close();
    });

    GridPane layout=new GridPane();
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.setVgap(8);
    layout.setHgap(10);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(indexname, typename, tf1, tf2, create);

    GridPane.setConstraints(indexname, 0, 0);
    GridPane.setConstraints(tf1, 1, 0);
    GridPane.setConstraints(typename, 0, 1);
    GridPane.setConstraints(tf2, 1, 1);
    GridPane.setConstraints(create, 1, 3);

    scene=new Scene(layout, 350, 200);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("DIALOGUE");
    stage.setScene(scene);
    stage.showAndWait();
  }
}
