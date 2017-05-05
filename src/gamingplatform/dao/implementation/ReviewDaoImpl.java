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
import gamingplatform.dao.interfaces.ReviewDao;
import gamingplatform.model.Review;

public class ReviewDaoImpl extends DaoDataMySQLImpl implements ReviewDao {

    //Variabili di appoggio per preparare le query
    private PreparedStatement selectReview,
                              insertReview,
                              deleteReview,
                              selectReviews,
                              selectReviewsByUser,
                              selectReviewsByGame,
                              updateReview;


    //Costruttore
    public ReviewDaoImpl(DataSource datasource){
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
            selectReview=connection.prepareStatement("SELECT * FROM review WHERE id_user=? AND id_games=?");

            //query che mi restituisce lista di giochi
            selectReviews=connection.prepareStatement("SELECT * FROM review");

            //query che mi restituisce lista di giochi
            selectReviewsByUser=connection.prepareStatement("SELECT * FROM review WHERE id_user=?");

            //query che mi restituisce lista di giochi
            selectReviewsByGame=connection.prepareStatement("SELECT * FROM review WHERE id_game=?");

            //query di inserimento di una nuova tupla nella tabella game
            insertReview=connection.prepareStatement("INSERT INTO review " +
                    "                                           VALUES (id_user=?," +
                    "                                                  id_game=?,"+
                    "                                                  title=?," +
                    "                                                  body=?," +
                    "                                                  vote=?)");

            //query di eliminazione di un gioco con id dato
            deleteReview=connection.prepareStatement("DELETE FROM review WHERE id_user=? AND id_game=?");

            //query di update di un dato gioco
            updateReview=connection.prepareStatement(" UPDATE game" +
                    "                                      SET title=?" + //L'ID NON LO PUOI MODIFICARE
                    "                                          body=?" +
                    "                                          vote=?" +
                    "                                      WHERE id_user=? AND id_game=?");


        }catch (SQLException e){
            throw new DaoException("Error initializing review dao", e);
        }
    }




    /**
     * Metodo che seleziona una review dati id_user e id_game
     * @param idUser è l'id dell'utente che ha fatto la recensione
     * @param idGame è l'id del gioco recensito
     * @return la rewiew desiderata
     */
    public Review getReview(int idUser,int idGame) throws DaoException{
        Review r=new Review(this);
        try{
            this.selectReview.setInt(1,idUser);
            this.selectReview.setInt(2,idGame);
            ResultSet rs=this.selectReview.executeQuery();
            while(rs.next()){
                r.setIdUser(rs.getInt("id_user"));
                r.setIdGame(rs.getInt("id_game"));
                r.setTitle(rs.getString("title"));
                r.setBody(rs.getString("body"));
                r.setVote(rs.getInt("vote"));
            }
        }catch (SQLException e){
            throw new DaoException("Error query getReview", e);

        }
        return r;
    }




    /**
     * Mestodo che restituisce la lista di reviews
     * @return lista di reviews
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Review> getReviews() throws DaoException{
        List<Review> lista=new ArrayList<Review>();
        try{
            ResultSet rs=this.selectReviews.executeQuery();

            while(rs.next())
            {
                Review r=new Review(this);
                r.setIdUser(rs.getInt("id_user"));
                r.setIdGame(rs.getInt("id_game"));
                r.setTitle(rs.getString("title"));
                r.setBody(rs.getString("body"));
                r.setVote(rs.getInt("vote"));
                lista.add(r);
            }
        }catch (SQLException e){
            throw new DaoException("Error query getReviews", e);

        }

        return lista;
    }



    /**
     * Mestodo che restituisce la lista di reviews scritte da un dato user
     * @return lista di reviews
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Review> getReviewsByUser() throws DaoException{
        List<Review> lista=new ArrayList<Review>();
        try{
            ResultSet rs=this.selectReviewsByUser.executeQuery();

            while(rs.next())
            {
                Review r=new Review(this);
                r.setIdUser(rs.getInt("id_user"));
                r.setIdGame(rs.getInt("id_game"));
                r.setTitle(rs.getString("title"));
                r.setBody(rs.getString("body"));
                r.setVote(rs.getInt("vote"));
                lista.add(r);
            }
        }catch (SQLException e){
            throw new DaoException("Error query getReviewsByUser", e);

        }

        return lista;
    }



    /**
     * Mestodo che restituisce la lista di reviews relative a un dato gioco
     * @return lista di reviews
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Review> getReviewsByGame() throws DaoException{
        List<Review> lista=new ArrayList<Review>();
        try{
            ResultSet rs=this.selectReviewsByGame.executeQuery();

            while(rs.next())
            {
                Review r=new Review(this);
                r.setIdUser(rs.getInt("id_user"));
                r.setIdGame(rs.getInt("id_game"));
                r.setTitle(rs.getString("title"));
                r.setBody(rs.getString("body"));
                r.setVote(rs.getInt("vote"));
                lista.add(r);
            }
        }catch (SQLException e){
            throw new DaoException("Error query getReviewsByGame", e);

        }

        return lista;
    }


    /**
     * Metodo di inserimento di un nuovo elemento nella tabella review del DataBase
     * @param idUser identificativo dell'user autore della recensione
     * @param idGame identificativo del gioco recensito
     * @param title titolo della recensione
     * @param body corpo della recensione
     * @param vote voto assegnato al gioco
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void insertReview(int idUser, int idGame, String title, String body,int vote) throws DaoException{
        try{
            this.insertReview.setInt(1,idUser);
            this.insertReview.setInt(2,idGame);
            this.insertReview.setString(3,title);
            this.insertReview.setString(4,body);
            this.insertReview.setInt(5,vote);
            this.insertReview.executeUpdate();

        } catch (SQLException e){
            throw new DaoException("Error query insertReview", e);

        }
    }




    /**
     * Metodo che permette la cancellazione di un gioco dalla tabella game dato id_user e id_game
     * @param idUser è l'id dello user autore della recensione
     * @param idGame è l'id del gioco recensito
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void deleteReview(int idUser, int idGame) throws DaoException{
        try{
            this.deleteReview.setInt(1,idUser);
            this.deleteReview.setInt(1,idGame);
            this.deleteReview.executeUpdate();
        }catch (SQLException e){
            throw new DaoException("Error query deleteReview", e);

        }
    }



    /**
     * Metodo che permette la modifica di una review
     * @param idUser è l'id dell'user autore della review
     * @param idGame è l'id del gioco recensito
     * @param title è il titolo della recensione
     * @param body  è il corpo della recensione
     * @param vote è il voto assegnato al gioco
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void updateReview(int idUser, int idGame, String title, String body, int vote) throws DaoException{
        try{
            this.updateReview.setString(1,title);
            this.updateReview.setString(2,body);
            this.updateReview.setInt(3,vote);
            this.updateReview.setInt(4,idUser);
            this.updateReview.setInt(5,idGame);
            this.updateReview.executeUpdate();
        }catch (SQLException e){
            throw new DaoException("Error query updateReview", e);

        }
    }


    public void destroy() throws DaoException{

        //chiudo le quary precompilate
        try {
            this.selectReview.close();
            this.insertReview.close();
            this.deleteReview.close();
            this.selectReviews.close();
            this.selectReviewsByUser.close();
            this.selectReviewsByGame.close();
            this.updateReview.close();

        } catch (SQLException e) {
            throw new DaoException("Error destroy ReviewDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}
