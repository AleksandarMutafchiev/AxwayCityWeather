package service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.City;
import entity.Weather;
import org.jvnet.hk2.annotations.Service;
import repository.CityRepository;
import service.CityService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private static final int KELVIN_CELSIUS = -273;

    @Override
    public City createCity(String name) {
        CityRepository cityRepository = new CityRepository();
        return cityRepository.createCity(new City(name));
    }

    @Override
    public List<City> getAllCities() {
        CityRepository cityRepository = new CityRepository();
        return cityRepository.getAllCities();
    }

    @Override
    public City getCityById(Long id) throws IOException {
        CityRepository cityRepository = new CityRepository();
        return getTempForGivenCity(cityRepository.getCityById(id));
    }

    private City getTempForGivenCity(City city) throws IOException {
        URL url = new URL(
                "http://api.openweathermap.org/data/2.5/weather?q=" +
                        city.getName() +
                        "&appid=c6f1b4eeb4f415396e382886fe905a38"
        );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream responseBodyStream;
        if (con.getResponseCode() == 200) {
            responseBodyStream = con.getInputStream();
        } else {
            responseBodyStream = con.getErrorStream();
        }
        StringBuffer sb = new StringBuffer();
        int b = responseBodyStream.read();
        while (b != -1) {
            sb.append((char) b);
            b = responseBodyStream.read();
        }
        String json = sb.toString();
        System.out.println("Result = " + json);

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        int status = obj.get("cod").getAsInt();
        if (status == 200) {
            JsonObject mainData = obj.get("main").getAsJsonObject();
            Weather weather = new Weather();
            weather.setCurrentTemp(mainData.get("temp").getAsInt() + KELVIN_CELSIUS);
            weather.setMinTemp(mainData.get("temp_min").getAsInt() + KELVIN_CELSIUS);
            weather.setMaxTemp(mainData.get("temp_max").getAsInt() + KELVIN_CELSIUS);
            city.setWeather(weather);

        }
        return city;
    }
}
