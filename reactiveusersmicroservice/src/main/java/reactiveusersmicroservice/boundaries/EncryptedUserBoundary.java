package reactiveusersmicroservice.boundaries;

import java.util.List;

public class EncryptedUserBoundary {
    private String email;
    private Name name;
    private String birthdate;
    private String recruitdate;
    private List<String> roles;

    public EncryptedUserBoundary() {
    }

    public EncryptedUserBoundary(String email, Name name, String birthdate, String recruitdate, List<String> roles) {
        this.email = email;
        this.name = name;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getRecruitdate() {
        return recruitdate;
    }

    public void setRecruitdate(String recruitdate) {
        this.recruitdate = recruitdate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "EncryptedUserBoundary{" +
                "email='" + email + '\'' +
                ", name=" + name +
                ", birthdate='" + birthdate + '\'' +
                ", recruitdate='" + recruitdate + '\'' +
                ", roles=" + roles +
                '}';
    }
}
