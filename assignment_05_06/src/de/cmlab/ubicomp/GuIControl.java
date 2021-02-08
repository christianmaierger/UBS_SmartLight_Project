package e.cmlab.ubicomp;

import de.cmlab.ubicomp.AndroidActuator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class GuIControl extends Application {

    boolean lightsOn;


    // launch the application
    public void start(Stage s) throws Exception
    {

                // set title for the stage
                s.setTitle("Button_Light");

                // create a button
                Button b = new Button("turn ON");
                b.setTextFill(Color.YELLOWGREEN);

                // create a Tile pane
                VBox r = new VBox();


                // create a label
                Label l = new Label("Programm is OFF");


                //Instantiating the Translate class
                Translate translate = new Translate();

                //setting the properties of the translate objects
                translate.setX(70);
                translate.setY(70);
                translate.setZ(70);

                //applying transformation to button and label
                b.getTransforms().addAll(translate);
                l.getTransforms().addAll(translate);


                // action event
                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        AndroidActuator test = new AndroidActuator();
                        if( lightsOn ) {
                            //here need to inserted our method to turn the lights off
                            test.shutDown();
                            l.setText("Programm is OFF");
                            lightsOn = false;
                            b.setText("turn ON");
                            b.setTextFill(Color.YELLOWGREEN);


                        }else {
                            //here need to inserted our method to turn the lights on
                            test.startUp();
                            l.setText("Programm is ON");
                            lightsOn = true;
                            b.setText("turn OFF");
                            b.setTextFill(Color.DARKRED);
                        }
                    }
                };

                // when button is pressed
                b.setOnAction(event);

                // add button
                r.getChildren().add(b);
                // add label
                r.getChildren().add(l);

                // create a scene
                Scene sc = new Scene(r, 200, 200);

                // set the scene
                s.setScene(sc);

                s.show();
            }

        public static void main(String[] args) {
        launch(args);
    }
}
