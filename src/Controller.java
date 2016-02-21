import javafx.beans.property.IntegerProperty;
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
        GameState s = GameState.getInstance();
        Nation n = s.getNations().get(nationID);

        switch(s.gameProgress) {
            case Landnahme: {
                System.out.println("rrr" + nationID);
                if (n.isUnOccupied())
                    n.setOwner(Owner.Player1);
                n = s.getNextUnoccupied();
                if (n != null) {
                    n.setOwner(Owner.Player2);
                    n.incrementTroopCount(); // damit er was zum verteidugen hat
                    n.incrementTroopCount(); // damit er was zum verteidugen hat
                }
//        Continent c = s.getContinents().get(n.name);
//        c.setOwner();
                System.out.println(s.getAcquiredCount());
                if (s.allClicked())
 //test                   s.gameProgress = GameState.GameProgress.Verstärkung;
                s.gameProgress = GameState.GameProgress.Angreifen; // test

                s.assignContinentBonus(nationID);

                //       if (s.isPlayerOnesTurn()) {
                //           s.setPlayerOnesTurn(false);
                System.out.println("sss" + s.getNations().get(nationID).getOwner());
                //           s.getNations().get(nationID).setOwner(Owner.Player1);
                System.out.println("sss" + s.getNations().get(nationID).getOwner());
                System.out.println(s.getContinents());
                System.out.println(s.getStatus());
                break;
                //       }
            }
             case Verstärkung: {

                break;
            }
            case Bewegen: {
                break;
            }
            case Angreifen: {
                if (s.attackNationTupleCount == 0) {
                    s.attackNationTuple[0] = s.getNations().get(nationID);
                    s.attackNationTupleCount++;
                }
                else if (s.attackNationTupleCount == 1) {
                    s.attackNationTuple[1] = s.getNations().get(nationID);
                    if (s.isAttackSuccesful(s.attackNationTuple[0], s.attackNationTuple[1])) {
                        System.out.println("CONQUERED");
                        s.attackNationTuple[0].setOwner(Owner.Player1);
                        s.attackNationTuple[1].setOwner(Owner.Player1);
                    } else {
                        System.out.println("LOST");
                        s.attackNationTuple[0].setOwner(Owner.Player2);
                        s.attackNationTuple[1].setOwner(Owner.Player2);
                    }
                }
                // if all nations owned by either Player1 or Player2 set s.gameProgress = GameState.GameProgress.GameOver;
                break;
            }
            case GameOver: {
                break;
            }
            default:{
                break;
            }
        }
    }
}