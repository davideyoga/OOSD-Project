package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

public class Game{

      //Attributes
      private int id;
      private String name;
      private  int exp;
      private String image;
      private String description;

        public Game(DaoData dd) {
            this.id = id;
            this.name = name;
            this.exp = exp;
            this.image = image;
            this.description = description;
        }


         //Getter and Setter


         public int getId() {
             return id;
         }

         public void setId(int id) {
             this.id = id;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }

         public int getExp() {
             return exp;
         }

         public void setExp(int exp) {
             this.exp = exp;
         }

         public String getImage() {
             return image;
         }

         public void setImage(String image) {
             this.image = image;
         }

         public String getDescription() {
             return description;
         }

         public void setDescription(String description) {
             this.description = description;
         }
 }