package org.launchcode.demo.data;

import org.launchcode.demo.models.Bookshelf;
import org.launchcode.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshelfRepository extends CrudRepository<Bookshelf, Integer> {
    Bookshelf findByUser(User user);
}
