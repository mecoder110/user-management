package com.murtaza.model;

import com.murtaza.entity.City;
import com.murtaza.entity.Country;
import com.murtaza.entity.State;
import lombok.Data;

@Data
public class UserDto {

    private Integer userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String pwd;
    private String updatedPwd;

    private String country;
    private String state;
    private String city;
}
