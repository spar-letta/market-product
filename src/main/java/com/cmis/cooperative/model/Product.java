package com.cmis.cooperative.model;

import com.cmis.cooperative.model.dataType.ProductStatus;
import com.cmis.cooperative.model.vo.User;
import com.cmis.cooperative.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "market_product", name = "products")
@Where(clause = "deleted = false")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView({BaseView.ProductView.class})
    private Long id;

    @Column(name = "name")
    @JsonView({BaseView.ProductView.class})
    private String name;

    @Column(name = "description")
    @JsonView({BaseView.ProductView.class})
    private String description;

    @Column(name = "price")
    @JsonView({BaseView.ProductView.class})
    private BigDecimal price;

    @Column(name = "currency")
    @JsonView({BaseView.ProductView.class})
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonView({BaseView.ProductView.class})
    private ProductStatus status;

    @ManyToMany
    @JoinTable(name = "product_like_users", schema = "market_product",
            joinColumns = @JoinColumn(name = "product_like_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"product_like_id", "user_id"}))
    private Set<User> userLikes = new HashSet<>();
}
