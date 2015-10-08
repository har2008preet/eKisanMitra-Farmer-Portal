package wolverine.example.com.btp_farmer;

import android.content.Context;

/**
 * Created by Wolverine on 10-07-2015.
 */
public class Database {

    private static String TAG = Database.class.getSimpleName();
    String number;
    Context _context;

    public Database(Context applicationContext) {
        this._context = applicationContext;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber(){
        return this.number;
    }

}
