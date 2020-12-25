package Services;

import Controllers.MainController;
import Filters.*;

import javax.annotation.Priority;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {

        sortTesting();
   /*     //System.out.println(user.password);
        HashMap<String, ArrayList<String>> folders = new HashMap<String, ArrayList<String>>();
        folders.put("inbox", new ArrayList<String>());
        UUID ID = UUID.randomUUID();
        UUID ID2 = UUID.fromString( ID.toString() );
        System.out.println( ID == ID2 );
        folders.get("inbox").add(ID.toString());
        folders.get("inbox").add("fine");
        folders.get("inbox").remove("fine");
        for(int i=0; i<folders.get("inbox").size(); i++){
            System.out.println( folders.get("inbox").get(i) );
        }
        //MainController a = new MainController();*/
    }

    public static void sortTesting(){
        PriorityQueue q=new PriorityQueue();
        q.insert(5,3);
        q.insert(4,10);
        q.insert(2,1);
        q.insert(-1,5);
        System.out.println(q.removeMin());
        System.out.println(q.removeMin());
        System.out.println(q.removeMin());
        System.out.println(q.size());


        ArrayList<Mail> mails = new ArrayList<Mail>();
        LocalDate k = ((new Date()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        System.out.println("date:");
        System.out.println(k);
        System.out.println(k.getDayOfMonth());
        System.out.println(k.getMonth());
        System.out.println(k.getYear());
        System.out.print("\n");
        Mail mail1 = new Mail("ahmed@gmVail.com", "subject3 subject2", new Date(),
                3);
        mail1.setBodyText("one two three");
        Mail mail2=new Mail("basel@gmVail.com", "subject2", new Date(),
                1 );

        mail2.setBodyText("one four two");
        Mail mail3=new Mail("ziad@gmVail.com", "subject1", new Date(2018, 11, 25),
                2 );


        mail3.setBodyText("five four two");

        DateFilter s = new DateFilter();

       // mail1.date= mail1.date.plusDays(2);
      //  mail2.date= date.now().plusDays(1);
       // mail3.date=LocalDate.now().plusDays(3);



        mails.add(mail1);
        mails.add(mail2);
        mails.add(mail3);
        mails = s.meetCriteria(mails, new Date(System.currentTimeMillis()));
        Main.printMails(mails);
        //Sort.priority(mails);
        //Main.printMails(mails);
        //Sort.iterativeQuickSort(mails,"Subject");
        //Main.printMails(mails);
        //    Sort.iterativeQuickSort(mails,"Sender");
       // Sort.iterativeQuickSort(mails,"Body");

    }

    static void printMails(ArrayList<Mail> arr){
        for(int i=0; i<arr.size(); i++){
            System.out.println((arr.get(i)).getSubject());
        }
        System.out.print("\n");
    }

}
