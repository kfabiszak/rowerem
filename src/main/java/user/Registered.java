package user;

/**
 * Represent registered user.
 */
public class Registered extends User {

    /**
     * Username to log in with.
     */
    private String login;
    /**
     * Password to log in with.
     */
    private String password; //takie bezpieczne

    /**
     * Construct regiestered user.
     * @param id Unique ID
     * @param login Username to log in with
     */
    public Registered(String id, String login) {
        super(id); //TODO automatyczne generowanie id
        this.login = login;
    }

    /**
     * Log user out.
     */
    public void logOut() {

    }

}
