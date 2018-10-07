package cn.xyz.goods.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xyz.goods.book.domain.Book;
import cn.xyz.goods.category.domain.Category;
import cn.xyz.goods.pager.Expression;
import cn.xyz.goods.pager.PageBean;
import cn.xyz.goods.pager.PageConstans;

public class BookDao {
    QueryRunner qr =   new TxQueryRunner();
    public Book findByBid(String bid) throws SQLException {
    	 String sql = "select * from t_book where bid = ?";
    	 Map<String, Object> map = qr.query(sql, new MapHandler(),bid);
    	 Book book = CommonUtils.toBean(map, Book.class);
    	 Category category = CommonUtils.toBean(map, Category.class);
    	 book.setCategory(category);
		return book;
    }
    /**
     * �������ѯ
     * @param cid
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByCategory(String cid,int pc) throws SQLException{
    	List<Expression> exp = new ArrayList<Expression>();
    	exp.add(new Expression("cid","=",cid));
    	
		return findBook(exp,pc);
    	
    }
    /**
     * ͨ����������
     * @param bname
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByBname(String bname,int pc) throws SQLException{
    	List<Expression> exp = new ArrayList<Expression>();
    	exp.add(new Expression("bname","like","%"+bname+"%"));
    	
    	return findBook(exp,pc);
    	
    }
    public PageBean<Book> findByAuthor(String author,int pc) throws SQLException{
    	List<Expression> exp = new ArrayList<Expression>();
    	exp.add(new Expression("author","like","%"+author+"%"));
    	
    	return findBook(exp,pc);
    	
    }
    public PageBean<Book> findByPress(String press,int pc) throws SQLException{
    	List<Expression> exp = new ArrayList<Expression>();
    	exp.add(new Expression("author","like","%"+press+"%"));
    	
    	return findBook(exp,pc);
    	
    }
    //������ϲ�ѯ
    public PageBean<Book> findByCondition(Book book,int pc) throws SQLException{
    	List<Expression> exp = new ArrayList<Expression>();
    	exp.add(new Expression("bname","like","%"+book.getBname()+"%"));
    	exp.add(new Expression("author","like","%"+book.getAuthor()+"%"));
    	exp.add(new Expression("press","like","%"+book.getPress()+"%"));
    	return findBook(exp,pc);
    	
    }
    /**
     * ͨ�õĲ�ѯ����
     * Expression��ʱ�������ͱ���
     * @param explist����Ĳ�ѯ��Ϣ
     * @param pc��ǰҳ��
     * @return
     * @throws SQLException 
     */
    private PageBean<Book> findBook(List<Expression> explist,int pc) throws SQLException{
    	
    	int ps=PageConstans.BOOK_PAGE_SIZE;//ÿҳ����
    	
    	StringBuilder wheresql = new StringBuilder(" where 1 = 1");
    	List<Object> what = new ArrayList<Object>();//��Ӧÿһ���ʺŵ�ֵ
    	/**
    	 * ��Ӳ�ѯһ������
    	 * ��andΪ��ͷ ƴ�� ��ѯ������ �Ͳ����� 
    	 * ����Ϊ= > < �ȵ� ƴ�Ӳ�ѯ���ֵ�ֵ
    	 * 
    	 */
    	for(Expression exp:explist) {
    		wheresql.append(" and ").append(exp.getName()).append(" ")
    		.append(exp.getOperator()).append(" ");
    		if(!exp.getOperator().equals("is null")) {
    			 wheresql.append(" ?");
    			 what.add(exp.getValue());
    		}
    	}
    	//System.out.println(wheresql);
    	/**
    	 * �γ�������sql���
    	 */
    	String sql= "select count(*) from t_book"+	wheresql;
    	Number number = (Number) qr.query(sql, new ScalarHandler(),what.toArray());
    	int tr = number.intValue();//��ѯ�����ܼ�¼��
    	
    	sql = "select * from  t_book" +wheresql + " order by orderBy limit ?, ?";
    	what.add((pc - 1) * ps);//��һ���ʺ� ÿҳ��¼���±�
    	what.add(ps);//һ����ѯ��������
    	//ִ����� ��ѯ�����鼮��������
    	List<Book> beanlist = qr.query(sql, new BeanListHandler<Book>(Book.class),what.toArray());
    	/**
    	 * ����pagebean����
    	 * 
    	 */
    	PageBean<Book> pb = new PageBean<Book>();
    	pb.setBeanList(beanlist);
    	pb.setPs(ps);
    	pb.setTr(tr);
    	pb.setPc(pc);
    	
		return pb;
    	
    }
    public static void main(String[] args) throws SQLException {
		BookDao bookdao = new BookDao();
		List<Expression> list = new ArrayList<Expression>();
		list.add(new Expression("id","=","12"));
		list.add(new Expression("bname","=","xxx"));
		list.add(new Expression("price","is null",null));
		bookdao.findBook(list, 10);
	}
}
