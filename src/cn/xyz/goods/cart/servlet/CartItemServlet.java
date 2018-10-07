package cn.xyz.goods.cart.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.xyz.goods.book.domain.Book;
import cn.xyz.goods.cart.domain.CartItem;
import cn.xyz.goods.cart.service.CartItemService;
import cn.xyz.goods.user.domain.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartItemServlet
 */
@WebServlet("/CartItemServlet")
public class CartItemServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	CartItemService Cservice = new CartItemService();
	    public String myCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException{
	    	   User user =(User) req.getSession().getAttribute("loginuser");//��ȡ������Ϣ     
	    	   String uid = user.getUid();
	        	List<CartItem> CartItems = Cservice.findByUser(uid);//��ȡ���ﳵ����Ϣ
	    	    req.setAttribute("cartItemList",CartItems);
	    	    return "/jsps/cart/list.jsp";
	    }
	  
        public String add(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException {
        	     Map map = req.getParameterMap();
        	     CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
        	     Book book = CommonUtils.toBean(map, Book.class);
        	     cartItem.setBook(book);
        	     User user = (User) req.getSession().getAttribute("loginuser");
        	     cartItem.setUser(user);
        	     
        	     Cservice.add(cartItem);
        	     return myCart(req,resp);
        }
        public String batchDelete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException {
            String CartItemIds = req.getParameter("cartItemIds");
            System.out.println(CartItemIds);
            Cservice.batchDelete(CartItemIds);
            return myCart(req,resp);
        }
        public String updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
            String CartItemId = req.getParameter("cartItemId");
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            CartItem cartItem = Cservice.updateQuantity(CartItemId, quantity);
            
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
            sb.append(",");
            sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
            sb.append("}");
            System.out.println(sb);
            resp.getWriter().print(sb);
            return null;
        }
        public String loadCartItems(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
            String cartItemIds = req.getParameter("cartItemIds");//cartItemIds
            //System.out.println("x4ua"+cartItemIds);
            double total = Double.parseDouble(req.getParameter("total"));
            //System.out.println("综合"+total);
            List<CartItem> cartItems2 = Cservice.loadCartItems(cartItemIds);
            req.setAttribute("total",total);
            req.setAttribute("cartItemList",cartItems2);
            return "/jsps/cart/showitem.jsp";
            
        }
}
