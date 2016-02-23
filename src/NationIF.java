import javafx.beans.property.IntegerProperty;

/**
 * Created by Hugo on 23.02.2016.
 */
public interface NationIF {
    void setNeighbors(String[] neighbors);

    void setContinent(String continent);

    void init();

    void setOwner(Owner owner);

    boolean isNeighbourOf(NationIF nation);

    void decrementTroopCount();

    void incrementTroopCount();

    void setTroopCountInt(int troopCount);

    void setTroopCount(IntegerProperty troopCounter);

    boolean isUnOccupied();

    String getName();

    String[] getNeighbors();

    Owner getOwner();

    void addPartOfNation(NationIF partOf);

    IntegerProperty getTroopCount();
}
