package reactiveusersmicroservice.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactiveusersmicroservice.logic.DepartmentService;

@RestController
@RequestMapping(path = {"/departments"})
public class ReactiveDepartmentController {

    private DepartmentService departmentService;

    public ReactiveDepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

}
