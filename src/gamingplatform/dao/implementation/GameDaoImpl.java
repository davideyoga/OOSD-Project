package gamingplatform.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.GameDao;
import gamingplatform.model.Game;

public class GameDaoImpl extends DaoDataMySQLImpl implements GameDao {

    //Variabili di appoggio per preparare le query
    private PreparedStatement selectGameById,
                              selectGameByName,
                              insertGame,
                              deleteGameById,
                              deleteGameByName,
                              selectGames,
                              updateGame;


    //Costruttore
    public GameDaoImpl(DataSource datasource){
        super (datasource);
    }



    /**
     * Metodo incui viene inizializzata la connessione e vengono preparate le query
     * di insert, selct,delete e update
     * @throws DaoException eccezione che viene lanciata in caso di fallimento di
     * inizializzazione query
     */
    public void init() throws DaoException{
        try{
            super.init();//inizializzazione connessione

            //query che mi restituisce il gioco con id dato
            selectGameById=connection.prepareStatement("SELECT * FROM game WHERE id=?");

            //query che mi restituisce il gioco con id dato
            selectGameByName=connection.prepareStatement("SELECT * FROM game WHERE name=?");

            //query che mi restituisce lista di giochi
            selectGames=connection.prepareStatement("SELECT * FROM game");

            //query di inserimento di una nuova tupla nella tabella game
            insertGame=connection.prepareStatement("INSERT INTO game " +
                    "                                           VALUES name=?," +
                    "                                                  exp=?," +
                    "                                                  image=?," +
                    "                                                  description=?");

            //query di eliminazione di un gioco con id dato
            deleteGameById=connection.prepareStatement("DELETE FROM game WHERE id=?");

            //query di eliminazione di un gioco con id dato
            deleteGameByName=connection.prepareStatement("DELETE FROM game WHERE name=?");

            //query di update di un dato gioco
            updateGame=connection.prepareStatement("UPDATE game" +
                    "                                    SET name=?" + //L'ID NON LO PUOI MODIFICARE
                    "                                        exp=?" +
                    "                                        image=?" +
                    "                                        description=?" +
                    "                                    WHERE id=?");


        }catch (SQLException e){
            throw new DaoException("Error initializing GameDao", e);
        }
    }




    /**
     * Metodo che seleziona un gioco dato l'id
     * @param idGame è l'id del gioco che si vuole visualizzare
     * @return il gioco desiderato
     */
    public Game getGameById(int idGame) throws DaoException{
        Game g=new Game(this);
        try{
            this.selectGameById.setInt(1,idGame);
            ResultSet rs=this.selectGameById.executeQuery();
            while(rs.next()){
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setExp(rs.getInt("exp"));
                g.setImage(rs.getString("image"));
                g.setDescription(rs.getString("description"));
            }
        }catch (SQLException e){
            throw new DaoException("Error game getGameById", e);

        }
        return g;
    }




    /**
     * Metodo che seleziona un gioco dato il nome
     * @param nameGame è il nome del gioco che si vuole visualizzare
     * @return il gioco desiderato
     */
    public Game getGameByName(String nameGame) throws DaoException{
        Game g=new Game(this);
        try{
            this.selectGameById.setString(1,nameGame);
            ResultSet rs=this.selectGameByName.executeQuery();
            while(rs.next()){
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setExp(rs.getInt("exp"));
                g.setImage(rs.getString("image"));
                g.setDescription(rs.getString("description"));
            }
        }catch (SQLException e){
            throw new DaoException("Error query getGameByName", e);

        }
        return g;
    }




    /**
     * Mestodo che restituisce una lista di giochi
     * @return lista di giochi
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Game> getGames() throws DaoException{
        List<Game> lista=new ArrayList<Game>();
        try{
            ResultSet rs=this.selectGames.executeQuery();

            while(rs.next())
            {
                Game g=new Game(this);
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setExp(rs.getInt("exp"));
                g.setImage(rs.getString("image"));
                g.setDescription(rs.getString("description"));
                lista.add(g);
            }
        }catch (SQLException e){
            throw new DaoException("Error query getGames", e);

        }

        return lista;
    }




    /**
     * Metodo di inserimento di un nuovo elemento nella tabella game del DataBase
     * @param name è il nome del gioco da dover inserire
     * @param exp sono i punti esperienza che si posssono guadagnare giocandoci
     * @param image  è l'immagine di presentazione del gioco
     * @param description è la descrizione del gioco
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void insertGame(String name, int exp, String image, String description) throws DaoException{
        try{
            this.insertGame.setString(1,name);
            this.insertGame.setInt(2,exp);
            this.insertGame.setString(3,image);
            this.insertGame.setString(4,description);
            this.insertGame.executeQuery();

        } catch (SQLException e){
            throw new DaoException("Error query insertGame", e);

        }
    }




    /**
     * Metodo che permette la cancellazione di un gioco dalla tabella game dato l'id
     * @param idGame è l'id del gioco da cancellare
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void deleteGameById(int idGame) throws DaoException{
        try{
            this.deleteGameById.setInt(1,idGame);
            this.deleteGameById.executeQuery();
        }catch (SQLException e){
            throw new DaoException("Error query deleteGameById", e);

        }
    }




    /**
     * Metodo che permette la cancellazione di un gioco dalla tabella game dato il nome
     * @param nameGame è l'id del gioco da cancellare
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void deleteGameByName(String nameGame) throws DaoException{
        try{
            this.deleteGameByName.setString(1,nameGame);
            this.deleteGameByName.executeQuery();
        }catch (SQLException e){
            throw new DaoException("Error query deleteGameByName", e);

        }
    }




    /**
     * Metodo che permette la modifica di un daro gioco
     * @param id è il codice identificativo del gioco
     * @param name nome del gioco
     * @param exp punti esperienza dati dal gioco
     * @param image immagine di presentazione del gioco
     * @param description descrizione del gioco
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void updateGame(int id,String name, int exp, String image, String description) throws DaoException{
        try{
            this.updateGame.setString(1,name);
            this.updateGame.setInt(2,exp);
            this.updateGame.setString(3,image);
            this.updateGame.setString(4,description);
            this.updateGame.setInt(5,id);
            this.updateGame.executeQuery();
        }catch (SQLException e){
            throw new DaoException("Error query updateGame", e);

        }
    }

    public void destroy() throws DaoException{

        //chiudo le quary precompilate
        try {
            this.selectGameById.close();
            this.selectGameByName.close();
            this.insertGame.close();
            this.deleteGameById.close();
            this.deleteGameByName.close();
            this.selectGames.close();
            this.updateGame.close();


        } catch (SQLException e) {
            throw new DaoException("Error destroy GameDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}
