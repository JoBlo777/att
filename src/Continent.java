public class Continent {
    String name;
    int addValue;
    String[] nations;
    Owner ownedBy = null;

    public Continent(String name, int addValue, String[] nations) {
        this.name = name;
        this.addValue = addValue;
        this.nations = nations;
        GameState s = GameState.getInstance();
        for (String nation: nations) {
            s.getNations().get(nation).continent = this.name;
        }
    }

    public void ownesWholeContinent(){
        int count = 0;
        GameState s = GameState.getInstance();
        Owner o = null;
        for (String nation: nations) {
            o = s.getNations().get(nation).owner;
            if (o != null && o == Owner.Player1)
                count++;
            if (count == nations.length) {
                ownedBy = o;
                s.getNations().get(nation).owner.setBonus(this.addValue);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getAddValue() {
        return addValue;
    }

    public String[] getNations() {
        return nations;
    }

    public void setOwner(){
        int count = 0;
        GameState s = GameState.getInstance();
        Owner o = null;
        for (String nation: nations) {
            o = s.getNations().get(nation).owner;
            if (o != null && o == Owner.Player1)
                count++;
        }
        if (count == nations.length)
            ownedBy = o;
    }

    public boolean ownedByPlayer(Owner owner) {
        return (owner != null && owner.equals(this.ownedBy));
    }

    public boolean containsNation(String nation){
        boolean contains = false;
        for (String nation1 : nations )
            if (nation1.equals(nation)) {
                contains = true;
                break;
            }
        return contains;
    }

}