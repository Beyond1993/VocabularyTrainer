package vocabtrainer.interactionmodule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Printable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by wanye on 16-3-27.
 */
@WebServlet("/register")
public class registerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        String lastName = req.getParameter("lastName");
        String FirstName= req.getParameter("firstName");
        String email    = req.getParameter("email");

        if(nickname.isEmpty()){
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }

        List<Object> params = new ArrayList<Object>();
        params.add(nickname);

        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();


        Map<String, Object> list = null;
        try {
            list = jdbcUtils.findSimpleResult(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //this is for jquery ajax callback function
        if(list.isEmpty()){
            out.write("valid");
        }else{
            out.write("invalid");
        }
        if(password.isEmpty()){
            return;
        }

        //add user;
        params.clear();
        params.add(nickname);
        params.add(password);
        //params.add(lastName);
       // params.add(FirstName);
        //params.add(email);

        System.out.println(lastName + " " + FirstName + " " + email);
        boolean res = false;
        try {
            res = jdbcUtils.addUser(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(res){
            System.out.println("add successfully");
        }else{
            System.out.println("add faild");
        }



    }
}
