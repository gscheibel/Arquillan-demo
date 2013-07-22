package net.gscheibel.arquillian;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {

   @Id
   private String id;
   public String getId() {return id;}
   public void setId(String id) {this.id = id;}

   private String name;
   public String getName() {return name;}
   public void setName(String name) {this.name = name;}

   public Game() {
      this(null, null);
   }

   public Game(String id, String name) {
      this.id = id;
      this.name = name;
   }
}
