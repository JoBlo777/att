import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;
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
    public GameProgress gameProgress;
    public Nation[] attackNationTuple = new Nation[2];
    public int attackNationTupleCount = 0;
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



    public boolean troopMoveSuccessful (Nation homeland, Nation destinantion, int howMany) {

        int canBeMoved = homeland.troopCount.getValue() - 1;

        if (howMany > canBeMoved) {
            return false;
        }
        else {
            while (howMany != 0) {
                homeland.decrementTroopCount();
                destinantion.incrementTroopCount();
                howMany--;
            }
            return true;
        }

    }
    public boolean isAttackSuccesful (Nation attacker, Nation defender){
        int[]attackerCubes;
        int[]defenderCubes;


//Abchecken wieviele Armeen angreifen können, es wird immer das Maximum hergenommen, dann wird ein Array mit Würfelzahlen erstellt
        if (attacker.troopCount.getValue() > 3) {
            int dice1 = (int) (Math.random() * 5 + 1);
            int dice2 = (int) (Math.random() * 5 + 1);
            int dice3 = (int) (Math.random() * 5 + 1);

            attackerCubes = new int[]{dice1, dice2, dice3};
        }
        else if (attacker.troopCount.getValue() == 3) {
            int dice1 = (int) (Math.random() * 5 + 1);
            int dice2 = (int) (Math.random() * 5 + 1);
            attackerCubes = new int[]{dice1, dice2};
        }
        else if (attacker.troopCount.getValue() == 2) {
            int dice1 = (int) (Math.random() * 5 + 1);
            attackerCubes = new int[]{dice1};
        }
        else {
            return false;
        }

        if (defender.troopCount.getValue() >= 2) {
            int dice1 = (int) (Math.random() * 5 + 1);
            int dice2 = (int) (Math.random() * 5 + 1);

            defenderCubes = new int[]{dice1, dice2};
        }
        else {
            int dice1 = (int) (Math.random() * 5 + 1);

            defenderCubes = new int[]{dice1};
        }

//Die Arrays werden in absteigender reihenfolge sortiert
        Arrays.sort(attackerCubes);

        for (int i = 0, j = attackerCubes.length - 1; i < j; i++, j--) {
            int tmp = attackerCubes[i];
            attackerCubes[i] = attackerCubes[j];
            attackerCubes[j] = tmp;
        }
        Arrays.sort(defenderCubes);

        for (int i = 0, j = defenderCubes.length - 1; i < j; i++, j--) {
            int tmp = defenderCubes[i];
            defenderCubes[i] = defenderCubes[j];
            defenderCubes[j] = tmp;
        }


//die höchsten beiden Würfelzahlen werden verglichen, es wird festgestellt, ob der Angriff erfolgreich war oder nicht
        boolean win;

        if (attackerCubes.length >= 2 && defenderCubes.length == 1) {
            if (attackerCubes[0] >= defenderCubes[0]) {
                defender.setTroopCount(attacker.troopCount);
                defender.setOwner(attacker.owner);
                win = true;
            }
            else {
                attacker.decrementTroopCount();
                win = false;
            }
        }

        else if (attackerCubes.length == 1 && defenderCubes.length == 1) {
            if (attackerCubes[0] >= defenderCubes[0]) {
                defender.setTroopCount(attacker.troopCount);
                defender.setOwner(attacker.owner);
                win = true;
            }
            else {
                attacker.decrementTroopCount();
                win = false;
            }
        }

        else if (attackerCubes.length == 1 && defenderCubes.length == 2) {
            if (attackerCubes[0] >= defenderCubes[0]) {
                defender.decrementTroopCount();
                win = false;
            } else {
                attacker.decrementTroopCount();
                win = false;
            }
        }

        else {
            if (attackerCubes[0] >= defenderCubes[0]) {
                defender.decrementTroopCount();
                if (attackerCubes[1] >= defenderCubes[1]) {
                    defender.setTroopCount(attacker.troopCount);
                    defender.setOwner(attacker.owner);
                    win = true;
                } else {
                    attacker.decrementTroopCount();
                    win = false;
                }
            } else {
                attacker.decrementTroopCount();
                win = false;
                if (attackerCubes[1] >= defenderCubes[1]) {
                    defender.decrementTroopCount();
                } else {
                    attacker.decrementTroopCount();
                }
            }
        }
        return win;
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