package util;

public class StringUtils
{
    public static boolean isEmpty(String a)
    {
        if(a == null || a.length() <= 0)
            return true;
        else return false;
    }
}
