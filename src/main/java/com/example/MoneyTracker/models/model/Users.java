package com.example.MoneyTracker.models.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String password;

    private Integer budget;

    private Integer total;

    @OneToMany(mappedBy = "commute", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Commute commute;

    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Items item;

    public Users(String name, String email, String password, Integer budget, Integer total) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.budget = budget;
        this.total = total;
    }
}
