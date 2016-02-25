import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;

import java.util.Iterator;
import java.util.LinkedList;

public class NationClass extends Path implements NationIF {
    String name;
    String[] neighbors;
    LinkedList<NationIF> partOfNations;
    String continent;
    Owner owner;
    IntegerProperty troopCount;

    public NationClass(String name) {
        this.name = name;
        this.owner = Owner.Unowned;
        this.troopCount = new SimpleIntegerProperty(0);
    }
    //@Override
    public void setNeighbors(String[] neighbors) {
        this.neighbors = neighbors;
    }

    //@Override
    public void setContinent(String continent) {
        this.continent = continent;
    }

    //@Override
    public void init(){
        this.owner = Owner.Unowned;
        this.troopCount.setValue(0);
        this.fillHelper(owner.color);
    }

    //@Override
    public void setOwner(Owner owner) {
     //rs    this.owner = owner;
     //rs    this.owner.incrementOwnedNationCount();
        GameState.getInstance().updateNationsOwnedBy(owner,this);
     //rs    setFill(owner.color);
    }

    @Override
    public boolean isNeighbourOf(NationIF nation) {
        for (String nationId: neighbors){
            if (nationId != null && nationId.equals(nation.getName()))
                return true;
        }
        return false;
    }

    //@Override
    public boolean isNeighbourOf(NationClass nation){
        String s;
        boolean neighborOf = false;
        if (neighbors == null) {
            System.out.println(this.getName() + "NULL neighbor");
            return true;
        }
        for (int i = 0; i < neighbors.length; i++){
            if (!neighbors[i].isEmpty() &&
                neighbors[i].equals(nation)){
                   neighborOf = true;
                   break;
            }
            i++;
        }
        return false;
    }
    //@Override
    public void decrementTroopCount() {
        this.troopCount.setValue(new Integer(this.getTroopCount().getValue().intValue() - 1));
    }
    //@Override
    public void incrementTroopCount() {
         this.troopCount.setValue(new Integer(this.getTroopCount().getValue().intValue() + 1));
    }

    //@Override
    public void setTroopCountInt(int troopCount) {
        this.troopCount.setValue(this.troopCount.getValue().intValue() + troopCount);
    }

    //@Override
    public void setTroopCount(IntegerProperty troopCounter) {
        this.troopCount.setValue(troopCounter.getValue().intValue());
    }
    //@Override
    public boolean isUnOccupied(){
        return (owner == Owner.Unowned);
    }

    //@Override
    public String getName() {
        return name;
    }

    //@Override
    public String[] getNeighbors() {
        return neighbors;
    }

    public void fillHelper(Paint p) {
        this.setFill(p);
        if (this.partOfNations != null) {
            Iterator<NationIF> itr = this.partOfNations.iterator();
            while (itr.hasNext()) {
                ((Path) itr.next()).setFill(p);
            }
        }
    }
    //@Override
    public Owner getOwner() {
        return owner;
    }
    //@Override

    //@Override
    public IntegerProperty getTroopCount() {
        return troopCount;
    }

    public void addPartOfNation(NationIF nation) {
        if (this.partOfNations == null)
            this.partOfNations = new LinkedList<NationIF>();
        this.partOfNations.add(nation);
    }
}