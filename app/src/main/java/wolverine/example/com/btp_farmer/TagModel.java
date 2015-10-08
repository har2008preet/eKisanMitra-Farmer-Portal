package wolverine.example.com.btp_farmer;

/**
 * Created by Wolverine on 08-07-2015.
 */

public class TagModel {

    private  String Tag="";
    private  String Description="";

    /*public TagModel(String Tag,String Description) {
        super();
        this.Tag = Tag;
        this.Description= Description;
    }
*/
    /*********** Set Methods ******************/

    public void setTag(String Tag)
    {
        this.Tag = Tag;
    }


    public void setDescription(String Description)
    {
        this.Description= Description;
    }

    /*********** Get Methods ****************/

    public String getTag()
    {
        return this.Tag;
    }

    public String getDescription()
    {
        return this.Description;
    }
}