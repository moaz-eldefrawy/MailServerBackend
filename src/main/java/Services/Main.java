package Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {

        //System.out.println(user.password);
        Main.sortTesting();
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


        ArrayList mails = new ArrayList();

        Mail mail1 = new Mail("ahmed@gmVail.com", "subject3", new Date(),
                3);
        Mail mail2=new Mail("basel@gmVail.com", "subject2", new Date(),
                1 );
        Mail mail3=new Mail("ziad@gmVail.com", "subject1", new Date(),
                2 );


       // mail1.date= mail1.date.plusDays(2);
      //  mail2.date= date.now().plusDays(1);
       // mail3.date=LocalDate.now().plusDays(3);



        mails.add(mail1);
        mails.add(mail2);
        mails.add(mail3);
        Main.printMails(mails);
        Sort.priority(mails);
        Main.printMails(mails);
        Sort.iterativeQuickSort(mails,"Subject");
        Main.printMails(mails);
        //    Sort.iterativeQuickSort(mails,"Sender");
       // Sort.iterativeQuickSort(mails,"Body");

    }

    static void printMails(ArrayList arr){
        for(int i=0; i<arr.size(); i++){
            System.out.println(((Mail) arr.get(i)).subject);
        }
        System.out.print("\n");
    }

}
