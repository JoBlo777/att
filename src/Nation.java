
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.Styleable;
import javafx.scene.shape.Path;

public class Nation extends Path {
    String name;
    String[] neighbors;
    String continent;
    Owner owner;
    IntegerProperty troopCount;

    public Nation(String name) {
        this.name = name;
        troopCount = new SimpleIntegerProperty(0);
    }

    public void setNeighbors(String[] neighbors) {
        this.neighbors = neighbors;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        this.owner.incrementOwnedNationCount();
        GameState.getInstance().incrementAcquiredCount();
        setFill(owner.color);
    }

    public void incrementTroopCount() {
         this.troopCount.setValue(new Integer(this.getTroopCount().getValue().intValue() + 1));
    }

    public void setTroopCount(IntegerProperty troopCounter) {
        this.troopCount = troopCounter;
    }
    public boolean isUnOccupied(){
        return (owner == null);
    }

    public String getName() {
        return name;
    }

    public String[] getNeighbors() {
        return neighbors;
    }

    public Owner getOwner() {
        return owner;
    }

    public IntegerProperty getTroopCount() {
        return troopCount;
    }
}