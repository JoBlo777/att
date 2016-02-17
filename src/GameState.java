import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class GameState {
    private static GameState system;
    private Map<String, Nation> nations;
    private Map<String, Continent> continents;
    private boolean isPlayerOnesTurn = true;
    public StringProperty status;
    private int nationCount;
    private int acquiredCount;
    private GameProgress gameProgress;

    public enum GameProgress {
        Landnahme(Color.BLUE),
        Verstärkung(Color.RED),
        Bewegen(Color.GREY),
        Angreifen(Color.GREEN),
        GameOver(Color.YELLOW);

        Paint color;
        GameProgress(Paint color){
            this.color=color;
        }
    }

    private GameState(){
        status = new SimpleStringProperty("Status");
        gameProgress = GameProgress.Landnahme;
    }

    public static GameState getInstance(){
        if(system==null){
            system=new GameState();
        }
        return system;
    }

    public void incrementAcquiredCount(){
        this.acquiredCount++;
    }


    public void incrementNationCount(){
        this.nationCount++;
    }

    public boolean allClicked(){
        return (this.nationCount == this.acquiredCount);
    }

    public int getAcquiredCount(){
        return this.acquiredCount;
    }

    public int getNationCount(){
        return this.nationCount;
    }
    public Map<String, Nation> getNations() {
        if(nations==null) nations = new HashMap<>();
        return nations;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public Map<String, Continent> getContinents() {
        if(continents == null) continents=new HashMap<>();
        return continents;
    }

    public void setGameProgress(GameProgress p) {
        gameProgress = p;
        status.setValue(p.toString());
    }

    public Nation getNextUnoccupied() {
        Iterator<Nation> i = nations.values().iterator();
        while (i.hasNext()){
            Nation n = i.next();
            if (n.isUnOccupied())
                return n;
        }
        return null;
    }

    public void assignContinentBonus(String nationID) {
        Continent c = continents.get(nations.get(nationID).continent);
        c.setOwner();
        if (c.ownedBy != null)
            c.ownedBy.setBonus(c.getAddValue());
    }
    public boolean isPlayerOnesTurn() {
        return isPlayerOnesTurn;
    }

    public void setPlayerOnesTurn(boolean playerOnesTurn) {
        isPlayerOnesTurn = playerOnesTurn;
    }
}