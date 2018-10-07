package cn.xyz.goods.category.servlet;

import cn.itcast.servlet.BaseServlet;
import cn.xyz.goods.category.domain.Category;
import cn.xyz.goods.category.service.CategoryService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	CategoryService categoryservice = new CategoryService();
    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
    	List<Category> parents = categoryservice.findAll();
    	req.setAttribute("parents", parents);
		return "/jsps/left.jsp";
    }

}
