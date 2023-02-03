package model.rec;

public class RentVO {
	// 멤버필드 선언
	int dayeono, videono;
	String tel, dayeodate, turn;

	public RentVO() {

	}
	
	public RentVO(int dayeono, int videono, String tel, String dayeodate, String turn) {
		this.dayeono = dayeono;
		this.videono = videono;
		this.tel = tel;
		this.dayeodate = dayeodate;
		this.turn = turn;
	}

	public int getDayeono() {
		return dayeono;
	}

	public void setDayeono(int dayeono) {
		this.dayeono = dayeono;
	}

	public int getVideono() {
		return videono;
	}

	public void setVideono(int videono) {
		this.videono = videono;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDayeodate() {
		return dayeodate;
	}

	public void setDayeodate(String dayeodate) {
		this.dayeodate = dayeodate;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

}
