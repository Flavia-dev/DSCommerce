package com.anaflavia.DSCommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;
    private OrderStatus status;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();


    @OneToOne(mappedBy = "order" , cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name =  "client_id")
    private User client;

}
