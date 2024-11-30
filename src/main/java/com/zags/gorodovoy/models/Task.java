package com.zags.gorodovoy.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_edite")
    private Date dateEdit;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_finish")
    private Date dateFinish;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    @Column(name = "employee_id", nullable = true)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private TypeOfService service;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private Status status;

    @Column(name = "status_id", nullable = false)
    private Long statusId;


    public Task() {}
    private Date requestDate;
}




