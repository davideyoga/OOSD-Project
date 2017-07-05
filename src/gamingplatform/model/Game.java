package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

/**
 * Classe che rappresenta l'entita' gioco
 */
public class Game{

      //Attributes
      private int id;
      private String name;
      private  int exp;
      private String image;
      private String description;

    /**
     * Costrutture vuoto
     * @param ds
     */
    public Game(DaoData ds) {
          this.id = 0;
          this.name = null;
          this.exp = 0;
          this.image = null;
          this.description = null;
      }


         //Getter and Setter

    /**
     * @return Id corrente
     */
    public int getId() {
             return id;
         }

    /**
     * setta l'id
     * @param id
     */
    public void setId(int id) {
             this.id = id;
         }

    /**
     *
     * @return Nome corrente
     */
    public String getName() {
             return name;
         }

    /**
     * setta il nome
     * @param name
     */
    public void setName(String name) {
             this.name = name;
         }

    /**
     *
     * @return Esperienza corrente
     */
    public int getExp() {
             return exp;
         }

    /**
     * Setta il parametro exp
     * @param exp
     */
    public void setExp(int exp) {
             this.exp = exp;
         }

    /**
     * Torna immagine corrente
     * @return
     */
    public String getImage() {
             return image;
         }

    /**
     * Setta l'immagine
     * @param image
     */
    public void setImage(String image) {
             this.image = image;
         }

    /**
     * Torna descrizione corrente
     * @return
     */
    public String getDescription() {
             return description;
         }

    /**
     * Setta descrizione
     * @param description
     */
    public void setDescription(String description) {
             this.description = description;
         }
 }