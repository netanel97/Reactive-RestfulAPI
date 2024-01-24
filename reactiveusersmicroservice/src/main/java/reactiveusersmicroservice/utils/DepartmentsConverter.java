package reactiveusersmicroservice.utils;

import org.springframework.stereotype.Component;
import reactiveusersmicroservice.boundaries.DepartmentBoundary;
import reactiveusersmicroservice.data.DepartmentEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static reactiveusersmicroservice.utils.Constants.DATE_FORMAT;

@Component
public class DepartmentsConverter {


    public DepartmentEntity toEntity(DepartmentBoundary departmentBoundary) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDeptId(departmentBoundary.getDeptId());
        departmentEntity.setDepartmentName(departmentBoundary.getDepartmentName());
        departmentEntity.setCreationDate(departmentBoundary.getCreationDate());
        return departmentEntity;
    }

    public DepartmentBoundary toBoundary(DepartmentEntity departmentEntity) {
        DepartmentBoundary departmentBoundary = new DepartmentBoundary();
        departmentBoundary.setDeptId(departmentEntity.getDeptId());
        departmentBoundary.setDepartmentName(departmentEntity.getDepartmentName());
        departmentBoundary.setCreationDate(departmentEntity.getCreationDate());
        return departmentBoundary;
    }

    public String getNowDateString() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDate.format(dateTimeFormatter);
    }
}
