package reactiveusersmicroservice.controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactiveusersmicroservice.bounderies.DepartmentBoundary;
import reactiveusersmicroservice.logic.DepartmentsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/departments"})
public class ReactiveDepartmentsController {

    private DepartmentsService departmentsService;

    public ReactiveDepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<DepartmentBoundary> createDepartment(@RequestBody DepartmentBoundary departmentBoundary) {
        return this.departmentsService
                .createDepartment(departmentBoundary)
                .log();
    }

    @GetMapping(
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<DepartmentBoundary> getAllDepartments (){
        return this.departmentsService
                .getAll()
                .log();
    }

    @GetMapping(
            path = {"/{deptId}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<DepartmentBoundary> getDepartment(@PathVariable("deptId") String deptId) {
        return this.departmentsService
                .getSpecificDepartmentById(deptId)
                .log();
    }

    /**
     * Delete all departments from the database
     * @return void
     */
    @DeleteMapping
    public Mono<Void> deleteAll(){
        return this.departmentsService
                .deleteAll()
                .log();
    }
}
