package com.zags.gorodovoy.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private TypeOfService service; // Связь с видом услуги

    private Date requestDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "status", nullable = false)
    private String status; // "выполнена" или "не выполнена"

    public Task() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeOfService getService() {
        return service;
    }

    public void setService(TypeOfService service) {
        this.service = service;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date applicationDate) {
        this.requestDate = applicationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }}
