package cn.xyz.goods.book.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.xyz.goods.book.domain.Book;
import cn.xyz.goods.book.service.BookService;
import cn.xyz.goods.pager.PageBean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	BookService bookservice  = new BookService();
	/**
	 * ���pc��ǰҳ��
	 * @param req
	 * @return
	 */
	public static int getPc(HttpServletRequest req) {
		String parameter = req.getParameter("pc");
		int pc = 1;
		if(parameter!=null&&parameter.trim()!=null) {
			try {
				pc =Integer.parseInt(parameter);//����쳣�򷵻ؿ�
			} catch (RuntimeException e) {
			}
		}
		return pc;
	}
	/**
	 * http://localhost:8080/goods/BookServlet?method=findByCategory&cid=sss
	 * ���ܻ�ȡ��&pc=xx
	 * @param req
	 * @return
	 */
	public String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI();//��ȡ��/goods/BookServlet
		String md = req.getQueryString();//��ȡ��method=findByCategory&cid=sss
		url = url +"?"+ md;
		int index = url.lastIndexOf("&pc");
		//��pc����
		if(index!=-1) {
			  url = url.substring(0, index);
		}
		return url;
		
	}
	/**
	 * 1.���pc���Ϊ����Ϊ1
	 * 2.��ȡurl��ַ
	 * 3.��ȡ��ѯ�����ȱ�����
	 * 4.ʹ��pc �� cid ִ��bookservice.findCategory����
	 * 5.��ת��/jsps/book/list.jsp����ʾ��ѯ�����鼮��Ϣ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    int pc = getPc(request);
	    String url = getUrl(request);
		String cid = request.getParameter("cid");
		PageBean<Book> bk = bookservice.findByCategory(cid,pc);
		bk.setUrl(url);
		request.setAttribute("pb", bk);
		return "/jsps/book/list.jsp";
	}
   public String findByBname(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	   int pc=getPc(request);
	   String bname= request.getParameter("bname");
	   String url=getUrl(request);
	   PageBean<Book> bk = bookservice.findByBName(bname, pc);
	   bk.setUrl(url);
	   request.setAttribute("pb", bk);
	return "/jsps/book/list.jsp";
   }
   public String findByCombination(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	     Book book = CommonUtils.toBean( request.getParameterMap(),Book.class);
	//   System.out.println(book);
	   int pc = getPc(request);
	   String url = getUrl(request);
	   PageBean<Book> bk = bookservice.findByCombination(book, pc);
	   bk.setUrl(url);
	   request.setAttribute("pb", bk);
	   return "/jsps/book/list.jsp";
	   
   }
   public String findByAuthor(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	   String author = request.getParameter("author");
	   response.setCharacterEncoding("UTF-8");
	   try {
		author = new String(author .getBytes("iso8859-1"),"utf-8");
	} catch (UnsupportedEncodingException e) {
	
		e.printStackTrace();
	}
	    System.out.println(author);
	    int pc = getPc(request);
	    String url = getUrl(request);
	    PageBean<Book> pb = bookservice.findByAuthor(author, pc);
	    request.setAttribute("pb", pb);
	   return "/jsps/book/list.jsp";
   }
   public String findByPress(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	    String press = request.getParameter("press");
	    try {
			press = new String(press .getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} 
	    System.out.println(press);
	    int pc = getPc(request);
	    String url = getUrl(request);
	    PageBean<Book> pb = bookservice.findByPress(press, pc);
	    request.setAttribute("pb", pb);
	   return "/jsps/book/list.jsp";
  }
   public String load(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	   String bid = request.getParameter("bid");
	   Book book = bookservice.load(bid);
	   request.setAttribute("book", book);
	   return "jsps/book/desc.jsp";
   }
}
