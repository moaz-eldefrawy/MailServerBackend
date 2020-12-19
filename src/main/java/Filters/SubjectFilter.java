package Filters;

import Services.Mail;

import java.util.Locale;

public class SubjectFilter extends AbstractFilter{

    /**
     *
     * @param m: mail to be judged
     * @param searchString: the search string. will be cast to String
     * @return  if the mail's subject contains any word of the search string
     */
    @Override
    public boolean passesCriteria(Mail m, Object searchString) {
        // Split search string by spaces
        String[] keywords = ((String)searchString).split("\\s+");

        for (String keyword : keywords){
            if (m.getSubject().toLowerCase().contains(keyword.toLowerCase()))
                return true;
        }
        return false;
    }
}
