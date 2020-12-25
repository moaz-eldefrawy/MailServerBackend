package Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class Sort {

    /**
     * sorting according to priority
     *
     * @param mails
     */
    public static void priority(ArrayList<Mail> mails) {
       if (mails.size() == 0)
<<<<<<< HEAD
            return;//throw new RuntimeException();
=======
            throw new RuntimeException();

>>>>>>> fdd9430010cc41ff8c3a55234923f7a519d3b3f3
        PriorityQueue q = new PriorityQueue();
        for (int i = 0; i < mails.size(); i++) {

            Mail mail = mails.get(i);
            	System.out.println("KEY: " + mail.getPriority());
            //		System.out.println(mail.getSubject());
            
            q.insert(mail, mail.getPriority());
        }
        mails.clear();
        int queueSize = q.size();
        //System.out.println(q.size());

        for (int i = 0; i < queueSize; i++) {
            //	System.out.println(i);
            Object k = q.removeMin();
            mails.add((Mail)k);
        }
    }

    public static void iterativeQuickSort(ArrayList<Mail> mails, String sortType) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        stack.push(mails.size());
        while (stack != null && !stack.isEmpty()) {
            int end = (int) stack.pop();
            int start = (int) stack.pop();
            if (end - start < 2) {
                continue;
            }
            int p = start + ((end - start) / 2);
            if (sortType.equals("default"))
                p = partitionDate(mails, p, start, end);
            else if (sortType.equals("subject"))
                p = partitionSubject(mails, p, start, end);
            else if (sortType.equals("sender"))
                p = partitionSender(mails, p, start, end);
            else if (sortType.equals("body"))
                p = partitionBody(mails, p, start, end);

            stack.push(p + 1);
            stack.push(end);
            stack.push(start);
            stack.push(p);
        }

    }

    private static int partitionBody(ArrayList<Mail> mails, int position, int start, int end) {
        int low = start;
        int high = end - 2;
        Mail piv = (Mail) mails.get(position);
        String pivot = piv.getBodyText();
        swap(mails, position, end - 1);

        while (low < high) {

            Mail l = (Mail) mails.get(low);
            Mail h = (Mail) mails.get(high);

            if ((l.getBodyText()).compareTo(pivot) < 0) {
                low++;
            }
            else if (!((h.getBodyText()).compareTo(pivot) < 0)) {
                high--;
            }
            else {
                swap(mails, low, high);
            }
        }

        int index = high;
        Mail h = (Mail) mails.get(high);
        if ((h.getBodyText()).compareTo(pivot) < 0) {
            index++;
        }
        swap(mails, end - 1, index);

        return index;
    }

    private static int partitionSender(ArrayList<Mail> mails, int position, int start, int end) {
        int low = start;
        int high = end - 2;
        Mail piv = (Mail) mails.get(position);
        String pivot = piv.getSender();
        swap(mails, position, end - 1);

        while (low < high) {

            Mail l = (Mail) mails.get(low);
            Mail h = (Mail) mails.get(high);

            if ((l.getSender()).compareTo(pivot) < 0) {
                low++;
            }
            else if (!((h.getSender()).compareTo(pivot) < 0)) {
                high--;
            }
            else {
                swap(mails, low, high);
            }
        }

        int index = high;
        Mail h = (Mail) mails.get(high);
        if ((h.getSender()).compareTo(pivot) < 0) {
            index++;
        }
        swap(mails, end - 1, index);

        return index;
    }

    private static int partitionSubject(ArrayList<Mail> mails, int position, int start, int end) {
        int low = start;
        int high = end - 2;
        Mail piv = (Mail) mails.get(position);
        String pivot = piv.getSubject();
        swap(mails, position, end - 1);

        while (low < high) {

            Mail l = (Mail) mails.get(low);
            Mail h = (Mail) mails.get(high);

            if ((l.getSubject()).compareTo(pivot) < 0) {
                low++;
            }
            else if (!((h.getSubject()).compareTo(pivot) < 0)) {
                high--;
            }
            else {
                swap(mails, low, high);
            }
        }

        int index = high;
        Mail h = (Mail) mails.get(high);
        if ((h.getSubject()).compareTo(pivot) < 0) {
            index++;
        }
        swap(mails, end - 1, index);

        return index;
    }

    /**
     * for date sorting from newest to oldest
     *
     * @param mails
     * @param position
     * @param start
     * @param end
     * @return
     */

    private static int partitionDate(ArrayList<Mail> mails, int position, int start, int end) {
        int low = start;
        int high = end - 2;
        Mail piv = (Mail) mails.get(position);
        Date pivot = piv.getDate();
        swap(mails, position, end - 1);

        while (low < high) {
            Mail l = (Mail) mails.get(low);
            Mail h = (Mail) mails.get(high);
            if (l.getDate().compareTo(pivot) >= 0) {
                low++;
            }
            else if (!(h.getDate().compareTo(pivot) >= 0)) {
                high--;
            }
            else {
                swap(mails, low, high);
            }
        }

        int index = high;
        Mail h = (Mail) mails.get(high);

        if (h.getDate().compareTo(pivot) >= 0) {
            index++;
        }

        swap(mails, end - 1, index);
        return index;
    }

    public static void swap(ArrayList<Mail> mails, int i, int j) {
        Mail temp = (Mail) mails.get(i);
        mails.set(i, mails.get(j));
        mails.set(j, temp);
    }

}