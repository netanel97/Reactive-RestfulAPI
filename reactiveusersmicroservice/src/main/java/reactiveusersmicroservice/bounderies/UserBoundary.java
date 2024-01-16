package reactiveusersmicroservice.bounderies;

import java.util.Date;
import java.util.List;

public class UserBoundary {
    private String email;
    private Name name;
    private String password;

    private Date birthdate;
    private Date recruitdate;

    private List<String> roles;

    public UserBoundary() {
    }

    public UserBoundary(String email, Name name, String password, Date birthdate, Date recruitdate, List<String> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getRecruitdate() {
        return recruitdate;
    }

    public void setRecruitdate(Date recruitdate) {
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
        return "UserBoundary{" +
                "email='" + email + '\'' +
                ", name=" + name +
                ", password='" + password + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", recruitdate='" + recruitdate + '\'' +
                ", roles=" + roles +
                '}';
    }
}
