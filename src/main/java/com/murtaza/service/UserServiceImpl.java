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
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
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
    private EmailService emailService;
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
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean register(UserDto userDto) {

        boolean alreadyTaken = userRepository.existsByEmail(userDto.getEmail());
        if (alreadyTaken) throw new RuntimeException("Email already taken");

        Country country = countryRepository.findById(userDto.getCountryId())
                .orElseThrow();
        State state = stateRepository.findById(userDto.getStateId())
                .orElseThrow();
        City city = cityRepository.findById(userDto.getCityId())
                .orElseThrow();

        User user = modelMapper.map(userDto, User.class);

        user.setPwd(pwdGenerator.pwdGen(6));
        user.setUpdatedPwd("NO");
        user.setCountry(country);
        user.setState(state);
        user.setCity(city);
        User save = userRepository.save(user);

        // Email Send logic
        String subject="Login to temp password";
        String body = "Temp Password is " +user.getPwd();
        emailService.sendMail(user.getEmail(),subject,body);
        return true;
    }

    @Override
    public UserDto login(UserDto userDto) {
        User userFromDb = userRepository.findByEmail(userDto.getEmail());
        if (userFromDb.getPwd().equals(userDto.getPwd())) {
            return modelMapper.map(userFromDb, UserDto.class);
        } else {
            throw new RuntimeException("Invalid Cred");
        }
    }

    @Override
    public boolean updatePwd(ResetPwdDto resetPwdDto) {
        if (!resetPwdDto.getNewPwd().equals(resetPwdDto.getConfirmPwd())) {
            throw new RuntimeException("Password not match, retype!!");
        } else {
            User userFromDb = userRepository.findByEmail(resetPwdDto.getEmail());

            if (userFromDb.getPwd().equals(resetPwdDto.getOldPwd())) {
                userFromDb.setUpdatedPwd("YES");
                userFromDb.setPwd(resetPwdDto.getNewPwd());
                User save = userRepository.save(userFromDb);
                return true;
            }
            return false;
        }
    }

    @Override
    public QuoteApiResponseDto getQuote() {

        String apiUrl = "https://zenquotes.io/api/random";
        RestTemplate rt = new RestTemplate();
        // QuoteApiResponseDto body = rt.getForEntity(apiUrl, QuoteApiResponseDto.class).getBody();

        QuoteApiResponseDto[] response = rt.getForEntity(apiUrl, QuoteApiResponseDto[].class).getBody();
        log.info("========>>  " + response);
        QuoteApiResponseDto body = modelMapper.map(response[0], QuoteApiResponseDto.class);
        return body;
    }
}