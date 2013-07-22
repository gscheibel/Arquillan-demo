package net.gscheibel.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.DataSource;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class GameServiceTest {

   @Inject
   private GameService gameService;

   @Deployment(testable = true)
   public static WebArchive createDeployment() {
      return ShrinkWrap.create(WebArchive.class)
            .addPackage(GameService.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
   }

   @Test
   public void testSayHello() {
      GameService gs = new GameService();
      assertThat(gs.sayHello(), is(equalTo("Hello World")));
   }

   @Test
   public void testInsertAndRetrieveGame() throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException {
      final String testID = "gameID";
      final String testName = "gameNAME";

      gameService.createGame(testID, testName);
      final Game foundGame = gameService.findGame(testID);
      assertNotNull(foundGame);
      assertThat(foundGame.getId(), is(equalTo(testID)));
      assertThat(foundGame.getName(), is(equalTo(testName)));
   }

   @Test
   @DataSource("arq/games")
   @UsingDataSet("datasets/games.yml")
   @ShouldMatchDataSet("datasets/expected-games.yml")
   public void testUpdateGameName() throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException {
      gameService.changeName("1", "expected-game1");
   }
}
