package cn.xyz.goods.user.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.xyz.goods.user.domain.User;
import cn.xyz.goods.user.service.UserException;
import cn.xyz.goods.user.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * ����UserService����
	 */
	UserService userservice = new UserService();
	/**
	 * �ж��Ƿ��û��Ѿ�ע��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void ajaxUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String username = request.getParameter("username");
	   boolean b =  userservice.ajaxUsername(username);
	    response.getWriter().println(b);
	}
/**
 * �ж������Ƿ���ʹ��
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	public  void ajaxEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String email = request.getParameter("email");
         boolean b = userservice.ajaxEmail(email);
         response.getWriter().println(b);
	}
	/**
	 * �ж���֤���Ƿ�ǰ��һ��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public  void varifycode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String code1 = request.getParameter("code");
         String code2 = (String) request.getSession().getAttribute("vCode");
       //  System.out.println(code2+code1.equalsIgnoreCase(code2));
         response.getWriter().println(code1.equalsIgnoreCase(code2));      
	}
	/**
	 * 1.ע����Ϣ��װ��Userʵ������
	 * user���еı������������ȡ����Ϣ��һ��
	 * @throws IOException 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//�ѱ����ݵ����ݷ�װ��user����
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
	
		 /**
		  * 2.У�������Ϣ�����������Ϣ
		  * user��Ϣ��loginname loginpass reloginpass email code;
		  * �Ѵ�����Ϣװ��setAttribute��
		  */
		Map map=varifyRegist(user,request.getSession());
		//System.out.println(map.size() + " "+user);
	//	System.out.println(map);
		if(map.size()>0) {
			request.setAttribute("form", user);
			 request.setAttribute("errors", map);
			 return "/jsps/user/regist.jsp";
		}
		/**
		 * 3.ʹ�÷�����ע����Ϣ
		 */	
		userservice.add(user);
		return "/jsps/user/success.jsp";
	}
	/**
	 * ��֤������Ϣ
	 * @param user
	 */
	public Map<String,String> varifyRegist(User user,HttpSession session) {
           Map<String,String> map = new HashMap<String,String>();
           /**
            * 1.�û�����֤
            */
           String name= user.getLoginname();
           if(name==null||name.trim().isEmpty()) {
        	   map.put("loginnameError", "�û�������Ϊ��");
           }else  if(!(name.length()>3&&name.length()<20)){
        	   map.put("loginnameError", "�û���ֻ����3~20");
           }else if(userservice.ajaxUsername(name)) {
        	    map.put("loginnameError","�û����Ѵ���");
           }
           /**
            * 2.����������֤
            */
           String  password=user.getLoginpass();
           if(password==null||password.isEmpty()) {
        	   map.put("loginpasswordError","�������벻��Ϊ��");
           }else if(!(password.length()>3&&password.length()<20)) {
        	   map.put("loginpasswordError","��������ֻ����3~20");
           }
           /**
            * 3.ȷ��������֤
            */
           String password2 = user.getReloginpass();
           if(password2==null||password2.isEmpty()) {
        	   map.put("loginpassword2Error","ȷ�����벻��Ϊ��");
           }else if(!password2.equals(password)) {
        	   map.put("loginpassword2Error","ǰ���������벻һ��");
           }
           /**
            *4. email��֤
            */
           String email = user.getEmail();
           String z = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
           if(email==null||email.trim().isEmpty()) {
        	   map.put("loginemailError","y���䲻��Ϊ��");
           }else if(!Pattern.matches(z, email)) {
        	   map.put("loginemailError","y�����ʽ����ȷ");
           }
           /**
            * 5.��֤����֤
            */
           String code1 = user.getCode();
           String code2 = (String) session.getAttribute("vCode");
           if(code1==null||code1.trim().isEmpty()) {
        	   map.put("loginecode","��֤�벻��Ϊ��");
           }else if(!code1.equalsIgnoreCase(code2)) {
        	   map.put("loginecode","��֤�벻��ȷ");
           }
		return map;
	}
	/**
	 * �����
	 * 1.��ȡ������
	 * 2.
	 */
	public String activation(HttpServletRequest request, HttpServletResponse response) {
	    String activationCode = request.getParameter("activationCode");
	    try {
			userservice.activationCode(activationCode);
			request.setAttribute("msg", "����ɹ�");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}	
		return "jsps/user/success.jsp";	
	}
	/**
	 * ���뺯��
	 * @param request
	 * @param response
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String name= request.getParameter("username");
		String password = request.getParameter("password");
	    try {
			User user = userservice.login(name, password);
			if(user==null) {
				request.setAttribute("msg", "�û������������");
				return "/jsps/user/login.jsp";//����ʧ�ܻ��ǵ������
			}
			request.setAttribute("msg", "����ɹ�");
			request.getSession().setAttribute("loginuser", user);//���������û���Ϣ
			response.sendRedirect("/goods/jsps/main.jsp");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �޸����뺯��
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public String updatePassword(HttpServletRequest request,HttpServletResponse response) throws IOException {
	//	System.out.println(request.getSession().getAttribute("loginuser"));
		User user = (User) request.getSession().getAttribute("loginuser");
		User userform = CommonUtils.toBean(request.getParameterMap(), User.class);
		System.out.println(user);
		if(user==null) {//�û�δ����
			request.setAttribute("msg", "�㻹û�е���");
			return "jsps/user/login.jsp";
        }
		  try {
			userservice.updatePassword(user.getUid(), userform.getLoginpass(), userform.getNewpass());
			request.setAttribute("msg", "�޸�����ɹ�");
			return "jsps/user/success.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", user);
			return "jsps/user/pwd.jsp";
		}
	}
	
   /**
    * �˳��û�
    * @param req
    * @param resp
    * @throws ServletException
    * @throws IOException
    */
	public  String quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 req.getSession().invalidate();
		 req.getSession().setAttribute("loginuser", null);
		 req.getSession().removeAttribute("loginuser");
		 return "/jsps/user/login.jsp";
	}
}
