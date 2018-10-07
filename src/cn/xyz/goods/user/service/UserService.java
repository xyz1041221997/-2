package cn.xyz.goods.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.management.RuntimeErrorException;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.xyz.goods.user.dao.UserDao;
import cn.xyz.goods.user.domain.User;

public class UserService {
	 UserDao userdao = new UserDao();
	  public boolean ajaxUsername(String username){
    	   try {
			return userdao.ajaxUsername(username);
		} catch (SQLException e) {
			 throw new RuntimeException(e);
		}
      }
      public boolean ajaxEmail(String email){
    	 try {
			return userdao.ajaxEmail(email);
		}  catch (SQLException e) {
			 throw new RuntimeException(e);
		}
      }
      public void add(User user) throws IOException {
    	  /**
    	   * 1.补全user的信息 uid status activationCode
    	   */
    	  user.setUid(CommonUtils.uuid());
    	  user.setStatus(false);
          user.setActivationCode(CommonUtils.uuid());
          /**
           * 2.使用dao层注册
           */
           try {
			userdao.add(user);
		} catch (SQLException e) {
			 throw new RuntimeException(e);
		}
        //   System.out.println("用户为"+user);
           /**
            *3. 发送邮件
            * 读取配置文件
            */
           Properties pro = new Properties();
           pro.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
           String host = pro.getProperty("host");
           String name = pro.getProperty("username");
           String password = pro.getProperty("password");
           String from = pro.getProperty("from");
           String title = pro.getProperty("subject");
           //System.out.println(host+name+password+from);
          Session session = MailUtils.createSession(host, name, password);
          
          
          /**
           * 把占位符替换成注册码
           */
          String content = MessageFormat.format(pro.getProperty("content"), user.getActivationCode());
          Mail mail = new Mail(from,user.getEmail(),title,content);
          try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
      }
      public User activationCode(String activationcode) throws UserException {
          
    	  try {
    		  User user= userdao.findCode(activationcode);
			if(user==null) {
			    throw	new UserException("用户不存在");
			}if(user.getStatus()) {
				 throw	new UserException("用户已经激活");
			}
			userdao.updatestatus(user.getUid(), true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	  
      }
      public User login(String name,String password) throws SQLException {
		return userdao.login(name, password);  	  
      }
      /**
       * 通过老密码找人
       * @param uid
       * @param loginpass
       * @param newpass
     * @throws UserException 
       */
      public void updatePassword(String uid,String loginpass,String newpass) throws UserException{
        try {
			boolean have = userdao.findUpdate(uid,loginpass);
			if(!have) {
			     throw new UserException("密码错误");
			}
			userdao.updatePassword(uid, loginpass, newpass);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
      
      }
//     public static void main(String[] args) {
//    	  Session session = MailUtils.createSession("smtp.qq.com", "1041221997", "seraheuvpagkbbbe");
//    	  Mail mail = new Mail("1041221997@qq.com","1041221997@qq.com","测试","测试");
//    	  try {
//			MailUtils.send(session, mail);
//		} catch (MessagingException | IOException e) {
//			e.printStackTrace();
//		}
//	}
}
