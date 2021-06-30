package laborai.gui.fx;

import laborai.gui.MyException;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.BstSetKTUx;
import Lab3Ordinaitė.KnygaGamyba;
import Lab3Ordinaitė.Knyga;
import Lab3Ordinaitė.Greitaveika;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import laborai.studijosktu.SortedSetADTx;

/**
 * Lab3 langas su JavaFX
 * <pre>
 *                   Lab3WindowFX (BorderPane)
 *  |-------------------------Top-------------------------|
 *  |                        menuFX                       |
 *  |------------------------Center-----------------------|
 *  |  |-----------------------------------------------|  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                    taOutput                   |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |-----------------------------------------------|  |                                           |
 *  |------------------------Bottom-----------------------|
 *  |  |~~~~~~~~~~~~~~~~~~~paneBottom~~~~~~~~~~~~~~~~~~|  |
 *  |  |                                               |  |
 *  |  |  |-------------||------------||------------|  |  |
 *  |  |  | paneButtons || paneParam1 || paneParam2 |  |  |
 *  |  |  |             ||            ||            |  |  |
 *  |  |  |-------------||------------||------------|  |  |
 *  |  |                                               |  |
 *  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
 *  |-----------------------------------------------------|
 * </pre>
 *
 * @author darius.matulis@ktu.lt
 */
public class Lab3WindowFX extends BorderPane implements EventHandler<ActionEvent> {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private static final int TF_WIDTH = 120;
    private static final int TF_WIDTH_SMALLER = 70;

    private static final double SPACING = 5.0;
    private static final Insets INSETS = new Insets(SPACING);
    private static final double SPACING_SMALLER = 2.0;
    private static final Insets INSETS_SMALLER = new Insets(SPACING_SMALLER);

    private final TextArea taOutput = new TextArea();
    private final GridPane paneBottom = new GridPane();
    private final GridPane paneParam2 = new GridPane();
    private final TextField tfDelimiter = new TextField();
    private final TextField tfInput = new TextField();
    private final ComboBox cmbTreeType = new ComboBox();
    private PanelsFX paneParam1, paneButtons;
    private MenuFX menuFX;
    private final Stage stage;

    private static SortedSetADTx<Knyga> knyguSet;
    
  SortedSetADTx<Knyga> knyguUnion;
    
    private int sizeOfInitialSubSet, sizeOfGenSet, sizeOfLeftSubSet;
    private double coef;
    private final String[] errors;

    public Lab3WindowFX(Stage stage) {
        this.stage = stage;
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
        initComponents();
    }

    private void initComponents() {
        //======================================================================
        // Sudaromas rezultatų išvedimo VBox klasės objektas, kuriame
        // talpinamas Label ir TextArea klasės objektai
        //======================================================================        
        VBox vboxTaOutput = new VBox();
        vboxTaOutput.setPadding(INSETS_SMALLER);
        VBox.setVgrow(taOutput, Priority.ALWAYS);
        vboxTaOutput.getChildren().addAll(new Label(MESSAGES.getString("border1")), taOutput);
        //======================================================================
        // Formuojamas mygtukų tinklelis (mėlynas). Naudojama klasė PanelsFX.
        //======================================================================
        paneButtons = new PanelsFX(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4"),
                    MESSAGES.getString("button5"),
                    MESSAGES.getString("button6"),
                    MESSAGES.getString("button7"),
                    MESSAGES.getString("button8")},
                2, 4);
        disableButtons(true);
        //======================================================================
        // Formuojama pirmoji parametrų lentelė (žalia). Naudojama klasė PanelsFX.
        //======================================================================
        paneParam1 = new PanelsFX(
                new String[]{
                    MESSAGES.getString("lblParam11"),
                    MESSAGES.getString("lblParam12"),
                    MESSAGES.getString("lblParam13"),
                    MESSAGES.getString("lblParam14"),
                    MESSAGES.getString("lblParam15")},
                new String[]{
                    MESSAGES.getString("tfParam11"),
                    MESSAGES.getString("tfParam12"),
                    MESSAGES.getString("tfParam13"),
                    MESSAGES.getString("tfParam14"),
                    MESSAGES.getString("tfParam15")},
                TF_WIDTH_SMALLER);
        //======================================================================
        // Formuojama antroji parametrų lentelė (gelsva).
        //======================================================================
        paneParam2.setAlignment(Pos.CENTER);
        paneParam2.setNodeOrientation(NodeOrientation.INHERIT);
        paneParam2.setVgap(SPACING);
        paneParam2.setHgap(SPACING);
        paneParam2.setPadding(INSETS);

        paneParam2.add(new Label(MESSAGES.getString("lblParam21")), 0, 0);
        paneParam2.add(new Label(MESSAGES.getString("lblParam22")), 0, 1);
        paneParam2.add(new Label(MESSAGES.getString("lblParam23")), 0, 2);

        cmbTreeType.setItems(FXCollections.observableArrayList(
                new String[]{
                    MESSAGES.getString("cmbTreeType1"),
                    MESSAGES.getString("cmbTreeType2"),
                    MESSAGES.getString("cmbTreeType3")
                }));
        cmbTreeType.setPrefWidth(TF_WIDTH);
        cmbTreeType.getSelectionModel().select(0);
        paneParam2.add(cmbTreeType, 1, 0);

        tfDelimiter.setPrefWidth(TF_WIDTH);
        tfDelimiter.setAlignment(Pos.CENTER);
        paneParam2.add(tfDelimiter, 1, 1);

        // Vėl pirmas stulpelis, tačiau plotis - 2 celės
        tfInput.setEditable(false);
        tfInput.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam2.add(tfInput, 0, 3, 2, 1);
        //======================================================================
        // Formuojamas bendras parametrų panelis (tamsiai pilkas).
        //======================================================================
        paneBottom.setPadding(INSETS);
        paneBottom.setHgap(SPACING);
        paneBottom.setVgap(SPACING);
        paneBottom.add(paneButtons, 0, 0);
        paneBottom.add(paneParam1, 1, 0);
        paneBottom.add(paneParam2, 2, 0);
        paneBottom.alignmentProperty().bind(new SimpleObjectProperty<>(Pos.CENTER));

        menuFX = new MenuFX() {
            @Override
            public void handle(ActionEvent ae) {
                Object source = ae.getSource();
                if (source.equals(menuFX.getMenus().get(0).getItems().get(0))) {
                    fileChooseMenu();
                } else if (source.equals(menuFX.getMenus().get(0).getItems().get(1))) {
                    KsFX.ounerr(taOutput, MESSAGES.getString("msg1"));
                } else if (source.equals(menuFX.getMenus().get(0).getItems().get(3))) {
                    System.exit(0);
                } else if (source.equals(menuFX.getMenus().get(1).getItems().get(0))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle(MESSAGES.getString("menuItem21"));
                    alert.setHeaderText(MESSAGES.getString("author"));
                    alert.showAndWait();
                }
            }
        };

        //======================================================================
        // Formuojamas Lab3 langas
        //======================================================================          
        // ..viršuje, centre ir apačioje talpiname objektus..
        setTop(menuFX);
        setCenter(vboxTaOutput);

        VBox vboxPaneBottom = new VBox();
        VBox.setVgrow(paneBottom, Priority.ALWAYS);
        vboxPaneBottom.getChildren().addAll(new Label(MESSAGES.getString("border2")), paneBottom);
        setBottom(vboxPaneBottom);
        appearance();

        paneButtons.getButtons().forEach((btn) -> {
            btn.setOnAction(this);
        });
        cmbTreeType.setOnAction(this);
    }

    private void appearance() {
        paneButtons.setBackground(new Background(new BackgroundFill(Color.rgb(112, 162, 255)/* Blyškiai mėlyna */, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam1.setBackground(new Background(new BackgroundFill(Color.rgb(204, 255, 204)/* Šviesiai žalia */, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam1.getTfOfTable().get(2).setEditable(false);
        paneParam1.getTfOfTable().get(2).setStyle("-fx-text-fill: red");
        paneParam1.getTfOfTable().get(4).setEditable(true);
        
        paneParam2.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 153)/* Gelsva */, CornerRadii.EMPTY, Insets.EMPTY)));
        paneBottom.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        taOutput.setFont(Font.font("Monospaced", 12));
        taOutput.setStyle("-fx-text-fill: black;");
        taOutput.setEditable(false);
    }

    @Override
    public void handle(ActionEvent ae) {
        try {
            System.gc();
            System.gc();
            System.gc();
            Region region = (Region) taOutput.lookup(".content");
            region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            Object source = ae.getSource();
            if (source instanceof Button) {
                handleButtons(source);
            } else if (source instanceof ComboBox && source.equals(cmbTreeType)) {
                disableButtons(true);
            }
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsFX.ounerr(taOutput, errors[e.getCode()] + ": " + e.getMessage());
                if (e.getCode() == 2) {
                    paneParam1.getTfOfTable().get(0).setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    paneParam1.getTfOfTable().get(1).setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            } else if (e.getCode() == 4) {
                KsFX.ounerr(taOutput, MESSAGES.getString("msg3"));
            } else {
                KsFX.ounerr(taOutput, e.getMessage());
            }
        } catch (java.lang.UnsupportedOperationException e) {
            KsFX.ounerr(taOutput, e.getLocalizedMessage());
        } catch (Exception e) {
            KsFX.ounerr(taOutput, MESSAGES.getString("error5"));
            e.printStackTrace(System.out);
        }
    }

    private void handleButtons(Object source) throws MyException {
        if (source.equals(paneButtons.getButtons().get(0))) {
            treeGeneration(null);
        } else if (source.equals(paneButtons.getButtons().get(1))) {
            treeIteration();
        } else if (source.equals(paneButtons.getButtons().get(2))) {
            treeAdd();
        } else if (source.equals(paneButtons.getButtons().get(3))) {
            treeEfficiency();
        } else if (source.equals(paneButtons.getButtons().get(4))) {
            treeRemove();
        } else if (source.equals(paneButtons.getButtons().get(5))) {

              headSet();
            
        }
        else if  (source.equals(paneButtons.getButtons().get(6)))
        {
            aukstis();
        }
         else if  (source.equals(paneButtons.getButtons().get(7)))
        {
            unionIntersection();
        }
    }

    private void treeGeneration(String filePath) throws MyException {
        // Nuskaitomi parametrai
        readTreeParameters();
        // Sukuriamas aibės objektas, priklausomai nuo medžio pasirinkimo
        // cmbTreeType objekte
        createTree();

       Knyga[] knyguArray;
        // Jei failas nenurodytas - generuojama
        if (filePath == null) {
            knyguArray = KnygaGamyba.generuotiIrIsmaisyti(sizeOfGenSet, sizeOfInitialSubSet, coef);
            paneParam1.getTfOfTable().get(2).setText(String.valueOf(sizeOfLeftSubSet));
        } else { // Skaitoma is failo
            knyguSet.load(filePath);
            knyguArray = new Knyga[knyguSet.size()];
            int i = 0;
            for (Object o : knyguSet.toArray()) {
                knyguArray[i++] = (Knyga) o;
            }
          //   Skaitant iš failo išmaišoma standartiniu Collections.shuffle metodu.
            Collections.shuffle(Arrays.asList(knyguArray), new Random());
        }

        // Išmaišyto masyvo elementai surašomi i aibę
        knyguSet.clear();
        
        for (Knyga a : knyguArray) {
            knyguSet.add(a);
        }
        // Išvedami rezultatai
        // Nustatoma, kad eilutės pradžioje neskaičiuotų išvedamų eilučių skaičiaus
        KsFX.setFormatStartOfLine(true);
        KsFX.oun(taOutput, knyguSet.toVisualizedString(tfDelimiter.getText()),
                MESSAGES.getString("msg5"));
        // Nustatoma, kad eilutės pradžioje skaičiuotų išvedamų eilučių skaičių
        KsFX.setFormatStartOfLine(false);
        disableButtons(false);
    }

    private void treeAdd() throws MyException {
        KsFX.setFormatStartOfLine(true);
        Knyga knyga = KnygaGamyba.gautiIsBazes();
        knyguSet.add(knyga);
        paneParam1.getTfOfTable().get(2).setText(String.valueOf(--sizeOfLeftSubSet));   
        KsFX.oun(taOutput, knyga, MESSAGES.getString("msg7"));
        KsFX.oun(taOutput, knyguSet.toVisualizedString(tfDelimiter.getText()));
        KsFX.setFormatStartOfLine(false);
    }

    private void treeRemove() {
        KsFX.setFormatStartOfLine(true);
        if (knyguSet.isEmpty()) {
            KsFX.ounerr(taOutput, MESSAGES.getString("msg4"));
            KsFX.oun(taOutput, knyguSet.toVisualizedString(tfDelimiter.getText()));
        } else {
            int nr = new Random().nextInt(knyguSet.size());
            Knyga knyga = (Knyga) knyguSet.toArray()[nr];
            knyguSet.remove(knyga);
            KsFX.oun(taOutput, knyga, MESSAGES.getString("msg6"));
            KsFX.oun(taOutput, knyguSet.toVisualizedString(tfDelimiter.getText()));
        }
        KsFX.setFormatStartOfLine(false);
    }

    private void treeIteration() {
        KsFX.setFormatStartOfLine(true);
        if (knyguSet.isEmpty()) {
            KsFX.ounerr(taOutput, MESSAGES.getString("msg4"));
        } else {
            KsFX.oun(taOutput, knyguSet, MESSAGES.getString("msg8"));
        }
        KsFX.setFormatStartOfLine(false);
    }

    private void treeEfficiency() {
        KsFX.setFormatStartOfLine(true);
        KsFX.oun(taOutput, "", MESSAGES.getString("msg2"));
        paneBottom.setDisable(true);
        menuFX.setDisable(true);

        Greitaveika gt = new Greitaveika();

        // Si gija paima rezultatus is greitaveikos tyrimo gijos ir isveda 
        // juos i taOutput. Gija baigia darbą kai gaunama FINISH_COMMAND     
        new Thread(() -> {
            try {
                String result;
                while (!(result = gt.getResultsLogger().take())
                        .equals(Greitaveika.FINISH_COMMAND)) {
                    KsFX.ou(taOutput, result);
                    gt.getSemaphore().release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            gt.getSemaphore().release();
            paneBottom.setDisable(false);
            menuFX.setDisable(false);
        }, "Greitaveikos_rezultatu_gija").start();

        //Sioje gijoje atliekamas greitaveikos tyrimas
        new Thread(() -> gt.pradetiTyrima(), "Greitaveikos_tyrimo_gija").start();
    }

    private void readTreeParameters() throws MyException {
        // Truputėlis kosmetikos..
        for (int i = 0; i < 2; i++) {
            paneParam1.getTfOfTable().get(i).
                    setStyle("-fx-control-inner-background: white; ");
            paneParam1.getTfOfTable().get(i).applyCss();
        }
        // Nuskaitomos parametrų reiksmės. Jei konvertuojant is String
        // įvyksta klaida, sugeneruojama NumberFormatException situacija. Tam, kad
        // atskirti kuriame JTextfield'e ivyko klaida, panaudojama nuosava
        // situacija MyException
        int i = 0;
        try {
            // Pakeitimas (replace) tam, kad sukelti situaciją esant
            // neigiamam skaičiui        
            sizeOfGenSet = Integer.valueOf(paneParam1.getParametersOfTable().get(i).replace("-", "x"));
            sizeOfInitialSubSet = Integer.valueOf(paneParam1.getParametersOfTable().get(++i).replace("-", "x"));
            sizeOfLeftSubSet = sizeOfGenSet - sizeOfInitialSubSet;
            ++i;
            coef = Double.valueOf(paneParam1.getParametersOfTable().get(++i).replace("-", "x"));
        } catch (NumberFormatException e) {
            // Galima ir taip: pagauti exception'ą ir vėl mesti
            throw new MyException(paneParam1.getParametersOfTable().get(i), e, i);
        }
    }

    private void createTree() throws MyException {
        switch (cmbTreeType.getSelectionModel().getSelectedIndex()) {
            case 0:
                knyguSet = new BstSetKTUx(new Knyga());
                break;
            case 1:
                knyguSet = new AvlSetKTUx(new Knyga());
                break;
            default:
                disableButtons(true);
                throw new MyException(MESSAGES.getString("msg1"));
        }
    }

    protected void disableButtons(boolean disable) {
        for (int i : new int[]{1, 2, 4, 5, 6, 7}) {
            if (i < paneButtons.getButtons().size() && paneButtons.getButtons().get(i) != null) {
                paneButtons.getButtons().get(i).setDisable(disable);
            }
        }
    }

    private void fileChooseMenu() throws MyException {
        FileChooser fc = new FileChooser();
        // Papildoma mūsų sukurtais filtrais
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fc.setTitle((MESSAGES.getString("menuItem11")));
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            treeGeneration(file.getAbsolutePath());
        }
    }

    public static void createAndShowFXGUI(Stage stage) {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        Lab3WindowFX window = new Lab3WindowFX(stage);
        stage.setScene(new Scene(window, 1100, 650));
        stage.setTitle(MESSAGES.getString("title"));
        stage.getIcons().add(new Image("file:" + MESSAGES.getString("icon")));
        stage.show();
    }
    
    public void headSet()
    {
     Knyga [] knygaArray;
     
     knygaArray=new Knyga [knyguSet.size()];
     int i=0;
     for (Object a : knyguSet.toArray())
     {
         knygaArray[i++]=(Knyga) a;
     }
     int nr = Integer.parseInt(paneParam1.getParametersOfTable().get(4));
     if(nr>=knyguSet.size())
         KsFX.oun(taOutput, "Pasirinktas skaičius per didelis");
        else KsFX.oun(taOutput,  knyguSet.headSet(knygaArray[nr]),
                " Grąžinamas aibės poaibis iki elemento:");
     
    }
    public void aukstis()
    {
        KsFX.oun(taOutput, "Pradinio medžio aukštis: "+ knyguSet.height());

    }
    
    public void unionIntersection() 
    {
        SortedSetADTx<Knyga> knyguAibe1 = new BstSetKTUx(new Knyga());
        SortedSetADTx<Knyga> knyguAibe2 = new BstSetKTUx(new Knyga());
        Knyga [] knyguArray=KnygaGamyba.generuoti(3);
        Knyga [] knyguArray2=KnygaGamyba.generuoti(4);
        Knyga a4 = new Knyga("Antanas-Škėma", "Drobulė", "Romanas", 2005, 500, 15);
        Knyga a5 = new Knyga("Salomėja-Nėris", "Žiema", "Eilėraščiai", 2001, 800, 85);
       
        for (Knyga a : knyguArray) {
            knyguAibe1.add(a);
            knyguAibe1.add(a4);
            knyguAibe1.add(a5);
        }
        KsFX.oun(taOutput, "Pirma aibė: " + knyguAibe1.toString());
        for (Knyga a : knyguArray2) {
            knyguAibe2.add(a);
            knyguAibe2.add(a4);
            knyguAibe2.add(a5);
        }
        KsFX.oun(taOutput, "Antra aibė: " + knyguAibe2.toString());
        
        knyguSet.clear();
        for(Knyga a : knyguAibe1)
        {
           if(knyguAibe2.contains(a))
               knyguSet.add(a);
        }
        if(knyguSet.size()>0)
        { 
            KsFX.oun(taOutput, "Sankirta: "+ knyguSet.toString());
        }
        else
             KsFX.oun(taOutput, "Sankirta neegzistuoja");

        knyguSet.clear();
         for (Knyga a : knyguAibe1) {
            for (Knyga a2 : knyguAibe2)
            {
              knyguSet.add(a);
              knyguSet.add(a2);
            } 
         }
       KsFX.oun(taOutput, "Sąjunga: " + knyguSet.toString());
     }
          
    }

