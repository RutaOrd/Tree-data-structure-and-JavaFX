/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Ordinaitė;
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import laborai.gui.fx.Lab3WindowFX;

/**
 *
 * @author rordi
 */
public class GUIFX extends Application {
      public static void main(String [] args) {
        GUIFX.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
        KnygaTestai.aibėsTestas();
        Lab3WindowFX.createAndShowFXGUI(primaryStage);
    }
    
    
}
