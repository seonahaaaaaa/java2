package shop.cart;

/*
 * 이 파일은 Java Resources / src 밑에 있어야한다.
 */
public class Goods { 
	private String  id;	 
	private String  name; 
	private int     price; 
	 
	public Goods(String id, String name, int price) { 
		this.id = id; 
		this.name = name; 
		this.price = price; 
	} 
		 
	public void setPrice(int price) { 
		this.price = price; 
	} 
		 
	public String getId() { 
		return id; 
	} 
	 
	public String getName() { 
		return name; 
	} 
	 
	public int getPrice() { 
		return price; 
	}	 
}
