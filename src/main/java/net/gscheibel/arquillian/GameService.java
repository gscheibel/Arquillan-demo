package net.gscheibel.arquillian;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

public class GameService {

   @Inject
   GameDAO gameDAO;


   public void setGameDAO(GameDAO gameDAO) {
      this.gameDAO = gameDAO;
   }

   public String sayHello() {
      return "Hello World";
   }

   public void createGame(String id, String name) throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException {
      gameDAO.insertGame(new Game(id, name));
   }

   public Game findGame(String id){
      return gameDAO.findGame(id);
   }

   public void changeName(String id, String name) throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException {
      gameDAO.changeName(id, name);
   }
}
