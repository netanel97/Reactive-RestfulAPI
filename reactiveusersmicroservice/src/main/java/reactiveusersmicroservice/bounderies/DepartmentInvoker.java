package reactiveusersmicroservice.bounderies;

public class DepartmentInvoker {
    private DepartmentId department;


    public DepartmentInvoker() {
    }
    public DepartmentInvoker(DepartmentId department) {
        this.department = department;
    }


    public DepartmentId getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentId department) {
        this.department = department;
    }


    @Override
    public String toString() {
        return "DepartmentInvoker{" +
                "department=" + department +
                '}';
    }
}
