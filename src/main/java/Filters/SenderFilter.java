package Filters;

import Services.Mail;

public class SenderFilter extends AbstractFilter{
    /**
     *
     * @param m: mail to be judged
     * @param sender: the sender email, will be cast to String
     * @return  if the mail's sender matches the passed sender
     */
    @Override
    public boolean passesCriteria(Mail m, Object sender) {
        return m.getSender().equals((String) sender);
    }
}
