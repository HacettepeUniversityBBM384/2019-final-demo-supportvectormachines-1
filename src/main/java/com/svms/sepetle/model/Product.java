package com.svms.sepetle.model;


import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@DynamicUpdate
@Table(name = "product", schema = "sepetledb")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Length(min = 3, message = "*Name must have at least 5 characters")
    private String name;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private User seller;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Category category;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "*Quantity has to be non negative number")
    private Integer quantity;

    @Column(name = "rate", nullable = false)
    @Min(value = 0, message = "*Rate has to be non negative number")
    private Float rate;

    @Column(name = "review_count", nullable = false)
    @Min(value = 0, message = "*Reviews has to be non negative number")
    private Integer review_count;

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.00", message = "*Price has to be non negative number")
    private BigDecimal price;

    @ManyToMany(mappedBy = "products")
    private Collection<Order> orders;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getReview_count() {
        return review_count;
    }

    public void setReview_count(Integer review_count) {
        this.review_count = review_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
