package cn.xyz.goods.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.xyz.goods.user.domain.User;

public class UserDao {
     
      QueryRunner qr =  new TxQueryRunner();
      /**
       * 验证用户是否已经注册
       * 大于0则已经注册
     * @throws SQLException 
       */
      public boolean ajaxUsername(String username) throws SQLException {
    	   String sql = "select count(1) from t_user where loginname=?";
 	      Number u = (Number) qr.query(sql,new ScalarHandler(),username);
 	      return u.intValue()>0;
      }
      public boolean ajaxEmail(String email) throws SQLException {
    	  String sql = "select count(1) from t_user where email=?";
    	  Number n= (Number)qr.query(sql, new ScalarHandler(),email);
    	  return n.intValue()>0;
      }
      public void add(User user) throws SQLException {
    	  String sql = "INSERT into t_user VALUE(?,?,?,?,?,?)";
    	  Object[] parm = {user.getUid(),user.getLoginname(),user.getLoginpass(),
    			  user.getEmail(),user.getStatus(),user.getActivationCode()};
    	  qr.update(sql,parm);
    	//  System.out.println("jihewei"+parm);
      }
      /**
       * 用户激活
       * @param code
       * @return
       * @throws SQLException
       */
      public User findCode(String code) throws SQLException {
    	  String sql = "select * from t_user where activationCode=?";
    	 User user= qr.query(sql,new BeanHandler<User>(User.class),code);
		return user;
      }
      public void  updatestatus(String uid,boolean status) throws SQLException {
    	  String sql = "update t_user set status=? where uid=?";
    	  qr.update(sql, status,uid);
      }
      /**
       * 登入函数把查询到的user信息封装到User实体类中
       * @param name
       * @param password
       * @return
       * @throws SQLException
       */
      public User login(String name,String password) throws SQLException {
    	   String sql = "select * FROM t_user where loginname =? AND loginpass = ?";
    	   User user = qr.query(sql, new BeanHandler<User>(User.class),name,password);
;    	  return user;
      }
      /**
       * 查询是否老密码正确
       * @param uid
       * @return
       * @throws SQLException
       */
      public boolean findUpdate(String uid,String loginpass) throws SQLException {
    	  String sql = "select count(*) from t_user where uid=? and loginpass=?";
    	Number n =  (Number) qr.query(sql,new ScalarHandler(), uid,loginpass);
		return n.intValue() > 0;   	  
      }
      /**
       * 修改密码
       * @param uid
       * @param loginpass
       * @param newpass
       * @throws SQLException
       */
      public void updatePassword(String uid,String loginpass,String newpass) throws SQLException {
    	   String sql = "update t_user set loginpass=? where uid=? and loginpass=?";
    	   qr.update(sql,newpass,uid,loginpass);
      }
//      public static void main(String[] args) throws SQLException {
//    	 UserDao ud = new UserDao();
//        System.out.println(ud.findUpdate("1AA84B5702B24760A5B5D7FB11FB11A1"));  
//	}
}
