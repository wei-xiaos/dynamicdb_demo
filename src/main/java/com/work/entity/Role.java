package com.work.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="tbl_role")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_name")
    private String roleName;
}
