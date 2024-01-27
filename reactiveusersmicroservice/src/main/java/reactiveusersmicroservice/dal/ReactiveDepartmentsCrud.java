package reactiveusersmicroservice.dal;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactiveusersmicroservice.data.DepartmentEntity;

public interface ReactiveDepartmentsCrud extends ReactiveMongoRepository<DepartmentEntity, String> {
}
