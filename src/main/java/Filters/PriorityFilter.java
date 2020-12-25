package Filters;

import Services.Mail;

public class PriorityFilter extends AbstractFilter {
    @Override
    public boolean passesCriteria(Mail m, Object priority) {
        return m.getPriority() == Integer.parseInt( (String)priority);
    }

}
