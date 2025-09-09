package com.murtaza.controller;

import com.murtaza.model.*;
import com.murtaza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/mail-taken/{email}")
    public ResponseEntity<ApiResponse<Boolean>> isEmailExist(@PathVariable String email) {
        ApiResponse response = new ApiResponse();
        boolean emailExist = userService.isEmailExist(email);
        if (emailExist) {
            response.setStatus(200);
            response.setMessaage("Email Available");
            response.setData(emailExist);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessaage("Email Not Available");
            response.setData(emailExist);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/drop-down/country")
    public ResponseEntity<ApiResponse<List<CountryDto>>> getCountryList() {
        ApiResponse response = new ApiResponse();
        List<CountryDto> countries = userService.getCountries();
        if (countries.isEmpty()) {
            response.setStatus(500);
            response.setMessaage("No Country Found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(200);
            response.setMessaage("Country Data Found");
            response.setData(countries);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/drop-down/state/{countryId}")
    public ResponseEntity<ApiResponse<List<StateDto>>> getStateList(@PathVariable Integer countryId) {
        ApiResponse response = new ApiResponse();
        List<StateDto> states = userService.getStates(countryId);
        if (states.isEmpty()) {
            response.setStatus(500);
            response.setMessaage("No State Found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(200);
            response.setMessaage("State Data Found");
            response.setData(states);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/drop-down/city/{stateId}")
    public ResponseEntity<ApiResponse<List<CityDto>>> getCityList(@PathVariable Integer stateId) {
        ApiResponse response = new ApiResponse();
        List<CityDto> cities = userService.getCities(stateId);
        if (cities.isEmpty()) {
            response.setStatus(500);
            response.setMessaage("No City Found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(200);
            response.setMessaage("City Data Found");
            response.setData(cities);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/reqister")
    public ResponseEntity<ApiResponse<Boolean>> register(@RequestBody UserDto userDto) {
        ApiResponse response = new ApiResponse();
        boolean register = userService.register(userDto);
        if (register) {
            response.setStatus(200);
            response.setMessaage("Register Successful");
            response.setData(register);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessaage("Register Not Successful");
            response.setData(register);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody UserDto userDto) {
        ApiResponse response = new ApiResponse();
        UserDto login = userService.login(userDto);

        if (login != null) {
            response.setStatus(200);
            response.setMessaage("Login Successful");
            response.setData(login);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(401);
            response.setMessaage("Bad Credential");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset-pwd")
    public ResponseEntity<ApiResponse<Boolean>> resetPwd(@RequestBody ResetPwdDto resetPwdDto) {
        ApiResponse response = new ApiResponse();
        boolean updated = userService.updatePwd(resetPwdDto);

        if (updated) {
            response.setStatus(200);
            response.setMessaage("Reset Password Successful");
            response.setData(updated);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(401);
            response.setMessaage("Bad Request");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/quotes")
    public ResponseEntity<ApiResponse<QuoteApiResponseDto>> getQuotes() {
        ApiResponse response = new ApiResponse();
        QuoteApiResponseDto quote = userService.getQuote();
        if (quote != null) {
            response.setStatus(200);
            response.setMessaage("Api fetch successfully");
            response.setData(quote);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatus(500);
            response.setMessaage("Internal Server Issue");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
