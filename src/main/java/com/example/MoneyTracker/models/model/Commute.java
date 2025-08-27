package com.example.MoneyTracker.models.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "commute")
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String route;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonBackReference
    private Users user;

    public Commute(String route, Integer price, Users user) {
        this.route = route;
        this.price = price;
        this.user = user;
    }
}
