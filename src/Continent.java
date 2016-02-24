public class Continent {
    String name;
    int addValue;
    String[] nations;
    Owner ownedBy = Owner.Unowned;

    public Continent(String name, int addValue, String[] nations) {
        this.name = name;
        this.addValue = addValue;
        this.nations = nations;
        GameState s = GameState.getInstance();
        for (String nation: nations) {
            s.getNations().get(nation).continent = this.name;
        }
    }

/*    public void ownsWholeContinent(){
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
*/
    public String getName() {
        return name;
    }

    public int getAddValue() {
        return addValue;
    }

    public String[] getNations() {
        return nations;
    }

    public void setOwner() {
        int countO1 = 0;
        int countO2 = 0;

        GameState s = GameState.getInstance();

        Owner o;

        for (String nation : nations) {
            o = s.getNations().get(nation).owner;
            if (o != null) {
                if (o == Owner.Player1)
                    countO1++;
                if (o == Owner.Player2)
                    countO2++;
            }
            if (countO2 == nations.length) {
                ownedBy = Owner.Player2;
                ownedBy.setBonus(this.getAddValue());
                System.out.println(ownedBy.getBonus());
            }
            else if (countO1 == nations.length) {
                ownedBy = Owner.Player1;
                ownedBy.setBonus(this.getAddValue());
                System.out.println(ownedBy.getBonus());
            }
        }
    }
    public boolean ownedByPlayer(Owner owner) {
        return (owner != null && owner.equals(this.ownedBy));
    }

    public Owner getOwner() {
        return ownedBy;
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