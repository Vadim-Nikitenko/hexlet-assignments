package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.model.Weather;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    ObjectMapper objectMapper;

    // BEGIN
    @GetMapping("/cities/{id}")
    public Object getCity(@PathVariable(value = "id") String id) throws JsonProcessingException {
        City city = cityRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CityNotFoundException("city not found"));
        return objectMapper.readValue(weatherService.getWeather(city.getName()), Object.class);

    }

    @GetMapping("/search")
    public List<Map<String, String>> getCityByName(@RequestParam(value = "name", required = false) String name) throws Exception {
        if(name == null || name.isEmpty()) {
            List<City> citiesOrderedByName = cityRepository.findCitiesOrderedByName();
            return map(citiesOrderedByName);
        }
        List<City> cityList = cityRepository.findByNameStartsWith(name);
        return map(cityList);
    }

    private List<Map<String, String>> map(List<City> citiesOrderedByName) throws JsonProcessingException {
        List<Map<String, String>> result = new ArrayList<>();
        for (City c : citiesOrderedByName) {
            Weather weather = objectMapper.readValue(weatherService.getWeather(c.getName()), Weather.class);
            Map<String, String> map = new HashMap<>();
            map.put("name", c.getName());
            map.put("temperature", weather.getTemperature());
            result.add(map);
        }
        return result;
    }

    // END
}

