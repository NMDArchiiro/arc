package com.arc.app.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
public class BaseEntity extends AuditableEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "voided")
    protected Boolean voided;

}
