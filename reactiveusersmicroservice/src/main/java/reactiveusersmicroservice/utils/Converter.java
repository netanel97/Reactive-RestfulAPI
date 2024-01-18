package reactiveusersmicroservice.utils;


import org.springframework.stereotype.Component;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.data.UserEntity;

@Component
public class Converter {
    // TODO: add users converter (toBoundery, toEntiry)

    public UserEntity toEntity(UserBoundary userBoundary) {
        UserEntity entity = new UserEntity();
        entity.setEmail(userBoundary.getEmail());
        entity.setName(userBoundary.getName());
        entity.setPassword(userBoundary.getPassword());
        entity.setBirthdate(userBoundary.getBirthdate());
        entity.setRecruitdate(userBoundary.getRecruitdate());
        entity.setRoles(userBoundary.getRoles());
        return entity;

    }

    public UserBoundary toBoundary(UserEntity userEntity) {


        UserBoundary boundary = new UserBoundary();
        boundary.setEmail(userEntity.getEmail());
        boundary.setName(userEntity.getName());
        boundary.setBirthdate(userEntity.getBirthdate());
        boundary.setRecruitdate(userEntity.getRecruitdate());
        boundary.setRoles(userEntity.getRoles());
        System.err.println("boundary: " + boundary);
        return boundary;
    }
}
