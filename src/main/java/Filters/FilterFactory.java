package Filters;

public class FilterFactory {

    public static AbstractFilter getFilter(String type){
        switch (type){

        }
        type = type.toLowerCase();
        switch(type) {
            case "attachments":
                return new AttachmentsFilter();
            case "body":
                return new BodyFilter();
            case "date":
                return new DateFilter();
            case "priority":
                return new PriorityFilter();
            case "sender":
                return new SenderFilter();
            case "subject":
                return new SubjectFilter();

            default:
                return null;
                // code block
        }
    }
}
