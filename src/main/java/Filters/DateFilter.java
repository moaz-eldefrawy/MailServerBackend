package Filters;

import Services.Mail;

import java.util.Date;

public class DateFilter extends AbstractFilter {
    @Override
    public boolean passesCriteria(Mail m, Object date) {
        return ((Date) date).getDay() == m.getDate().getDay();
    }

}
