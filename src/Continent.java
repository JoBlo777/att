public class Continent {
    String name;
    int addValue;
    String[] nations;

    public Continent(String name, int addValue, String[] nations) {
        this.name = name;
        this.addValue = addValue;
        this.nations = nations;
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
}