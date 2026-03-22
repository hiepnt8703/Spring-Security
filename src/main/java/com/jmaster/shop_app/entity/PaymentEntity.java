package com.jmaster.shop_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;
    private Double amount;
    private String status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
