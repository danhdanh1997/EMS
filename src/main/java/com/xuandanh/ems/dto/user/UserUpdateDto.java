package com.xuandanh.ems.dto.user;

import com.xuandanh.ems.enums.Roles;

public class UserUpdateDto {
    // skipping updating passord as of now
    private Integer id;
    private String firstName;
    private String lastName;
    private Roles role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
