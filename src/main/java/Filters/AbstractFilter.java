package Filters;

import Services.Mail;

import java.util.ArrayList;
import java.util.Locale;


public abstract class AbstractFilter {

    public ArrayList<Mail> filter(ArrayList<Mail> mails, Object val){
        ArrayList<Mail> filtered = new ArrayList<>();
        for(Mail m : mails){
            if (passesCriteria(m, val)){
                filtered.add(m);
            }
        }

        return filtered;
    }

    /**
     *
     * @param searchKeyWord the word you're looking for
     * @param searchString  where you are looking for it
     * @return  if the searchString contains searchKeyWord (case insensitive)
     */
    public static boolean contains (String searchKeyWord, String searchString){
        return searchString.toLowerCase().contains(searchKeyWord.toLowerCase());
    }

    abstract public boolean passesCriteria(Mail m, Object val);
}

