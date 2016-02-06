package shared;

/**
 * Created by filippos on 21/1/2016.
 */
public class AccountProperties {
    private String username;
    private String password;
    private String hash ;

    private AccountProperties() {
        // WS_IP = getResources().getString(R.string.wsip);

        // WS_IP = Resources.getSystem().getString(R.string.wsip);
    }

    private static AccountProperties instance = null;

    public static void init() {
        if (instance == null) {
            instance = new AccountProperties();
        }
    }

    public static AccountProperties getInstance() {
        if (instance == null) {
            instance = new AccountProperties();
        }
        return instance;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.username = hash;
    }


}
