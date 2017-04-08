package user;

public class Registered extends User {

    private String login;
    private String password; //takie bezpieczne

    public Registered(String id, String login) {
        super(id); //TODO automatyczne generowanie id
        this.login = login;
    }

    public void logOut() {

    }

}
