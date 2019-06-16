package com.svms.sepetle.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "address")
    private String address;

    @Column(name = "purchase_date")
    private Date purchase_date;

    @ManyToMany() //order_id den order'a ait products alınabilir
    @JoinTable(name = "order_detail", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public Long getOrder_id() {
        return id;
    }

    public void setOrder_id(Long order_id) {
        this.id = order_id;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal quantity) {
        this.price = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String comment) {
        this.address = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", purchase_date=" + purchase_date +
                ", products=" + products +
                '}';
    }
}
