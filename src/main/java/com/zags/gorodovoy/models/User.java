package com.zags.gorodovoy.models;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    //private List<Employee> employees;

    private String login;
    private String gender;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String passportSeries;
    private String passportNumber;
    private Boolean isAdmin = false;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(mappedBy = "user")
    private List<Document> documents; // Связь с документами

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    } */

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void login(String login) {
        this.login = login;
    }

    public void IsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Task> getRequests() {
        return tasks;
    }

    public void setRequests(List<Task> tasks) { // Исправлено имя метода
        this.tasks = tasks;
    }

    public List<Document> getDocuments() {
        return documents; // Геттер для документов
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents; // Сеттер для документов
    }
}