package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class Employee {

    private String firstName;
    private String lastName;
    private int id;
    private String code;
    private String location;
    private JobProfile jobProfile;

    private Employee() {
    }

    public Employee(int id, String firstName, String lastName) {
        this(firstName, lastName, id, firstName.substring(0, 1) + lastName.substring(0, 2), "Test", new JobProfile("TestProfile"));
    }

    public Employee(String firstName, String lastName, int id, String code, String location, JobProfile jobProfile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.code = code.toLowerCase();
        this.location = location;
        this.jobProfile = jobProfile;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public JobProfile getJobProfile() {
        return jobProfile;
    }
}
