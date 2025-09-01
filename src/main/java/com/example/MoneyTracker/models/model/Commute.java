package com.example.MoneyTracker.models.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "commute")
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String route;

    private Integer price;
    
    private Date date;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonBackReference
    private Users user;

    public Commute(String route, Integer price, Date date, Users user) {
        this.route = route;
        this.price = price;
        this.user = user;
        this.date = date;
    }
}
