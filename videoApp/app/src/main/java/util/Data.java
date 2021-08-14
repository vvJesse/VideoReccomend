package util;

public class Data
{
    private String[] userIDs = new String[200];
    private String[] userPasswords = new String[200];
    private int size = 0;
    public void register(String userID, String userPasswrod)
    {
        userIDs[size] = userID;
        userPasswords[size] = userPasswrod;
        size++;
    }
    public boolean login(String userID, String userPassword)
    {
        for(int i = 0; i < size; i++)
            if(userIDs[i].equals(userID) && userPasswords[i].equals(userPassword))
                return true;

            return false;
    }
    private static Data instance;
    private Data()
    {
    }
    public static Data getInstance()
    {
        if(instance == null)
            instance = new Data();
        return instance;
    }
}
