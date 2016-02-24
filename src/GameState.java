import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.*;

public class GameState {
    private static GameState system;
    public Map<String, NationClass> nations;
    private Map<String, Continent> continents;
    private boolean isPlayerOnesTurn = true;
    public StringProperty status;
    private int nationCount;
    private int acquiredCount;
    public GameProgress gameProgress;
    public NationClass[] attackNationTuple = new NationClass[2];
    public int attackNationTupleCount = 0;
    private HashMap<Owner,ArrayList<NationClass>> ownedNationsByPlayer;

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
        ownedNationsByPlayer = new HashMap<Owner,ArrayList<NationClass>>();
        ownedNationsByPlayer.put(Owner.Player1,null);
        ownedNationsByPlayer.put(Owner.Player2,null);
    }

    public static GameState getInstance(){
        if(system==null){
            system=new GameState();
        }
        return system;
    }

    private void reset(){
        isPlayerOnesTurn = true;
        status = new SimpleStringProperty("Status");
        acquiredCount = 0;
        gameProgress = GameProgress.Landnahme;
        attackNationTuple = new NationClass[2];
        attackNationTupleCount = 0;
        ownedNationsByPlayer = new HashMap<Owner,ArrayList<NationClass>>();
        ownedNationsByPlayer.put(Owner.Player1,null);
        ownedNationsByPlayer.put(Owner.Player2,null);
        Iterator<NationClass> i = nations.values().iterator();
        NationIF nation;
        while (i.hasNext()){
            nation = i.next();
            nation.init();
            nation.getOwner().reset();
        }
    }

    public static void resetGame(){
        system.reset();
    }

    public void incrementAcquiredCount(){
        this.acquiredCount++;
    }

    public void updateNationsOwnedBy(Owner newOwner, NationClass nation){
        if (nation.getOwner() != null &&
            nation.getOwner().equals(newOwner))
        return;
        if (nation.getOwner() == Owner.Unowned) {
            //rs nation.setOwner(newOwner);
            //rs stat
            nation.owner = newOwner;
            newOwner.incrementOwnedNationCount();
            //GameState.getInstance().updateNationsOwnedBy(owner,this);
            nation.fillHelper(newOwner.color);
            // rs end
            ArrayList<NationClass> nations = ownedNationsByPlayer.get(newOwner);
            if (nations == null) {
                nations = new ArrayList<NationClass>();
            }
            nations.add(nation);
            this.ownedNationsByPlayer.put(newOwner, nations);
        }
        else if (!nation.getOwner().equals(newOwner)){
            ArrayList<NationClass> nations = ownedNationsByPlayer.get(nation.getOwner());
            for (int i = 0; i < nations.size(); i++) {
                if (nations.get(i).getName().equals(nation.getName())) {
                    nations.remove(i);
                    ownedNationsByPlayer.get(newOwner).add(nation);
                    //rs nation.setOwner(newOwner);
                    //rs stat
                    nation.owner = newOwner;
                    newOwner.incrementOwnedNationCount();
                    //GameState.getInstance().updateNationsOwnedBy(owner,this);
                    nation.fillHelper(newOwner.color);
                    // rs end
                    break;
                }
            }
        }
/*
        Owner oldOwner;
        if (newOwner == Owner.Player1)
            oldOwner = Owner.Player2;
        else
            oldOwner = Owner.Player1;

        if (this.ownedNationsByPlayer.get(newOwner) != null) {
            ArrayList<NationIF> nations = ownedNationsByPlayer.get(newOwner);
            for (int i = 0; i < nations.size(); i++) {
                if (nations.get(i).getName().equals(nation.getName())) {
                    nations.remove(i);
                    ownedNationsByPlayer.get(newOwner).add(nation);
                }
            }

            this.ownedNationsByPlayer.get(newOwner).add(nation);
        }
        else {
            ArrayList<NationIF> nations = new ArrayList<>();
            nations.add(nation);
            this.ownedNationsByPlayer.put(newOwner,nations);
        }
        */
        System.out.println(nation.getElements().size());
        incrementAcquiredCount();
    }

    public String printNationsOwnedBy(Owner owner){
        String o = "";
        String[] s = getNationsOwnedBy(owner);
        for (String value: s) {
            o += " " + value;
        }
        return o;
    }

    public ArrayList<NationClass> getNationsOwnedByB(Owner owner){
        if (ownedNationsByPlayer.isEmpty() ||
                ownedNationsByPlayer.containsKey(owner) == false)
            return null;
        return ownedNationsByPlayer.get(owner);
    }
    public String[] getNationsOwnedBy(Owner owner){
        if (ownedNationsByPlayer.isEmpty() ||
            ownedNationsByPlayer.containsKey(owner) == false)
            return null;
        ArrayList<NationClass> nations = ownedNationsByPlayer.get(owner);
        String[] nationsAsString = new String[nations.size()];
        for (int i = 0; i <nations.size(); i++) {
            nationsAsString[i] = nations.get(i).getName();
        }
        return nationsAsString;
    }
    public void incrementNationCount(){
        this.nationCount++;
    }

    public boolean allClicked(){
        return (this.nationCount == this.acquiredCount);
    }

    public boolean troopMoveSuccessful (NationClass homeland, NationClass destinantion, int howMany) {

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

    public boolean isGameOver() {
        return (ownedNationsByPlayer.get(Owner.Player1).isEmpty() ||
                ownedNationsByPlayer.get(Owner.Player2).isEmpty());
    }

    public boolean isAttackSuccesful (NationClass attacker, NationClass defender){
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
                int attackingTroops = attackerCubes.length;
                defender.setTroopCountInt(attackingTroops);
                while (attackingTroops != 0) {
                    attacker.decrementTroopCount();
                    attackingTroops--;
                }
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
                int attackingTroops = attackerCubes.length;
                defender.setTroopCountInt(attackingTroops);
                while (attackingTroops != 0) {
                    attacker.decrementTroopCount();
                    attackingTroops--;
                }
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
                    int attackingTroops = attackerCubes.length;
                    defender.setTroopCountInt(attackingTroops);
                    while (attackingTroops != 0) {
                        attacker.decrementTroopCount();
                        attackingTroops--;
                    }
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
    public Map<String, NationClass> getNations() {
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

    public NationIF getNextUnoccupied() {
        Iterator<NationClass> i = nations.values().iterator();
        while (i.hasNext()){
            NationIF n = i.next();
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