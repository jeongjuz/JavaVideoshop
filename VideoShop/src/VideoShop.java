import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.CustomerView;
import view.RentView;
import view.VideoView;

public class VideoShop extends JFrame{
	CustomerView customer;
	VideoView video;
	RentView rent;
	
	public VideoShop() {
		customer = new CustomerView();
		video = new VideoView();
		rent = new RentView();
		
		
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("고객관리", customer);
		pane.addTab("비디오관리", video);
		pane.addTab("대여관리", rent);
		pane.setSelectedIndex(0); // 초기화면 지정
		
		getContentPane().add("Center", pane);
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new VideoShop();
	}

}
