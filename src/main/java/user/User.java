package user;

/**
 * Represents an user.
 */
public class User {

    /**
     * Unique ID of the user.
     */
    protected String id;

    /**
     * Construct user with ID.
     * @param id Id of constructed User
     */
    public User(String id) {
        this.id = id; // TODO generowanie id
    }

    /**
     * Registers current user.
     * @param login Username of user
     * @return Instance of registered user ID of this
     */
    public Registered register(String login) {
        return new Registered(this.id, login);
    }

    /**
     * Logs user in
     * @param login username to log in with
     */
    public void logIn(String login) {
        //TODO znaleźć takiego usera itp
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
