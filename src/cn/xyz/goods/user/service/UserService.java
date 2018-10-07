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
    	   * 1.��ȫuser����Ϣ uid status activationCode
    	   */
    	  user.setUid(CommonUtils.uuid());
    	  user.setStatus(false);
          user.setActivationCode(CommonUtils.uuid());
          /**
           * 2.ʹ��dao��ע��
           */
           try {
			userdao.add(user);
		} catch (SQLException e) {
			 throw new RuntimeException(e);
		}
        //   System.out.println("�û�Ϊ"+user);
           /**
            *3. �����ʼ�
            * ��ȡ�����ļ�
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
           * ��ռλ���滻��ע����
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
			    throw	new UserException("�û�������");
			}if(user.getStatus()) {
				 throw	new UserException("�û��Ѿ�����");
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
       * ͨ������������
       * @param uid
       * @param loginpass
       * @param newpass
     * @throws UserException 
       */
      public void updatePassword(String uid,String loginpass,String newpass) throws UserException{
        try {
			boolean have = userdao.findUpdate(uid,loginpass);
			if(!have) {
			     throw new UserException("�������");
			}
			userdao.updatePassword(uid, loginpass, newpass);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
      
      }
//     public static void main(String[] args) {
//    	  Session session = MailUtils.createSession("smtp.qq.com", "1041221997", "seraheuvpagkbbbe");
//    	  Mail mail = new Mail("1041221997@qq.com","1041221997@qq.com","����","����");
//    	  try {
//			MailUtils.send(session, mail);
//		} catch (MessagingException | IOException e) {
//			e.printStackTrace();
//		}
//	}
}
