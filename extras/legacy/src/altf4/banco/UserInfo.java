package altf4.banco;
public class UserInfo {

    private static String USER_ID = "";
    
    public static String getUserID(){
        
        return USER_ID;
    }
    
    public static void setUserID(String user){
        
        USER_ID = user;
    }
    
    public static void leftAccount(){
        
        USER_ID = "";
    }
}