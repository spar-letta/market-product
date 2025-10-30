package com.cmis.cooperative.model.vo;

import com.cmis.cooperative.views.BaseView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sub_counties", schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubCounty implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "public_id", updatable = false, insertable = false)
    @JsonView({BaseView.NameSocietyView.class, BaseView.AmendmentView.class})
    private UUID publicId;

    @Column(name = "name", updatable = false, insertable = false)
    @JsonView({BaseView.NameSocietyView.class, BaseView.AmendmentView.class})
    private String name;
}
