package bean;

public class User {
	private int id;
	private String name;
	private String password;
	private String user_image;
	private String user_register_time;
	
	public User()
	{
		
	}
	public User(String name, String password)
	{
		this.name=name;
		this.password=password;
	}
	
	public User(int id, String name, String password, String user_image, String user_register_time) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.user_image = user_image;
		this.user_register_time = user_register_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public String getUser_register_time() {
		return user_register_time;
	}
	public void setUser_register_time(String user_register_time) {
		this.user_register_time = user_register_time;
	}
	

}
