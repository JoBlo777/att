import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class Controller {
    public Controller(){

    }

    public void incrementTroopsOnNation(String nationID){
        GameState s = GameState.getInstance();
        if (s.getNations().get(nationID).getOwner() == Owner.Player1)
            s.getNations().get(nationID).incrementTroopCount();
    }

    public void clickedOnNation(String nationID){
        System.out.println("rrr" + nationID);
       //rs
        GameState s = GameState.getInstance();
        Nation n = s.getNations().get(nationID);
        if (n.isUnOccupied())
            n.setOwner(Owner.Player1);
        n = s.getNextUnoccupied();
        if (n != null)
            n.setOwner(Owner.Player2);
//        Continent c = s.getContinents().get(n.name);
//        c.setOwner();
        System.out.println(s.getAcquiredCount());
        if (s.allClicked())
            s.setGameProgress(GameState.GameProgress.Verstärkung);

        s.assignContinentBonus(nationID);

        //       if (s.isPlayerOnesTurn()) {
 //           s.setPlayerOnesTurn(false);
            System.out.println("sss" + s.getNations().get(nationID).getOwner());
 //           s.getNations().get(nationID).setOwner(Owner.Player1);
            System.out.println("sss" + s.getNations().get(nationID).getOwner());
            System.out.println(s.getContinents());
            System.out.println(s.getStatus());
 //       }
    }
}