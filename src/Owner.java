
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Owner {
    Player1(Color.BLUE),
    Player2(Color.RED),
    Unowned(Color.GREY);

    private int bonus = 0;
    private int nationCount = 0;
    private int amountReinforcement = 0;

    Paint color;
    Owner(Paint color){
        this.color=color;
    }


    public void incrementOwnedNationCount(int count){
        this.nationCount = nationCount;
    }

    public void incrementOwnedNationCount(){
        this.nationCount++;
    }
    public void reset(){
        nationCount = 0;
        bonus = 0;
    }

    public int getOwnedNationCount(){
        return this.nationCount;
    }

    public void setBonus(int bonus){
    this.bonus = bonus;
}
    public int getBonus(){
        return this.bonus;
    }

    public void setAmountReinforcement(){
        this.amountReinforcement = this.nationCount/3 + bonus;
    }
    public int getAmountReinforcement(){
        return this.amountReinforcement;
    }

}
