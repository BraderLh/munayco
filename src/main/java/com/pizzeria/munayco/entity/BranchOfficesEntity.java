package com.pizzeria.munayco.entity;

import com.pizzeria.munayco.entity.common.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "branch_offices")
public class BranchOfficesEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_office_id")
    private Integer branchOfficesId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "image")
    private String image;

    @Column(name = "location")
    private String location;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "phone")
    private String phone;

    @Column(name = "telephone")
    private String telephone;
}
