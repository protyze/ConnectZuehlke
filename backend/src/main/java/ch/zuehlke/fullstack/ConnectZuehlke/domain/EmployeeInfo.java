package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class EmployeeInfo {
    private Employee employee;
    private byte[] picture;

    public EmployeeInfo(Employee employee, byte[] picture) {
        this.employee = employee;
        this.picture = picture;
    }

    public Employee getEmployee() {
        return employee;
    }

    public byte[] getPicture() {
        return picture;
    }
}
