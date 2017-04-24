package com.netease.course.web.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.netease.course.web.meta.ListBuy;
import com.netease.course.web.meta.Person;
import com.netease.course.web.meta.Product;
import com.netease.course.web.meta.ShowProduct;
import com.netease.course.web.meta.Trx;


@Repository
public class JdbcDao {
	
	private JdbcTemplate jdbcTemplate;	
	
	   @Autowired
	    public void setDataSource(DataSource dataSource){
	  	  this.jdbcTemplate =new JdbcTemplate(dataSource);
	    }
	
	public List<Person> getPersonList(){
		return this.jdbcTemplate.query("select * from person", 
				new RowMapper<Person>() {
					public Person mapRow(ResultSet rs, int row) throws SQLException {
				Person person=new Person(
						rs.getInt("id"),
						rs.getInt("userType"),
						rs.getString("userName"), 
						rs.getString("password"),
						rs.getString("nickName")						
						);
						return person;
			}
		});		
	}	
	public Person getPersonByName (String userName){
		return this.jdbcTemplate.queryForObject("select * from person where userName=?", 
				new Object[]{userName},
				new RowMapper<Person>(){
					public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
						Person person=new Person(
								rs.getInt("id"),
								rs.getInt("userType"),
								rs.getString("userName"), 
								rs.getString("password"),
								rs.getString("nickName")
								);
								return person;
					}
			
		});
	}
	
	public List<Product> getProductList(){
		return this.jdbcTemplate.query("select * from product", new RowMapper<Product>() {
			public Product mapRow(ResultSet rs, int row) throws SQLException {
				Product product=new Product(
						rs.getInt("id"),
						rs.getInt("isBuy"),
						rs.getInt("isSell"),
						rs.getDouble("price"),
						rs.getString("title"),
						rs.getString("image"),
						rs.getString("summary"), 
						rs.getString("detail"),
						rs.getLong("saleNum"));
						return product;
			}
		});		
	}
	public int insertProduct(double price,String title,String image,String summary,
			String detail){
		 this.jdbcTemplate.update("insert into product (price,title,image,summary,detail,isBuy,isSell,saleNum)value(?,?,?,?,?,?,?,?)",
				price,title,image,summary,detail,0,0,0);
		 return 1;
	}
	
	public int updateProduct(int id,double price,String title,String image,String summary,
			String detail){
		 this.jdbcTemplate.update("update product set price=?,title=?,image=?,summary=?,detail=? where id=?",
				new Object[]{price,title,image,summary,detail,id});	
		 return 1;
	}
	public int updateProductSaleNum(int id,long saleNum){
		 this.jdbcTemplate.update("update product set saleNum=saleNum+?,isBuy=1 where id=?",
				new Object[]{id,saleNum});	
		 return 1;
	}
	
	public int deleteProduct(int id){
		this.jdbcTemplate.update("delete from product where id=?",new Object[]{id});
		return 1;
	}
	
	public Product getProById(int id){
		return this.jdbcTemplate.queryForObject("select * from product where id=?", new Object[]{id},
				new RowMapper<Product>(){

					public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
						Product product=new Product(
						rs.getInt("id"),
						rs.getInt("isBuy"),
						rs.getInt("isSell"),
						rs.getDouble("price"),
						rs.getString("title"),
						rs.getString("image"),
						rs.getString("summary"), 
						rs.getString("detail"),
						rs.getLong("saleNum"));
						return product;
					}
			
		});
	}
	
	public Product getProByImage(String image){
		return this.jdbcTemplate.queryForObject("select * from product where image=?", new Object[]{image},
				new RowMapper<Product>(){

					public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
						Product product=new Product(
						rs.getInt("id"),
						rs.getInt("isBuy"),
						rs.getInt("isSell"),
						rs.getDouble("price"),
						rs.getString("title"),
						rs.getString("image"),
						rs.getString("summary"), 
						rs.getString("detail"),
						rs.getLong("saleNum"));
						return product;
					}
			
		});
	}
	
	public ShowProduct getShowProductById(int id){
		return this.jdbcTemplate.queryForObject("select product.id,isBuy,isSell,title,summary,detail,image,price,buyPrice,buyNum,saleNum from product LEFT OUTER JOIN trx on product.id=trx.productId where product.id=?",
				new Object[]{id},new RowMapper<ShowProduct>(){
					public ShowProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
						ShowProduct sp=new ShowProduct(
								rs.getInt("product.id"),
								rs.getInt("isBuy"),
								rs.getInt("isSell"),
								rs.getString("title"),
								rs.getString("summary"),
								rs.getString("detail"),
								rs.getString("image"),
								rs.getDouble("price"),
								rs.getDouble("buyPrice"),
								rs.getLong("buyNum"),
								rs.getLong("saleNum")
								);
						return sp;
					}
			
		});
	}
	
	public List<Product> getProductListIsbuy(){
		return this.jdbcTemplate.query("select * from product where isBuy=0", new RowMapper<Product>() {
			public Product mapRow(ResultSet rs, int row) throws SQLException {
				Product product=new Product(
						rs.getInt("id"),
						rs.getInt("isBuy"),
						rs.getInt("isSell"),
						rs.getDouble("price"),
						rs.getString("title"),
						rs.getString("image"),
						rs.getString("summary"), 
						rs.getString("detail"),
						rs.getLong("saleNum"));
						return product;
			}
		});		
	}
	public List<Product> getProductListIsSell(){
		return this.jdbcTemplate.query("select * from product where isSell=0", new RowMapper<Product>() {
			public Product mapRow(ResultSet rs, int row) throws SQLException {
				Product product=new Product(
						rs.getInt("id"),
						rs.getInt("isBuy"),
						rs.getInt("isSell"),
						rs.getDouble("price"),
						rs.getString("title"),
						rs.getString("image"),
						rs.getString("summary"), 
						rs.getString("detail"),
						rs.getLong("saleNum"));
						return product;
			}
		});		
	}
	//交易接口
	public int trxById(int id,int buyNum,String buyTime,double total){
		this.jdbcTemplate.update("update trx set t.productId=?,t.buyNum=?,t.buyTime=?,t.total=?,t.personId=1",
				new Object[]{id,buyNum,buyTime,total});
		return 1;
	}
	

	public List<Trx> getTrxList(){
		return this.jdbcTemplate.query("select * from trx", new RowMapper<Trx>() {
			public Trx mapRow(ResultSet rs, int row) throws SQLException {
				Trx trx=new Trx(rs.getInt("id"), 
						rs.getInt("productId"), 
						rs.getInt("personId"), 
						rs.getDouble("buyPrice"),
						rs.getDouble("total"), 
						rs.getLong("buyNum"), 
						rs.getLong("buyTime"));			
						return trx;
			}
		});		
	}	
	
	public List<ListBuy> getBuyList(){
		return this.jdbcTemplate.query("select trx.id,productId,buyPrice,buyTime,buyNum,total,"
				+ "title,image from trx LEFT JOIN product on trx.productId=product.id", new RowMapper<ListBuy>() {
			public ListBuy mapRow(ResultSet rs, int row) throws SQLException {
				ListBuy lb=new ListBuy(
						rs.getInt("productId"), 
						rs.getString("title"), 
						rs.getString("image"), 
						rs.getDouble("buyPrice"), 
						rs.getDouble("total"), 
						rs.getLong("buyTime"), 
						rs.getLong("buyNum")
						);
				return lb;
			}
		});		
	}	
	
	
}
