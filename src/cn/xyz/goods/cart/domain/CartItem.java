package cn.xyz.goods.cart.domain;

import java.math.BigDecimal;

import cn.xyz.goods.book.domain.Book;
import cn.xyz.goods.user.domain.User;

public class CartItem {
     private   String cartItemId;
     private int  quantity;
     private  Book book;
     private User user;
     /**
      * 得到当前当前总价格
      * 解决了double类型不精确的的问题
      * @return
      */
     public double getSubtotal() {
    	  BigDecimal p1 = new BigDecimal(book.getCurrPrice() + "");
    	  BigDecimal p2 = new BigDecimal(quantity);
    	 BigDecimal p3 = p1.multiply(p2);
    	  return p3.doubleValue();
     }
	public CartItem(String cartItemId, int quantity, Book book, User user) {
		super();
		this.cartItemId = cartItemId;
		this.quantity = quantity;
		this.book = book;
		this.user = user;
	}
	public CartItem() {
		super();
	}
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity + ", book=" + book + ", user=" + user
				+ "]";
	}
	
}
