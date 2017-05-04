package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Review;

import java.util.List;

public interface ReviewDao extends DaoData{

    public Review getReview( int keyUser, int keyGame) throws DaoException;

    public List<Review> getReviews() throws DaoException;

    public List<Review> getReviewsByUser() throws DaoException;

    public List<Review> getReviewsByGame() throws DaoException;

    public void insertReview(Review r) throws DaoException;

    public void deleteReview(int keyUser, int keyGame) throws DaoException;

    public void updateReview(int id_user, int id_game, String title, String body, int vote) throws DaoException;



    public void destroy() throws DaoException;
}