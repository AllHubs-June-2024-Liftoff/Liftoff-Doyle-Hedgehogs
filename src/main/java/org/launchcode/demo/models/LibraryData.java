package org.launchcode.demo.models;

import java.util.ArrayList;

public class LibraryData {

    public static ArrayList<BookshelfVolume> findByColumnAndValue(String column, String value, Iterable<BookshelfVolume> allBookshelfVolumes) {
        ArrayList<BookshelfVolume> results = new ArrayList<>();

        for (BookshelfVolume bookshelfVolume : allBookshelfVolumes) {
            String aValue = getFieldValue(bookshelfVolume, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(bookshelfVolume);
            }

        }
        return results;
    }

    public static String getFieldValue(BookshelfVolume bookshelfVolume, String column){
        String theValue = "";
        if (column.equals("title")){
            theValue = bookshelfVolume.volume.getTitle();
        }
        else if (column.equals("author")){
            theValue = bookshelfVolume.volume.getAuthor();
        }
        return theValue;

    }

}