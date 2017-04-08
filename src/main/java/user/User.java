package user;

public class User {

    protected String id;

    public User(String id) {
        this.id = id; // TODO generowanie id
    }

    public Registered register(String login) {
        return new Registered(this.id, login);
    }

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
