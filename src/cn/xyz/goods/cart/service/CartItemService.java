package cn.xyz.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.xyz.goods.cart.dao.CartItemDao;
import cn.xyz.goods.cart.domain.CartItem;

public class CartItemService {
      CartItemDao Cdao = new CartItemDao();
      public List<CartItem> findByUser(String uid) throws SQLException{
    	  return Cdao.findByUser(uid);
      }
      public CartItem updateQuantity(String cartItemId,int quantity) throws SQLException {
          Cdao.updateQuantity(cartItemId, quantity);
          CartItem cartItem = Cdao.findBycartItemId(cartItemId);
          return cartItem;
      }
      /**
       * _cartItemΪ���ݿ��ѯ�Ĺ��ﳵ��Ϣ
       * @param cartItem
       * @throws SQLException
       */
      public void add(CartItem cartItem) throws SQLException {
    	         CartItem _cartItem = Cdao.findBybidanduid(cartItem.getBook().getBid(),
    	        	                                 cartItem.getUser().getUid());
    	         if(_cartItem==null) {
    	        	 cartItem.setCartItemId(CommonUtils.uuid());//���ù��ﳵid
    	        	  Cdao.addBook(cartItem);//����޴���Ʒ��Ϣ�������Ʒ
    	         }else {
    	        	  int quantity = _cartItem.getQuantity() + cartItem.getQuantity();
    	        	  Cdao.updateQuantity(_cartItem.getCartItemId(), quantity);
    	         }
      }
      public void batchDelete(String CartItemIds) throws SQLException {
          Cdao.batchDelete(CartItemIds);
      }
      public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
          return Cdao.loadCartItems(cartItemIds);
      }
}
