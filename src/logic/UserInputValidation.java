package logic;

/**
 * Created by Nikolaj on 30-04-2017.
 */
public class UserInputValidation
{
    public static boolean notValid_string(String input)
    {
        if(input.contains("'") || input.contains("-"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean notValid_integer(String input)
    {
        try
        {
            int i = Integer.parseInt(input);
            return true;

        } catch (Exception e)
        {
            return false;
        }
    }
}
