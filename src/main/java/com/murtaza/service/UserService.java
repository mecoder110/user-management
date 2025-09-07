package com.murtaza.service;


import com.murtaza.entity.City;
import com.murtaza.entity.Country;
import com.murtaza.entity.State;
import com.murtaza.model.*;

import java.util.List;

public interface UserService {

    public List<CountryDto> getCountries();

    public List<StateDto> getStates(Integer countryId);

    public List<CityDto> getCities(Integer stateId);

    public boolean isEmailExist(String email);

    public boolean register(UserDto userDto);

    public UserDto login(String email, String pwd);

    public boolean updatePwd(ResetPwdDto resetPwdDto);
    // third party API
    public QuoteApiResponseDto getQuote();
}
