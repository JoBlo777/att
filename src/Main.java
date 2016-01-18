
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,null,null)));

        FileReader fr = new FileReader("C:\\dld\\PK2015WS\\pk15w19p_Abschlussbeispiel\\pk15w19p_Abschlussbeispiel/world.map");
        BufferedReader br = new BufferedReader(fr);
        String zeile="";

        DataSystem system = DataSystem.getInstance();
        Map<String,Nation> Nations = system.getNations();

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

                Path path = new Path();
                MoveTo moveTo = new MoveTo();
                moveTo.setX(Double.parseDouble(parts[i]));
                moveTo.setY(Double.parseDouble(parts[i+1]));
                path.getElements().add(moveTo);
                i=i+2;

                for(;i<parts.length;i=i+2){
                    LineTo line = new LineTo();
                    line.setX(Double.parseDouble(parts[i]));
                    line.setY(Double.parseDouble(parts[i+1]));
                    path.getElements().add(line);
                }
                path.setFill(Owner.Unowned.color);
                path.setId(nameNoSpace);
                path.setOnMouseClicked(me -> mouseClickHandler(me));
                root.getChildren().add(path);

            }
            else if(parts[0].equals("capital-of")){
                Nation nation = new Nation(name);

                double x = Double.parseDouble(parts[i]);
                double y = Double.parseDouble(parts[i+1]);

                Label count = new Label();
                count.setLayoutX(x);
                count.setLayoutY(y);
                count.textProperty().bind(nation.getTruppCounter().asString());
                count.setId(nameNoSpace);
                count.setOnMouseClicked(me -> mouseClickHandler(me));
                system.getNations().put(nameNoSpace,nation);
                root.getChildren().add(count);
            }
            else if(parts[0].equals("neighbors-of")){
                Nation nation = system.getNations().get(nameNoSpace);
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

                system.getContinents().put(nameNoSpace,new Continent(name,addValue,nations.toArray(new String[nations.size()])));
            }
        }
        br.close();

        Label status = new Label();
        status.textProperty().bind(system.statusProperty());
        status.setLayoutX(400);
        status.setLayoutY(600);
        root.getChildren().add(status);
        System.out.println("RS" + Nations.toString()); //rfs


        Scene scene = new Scene(root,1300,650);
        primaryStage.setTitle("All those Territories");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller = new Controller();
    }

    private void mouseClickHandler(MouseEvent me){
//original        controller.clickedOnNation(((Node)me.getSource()).getId());
        //rs start
        //((Node)me.
        String id = ((Node)me.getSource()).getId();
        System.out.println(id);
        ObservableList<Node> ol = ((Node)me.getSource()).getScene().getRoot().getChildrenUnmodifiable();
        for (int i = 0; i < ol.size()-1; i++){
            if (ol.get(i).getId().equals(id)) {
                System.out.println("id " + id + " found");
                ((Path)ol.get(i)).setFill(Owner.Player1.color);
                ((Path)ol.get(i+1)).setFill(Owner.Player2.color);
                //p.setFill(Owner.Player1.color);
                //((Path)ol.get(i)).setFill();
                break;
            }
        }
        //System.out.println("zzz" + (Path)ol.get(0));

        controller.clickedOnNation(((Node)me.getSource()).getId());
        //rs end
    }

    public static void main(String[] args) {
        launch(args);
    }
}
