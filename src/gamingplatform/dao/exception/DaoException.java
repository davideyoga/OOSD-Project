package gamingplatform.dao.exception;

/**
 * Classe utilizzata per sollevare eccezzioni riguardanti il Dao
 * @author Davide Micarelli
 */
public class DaoException extends Exception{

    /**
     * Eccezzione con un messaggio di descrizione
     * @param message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Eccezzione con un messaggio di errore e la causa
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Eccezzione con causa
     * @param cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }

    /**
     * Torna una descrizione dell'eccezzione
     * @return descrizione dell'eccezzione
     */
    @Override
    public String getMessage() {
        return super.getMessage() + (getCause()!=null?" ("+getCause().getMessage()+")":"");                
    }
	
}