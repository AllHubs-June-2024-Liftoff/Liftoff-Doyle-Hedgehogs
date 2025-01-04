package org.launchcode.demo.data;

import org.launchcode.demo.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {
    Iterable<Location> findAllByOrderByNameAsc();
}
