
import javafx.beans.property.IntegerProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;

public class PartOfNationClass extends Path implements PartOfNationIF {
    private NationIF parent;

    public PartOfNationClass(NationIF parent) {
        this.parent = parent;
        //addPartOfNation(parent);
    }

    public void setNeighbors(String[] neighbors) {
        parent.setNeighbors(neighbors);
    }

    public void setContinent(String continent) {
        parent.setContinent(continent);
    }

    public void init(){
        parent.init();
       // this.fillHelper(owner.color);
    }

    public void setOwner(Owner owner) {
     //rs    this.owner = owner;
     //rs    this.owner.incrementOwnedNationCount();
        //this.parent = owner;
        parent.setOwner(owner);
        //GameState.getInstance().updateNationsOwnedBy(owner,this);
     //rs    setFill(owner.color);
    }

    @Override
    public boolean isNeighbourOf(NationIF nation) {
        return parent.isNeighbourOf(nation);
    }

    public boolean isNeighbourOf (PartOfNationClass nation){
        return parent.isNeighbourOf(nation);
    }
    public void decrementTroopCount() {
        parent.decrementTroopCount();
    }
    public void incrementTroopCount() {
         parent.incrementTroopCount();
    }

    public void setTroopCountInt(int troopCount) {
        parent.setTroopCountInt(troopCount);
    }

    public void setTroopCount(IntegerProperty troopCounter) {
        parent.setTroopCount(troopCounter);
    }
    public boolean isUnOccupied(){
         return parent.isUnOccupied();
    }

    public String getName() {
        return parent.getName();
    }

    public String[] getNeighbors() {
        return parent.getNeighbors();
    }

    private void fillHelper(Paint p) {
    }
    public Owner getOwner() {
        return parent.getOwner();
    }
    public void addPartOfNation(NationIF partOf) {
        this.parent.addPartOfNation(partOf);
     /*   if (this.partOfNations == null){
            this.partOfNations = new ArrayList<PartOfNationClass>();
        }
        System.out.println("this name" + this.getName());
        System.out.println("this neighbors" + this.getNeighbors());
        System.out.println("this fill" + this.getFill());
        System.out.println("this owner" + this.getOwner());
        System.out.println("partOf name" + partOf.getName());
        System.out.println("partOf neighbors" + partOf.getNeighbors());
        System.out.println("partOf fill" + partOf.getFill());
        System.out.println("partOf owner" + partOf.getOwner());

        this.partOfNations.add(partOf);*/
    }
    public IntegerProperty getTroopCount() {
        return parent.getTroopCount();
    }

    }