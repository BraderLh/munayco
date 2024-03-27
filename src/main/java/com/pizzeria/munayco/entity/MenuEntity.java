package com.pizzeria.munayco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pizzeria.munayco.entity.common.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "menu")
public class MenuEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "menu_file", unique = true, nullable = false)
    private String menuFile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_items",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<ItemsEntity> itemsEntitySet = new HashSet<>();
}
