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

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;
import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

public class ReviewDaoImpl extends DaoDataMySQLImpl implements ReviewDao {

    //Variabili di appoggio per preparare le query
    private PreparedStatement selectReview,
                              insertReview,
                              deleteReview,
                              selectReviews,
                              selectReviewsByUser,
                              selectReviewsByGame,
                              updateReview;


    /**
     * Costruttore per inizializzare la connessione
     * @param datasource e' la risorsa di connessione messa a disposizione del connection pooling
     */
    public ReviewDaoImpl(DataSource datasource){
        super (datasource);
    }



    /**
     * Metodo in cui viene inizializzata la connessione e vengono preparate le query
     * di insert, select,delete e update
     * @throws DaoException eccezione che viene lanciata in caso di fallimento di
     * inizializzazione query
     */
    public void init() throws DaoException{
        try{
            super.init();//inizializzazione connessione

            //query che mi restituisce la review scritta da un user su un certo gioco
            selectReview=connection.prepareStatement("SELECT * FROM review WHERE id_user=? AND id_games=?");

            //query che mi restituisce la lista delle review di tuuti i giochi
            selectReviews=connection.prepareStatement("SELECT * FROM review");

            //query che mi restituisce la lista di reviews scritte da un dato utente
            selectReviewsByUser=connection.prepareStatement("SELECT * FROM review WHERE id_user=?");

            //query che mi restituisce la lista di reviews scritte su un dato gioco
            selectReviewsByGame=connection.prepareStatement("SELECT * FROM review WHERE id_game=?");

            //query di inserimento di una nuova tupla nella tabella review
            insertReview=connection.prepareStatement("INSERT INTO review " +
                    "                                           VALUES (id_user=?," +
                    "                                                  id_game=?,"+
                    "                                                  title=?," +
                    "                                                  body=?," +
                    "                                                  vote=?)");

            //query di eliminazione di una review
            deleteReview=connection.prepareStatement("DELETE FROM review WHERE id_user=? AND id_game=?");

            //query di update di una review
            updateReview=connection.prepareStatement(" UPDATE review " +
                    "                                      SET title=?, " +
                    "                                          body=?," +
                    "                                          vote=?" +
                    "                                      WHERE id_user=? AND id_game=?");


        }catch (Exception e){
            throw new DaoException("Error initializing review dao", e);
        }
    }

    /**
     * Metodo che restituisce una review vuota
     * @return una review vuota
     */
    @Override
    public Review getReview() {
        return new Review(this);
    }

    @Override
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
                r.setTitle(stripSlashes(rs.getString("title")));
                r.setBody(stripSlashes(rs.getString("body")));
                r.setVote(rs.getInt("vote"));
            }
        }catch (Exception e){
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
                r.setTitle(stripSlashes(rs.getString("title")));
                r.setBody(stripSlashes(rs.getString("body")));
                r.setVote(rs.getInt("vote"));
                lista.add(r);
            }
        }catch (Exception e){
            throw new DaoException("Error query getReviews", e);

        }

        return lista;
    }



    /**
     * Mestodo che restituisce la lista di reviews scritte da un dato user
     * @param idUser è l'id dell'user autore delle recensioni
     * @return lista di reviews
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Review> getReviewsByUser(int idUser) throws DaoException{
        List<Review> lista=new ArrayList<Review>();
        try{

            selectReviewsByGame.setInt(1,idUser);
            ResultSet rs=this.selectReviewsByUser.executeQuery();

            while(rs.next())
            {
                Review r=new Review(this);
                r.setIdUser(rs.getInt("id_user"));
                r.setIdGame(rs.getInt("id_game"));
                r.setTitle(stripSlashes(rs.getString("title")));
                r.setBody(stripSlashes(rs.getString("body")));
                r.setVote(rs.getInt("vote"));
                lista.add(r);
            }
        }catch (Exception e){
            throw new DaoException("Error query getReviewsByUser", e);

        }

        return lista;
    }



    /**
     * Mestodo che restituisce la lista di reviews relative a un dato gioco
     * @param idGame è l'id del gioco recensito
     * @return lista di reviews
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Review> getReviewsByGame(int idGame) throws DaoException{
        List<Review> lista=new ArrayList<>();
        try{
            selectReviewsByGame.setInt(1,idGame);
            ResultSet rs=this.selectReviewsByGame.executeQuery();

            while(rs.next())
            {
                Review r=new Review(this);
                r.setIdUser(rs.getInt("id_user"));
                r.setIdGame(rs.getInt("id_game"));
                r.setTitle(stripSlashes(rs.getString("title")));
                r.setBody(stripSlashes(rs.getString("body")));
                r.setVote(rs.getInt("vote"));
                lista.add(r);
            }
        }catch (Exception e){
            throw new DaoException("Error query getReviewsByGame", e);

        }

        return lista;
    }

    /**
     * Metodo di inserimento di un nuovo elemento nella tabella review del DataBase
     * @param review e' la review da inserire nel database
     * @throws DaoException lancia eccezione in caso di errore
     */
    @Override
    public void insertReview(Review review) throws DaoException {
        try{
            this.insertReview.setInt(1,review.getIdUser());
            this.insertReview.setInt(2,review.getIdGame());
            this.insertReview.setString(3,addSlashes(review.getTitle()));
            this.insertReview.setString(4,addSlashes(review.getBody()));
            this.insertReview.setInt(5,review.getVote());
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
        }catch (Exception e){
            throw new DaoException("Error query deleteReview", e);

        }
    }


    /**
     * Metodo che permette la modifica di una review
     * @param review e' la review di cui si deve effettuare l'update
     * @throws DaoException lancia eccezione in caso di errore
     */
    @Override
    public void updateReview(Review review) throws DaoException {

        try{
            this.updateReview.setString(1,addSlashes(review.getTitle()));
            this.updateReview.setString(2,addSlashes(review.getBody()));
            this.updateReview.setInt(3,review.getVote());
            this.updateReview.setInt(4,review.getIdUser());
            this.updateReview.setInt(5,review.getIdGame());
            this.updateReview.executeUpdate();
        }catch (Exception e){
            throw new DaoException("Error query updateReview", e);

        }
    }

    /**
     * Metodo che chiude la connessione e le query preparate
     * @throws DaoException lancia eccezione in caso di errore
     */
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

        } catch (Exception e) {
            throw new DaoException("Error destroy ReviewDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}
