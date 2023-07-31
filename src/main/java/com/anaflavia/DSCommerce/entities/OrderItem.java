package com.anaflavia.DSCommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_order_item")
public class OrderItem {

    @EmbeddedId
    private OrdemItemPk id = new OrdemItemPk();

    private Integer quantity;
    private Double price;

}
