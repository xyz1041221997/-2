package cn.xyz.goods.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xyz.goods.book.domain.Book;
import cn.xyz.goods.cart.domain.CartItem;
import cn.xyz.goods.user.domain.User;

public class CartItemDao {
        QueryRunner qr = new TxQueryRunner();
        /**
         * ��ѯ���ﳵ�Ƿ������ͬ��Ʒ
         * @param bid
         * @param uid
         * @return
         * @throws SQLException
         */
        public CartItem findBybidanduid(String bid,String uid) throws SQLException {
        	 String sql = "select * from t_cartitem where bid=? and uid = ?";
        	 Map<String, Object> map = qr.query(sql,new MapHandler(), bid,uid);
        	 CartItem cartItem = getCartItem(map);
        	 return cartItem;
        }
        /**
         * û��ʱֱ�������Ʒ��Ϣ
         * @param cartItem
         * @throws SQLException 
         */
        public void addBook(CartItem cartItem) throws SQLException {
        	 String sql = "insert into t_cartitem(cartItemId,quantity,bid,uid) value(?,?,?,?)";
        	 Object[] param = {cartItem.getCartItemId(),cartItem.getQuantity(),
        			            cartItem.getBook().getBid(),cartItem.getUser().getUid()};
        	 qr.update(sql,param);	 
        }
        /**
         * 修改图书数量
         * @param cartItemId
         * @param quantity
         * @throws SQLException
         */
        public void updateQuantity(String cartItemId,int quantity) throws SQLException {
        	  String sql = "update t_cartitem set quantity=? where cartItemId = ?";
        	   qr.update(sql,quantity,cartItemId);
        }
        /**
         * 查询图书信息通过 cartItemId
         * @param cartItemId
         * @return
         * @throws SQLException
         */
        public CartItem findBycartItemId(String cartItemId) throws SQLException {
                  String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and cartItemId = ?";
                  Map<String, Object> map = qr.query(sql,new MapHandler(),cartItemId);
                  return getCartItem(map);
        }
      
        /**
         * ��һ��map��ѯ����Ϣӳ�䵽��Ӧ��ʵ������ �γ�CartItem��
         * ���ﳵ�� ��װ������ ����User��
         * @param map
         * @return
         */
        public CartItem getCartItem(Map<String,Object>  map) {
        	if(map == null||map.size()==0) {
        		return null;
        	}
	         Book book = CommonUtils.toBean(map, Book.class);
	         User user = CommonUtils.toBean(map, User.class);
	         CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
	         cartItem.setBook(book);
	         cartItem.setUser(user);
	         return cartItem;  	
        }
        /**把list中map装换成list CartItem对象
         * �Ѷ��map<List<map>>ӳ���List<CartItem>
         * @param maplist
         * @return
         */
        public List<CartItem> getCartItemlist(List<Map<String,Object>> maplist){
        	List<CartItem> CartItemList = new ArrayList<CartItem>();
        	for(Map<String,Object> map:maplist){
        		CartItem cartItem =  getCartItem(map);
        		CartItemList.add(cartItem);
        	}
        	return CartItemList;
        }
        /**
         * �Ѳ�ѯ������Ϣӳ�䵽һ��map ������
         * @param uid
         * @return
         * @throws SQLException
         */
        public List<CartItem> findByUser(String uid) throws SQLException{
        	String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and uid=? order by c.orderBy";
        	List<Map<String, Object>> maplist = qr.query(sql,new MapListHandler(),uid);
            
        	return getCartItemlist(maplist);
        }
        /**
         * 分割传入的CartItemId数组得到cartItemId In(....)
         * @param len
         * @return
         */
        public String getwheresql(int len) {
             StringBuilder wheresql = new StringBuilder("cartItemId in(");
             for(int i = 0;i < len;i++) {
                 wheresql.append("?");
                 if(i < len -1) {
                     wheresql.append(",");
                 }         
             }
             wheresql.append(")");
             return wheresql.toString();
        }
        /**
         * 批量删除书籍
         * @param CartItemIds
         * @throws SQLException
         */
        public void batchDelete(String CartItemIds) throws SQLException {
            Object[] CartItems = CartItemIds.split(",");
            String wheresql = getwheresql(CartItems.length);
            String sql = "delete from t_cartitem where " + wheresql;
            qr.update(sql,CartItems);
        }
        /**
         * 查询cartItemIds
         * @param cartItems
         * @return
         * @throws SQLException
         */
        public List<CartItem> loadCartItems(String cartItemIds) throws SQLException{
           Object[] cartItemArray = cartItemIds.split(",");//问号对应的值
          // System.out.println(cartItemArray.length);
           String wheresql = getwheresql(cartItemArray.length);//得到问好的个数
           String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and " + wheresql;
           return getCartItemlist(qr.query(sql, new MapListHandler(),cartItemArray));
  }
}
