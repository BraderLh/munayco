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
@NamedQuery(name = "UsersEntity.findByEmail", query = "select u from UsersEntity u where u.email=:email")
@NoArgsConstructor
@Setter
@Table(name = "users")
public class UsersEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "names", nullable = false)
    private String names;

    @Column(name = "surnames", nullable = false)
    private String surnames;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;
}
