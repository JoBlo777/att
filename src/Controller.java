import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.Iterator;

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
        NationIF n = s.getNations().get(nationID);

        switch(s.gameProgress) {
            case Landnahme: {
                s.status.setValue("Landnahme");
                System.out.println("LANDNAHME STATE");
                System.out.println("rrr " + nationID);
                if (n.isUnOccupied())
                    //s.updateNationsOwnedBy(Owner.Player1, n);
                    n.setOwner(Owner.Player1);
                n = s.getNextUnoccupied();
                if (n != null) {
                    //s.updateNationsOwnedBy(Owner.Player2, n);
                    n.setOwner(Owner.Player2);
                    // n.setTroopCountInt(4);
                }
//        Continent c = s.getContinents().get(n.name);
//        c.setOwner();
                System.out.println("clicked count " + s.getAcquiredCount());
                if (s.allClicked()) {
                    //test                   s.gameProgress = GameState.GameProgress.Verstärkung;
                    s.gameProgress = GameState.GameProgress.Angreifen;
                    s.status.setValue("Angreifen");
                    // recalculate Continent and Nation bases boni
                    s.assignReinforcements();
                    s.distributeReinforcements();
                }
                //s.assignContinentBonus(nationID);

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
                 System.out.println("VERSTÄRKUNG STATE");
                break;
            }
            case Bewegen: {
                System.out.println("BEWEGEN STATE");
                NationClass nation = (NationClass) s.getNations().get(nationID);
                System.out.println("owner " + nation.getOwner() + " tuplecount = " + s.bewegenVonNachTupleCount);
                //reset wenn zweites mal Angreifer gewählt
//                if (s.bewegenVonNachTupleCount == 1 && nation.getOwner() == Owner.Player1)
//                    s.bewegenVonNachTupleCount = 0;

                if (s.bewegenVonNachTupleCount == 0 && (nation.getOwner() == Owner.Player1)) {
                    s.bewegenVonNachTuple[0] = nation;
                    s.bewegenVonNachTupleCount++;
                } else if (s.bewegenVonNachTupleCount == 1)
                        /* && s.attackNationTuple[0].isNeighbourOf(nation))*/ {
                    s.bewegenVonNachTupleCount++;
                    s.bewegenVonNachTuple[1] = nation;
                    System.out.println("VOR VERSCHIEBEN NACH troop count = " + s.bewegenVonNachTuple[1].getTroopCount());
                    if (s.troopMoveSuccessful (s.bewegenVonNachTuple[0], s.bewegenVonNachTuple[1], s.bewegenVonNachTuple[0].getTroopCount().intValue()-1)) {
                        System.out.println("NACH VERSCHIEBEN country count = " + s.bewegenVonNachTuple[1].getTroopCount());
                     } else {
                        System.out.println("NICHT VERSCHOBEN");
                   }
                }
                if (s.bewegenVonNachTupleCount > 1) {
                    s.bewegenVonNachTupleCount = 0;
                    s.gameProgress = GameState.GameProgress.Angreifen;
                    s.status.setValue("Angreifen");
                }
                break;
            }
            case Angreifen: {
                System.out.println("ANGREIFEN STATE");
                System.out.println("vor angriff player1 nations " + s.printNationsOwnedBy(Owner.Player1));
                System.out.println("vor angriff player2 nations " + s.printNationsOwnedBy(Owner.Player2));
                NationClass nation = (NationClass) s.getNations().get(nationID);
                System.out.println("owner " + nation.getOwner() + "tuplecount " + s.attackNationTupleCount);
                //reset wenn zweites mal Angreifer gewählt
                if (s.attackNationTupleCount >= 1 && nation.getOwner() == Owner.Player1)
                    s.attackNationTupleCount = 0;

                if (s.attackNationTupleCount == 0 && (nation.getOwner() == Owner.Player1)) {
                    s.attackNationTuple[0] = nation;
                    s.attackNationTupleCount++;
                }
                else if (s.attackNationTupleCount == 1 && // let's attack Attacker und Defender gesetzt
                        (nation.getOwner() == Owner.Player2)
                        /* && s.attackNationTuple[0].isNeighbourOf(nation)*/) {
                    s.attackNationTupleCount++;
                    s.attackNationTuple[1] = nation;
                    if (s.isAttackSuccesful(s.attackNationTuple[0], s.attackNationTuple[1])) {
                        System.out.println("CONQUERED country" + s.attackNationTuple[1].getId());
                        //s.updateNationsOwnedBy(Owner.Player1, s.attackNationTuple[0]);
                        //s.updateNationsOwnedBy(Owner.Player1, s.attackNationTuple[1]);
                        //s.attackNationTuple[0].setOwner(Owner.Player1);
                        //s.attackNationTuple[1].setOwner(Owner.Player1);
                    } else {
                        System.out.println("LOST");
                        //s.attackNationTuple[0].setOwner(Owner.Player2);
                        //s.updateNationsOwnedBy(Owner.Player2, s.attackNationTuple[0]);
                        //s.updateNationsOwnedBy(Owner.Player2, s.attackNationTuple[1]);
                        //s.attackNationTuple[0].setOwner(Owner.Player2);
                        //s.attackNationTuple[1].setOwner(Owner.Player2);
                    }
                    System.out.println("nach angriff player1 nations " + s.printNationsOwnedBy(Owner.Player1));
                    System.out.println("nach angriff player2 nations " + s.printNationsOwnedBy(Owner.Player2));
                }
                // if all nations owned by either Player1 or Player2 GameOver
                if (s.isGameOver()) {
                    s.gameProgress = GameState.GameProgress.GameOver;
                }
                //AI = Player2
                if (s.attackNationTupleCount > 1) {
                    System.out.println("AI " + s.attackNationTupleCount);
                    boolean cont = true;
                    for (String id: s.getNationsOwnedBy(Owner.Player1)){
                        if (!cont)
                            break;
                        //Iterator<NationClass> i = s.nations.values().iterator();
                        ArrayList<NationClass> list = s.getNationsOwnedByB(Owner.Player2);
                        Iterator<NationClass> i = list.iterator();
                        while (cont && i.hasNext()){
                            nation = i.next();
                            if (s.nations.get(id).isNeighbourOf(nation)){
                                System.out.println("match");
                                System.out.println("attacker id"  + ((Path)nation).getId() + " attacker owner " + nation.getOwner()
                                        + " attacker troop count " + nation.getTroopCount());
                                System.out.println("defender id "  + ((Path)s.nations.get(id)) + " defender owner " + s.nations.get(id).getOwner()
                                        + " defender troop count " + s.nations.get(id).getTroopCount());
                                if (s.isAttackSuccesful(nation, s.nations.get(id))) {
                                    System.out.println("AI CONQUERED country " + s.nations.get(id));
                                    //s.updateNationsOwnedBy(Owner.Player1, s.attackNationTuple[0]);
                                    //s.updateNationsOwnedBy(Owner.Player2, s.attackNationTuple[1]);
                                    //s.attackNationTuple[0].setOwner(Owner.Player1);
                                    //s.attackNationTuple[1].setOwner(Owner.Player1);
                                } else {
                                    System.out.println("AI LOST");
                                    //s.attackNationTuple[0].setOwner(Owner.Player2);
                                    //s.updateNationsOwnedBy(Owner.Player2, s.attackNationTuple[0]);
                                    //s.updateNationsOwnedBy(Owner.Player2, s.attackNationTuple[1]);
                                    //s.attackNationTuple[0].setOwner(Owner.Player2);
                                    //s.attackNationTuple[1].setOwner(Owner.Player2);
                                }
                                cont = false;
                            }
                        }
                    }
                    s.attackNationTupleCount = 0;
                    System.out.println("nach angriff player1 nations " + s.printNationsOwnedBy(Owner.Player1));
                    System.out.println("nach angriff player2 nations " + s.printNationsOwnedBy(Owner.Player2));
                    s.gameProgress = GameState.GameProgress.Bewegen;
                    s.status.setValue("Bewegen");
                }
                break;
            }
            case GameOver: {
                s.status.setValue("Game Over");
                System.out.println("GAME-OVER RSTATE");
                break;
            }
            default:{
                s.status.setValue("Default");
                System.out.println("DEFAULT STATE");
                break;
            }
        }
    }
}