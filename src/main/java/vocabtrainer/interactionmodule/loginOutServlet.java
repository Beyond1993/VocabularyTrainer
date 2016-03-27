package vocabtrainer.interactionmodule;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Created by wanye on 16-3-24.
 */
public class loginOutServlet extends HttpServlet {
    /**
     * @category 退出登录的Servlet,注销
     * @author Bird
     */
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req,resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);//防止创建Session
        if(session == null){
            //response.sendRedirect("/index.jsp");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }
        session.removeAttribute("username");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
        // response.sendRedirect("index.jsp");
    }
}