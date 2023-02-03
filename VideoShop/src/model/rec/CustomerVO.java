package model.rec;

public class CustomerVO {
	String tel, name, addtel, addr, email;
	
	public CustomerVO() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerVO(String tel, String name, String addtel, String addr, String email) {
		this.tel = tel;
		this.name = name;
		this.addtel = addtel;
		this.addr = addr;
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddtel() {
		return addtel;
	}

	public void setAddtel(String addtel) {
		this.addtel = addtel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
