package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import model.rec.CustomerVO;
import view.CustomerView;

public class SameName extends JFrame {
	// 멤버변수 선언
	CustomerView cv;
	// JButton btn;
	JRadioButton[] rbt;
	ArrayList list;
	String tel;
	CustomerView Child;

	// 기본 생성자
	public SameName() {

	}

	// 인자있는 생성자
	public SameName(ArrayList list, CustomerView parent) throws Exception {
		Child = new CustomerView();
		Child = parent;
		this.list = list;
		int i = 0;
		cv = new CustomerView();
		// System.out.println(list.get(0));

		setLayout(new FlowLayout());

		rbt = new JRadioButton[list.size()];

		// 이벤트 객체 생성
		RbtEvent evt = new RbtEvent();

		while (i < rbt.length) {
			CustomerVO vo = new CustomerVO();
			vo = (CustomerVO) list.get(i);
			rbt[i] = new JRadioButton(vo.getTel());

			// 핸들러 객체 연결
			rbt[i].addActionListener(evt);

			add(rbt[i]);
			i++;
		}

		setTitle("이름 검색");
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public CustomerView eventProc(CustomerView turn) {

		return turn;
	}

	public class RbtEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JRadioButton temp = (JRadioButton) e.getSource();
			Child.searchTel(temp.getText(), e);
			System.out.println(temp.getText());
			setVisible(false);

		}

	}

}
