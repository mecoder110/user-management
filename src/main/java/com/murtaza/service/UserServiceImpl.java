package com.murtaza.service;

import com.murtaza.entity.City;
import com.murtaza.entity.Country;
import com.murtaza.entity.State;
import com.murtaza.entity.User;
import com.murtaza.model.*;
import com.murtaza.repository.CityRepository;
import com.murtaza.repository.CountryRepository;
import com.murtaza.repository.StateRepository;
import com.murtaza.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PwdGenerator pwdGenerator;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CountryDto> getCountries() {
        List<Country> all = countryRepository.findAll();
        return all.stream().map(country -> {
                    return modelMapper.map(country, CountryDto.class);
                }

        ).collect(Collectors.toList());
    }

    @Override
    public List<StateDto> getStates(Integer countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow();
        List<State> byCountry = stateRepository.findByCountry(country);
        return byCountry.stream().map(state -> {
            return modelMapper.map(state, StateDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public List<CityDto> getCities(Integer stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow();

        List<City> byState = cityRepository.findByState(state);
        return byState.stream().map(city -> {
            return modelMapper.map(city, CityDto.class);
        }).collect(Collectors.toList());

    }


    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean register(UserDto userDto) {
        boolean alreadyTaken = userRepository.existsByEmail(userDto.getEmail());
        if(alreadyTaken) throw new RuntimeException("Email already taken");
        User user = modelMapper.map(userDto, User.class);
        user.setPwd(pwdGenerator.pwdGen());
        User save = userRepository.save(user);
        return true;
    }

    @Override
    public UserDto login(String email, String pwd) {
        User userFromDb = userRepository.findByEmail(email);
        if (userFromDb.getPwd().equals(pwd)) {
            return modelMapper.map(userFromDb, UserDto.class);
        } else {
            throw new RuntimeException("Invalid Cred");
        }

    }

    @Override
    public boolean updatePwd(ResetPwdDto resetPwdDto) {
        User userFromDb = userRepository.findByEmail(resetPwdDto.getEmail());
        if (userFromDb.getPwd().equals(resetPwdDto.getOldPwd())) {
            if (resetPwdDto.getNewPwd().equals(resetPwdDto.getConfirmPwd())) {
                userFromDb.setPwd(resetPwdDto.getNewPwd());
                userFromDb.setUpdatedPwd(resetPwdDto.getNewPwd());
                User save = userRepository.save(userFromDb);
                return true;
            } else {
                throw new RuntimeException("New and Confirm Password not match");
            }
        } else {
            throw new RuntimeException("New and Confirm Password not match");
        }
    }

    @Override
    public QuoteApiResponseDto getQuote() {
        return new QuoteApiResponseDto(1, "Why Developer use dark mode," +
                " Coz light attract bugs! Heheheh", "unknown");
    }
}