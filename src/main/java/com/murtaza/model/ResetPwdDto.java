package com.murtaza.model;

import lombok.Data;

@Data
public class ResetPwdDto {

    private String email;
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}
