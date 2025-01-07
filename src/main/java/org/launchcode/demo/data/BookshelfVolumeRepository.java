package org.launchcode.demo.data;

import org.launchcode.demo.models.BookshelfVolume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshelfVolumeRepository extends CrudRepository<BookshelfVolume, Integer> {
    BookshelfVolume findByBookshelfId (Integer id);
}
