package reactiveusersmicroservice.boundaries;

public class NewUserBoundary extends UserBoundary {
    private String password;

    public NewUserBoundary() {
        super();
    }

    public NewUserBoundary(String password) {
        super();
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
