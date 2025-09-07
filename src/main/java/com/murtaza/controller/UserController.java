package com.murtaza.controller;

import com.murtaza.entity.City;
import com.murtaza.entity.Country;
import com.murtaza.entity.State;
import com.murtaza.model.*;
import com.murtaza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/mail-taken/{email}")
    public ResponseEntity<Boolean> isEmailExist(@PathVariable String email) {
        return new ResponseEntity<>(userService.isEmailExist(email), HttpStatus.OK);
    }

    @GetMapping("/drop-down/country")
    public ResponseEntity<List<CountryDto>> getCountryList() {
        return new ResponseEntity<>(userService.getCountries(), HttpStatus.OK);
    }

    @GetMapping("/drop-down/state/{countryId}")
    public ResponseEntity<List<StateDto>> getStateList(@PathVariable Integer countryId) {
        return new ResponseEntity<>(userService.getStates(countryId), HttpStatus.OK);
    }

    @GetMapping("/drop-down/city/{stateId}")
    public ResponseEntity<List<CityDto>> getCityList(@PathVariable Integer stateId) {
        return new ResponseEntity<>(userService.getCities(stateId), HttpStatus.OK);
    }

    @PostMapping("/reqister")
    public ResponseEntity<Boolean> register(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.register(userDto), HttpStatus.OK);
    }

    @PostMapping("/login/{email}/{pwd}")
    public ResponseEntity<Map<String, String>> login(@PathVariable String email,
                                                     @PathVariable String pwd) {
        UserDto login = userService.login(email, pwd);
        Map<String, String> response = new HashMap<>();
        if (login.getUpdatedPwd() == null || login.getUpdatedPwd().trim().equals("")) {
            response.put("status", "UPDATE_REQUIRED");
            response.put("message", "Password need to be updated");
            response.put("redirect-url", "/user/reset-pwd");
        } else {

            response.put("status", "SUCCESS");
            response.put("message", "login successful");
            response.put("redirect-url", "/user/quotes");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-pwd")
    public ResponseEntity<Boolean> resetPwd(@RequestBody ResetPwdDto resetPwdDto) {
        return new ResponseEntity<>(userService.updatePwd(resetPwdDto), HttpStatus.OK);
    }

    @GetMapping("/quotes")
    public ResponseEntity<QuoteApiResponseDto> getQuotes() {
        return new ResponseEntity<>(userService.getQuote(), HttpStatus.OK);
    }
}
