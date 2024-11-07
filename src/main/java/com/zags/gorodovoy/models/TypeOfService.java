package com.zags.gorodovoy.models;
import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class TypeOfService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", nullable = false)
    private String serviceType; // "заключение брака", "развод", "рождение ребенка", "смерть"

    public TypeOfService() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}