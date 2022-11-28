package exercise.repository;

import exercise.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    // BEGIN
    @Query(nativeQuery = true,value = "select * from cities as c where LOWER(c.name) like LOWER(CONCAT(:character, '%'))")
    List<City> findByNameStartsWith(String character);

    @Query(nativeQuery = true,value = "select * from cities as c order by c.name")
    List<City> findCitiesOrderedByName();
    // END
}
