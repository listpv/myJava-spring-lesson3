package com.geekbrains.entity;

import javax.persistence.*;

@Entity
@Table(name = "goods_data")
public class GoodData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "good_id")
    Good good;

    @Column(name = "price")
    private Double price;

    public GoodData(User user, Good good) {
        this.user = user;
        this.good = good;
        this.price = good.getPrice();
        user.goodData.add(this);
        good.goodData.add(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoodData{" +
                "user=" + user +
                ", good=" + good +
                ", price=" + price +
                '}';
    }
}
