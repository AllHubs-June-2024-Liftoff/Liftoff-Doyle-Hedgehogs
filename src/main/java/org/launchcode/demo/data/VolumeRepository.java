package org.launchcode.demo.data;


import org.launchcode.demo.models.Volume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeRepository extends CrudRepository<Volume, Integer> {
}
