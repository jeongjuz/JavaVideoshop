package model.rec;

public class VideoVO {
	// 멤버변수 선언
	String title, genre, director, actor, exp, content;
	String videono;

	// 생성자
	public VideoVO() {

	}

	public VideoVO(String title, String genre, String director, String actor, String content) {
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.actor = actor;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getVideono() {
		return videono;
	}

	public void setVideono(String videono) {
		this.videono = videono;
	}

}