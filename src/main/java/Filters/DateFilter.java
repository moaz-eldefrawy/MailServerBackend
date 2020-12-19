package Filters;

import Services.Mail;

import java.util.Date;

public class DateFilter extends AbstractFilter {
    /**
     *
     * @param m, mail to be judged
     * @param val, will be cast to Date
     * @return  If m.getDate().getDay() == dat.getDay()
     */
    @Override
    public boolean passesCriteria(Mail m, Object val) {
        Date mDate = m.getDate();
        Date date = (Date) val;

        return mDate.getDay() == date.getDay()
                && mDate.getMonth() == date.getMonth()
                && mDate.getYear() == date.getYear();
    }

}
