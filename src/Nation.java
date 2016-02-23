
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.Styleable;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;

import java.util.ArrayList;

public class Nation extends Path {
    String name;
    String[] neighbors;
    ArrayList<Nation> partOfNations;
    String continent;
    Owner owner;
    IntegerProperty troopCount;

    public Nation(String name) {
        this.name = name;
        this.owner = Owner.Unowned;
        this.troopCount = new SimpleIntegerProperty(0);
    }

    public void setNeighbors(String[] neighbors) {
        this.neighbors = neighbors;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void init(){
        this.owner = Owner.Unowned;
        this.troopCount.setValue(0);
        this.fillHelper(owner.color);
    }

    public void setOwner(Owner owner) {
     //rs    this.owner = owner;
     //rs    this.owner.incrementOwnedNationCount();
        GameState.getInstance().updateNationsOwnedBy(owner,this);
     //rs    setFill(owner.color);
    }

    public boolean isNeighbourOf (Nation nation){
        for (String nationId: neighbors){
            if (nationId != null && nationId.equals(nation.getName()))
                return true;
        }
        return false;
    }
    public void decrementTroopCount() {
        this.troopCount.setValue(new Integer(this.getTroopCount().getValue().intValue() - 1));
    }
    public void incrementTroopCount() {
         this.troopCount.setValue(new Integer(this.getTroopCount().getValue().intValue() + 1));
    }

    public void setTroopCountInt(int troopCount) {
        this.troopCount.setValue(troopCount);
    }

    public void setTroopCount(IntegerProperty troopCounter) {
        this.troopCount.setValue(troopCounter.getValue().intValue());
    }
    public boolean isUnOccupied(){
        return (owner == Owner.Unowned);
    }

    public String getName() {
        return name;
    }

    public String[] getNeighbors() {
        return neighbors;
    }

    private void fillHelper(Paint p) {
        for (Nation n: this.partOfNations) {
            n.setFill(p);
        }
    }
    public Owner getOwner() {
        return owner;
    }
    public void addPartOfNation(Nation partOf) {
        if (this.partOfNations == null){
            this.partOfNations = new ArrayList<Nation>();
        }
        this.partOfNations.add(partOf);
    }
    public IntegerProperty getTroopCount() {
        return troopCount;
    }
}