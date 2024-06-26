package com.nelio.spring.course.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelio.spring.course.entities.pk.OrderItemPK;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tb_order_item")
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();
    private Integer quantity;
    private Double price;

    public OrderItem(Order order, Product product, Integer quantity) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = product.getPrice();
        id.getOrder().items.add(this);
        id.getProduct().items.add(this);
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Integer getQty() {
        return quantity;
    }

    public Double getSubTotal() {
        return price * quantity;
    }
}
