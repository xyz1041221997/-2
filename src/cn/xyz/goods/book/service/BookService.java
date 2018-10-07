package cn.xyz.goods.book.service;

import java.sql.SQLException;

import cn.xyz.goods.book.dao.BookDao;
import cn.xyz.goods.book.domain.Book;
import cn.xyz.goods.pager.PageBean;

public class BookService {
   BookDao bookdao =   new BookDao();
   /**
    * 分類查詢
    * @param cid
    * @param pc
    * @return
    * @throws SQLException
    */
		   public PageBean<Book> findByCategory(String cid,int pc) throws SQLException{
			   return bookdao.findByCategory(cid, pc);
		   }
		   /**
		    * 通過書名查詢
		    * @param bname
		    * @param pc
		    * @return
		    * @throws SQLException
		    */
		 public PageBean<Book> findByBName(String bname,int pc) throws SQLException{
			 return bookdao.findByBname(bname, pc);
		 }
		 /**
		  * 通過組合查詢
		  * @param book
		  * @param pc
		  * @return
		  * @throws SQLException
		  */
		 public PageBean<Book> findByCombination(Book book,int pc) throws SQLException{
			 return bookdao.findByCondition(book, pc);
		 }
		 public PageBean<Book> findByAuthor(String author,int pc) throws SQLException{
			 return bookdao.findByAuthor(author, pc);
		 }
		 public PageBean<Book> findByPress(String press,int pc) throws SQLException{
			 return bookdao.findByPress(press, pc);
		 }
		 public Book load(String bid) throws SQLException {
			    return  bookdao.findByBid(bid);
		 }
}
