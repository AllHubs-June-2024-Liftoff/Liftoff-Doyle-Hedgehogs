package org.launchcode.demo.data;

import org.launchcode.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}

//lets us fetch data from SQL table and perform CRUD operations
