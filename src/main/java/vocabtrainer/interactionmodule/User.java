package vocabtrainer.interactionmodule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by wanye on 16-3-24.
 */
public class User {

    private static String userName;
    private static String passWord;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public static boolean validate()
    {
        String url = "jdbc:mysql://localhost:3306/chinese";
        String user= "root";
        String dbpassword = "root";
        boolean result = false;

        try {
            String driver ="com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection   con = DriverManager.getConnection(url,user, dbpassword);

            if(con==null){
                System.out.println("can't open DBConnection");
            }

            String sql = "select * from user where username=? and password=password(?)";

            PreparedStatement pstmt=con.prepareStatement(sql);

            pstmt.setString(1, userName);
            pstmt.setString(2, passWord);
            System.out.println(sql);


            ResultSet rs=pstmt.executeQuery();
            if(rs.next())
            {
                System.out.println("PASS");
                result=true;

            }
            else
            {
                System.out.println("FAIL");
                result=false;
            }
            pstmt.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }

}
