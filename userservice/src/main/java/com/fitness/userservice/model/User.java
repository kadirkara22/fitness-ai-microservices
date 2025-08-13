package com.fitness.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;

    @Column(unique = true,nullable = false)
    private  String email;

    @Column(nullable = false)
    private  String password;
    private  String firstName;
    private  String lastName;

    @Enumerated(EnumType.STRING)
    private  UserRole role=UserRole.USER;

    @CreationTimestamp
    private  LocalDateTime createdAt;

    @UpdateTimestamp
    private  LocalDateTime updatedAt;
}
