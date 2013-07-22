package net.gscheibel.arquillian;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class GameDAO {
   @PersistenceContext
   EntityManager em;

   @Resource
   UserTransaction ut;

   public void insertGame(Game game) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
      ut.begin();
      em.persist(game);
      ut.commit();
   }

   public Game findGame(String id) {
      return em.find(Game.class, id);
   }

   public void changeName(String id, String name) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
      ut.begin();
      final Game game = findGame(id);
      game.setName(name);
      em.merge(game);
      ut.commit();
   }
}
