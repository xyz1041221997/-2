package cn.xyz.goods.book.domain;

import cn.xyz.goods.category.domain.Category;


public class Book {
      private String bid;//主键id
      private String bname;//书名
      private String author;//作者
      private double price;//价格
      private double currPrice;//现价
      private double  discount;//折扣
      private String press;//出版社
      private String publishtime;//出版时间
      private int edition;//版本
      private int pageNum;//页数
      private int wordNum;//字数
      private String printtime;//刷新时间
      private int booksize;
      private String paper;//
      private Category category;//分类
      private String image_w;//大图路劲
      private String image_b;//小土路基
	public Book() {
		super();
	}
	public Book(String bid, String bname, String author, double price, double currPrice, double discount, String press,
			String publishtime, int edition, int pageNum, int wordNum, String printtime, int booksize, String paper,
			Category category, String image_w, String image_b) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.author = author;
		this.price = price;
		this.currPrice = currPrice;
		this.discount = discount;
		this.press = press;
		this.publishtime = publishtime;
		this.edition = edition;
		this.pageNum = pageNum;
		this.wordNum = wordNum;
		this.printtime = printtime;
		this.booksize = booksize;
		this.paper = paper;
		this.category = category;
		this.image_w = image_w;
		this.image_b = image_b;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCurrPrice() {
		return currPrice;
	}
	public void setCurrPrice(double currPrice) {
		this.currPrice = currPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getWordNum() {
		return wordNum;
	}
	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}
	public String getPrinttime() {
		return printtime;
	}
	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}
	public int getBooksize() {
		return booksize;
	}
	public void setBooksize(int booksize) {
		this.booksize = booksize;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImage_w() {
		return image_w;
	}
	public void setImage_w(String image_w) {
		this.image_w = image_w;
	}
	public String getImage_b() {
		return image_b;
	}
	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", author=" + author + ", price=" + price + ", currPrice="
				+ currPrice + ", discount=" + discount + ", press=" + press + ", publishtime=" + publishtime
				+ ", edition=" + edition + ", pageNum=" + pageNum + ", wordNum=" + wordNum + ", printtime=" + printtime
				+ ", booksize=" + booksize + ", paper=" + paper + ", category=" + category + ", image_w=" + image_w
				+ ", image_b=" + image_b + "]";
	}
      
      
}
