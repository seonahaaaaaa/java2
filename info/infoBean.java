package info;

public class infoBean {
	
	private String id;
	private String name;
	
	public String getGender() {
		String gender = "";

		char sexNum = id.charAt(7);
		if(sexNum=='1' || sexNum=='3') gender="남성";
		else gender = "여성";
		
		return gender;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
