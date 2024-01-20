package reactiveusersmicroservice.logic;


import org.springframework.stereotype.Service;
import reactiveusersmicroservice.bounderies.DepartmentBoundary;
import reactiveusersmicroservice.dal.ReactiveDepartmentsCrud;
import reactiveusersmicroservice.utils.DepartmentsConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveDepartmentsService implements DepartmentsService {
    private DepartmentsConverter departmentsConverter;
    private ReactiveDepartmentsCrud reactiveDepartmentsCrud;

    public ReactiveDepartmentsService(ReactiveDepartmentsCrud reactiveDepartmentsCrud, DepartmentsConverter departmentsConverter) {
        this.reactiveDepartmentsCrud = reactiveDepartmentsCrud;
        this.departmentsConverter = departmentsConverter;
    }

    @Override
    public Mono<DepartmentBoundary> createDepartment(DepartmentBoundary department) {
        return reactiveDepartmentsCrud.existsById(department.getDeptId())
            .flatMap(exists -> {
                if (exists) {
                    return Mono.just(new DepartmentBoundary()); //TODO: need to check if need to throw exception or 200ok
                } else {
                    department.setCreationDate(departmentsConverter.getNowDateString());
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
                .deleteAll();
    }
}
