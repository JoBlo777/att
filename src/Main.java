
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import org.*;
//import org.scenicview.ScenicView;

public class Main extends Application {

    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //rs start
        final ArrayList<String> input = new ArrayList<String>();

        //rs end

        //BorderPane root = new BorderPane();
        Pane root = new Pane();
        BorderPane bp = new BorderPane();
        ToolBar toolbar = new ToolBar();
        toolbar.setBackground(new Background(new BackgroundFill(Color.AQUA,null,null)));
        ContextMenu m = new ContextMenu(new MenuItem("help"));
        toolbar.setContextMenu(m);
        HBox statusbar = new HBox();
        statusbar.setBackground(new Background(new BackgroundFill(Color.DARKRED,null,null)));
        Node appContent = new Path();
        bp.setTop(toolbar);
        bp.setCenter(appContent);
        bp.setBottom(statusbar);

        bp.setRight(new Label("RIGHT SIDEBAR - CONTROLS"));
        bp.setLeft(new Text("LEFT SIDEBAR - CONTROLS"));
        bp.setCenter(new Text("LET´S PLAY!"));
        bp.setCenter(root);

        //root.getBackground().
        //root.setBackground(new Background(new BackgroundFill(Color.AQUA,null,null)));

        //FileReader fr = new FileReader("C:\\dld\\PK2015WS\\pk15w19p_Abschlussbeispiel\\pk15w19p_Abschlussbeispiel/world.map");
        //FileReader fr = new FileReader("C:\\dld\\PK2015WS\\pk15w19p_Abschlussbeispiel\\pk15w19p_Abschlussbeispiel/africa.map");
        //FileReader fr = new FileReader("C:\\dld\\PK2015WS\\att\\src/africa.map");
        FileReader fr = new FileReader("C:\\dld\\PK2015WS\\att\\src/world.map");
        //FileReader fr = new FileReader("C:\\dld\\PK2015WS\\att\\src/three-continents.map");
        BufferedReader br = new BufferedReader(fr);
        String zeile="";

        GameState system = GameState.getInstance();
        Map<String, NationClass> Nations = system.getNations();

        while( (zeile = br.readLine())!= null){
            System.out.println(zeile);
            String[] parts = zeile.split(" ");

            int i = 1;
            String name="";
            String nameNoSpace="";
            while(parts[i].matches("[A-Za-z]+")){
                name+=parts[i]+" ";
                i++;
            }
            nameNoSpace=name.replace(" ","");

            if(parts[0].equals("patch-of")){
                NationIF nation;
                boolean childNation = false;
                if (system.getNations().get(nameNoSpace) != null){
                    nation = new PartOfNationClass(system.getNations().get(nameNoSpace));
                    childNation = true;
                }
                else {
                    childNation = false;
                    nation = new NationClass(nameNoSpace);
                }
                MoveTo moveTo = new MoveTo();
                moveTo.setX(Double.parseDouble(parts[i]));
                moveTo.setY(Double.parseDouble(parts[i+1]));
                ((Path)nation).getElements().add(moveTo);
                i=i+2;

                for(;i<parts.length;i=i+2){
                    LineTo line = new LineTo();
                    line.setX(Double.parseDouble(parts[i]));
                    line.setY(Double.parseDouble(parts[i+1]));
                    ((Path)nation).getElements().add(line);
                }
                ((Path)nation).setFill(Owner.Unowned.color);
                //test if (nameNoSpace.equals("NorthWestTerritory"))
                //    ((Path)nation).setFill(Color.BLACK);
                ((Path)nation).setId(nameNoSpace);
                if (childNation) {
                    System.out.println("existsNation " + ((Path)nation).getId());
                    ((PartOfNationClass)nation).addPartOfNation(nation);
                }
                else {
                    System.out.println("newNation " + ((Path)nation).getId());
                    system.getNations().put(nameNoSpace, (NationClass)nation);
                    system.incrementNationCount();
                }
                //rs start
                final ContextMenu contextMenu = new ContextMenu();
                contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent e) {
                        System.out.println("showing");
                        System.out.println(system.getNations().values());
                    }
                });
                contextMenu.setOnShown(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent e) {
                        System.out.println("shown");
                    }
                });

                MenuItem item1 = new MenuItem("About");
                item1.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("About");
                    }
                });
                MenuItem item2 = new MenuItem("Preferences");
                item2.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("Preferences");
                    }
                });
                contextMenu.getItems().addAll(item1, item2);

                final TextField textField = new TextField("Type Something");
                //textField.setContextMenu(contextMenu);
                toolbar.setContextMenu(contextMenu);
                //root.getChildren().add(textField);
                ContextMenu m2 = new ContextMenu(new MenuItem("help"));
                ((Path)nation).setAccessibleHelp("HELLO");
                ((Path)nation).setOnContextMenuRequested(e -> {
                    mouseClickHandler2(e);
                });
                //rs end
                ((Path)nation).setOnMouseClicked(me -> mouseClickHandler(me));
                root.getChildren().add((Node) nation);
                //rs start
                //NationIF existsNation = system.getNations().get(nameNoSpace);
                //if (existsNation != null) {
                //    System.out.println("existsNation " + existsNation.getId());
                //    existsNation.addPartOfNation(nation);
                //}
                //else {
                //    System.out.println("newNation " + nation.getId());
                //    system.getNations().put(nameNoSpace, nation);
                //    system.incrementNationCount();
                //}
            }
            else if(parts[0].equals("capital-of")){

                double x = Double.parseDouble(parts[i]);
                double y = Double.parseDouble(parts[i+1]);

                Label count = new Label();
                count.setLayoutX(x);
                count.setLayoutY(y);
                count.textProperty().bind( system.getNations().get(nameNoSpace).getTroopCount().asString());
                count.setId(nameNoSpace);
                count.setOnMouseClicked(me -> mouseClickHandler(me));
                root.getChildren().add(count);
            }
            else if(parts[0].equals("neighbors-of")){
                NationIF nation = system.getNations().get(nameNoSpace);
                List<String> neighbors = new ArrayList<>();

                //":" Symbol überspringen
                i++;

                while(i<parts.length){
                    String neighbor="";
                    while(i<parts.length&&parts[i].matches("[A-Za-z]+")){
                        neighbor+=parts[i];
                        i++;
                    }
                    neighbors.add(neighbor);
                    i++; //Aufzählungszeichen überspringen
                }
                nation.setNeighbors(neighbors.toArray(new String[neighbors.size()]));
            }
            else if(parts[0].equals("continent")){
                List<String> nations = new ArrayList<>();
                int addValue = Integer.parseInt(parts[i]);
                //addValue und ":" überspringen
                i=i+2;

                while(i<parts.length){
                    String nation="";
                    while(i<parts.length&&parts[i].matches("[A-Za-z]+")){
                        nation+=parts[i];
                        i++;
                    }
                    nations.add(nation);
                    i++; //Aufzählungszeichen überspringen
                }

                system.getContinents().put(nameNoSpace,new Continent(nameNoSpace,addValue,nations.toArray(new String[nations.size()])));
            }
        }
        br.close();

        Label status = new Label();
        status.textProperty().bind(system.statusProperty());
        status.setLayoutX(400);
        status.setLayoutY(600);
        system.status.setValue("Landnahme");
        root.getChildren().add(status);
        System.out.println("RS" + Nations.toString()); //rfs
        System.out.println("nationcount " + system.getNationCount()); //rfs


        Scene scene = new Scene(bp,1300,650, Color.BLANCHEDALMOND);
        primaryStage.setTitle("All those Territories");
        primaryStage.setScene(scene);
        //rs debugging
        //ScenicView.show(root);
        //rs test start
        //Canvas canvas = new Canvas( 512, 512 ); // 512 x 512
        //root.getChildren().add( canvas );


        //final ArrayList<String> input = new ArrayList<String>();
/*
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

      final GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        final Sprite tank = new Sprite();
        tank.setImage("sm-cartoon-tank.png"); //rs was moneybag.png
        tank.setPosition(200, 0);

        final ArrayList<Sprite> moneybagList = new ArrayList<Sprite>();

        for (int i = 0; i < 15; i++)
        {
            Sprite moneybag = new Sprite();
            moneybag.setImage("moneybag.png");
            double px = 350 * Math.random() + 50;
            double py = 350 * Math.random() + 50;
            moneybag.setPosition(px,py);
            moneybagList.add( moneybag );
        }

        final LongValue lastNanoTime = new LongValue( System.nanoTime() );

        final IntValue score = new IntValue(0);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime;
                elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic

                tank.setVelocity(0,0);
                if (input.contains("LEFT"))
                    tank.addVelocity(-50,0);
                if (input.contains("RIGHT"))
                    tank.addVelocity(50,0);
                if (input.contains("UP"))
                    tank.addVelocity(0,-50);
                if (input.contains("DOWN"))
                    tank.addVelocity(0,50);

                tank.update(elapsedTime);

                // rs collision detection start
                ObservableList<Node> ol = root.getChildrenUnmodifiable();
                Rectangle2D r = null;
                for (int i = 0; i < ol.size()-1; i++){
                        System.out.println("id " + ol.get(i) + " found" + i + ol.size());
                        r = new Rectangle2D(ol.get(i).getLayoutX(), ol.get(i).getLayoutY(),
                            ol.get(i).getBoundsInLocal().getHeight(),
                            ol.get(i).getBoundsInLocal().getWidth());
                        if (ol.get(i) instanceof Path && tank.getBoundary().intersects(r))
                    {
                            System.out.println("in bounds");

                            ((Path)ol.get(i)).setFill(Color.TOMATO);
                            i = ol.size();
                    }
                        //System.out.println(((Path)ol.get(i)).intersects
                }
                System.out.println("in bounds - escaped");

                // rs collision detection end

                // collision detection

                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while ( moneybagIter.hasNext() )
                {
                    Sprite moneybag = moneybagIter.next();
                    if ( tank.intersects(moneybag) )
                    {
                        moneybagIter.remove();
                        score.value++;
                    }
                }

                // render

                gc.clearRect(0, 0, 512,512);
                tank.render( gc );

                for (Sprite moneybag : moneybagList )
                    moneybag.render( gc );

                String pointsText = "Cash: $" + (100 * score.value);
                gc.fillText( pointsText, 360, 36 );
                gc.strokeText( pointsText, 360, 36 );
            }
        }.start();
*/
        //rs test end
        primaryStage.show();

        controller = new Controller();
    }

    private void mouseClickHandler2(ContextMenuEvent e) {
        System.out.println("bla");
    }

        private void mouseClickHandler(MouseEvent me){

        System.out.println("mouse button pressed" + me.getButton());
        String id = ((Node)me.getSource()).getId();
        if (me.getButton() == MouseButton.PRIMARY) {
            if (GameState.getInstance().gameProgress == GameState.GameProgress.GameOver){
                System.out.println("GAMEOVER");
                MenuItem i = new MenuItem("GAMEOVER - Would you like to FIGHT again ?");
                i.setDisable(false);
                MenuItem i2 = new MenuItem("GAMEOVER - Exit ?");
                i2.setDisable(false);
                ContextMenu m = new ContextMenu();
                m.getItems().add(i);
                m.getItems().add(i2);
                //controller.incrementTroopsOnNation(id);
                i.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("GAMEOVER - Would you like to FIGHT again?");
                        GameState.resetGame();
                    }
                });
                i2.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("GAMEOVER - EXIT?");
                        Platform.exit();
                        System.exit(0);
                    }
                });
                m.show((Node)me.getSource(), me.getScreenX(), me.getScreenY());
            }
            controller.clickedOnNation(id);
        }
        else if (me.getButton() == MouseButton.SECONDARY) {
            MenuItem i = new MenuItem("add troops by 1");
            i.setDisable(false);
            ContextMenu m = new ContextMenu(i);
            //controller.incrementTroopsOnNation(id);
            m.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    System.out.println("increment troops by one");
                    controller.incrementTroopsOnNation(id);
                }
            });

            m.show((Node)me.getSource(), me.getScreenX(), me.getScreenY());
        }
        //rs end
    }

    public static void main(String[] args) {
        launch(args);
    }
}
