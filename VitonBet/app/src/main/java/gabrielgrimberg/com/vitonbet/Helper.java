package gabrielgrimberg.com.vitonbet;

/**
 * Created by Ecoste on 11/22/2017.
 */

import com.google.firebase.auth.FirebaseAuth;

public class Helper {
    public static int GetBalance() {
        xAuth = FirebaseAuth.getInstance();

        xDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("");

        //return
    }
}
