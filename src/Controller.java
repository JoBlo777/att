
public class Controller {
    public Controller(){

    }
    public void clickedOnNation(String nationID){
        System.out.println("rrr" + nationID);
        //rs
        DataSystem s = DataSystem.getInstance();
        if (s.isPlayerOnesTurn()) {
            s.setPlayerOnesTurn(false);
            System.out.println("sss" + s.getNations().get(nationID).getOwner());
            s.getNations().get(nationID).setOwner(Owner.Player1);
            System.out.println("sss" + s.getNations().get(nationID).getOwner());
        }
    }
}