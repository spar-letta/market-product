package com.cmis.cooperative.model.vo;


import com.cmis.cooperative.views.BaseView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(schema = "public", name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "public_id", updatable = false, insertable = false)
    @JsonView({BaseView.ProductView.class})
    private UUID publicId;

    @Column(name = "first_name", updatable = false, insertable = false)
    private String firstName;

    @Column(name = "user_name", updatable = false, insertable = false)
    private String userName;

    @Column(name = "last_name", updatable = false, insertable = false)
    private String lastName;

    @Column(name = "user_name", updatable = false, insertable = false)
    private String surname;

    @Column(name = "other_name", updatable = false, insertable = false)
    private String otherName;

    @Column(name = "user_code_number")
    private UUID userCodeNumber;

    @Column(name = "contact_email")
    @JsonView({BaseView.NameSearchView.class, BaseView.AmendmentView.class})
    private String contactEmail;

    @Transient
    @JsonView({BaseView.ProductView.class})
    private String fullName;

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(" ").append(lastName);
        if (otherName != null)
            sb.append(" ").append(otherName);
        return sb.toString();
    }
}
