package reactiveusersmicroservice.logic;


import org.springframework.stereotype.Service;
import reactiveusersmicroservice.boundaries.DepartmentBoundary;
import reactiveusersmicroservice.dal.ReactiveDepartmentsCrud;
import reactiveusersmicroservice.converters.DepartmentsConverter;
import reactiveusersmicroservice.utils.DateUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class ReactiveDepartmentsService implements DepartmentsService {
    private final DepartmentsConverter departmentsConverter;
    private final ReactiveDepartmentsCrud reactiveDepartmentsCrud;
    private final UsersService reactiveUsersService;

    public ReactiveDepartmentsService(ReactiveDepartmentsCrud reactiveDepartmentsCrud, DepartmentsConverter departmentsConverter, UsersService reactiveUsersService) {
        this.reactiveDepartmentsCrud = reactiveDepartmentsCrud;
        this.departmentsConverter = departmentsConverter;
        this.reactiveUsersService = reactiveUsersService;
    }

    @Override
    public Mono<DepartmentBoundary> createDepartment(DepartmentBoundary department) {
        return reactiveDepartmentsCrud.existsById(department.getDeptId())
            .flatMap(exists -> {
                if (exists) {
                    return Mono.empty();
                } else {
                    department.setCreationDate(DateUtils.toString(LocalDate.now()));
                    return Mono.just(department)
                            .map(this.departmentsConverter::toEntity)
                            .flatMap(this.reactiveDepartmentsCrud::save)
                            .map(this.departmentsConverter::toBoundary);
                }
            });
    }

    @Override
    public Flux<DepartmentBoundary> getAll() {
        return this.reactiveDepartmentsCrud
                .findAll()
                .map(this.departmentsConverter::toBoundary);
    }

    @Override
    public Mono<DepartmentBoundary> getSpecificDepartmentById(String deptId) {
        return this.reactiveDepartmentsCrud
                .findById(deptId)
                .map(this.departmentsConverter::toBoundary);
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.reactiveDepartmentsCrud
                .deleteAll()
                .then(this.reactiveUsersService.removeAllDepartmentsFromUsers());
    }
}
