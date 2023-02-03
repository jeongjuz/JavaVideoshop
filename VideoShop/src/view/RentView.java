package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.RentDAO;

public class RentView extends JPanel {
	JTextField tfRentTel, tfRentCustName, tfRentVideoNum;
	JButton bRent;

	JTextField tfReturnVideoNum;
	JButton bReturn;

	RecentListTableModel rListTable;
	JTable tableRecentList;
	RentDAO dao = null;

	// ==============================================
	// 생성자 함수
	public RentView() {
		newObject(); // 객체 생성 메소드
		addLayout(); // 화면 구성 메소드
		eventProc(); // 이벤트 등록 메소드
		try {
			dao = new RentDAO();
			System.out.println("대여 디비 연결 성공");
			selectTable(); // rent 테이블의 정보가 기본으로 생성
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "대여 DB연결 실패" + e.getMessage());
		}
	}

	// ==============================================
	// 멤버 변수를 객체 생성
	void newObject() {
		tfRentTel = new JTextField(15);
		tfRentCustName = new JTextField(15);
		tfRentVideoNum = new JTextField(15);
		tfReturnVideoNum = new JTextField(15);
		bRent = new JButton("   대   여   ");
		bReturn = new JButton("   반   납   ");
		rListTable = new RecentListTableModel();
		tableRecentList = new JTable(rListTable);

	}

	// ==============================================
	// GUI 구성을 위해 Layout 지정하여 붙이기
	void addLayout() {
		// 위에 대여 , 반납 영역
		JPanel pNorth = new JPanel();
		// 대여부분
		JPanel pNorthLeft = new JPanel();
		pNorthLeft.setBorder(new TitledBorder(" 대      여   "));
		pNorthLeft.setLayout(new GridLayout(4, 2));
		pNorthLeft.add(new JLabel(" 전 화 번 호 "));
		pNorthLeft.add(tfRentTel);
		pNorthLeft.add(new JLabel(" 고  객   명 "));
		pNorthLeft.add(tfRentCustName);
		pNorthLeft.add(new JLabel(" 비디오 번호 "));
		pNorthLeft.add(tfRentVideoNum);
		pNorthLeft.add(bRent);

		// 반납부분
		JPanel pNorthRight = new JPanel();
		pNorthRight.setBorder(new TitledBorder(" 반   납   "));
		pNorthRight.add(new JLabel(" 비디오 번호 "));
		pNorthRight.add(tfReturnVideoNum);
		pNorthRight.add(bReturn);

		pNorth.setLayout(new GridLayout(1, 2));
		pNorth.add(pNorthLeft);
		pNorth.add(pNorthRight);

		// 아래 최신 비디오 목록 영역
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new BorderLayout());
		pCenter.add("Center", new JScrollPane(tableRecentList));

		// 전체 영역 붙이기
		setLayout(new BorderLayout());
		add("Center", pCenter);
		add("North", pNorth);
	}

	// 이벤트 등록 메소드
	void eventProc() {
		BtnEvent evt = new BtnEvent();
		// 이벤트 대상과 이벤트 핸들러 객체와 연결
		bRent.addActionListener(evt);
		bReturn.addActionListener(evt);
		tfRentTel.addActionListener(evt);

		// 대여목록에서 레코드 클릭하면 레코드의 비디오번호가 대여비디오번호로 출력
		tableRecentList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				int row = tableRecentList.getSelectedRow();
				int col = 0;
				String videoNum = String.valueOf(tableRecentList.getValueAt(row, col));
				tfReturnVideoNum.setText(videoNum);
			}

		});
	}

	// 이벤트 클래스
	class BtnEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();
			// 대여 버튼이 눌렸을 때
			if (evt == bRent) {
				/*
				 * 1. 화면의 입력값을 얻어오기 2. 렌트객체 생성 3. DAO의 videoRent(tel, custname, 비디오번호)
				 */

				String tel = tfRentTel.getText();
				String name = tfRentCustName.getText();
				int videoNum = Integer.parseInt(tfRentVideoNum.getText());

				// 대여중인지?
				try {
					boolean poss = dao.isPossible(videoNum);

					if (poss) {
						// 대여가능할때
						System.out.println("대여 가능");
						dao.videoRent(tel, name, videoNum);
						selectTable();

					} else {
						JOptionPane.showMessageDialog(null, "이미 대여중입니다.");
					}

				} catch (SQLIntegrityConstraintViolationException e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "없는 비디오");
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "이미 대여중입니다.");
				}

				// 반납 버튼이 눌렸을때
			} else if (evt == bReturn) {
				
				int videoNum = Integer.parseInt(tfReturnVideoNum.getText());
				// 만약 반납되었다면 반납 불가...
				try {
					boolean returnPoss = dao.isRentPossiblie(videoNum);
					
					if (returnPoss) {
						// 대여가능할때
						System.out.println("대여 가능");
						dao.videoReturn(videoNum);
						JOptionPane.showMessageDialog(null, "반납 성공");
						selectTable();

					} else {
						JOptionPane.showMessageDialog(null, "이미 반납된 비디오");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "이미 반납했거나 대여중이 아님.");
				}

				// 전화번호를 입력하고 엔터를 쳤을 때
			} else if (evt == tfRentTel) {
				String tel = tfRentTel.getText();
				String name = null;

				try {
					name = dao.findCustName(tel);

					selectTable();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "전화번호 고객 검색 실패");
					// TODO: handle exception
				}
				tfRentCustName.setText(name);
			}
		}
	}

	void selectTable() {

		try {
			ArrayList list = dao.recentList();
			rListTable.data = list;
			tableRecentList.setModel(rListTable);
			rListTable.fireTableDataChanged();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	class RecentListTableModel extends AbstractTableModel {

		ArrayList data = new ArrayList();
		String[] columnNames = { "비디오번호", "제목", "회원명", "전화번호", "반납예정일", "반납여부" };

//=============================================================
// 1. 기본적인 TabelModel  만들기
// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
// AbstractTabelModel에서 구현되지 않았기에...
// 반드시 사용자 구현 필수!!!!

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList) data.get(row);
			return temp.get(col);
		}

//===============================================================
// 2. 지정된 컬럼명으로 변환하기
//
//      기본적으로 A, B, C, D 라는 이름으로 컬럼명이 지정된다
		public String getColumnName(int col) {
			return columnNames[col];
		}
	}
}
