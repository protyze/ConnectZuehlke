package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.Objects;

import java.time.LocalDate;

public class Employee {

    private String firstName;
    private String lastName;
    private int id;
    private String code;
    private String location;
    private JobProfile jobProfile;
    private LocalDate entryDate;
    private String title;

    private Employee() {
    }

    public Employee(int id, String firstName, String lastName) {
        this(firstName, lastName, id, "Test", new JobProfile("TestProfile"), LocalDate.of(1980,1,1),"test title");
    }

    public Employee(String firstName, String lastName, int id, String location, JobProfile jobProfile, LocalDate entryDate, String title) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.code = firstName.substring(0, 1) + lastName.substring(0, 2);
        this.location = location;
        this.jobProfile = jobProfile;
        this.entryDate = entryDate;
        this.title = title;
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

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
