package reactiveusersmicroservice.converters;


import org.springframework.stereotype.Component;
import reactiveusersmicroservice.boundaries.UserBoundary;
import reactiveusersmicroservice.boundaries.NewUserBoundary;
import reactiveusersmicroservice.data.UserEntity;

@Component
public class UsersConverter {

    public UserEntity toEntity(NewUserBoundary newUserBoundary) {
        UserEntity entity = new UserEntity();
        entity.setEmail(newUserBoundary.getEmail());
        entity.setName(newUserBoundary.getName());
        entity.setPassword(newUserBoundary.getPassword());
        entity.setBirthdate(newUserBoundary.getBirthdate());
        entity.setRecruitdate(newUserBoundary.getRecruitdate());
        entity.setRoles(newUserBoundary.getRoles());
        return entity;
    }

    public UserBoundary toBoundary(UserEntity userEntity) {
        UserBoundary boundary = new UserBoundary();
        boundary.setEmail(userEntity.getEmail());
        boundary.setName(userEntity.getName());
        boundary.setBirthdate(userEntity.getBirthdate());
        boundary.setRecruitdate(userEntity.getRecruitdate());
        boundary.setRoles(userEntity.getRoles());
        return boundary;
    }

}
