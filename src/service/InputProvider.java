package src.service;
/**
 * Interface for input provision.
 *
 * @author Sergio Ruy
 */
public interface InputProvider {

    /**
     * Retrieves next string input.
     *
     * @return next string input
     */
    String getNext();

    /**
     * Retrieves next float input.
     *
     * @return next float input
     */
    float getNextFloat();
}
