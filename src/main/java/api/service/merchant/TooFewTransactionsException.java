package api.service.merchant;

public class TooFewTransactionsException extends Exception {

    public TooFewTransactionsException(String message) {
        super(message);
    }
}
