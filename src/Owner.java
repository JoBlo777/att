
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Owner {
    Player1(Color.BLUE),
    Player2(Color.RED),
    Unowned(Color.GREY);

    private int bonus;
    private int nationCount;
    private int amountReinforcement;

    Paint color;
    Owner(Paint color){
        System.out.println("owner created");
        this.color=color;
        this.bonus = 0;
    }

//    public void incrementOwnedNationCount(int count){
//        this.nationCount = this.nationCount + nationCount;
//    }

    public void incrementOwnedNationCount(){
        this.nationCount++;
    }

    public void decrementOwnedNationCount(){
        this.nationCount--;
    }
    public void reset(){
        nationCount = 0;
        amountReinforcement = 0;
        bonus = 0;
    }

    public int getOwnedNationCount(){
        return nationCount;
    }

    public void setBonus(int bonus){
       System.out.println("bonus set ");
       System.out.println("before set " + bonus);
       this.bonus = this.bonus + bonus;
       System.out.println("bonus "+ bonus);
    }
    public int getBonus(){
        return this.bonus;
    }

    public void setAmountReinforcement(){
        amountReinforcement = nationCount/3 + bonus;
    }
    public int getAmountReinforcement(){
        return amountReinforcement;
    }

}
