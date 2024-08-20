package com.digitinary.usermanagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String phoneNumber;
    private Set<Membership> membership;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    @Column(name = "user_name")
    public String getName() {
        return name;
    }

    @Column(name = "user_phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @OneToMany
    @JoinColumn(name = "user_id")
    public Set<Membership> getMembership() {
        return membership;
    }
}
