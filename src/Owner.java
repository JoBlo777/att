
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Owner {
    Player1(Color.BLUE),
    Player2(Color.RED),
    Unowned(Color.GREY);

    Paint color;
    Owner(Paint color){
        this.color=color;
    }


}
