package com.murtaza.model;

import lombok.Data;

@Data
public class UserDto {

    private Integer userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String pwd;
    private String updatedPwd;

    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
}
