package reactiveusersmicroservice.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import reactiveusersmicroservice.bounderies.Name;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
public class UserEntity {

    @Id
    private String email;
    private Name name;
    private String password;
    private String birthdate;
    private String recruitdate;
    private List<String> roles;

    private Set<String> departments = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String email, Name name, String password, String birthdate, String recruitdate, List<String> roles, Set<String> departments) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
        this.departments = departments;
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

    public Set<String> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<String> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", name=" + name +
                ", password='" + password + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", recruitdate='" + recruitdate + '\'' +
                ", roles=" + roles +
                ", departments=" + departments +
                '}';
    }
}
