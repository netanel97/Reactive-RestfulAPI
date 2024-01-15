package reactiveusersmicroservice.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactiveusersmicroservice.bounderies.Name;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class UserEntity {

    // TODO: check if the email is id or need another id.

    @Id
    private String email;
    private Name name;
    private String password; //TODO: check if there are at least 3 chars.
//    private String birthdate; //TODO: need to check the format - dd-MM-yyyy
//    private String recruitdate; //TODO: need to check the format - dd-MM-yyyy

    //TODO: delete if not necessary all the comments below.
    private Date birthdate;
    private Date recruitdate;

    private List<String> roles;

    public UserEntity() {
    }



    /*
    public UserEntity(String email, Name name, String password, String birthdate, String recruitdate, List<String> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
    }
    */

    public UserEntity(String email, Name name, String password, Date birthdate, Date recruitdate, List<String> roles) {
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

   /*
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
*/
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
