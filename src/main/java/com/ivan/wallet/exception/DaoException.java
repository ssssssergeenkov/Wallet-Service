package com.ivan.wallet.exception;

/**
 * The DaoException class is a custom exception that is thrown when an error occurs in a DAO (Data Access Object) class.
 * It extends the RuntimeException class.
 */
public class DaoException extends RuntimeException {

    /**
     * Constructs a new DaoException object with the specified cause.
     *
     * @param throwable The cause of the exception.
     */
    public DaoException(Throwable throwable) {
        super(throwable);
    }
}