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
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        //resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();

        String nickname = req.getParameter("nickname");// from ajax
        //only use isEmpty()  will lead to NullPointException
        if( nickname == null || nickname.isEmpty()){
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }

        String password = req.getParameter("password");//from ajax
        String lastName = req.getParameter("lastName"); //  from form
        String FirstName= req.getParameter("firstName"); // from form
        String email    = req.getParameter("email"); // from form

        System.out.println(nickname + " " + password );


        List<Object> params = new ArrayList<Object>();

        //ajax
        if(password ==null ||password.isEmpty() || lastName == null || lastName.isEmpty()){
            params.clear();
            params.add(nickname);

            Map<String, Object> list = null;
            try {
                list = jdbcUtils.findSimpleResult(params);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //this is for jquery ajax callback function
            if(list == null || list.isEmpty()){
                out.write("valid");
            }else{
                out.write("invalid");
            }

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
            req.getSession().setAttribute("username", req.getParameter("nickname"));
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }else{
            System.out.println("add faild");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }


    }
}
