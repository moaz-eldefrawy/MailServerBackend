import java.io.File;

public class Index {

    public static String IndexFilePath;
   /*

   
    public static DoubleLinkedList getListFromIndexFile()
    {
    	File readFrom=new File(IndexFilePath);
    	if(readFrom.length()==0)
         return new DoubleLinkedList();
        
        DoubleLinkedList currentEmails=new DoubleLinkedList();
         currentEmails=(DoubleLinkedList) FileManager.getFile(IndexFilePath);
        return currentEmails;
       
    }
   
    /*
     * set IndexFilePath & toIndexFilePath
     */

   /*
    public static void  manipulateIndexInfo(String destination, String ID, String action)
    {
       DoubleLinkedList mailsInfo = getListFromIndexFile();
       for(int i=0;i<mailsInfo.size();i++)
       {
           MailBasicInfo m=(MailBasicInfo) mailsInfo.get(i);
           if(m.ID.equals(ID))
           {
               if(action.equals("remove"))
               {
                   mailsInfo.remove(i);
                   FileManager.writeToFile(mailsInfo,IndexFilePath);
               }
               if(action.equals("move"))
               {   mailsInfo.remove(i);
                   FileManager.writeToFile(mailsInfo,IndexFilePath);
                   String temp = Index.IndexFilePath;
                   Index.IndexFilePath=destination;
                   writeToIndexFile(m);
                   Index.IndexFilePath = temp;
                   
               }
           }
       }
       
    }*/
 
}