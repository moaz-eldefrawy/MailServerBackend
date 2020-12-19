package Filters;

import Services.Mail;

import java.util.ArrayList;


abstract class AbstractFilter {

    public ArrayList<Mail> meetCriteria (ArrayList<Mail> mails, Object val){
        ArrayList<Mail> filtered = new ArrayList<>();
        for(Mail m : mails){
            if (passesCriteria(m, val)){
                filtered.add(m);
            }
        }

        return filtered;
    }

    abstract public boolean passesCriteria(Mail m, Object val);
}

