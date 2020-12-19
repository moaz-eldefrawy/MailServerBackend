package Filters;

import Services.Mail;

public class AttachmentsFilter extends AbstractFilter{

    /**
     *
     * @param m : the mail to be judged
     * @param searchString : the name of the attachment being searched for, will be cast to String
     * @return if the mail has an attachment that contains any word of the search string
     */
    @Override
    public boolean passesCriteria(Mail m, Object searchString) {
        for (String attachment : m.attachments){
            if (attachment.toLowerCase().contains(((String)searchString).toLowerCase()))
                return true;
        }
        return false;
    }
}

