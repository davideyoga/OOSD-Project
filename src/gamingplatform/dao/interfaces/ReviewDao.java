package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Review;

import java.util.List;

/**
 * Interfaccia per la gestione delle Review nel database
 */
public interface ReviewDao extends DaoData{

    /**
     * Torna Review vuota
     * @return
     */
    public Review getReview();

    /**
     * Torna review con tale id utente e id del gioco
     * @param idUser id dell'utente nel db
     * @param idGame id del gioco nel db
     * @return rewiew con tale id utente e id gioco
     * @throws DaoException
     */
    public Review getReview( int idUser, int idGame) throws DaoException;

    /**
     * Torna listsa di tutte le review nel database
     * @return
     * @throws DaoException
     */
    public List<Review> getReviews() throws DaoException;

    /**
     * Torna lista delle Review di tale id di User presenti nel db
     * @param idUser
     * @return
     * @throws DaoException
     */
    public List<Review> getReviewsByUser(int idUser) throws DaoException;

    /**
     * Torna lista delle Review di tale id del Game presenti nel db
     * @param idGame
     * @return
     * @throws DaoException
     */
    public List<Review> getReviewsByGame(int idGame) throws DaoException;

    /**
     * Inserisce nel db la review passata
     * @param review
     * @throws DaoException
     */
    public void insertReview(Review review) throws DaoException;

    /**
     * Cancella tale Review dal database
     * @param idUser
     * @param idGame
     * @throws DaoException
     */
    public void deleteReview(int idUser, int idGame) throws DaoException;

    /**
     * Esegue l'update della Review con tale id nel database
     * @param review
     * @throws DaoException
     */
    public void updateReview(Review review) throws DaoException;

}