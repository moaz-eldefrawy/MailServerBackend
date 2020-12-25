package Filters;

import Services.Contact;
import Services.Mail;
import Services.User;

import java.util.*;

public class ContactFilter{
    public static ArrayList<Contact> filter(ArrayList<Contact> contacts, String searchString) {
        ArrayList<Contact> newContacts = new ArrayList<>();

        for(Contact contact : contacts) {

            // Search for name
            if (AbstractFilter.contains(searchString, contact.getName())){
                newContacts.add(contact);
                continue;
            }

            for (String mail : contact.getEmails()){
                if (AbstractFilter.contains(searchString, mail)) {
                    newContacts.add(contact);
                    break;
                }
            }
        }

        return newContacts;
    }

}
