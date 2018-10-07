package cn.xyz.goods.category.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xyz.goods.category.dao.CategoryDao;
import cn.xyz.goods.category.domain.Category;

public class CategoryService {
private QueryRunner qr = new TxQueryRunner();
	
	/*
	 * ��һ��Map�е�����ӳ�䵽Category��
	 */
	private Category toCategory(Map<String,Object> map) {
		/*
		 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
		 */
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");// �����һ�����࣬��ôpid��null
		if(pid != null) {//���������ID��Ϊ�գ�
			/*
			 * ʹ��һ�����������������pid
			 * �ٰѸ��������ø�category
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/*
	 * ���԰Ѷ��Map(List<Map>)ӳ��ɶ��Category(List<Category>)
	 */
	private List<Category> toCategoryList(List<Map<String,Object>> mapList) {
		List<Category> categoryList = new ArrayList<Category>();//����һ���ռ���
		for(Map<String,Object> map : mapList) {//ѭ������ÿ��Map
			Category c = toCategory(map);//��һ��Mapת����һ��Category
			categoryList.add(c);//��ӵ�������
		}
		return categoryList;//���ؼ���
	}
	
	/**
	 * �������з���
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAll() throws SQLException {
		/*
		 * 1. ��ѯ������һ������
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		List<Category> parents = toCategoryList(mapList);
		
		/*
		 * 2. ѭ���������е�һ�����࣬Ϊÿ��һ������������Ķ������� 
		 */
		for(Category parent : parents) {
			// ��ѯ����ǰ������������ӷ���
			List<Category> children = findByParent(parent.getCid());
			// ���ø�������
			parent.setChildren(children);
		}
		return parents;
	}
	
	/**
	 * ͨ���������ѯ�ӷ���
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findByParent(String pid) throws SQLException {
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}
	
	/**
	 * ��ӷ���
	 * @param category
	 * @throws SQLException 
	 */
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		/*
		 * ��Ϊһ�����࣬û��parent�������������У�
		 * �������������Ҫ�������η��࣬������Ҫ�ж�
		 */
		String pid = null;//һ������
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql, params);
	}
	
	/**
	 * ��ȡ���и����࣬�������ӷ���ģ�
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents() throws SQLException {
		/*
		 * 1. ��ѯ������һ������
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		return toCategoryList(mapList);
	}
	
	/**
	 * ���ط���
	 * ���ɼ���һ�����࣬Ҳ�ɼ��ض�������
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}
	
	/**
	 * �޸ķ���
	 * �����޸�һ�����࣬Ҳ���޸Ķ�������
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?, pid=?, `desc`=? where cid=?";
		String pid = null;
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCname(), pid, category.getDesc(), category.getCid()};
		qr.update(sql, params);
	}
	
	/**
	 * ��ѯָ�����������ӷ���ĸ���
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), pid);
		return cnt == null ? 0 : cnt.intValue();
	}
	
	/**
	 * ɾ������
	 * @param cid
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}
}
