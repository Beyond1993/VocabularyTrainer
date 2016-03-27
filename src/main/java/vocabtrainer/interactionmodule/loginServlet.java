package vocabtrainer.interactionmodule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.util.UUID;

/**
 * Created by wanye on 16-3-24.
 */

public class loginServlet extends HttpServlet {

    private boolean validateToken(HttpServletRequest req)
    {
        try
        {
            String sessionToken = (String) req.getSession().getAttribute(
                    "clientToken");
            String clientToken = (String) req.getParameter("clientToken");
            System.out.println("old session token: " + sessionToken);
            System.out.println("old client token: " + clientToken);

            if (null == sessionToken || sessionToken.isEmpty()
                    || clientToken.equals(sessionToken))  {

                String sToken = UUID.randomUUID().toString().toUpperCase();
                req.getSession().setAttribute("clientToken", sToken);
                System.out.println("new token: " + (String) req.getSession().getAttribute(
                        "clientToken"));
                return true;
            }
            else
                return false;
        }
        catch (Exception e)
        {
            //LOGGER.error(e.getMessage());
            return false;
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // super.doGet(req, resp);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<HTML>");

        out.println("<HEAD><TITLE>"+username +"<br>" + password+"</TITLE></HEAD>");

        out.println("<BODY>");

        out.println("<BIG>Hello World</BIG>");

        out.println("</BODY></HTML>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        if (!validateToken(req)) {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }


        String username = req.getParameter("username");
        req.getParameter("password");

        if (username == null || username.equals("")) req.getRequestDispatcher("error.jsp").forward(req, resp);

        User user = new User();
        user.setUserName(req.getParameter("username"));
        user.setPassWord(req.getParameter("password"));
        System.out.println(req.getParameter("username") + req.getParameter("password"));

        if (user.validate()) {
            req.getSession().setAttribute("username", req.getParameter("username"));      //将user放在Attribute中
            req.getRequestDispatcher("index.jsp").forward(req, resp); //校验用户名密码正确，跳转到welcome.jsp

        } else

            req.getRequestDispatcher("error.jsp").forward(req, resp);  //校验用户名密码不正确，跳转到index.jsp
     }

    }
