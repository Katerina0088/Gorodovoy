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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private String gender;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String passportSeries;
    private String passportNumber;

    @OneToMany(mappedBy = "user")
    private List<Request> requests;

    @OneToMany(mappedBy = "user")
    private List<Document> documents; // Связь с документами

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) { // Исправлено имя метода
        this.requests = requests;
    }

    public List<Document> getDocuments() {
        return documents; // Геттер для документов
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents; // Сеттер для документов
    }
}