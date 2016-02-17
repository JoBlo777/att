
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Owner {
    Player1(Color.BLUE),
    Player2(Color.RED),
    Unowned(Color.GREY);

    private int bonus = 0;
    private int nationCount = 0;

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

    public int getOwnedNationCount(){
        return this.nationCount;
    }

    public void setBonus(int bonus){
    this.bonus = bonus;
}
public int getBonus(){
        return this.bonus;
    }
}
