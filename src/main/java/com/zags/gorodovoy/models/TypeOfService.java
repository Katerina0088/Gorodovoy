package com.zags.gorodovoy.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "services")
public class TypeOfService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", nullable = false)
    private String serviceType; // "заключение брака", "развод", "рождение ребенка", "смерть"

}