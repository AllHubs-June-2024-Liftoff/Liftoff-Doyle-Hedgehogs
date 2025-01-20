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
            theValue = bookshelfVolume.getVolume().getTitle();
        }
        else if (column.equals("author")){
            theValue = bookshelfVolume.getVolume().getAuthor();
        }

        return theValue;
    }

    public static ArrayList<BookshelfVolume> filterByLocation(String location, Iterable<BookshelfVolume> bookshelfVolumes){
        ArrayList<BookshelfVolume> resultsByLocation = new ArrayList<>();
        for (BookshelfVolume bookshelfVolume : bookshelfVolumes){
            String theLocation = bookshelfVolume.getBookshelf().getUser().getLocation().getName();
            if (theLocation.equals(location) && bookshelfVolume.getHas_book().equals(true)){
                resultsByLocation.add(bookshelfVolume);
            }
        }
        return resultsByLocation;
    }

    public static ArrayList<BookshelfVolume> filterByBookshelf(Bookshelf bookshelf, Iterable<BookshelfVolume> bookshelfVolumes){
        ArrayList<BookshelfVolume> resultsByBookshelf = new ArrayList<>();
        for (BookshelfVolume bookshelfVolume : bookshelfVolumes){
            Bookshelf theBookshelf = bookshelfVolume.getBookshelf();
            if (theBookshelf.equals(bookshelf)){
                resultsByBookshelf.add(bookshelfVolume);
            }
        }
        return resultsByBookshelf;
    }

}