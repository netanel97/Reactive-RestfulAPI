package reactiveusersmicroservice.logic;

import io.micrometer.common.util.internal.logging.InternalLogger;
import reactiveusersmicroservice.bounderies.DepartmentBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentsService {

    public Mono<DepartmentBoundary> createDepartment(DepartmentBoundary department);
    public Flux<DepartmentBoundary> getAll();

    public Mono<DepartmentBoundary> getSpecificDepartmentById(String deptId);

    public Mono<Void> deleteAll();
}
