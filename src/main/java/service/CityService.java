package service;

import entity.City;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface CityService {
    City createCity(String name);

    List<City> getAllCities();

    City getCityById(Long id) throws IOException;
}
