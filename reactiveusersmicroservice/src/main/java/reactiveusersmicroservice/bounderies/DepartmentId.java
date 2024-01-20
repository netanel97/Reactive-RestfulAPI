package reactiveusersmicroservice.bounderies;

public class DepartmentId {
    private String depId;

    public DepartmentId() {
    }
    public DepartmentId(String depId) {
        this.depId = depId;
    }

    public String getDepId() {
        return depId;
    }
    public void setDepId(String depId) {
        this.depId = depId;
    }

    @Override
    public String toString() {
        return "DepartmentId [depId=" + depId + "]";
    }
}
