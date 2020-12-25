package Filters;

import Services.Mail;

public class BodyFilter extends AbstractFilter{

    @Override
    public boolean passesCriteria(Mail m, Object searchString) {
        // Split search string by spaces
        String[] keywords = ((String)searchString).split("\\s+");

        for (String keyword : keywords){
            if (m.getBodyText().toLowerCase().contains(keyword.toLowerCase()))
                return true;
        }
        return false;
    }

}
