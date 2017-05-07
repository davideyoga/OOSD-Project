package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Review;

import java.util.List;

public interface ReviewDao extends DaoData{

    public Review getReview();

    public Review getReview( int idUser, int idGame) throws DaoException;

    public List<Review> getReviews() throws DaoException;

    public List<Review> getReviewsByUser(int idUser) throws DaoException;

    public List<Review> getReviewsByGame(int idGame) throws DaoException;

    public void insertReview(Review review);

    public void insertReview(int idUser, int idGame, String title, String body,int vote) throws DaoException;

    public void deleteReview(int idUser, int idGame) throws DaoException;

    public void updateReview(Review review);

    public void updateReview(int idUser, int idGame, String title, String body, int vote) throws DaoException;



    public void destroy() throws DaoException;
}