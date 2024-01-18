package com.saboresdigitais.quickeats.store.domain.entity;

import java.time.LocalDateTime;

import com.saboresdigitais.quickeats.store.domain.enums.ShippingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shipping_info")
public class ShippingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    private String trackingNumber;
    
    @OneToOne(mappedBy = "shippingInfo")
    private Order order;

    private LocalDateTime estimatedDeliveryDate;
}
