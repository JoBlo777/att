
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
     //rs    this.owner = owner;
     //rs    this.owner.incrementOwnedNationCount();
        GameState.getInstance().updateNationsOwnedBy(owner,this);
     //rs    setFill(owner.color);
    }

    public boolean isNeighbourOf (Nation nation){
        for (String nationId: neighbors){
            if (nationId.equals(nation.getName()))
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