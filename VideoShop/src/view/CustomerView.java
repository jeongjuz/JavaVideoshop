package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CustomerDAO;
import model.rec.CustomerVO;

public class CustomerView extends JPanel implements ActionListener {

	JTextField tfCustName, tfCustTel, tfCustTelAid, tfCustAddr, tfCustEmail;
	JButton bCustRegist, bCustModify;

	JTextField tfCustNameSearch, tfCustTelSearch;
	JButton bCustNameSearch, bCustTelSearch;
	
	CustomerDAO dao;
	
	
	public CustomerView() {
		tfCustName = new JTextField(20);
		tfCustTel = new JTextField(20);
		tfCustTelAid = new JTextField(20);
		tfCustAddr = new JTextField(20);
		tfCustEmail = new JTextField(20);

		tfCustNameSearch = new JTextField(20);
		tfCustTelSearch = new JTextField(20);

		bCustRegist = new JButton("회원가입");
		bCustModify = new JButton("회원수정");
		bCustNameSearch = new JButton("이름검색");
		bCustTelSearch = new JButton("번호검색");

		// 회원가입 부분 붙이기
		// ( 그 복잡하다던 GridBagLayout을 사용해서 복잡해 보임..다른 쉬운것으로...대치 가능 )
		JPanel pRegist = new JPanel();
		pRegist.setLayout(new GridBagLayout());
		GridBagConstraints cbc = new GridBagConstraints();
		cbc.weightx = 1.0;
		cbc.weighty = 1.0;
		cbc.fill = GridBagConstraints.BOTH;
		cbc.gridx = 0;
		cbc.gridy = 0;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(new JLabel("   이   름   "), cbc);
		cbc.gridx = 1;
		cbc.gridy = 0;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(tfCustName, cbc);
		cbc.gridx = 2;
		cbc.gridy = 0;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(bCustModify, cbc);
		cbc.gridx = 3;
		cbc.gridy = 0;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(bCustRegist, cbc);

		cbc.gridx = 0;
		cbc.gridy = 1;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(new JLabel("   전   화   "), cbc);
		cbc.gridx = 1;
		cbc.gridy = 1;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(tfCustTel, cbc);
		cbc.gridx = 2;
		cbc.gridy = 1;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(new JLabel(" 추가전화  "), cbc);
		cbc.gridx = 3;
		cbc.gridy = 1;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(tfCustTelAid, cbc);

		cbc.gridx = 0;
		cbc.gridy = 2;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(new JLabel("   주   소   "), cbc);
		cbc.gridx = 1;
		cbc.gridy = 2;
		cbc.gridwidth = 3;
		cbc.gridheight = 1;
		pRegist.add(tfCustAddr, cbc);

		cbc.gridx = 0;
		cbc.gridy = 3;
		cbc.gridwidth = 1;
		cbc.gridheight = 1;
		pRegist.add(new JLabel("   이메일   "), cbc);
		cbc.gridx = 1;
		cbc.gridy = 3;
		cbc.gridwidth = 3;
		cbc.gridheight = 1;
		pRegist.add(tfCustEmail, cbc);

		// 회원검색 부분 붙이기
		JPanel pSearch = new JPanel();
		pSearch.setLayout(new GridLayout(2, 1));
		JPanel pSearchName = new JPanel();
		pSearchName.add(new JLabel("      이    름   "));
		pSearchName.add(tfCustNameSearch);
		pSearchName.add(bCustNameSearch);
		JPanel pSearchTel = new JPanel();
		pSearchTel.add(new JLabel("   전화번호   "));
		pSearchTel.add(tfCustTelSearch);
		pSearchTel.add(bCustTelSearch);
		pSearch.add(pSearchName);
		pSearch.add(pSearchTel);

		// 전체 패널에 붙이기
		setLayout(new BorderLayout());
		add("Center", pRegist);
		add("South", pSearch);

		// 이벤트 등록
		bCustRegist.addActionListener(this);
		bCustModify.addActionListener(this);
		bCustNameSearch.addActionListener(this);
		bCustTelSearch.addActionListener(this);
		
		
		try {
			dao = new CustomerDAO(); // 예외처리는 임시로  throws한다 
			System.out.println("고객 디비 연결성공");
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "고객DB연결실패: " + e.getMessage());
		}
	}

	// ---------------------------------------------
	// ActionEvent 발생시 호출되는 메소드
	public void actionPerformed(ActionEvent ev) {
		Object o = ev.getSource();
		if (o == bCustRegist) {
			/*
			 * 1. 각 TextField 에서 입력값 얻어오기 
			 * 2. CustoemrVO 클래스의 setter 메소드를 이용하여 멤버필드에 1번값을 지정 
			 * 3. CustoemrDAO 클래스의 dao.regist() 호출 
			 * 4. 각 TextField 초기화
			 */
			String name = tfCustName.getText();
			String tel = tfCustTel.getText();
			String addtel = tfCustTelAid.getText();
			String addr = tfCustAddr.getText();
			String email = tfCustEmail.getText();
			
			// 객체 생성
			CustomerVO vo = new CustomerVO(tel, name, addtel, addr, email);
			
			try {
				dao.regist(vo);
				JOptionPane.showMessageDialog(null, "회원입력성공");
				clearScreen();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
		} else if (o == bCustModify) {
			/*
			 * 1. 각 TextField 에서 입력값 얻어오기 
			 * 2. CustoemrRecord 클래스의 setter 메소드를 이용하여 멤버필드에 1번값을 지정 
			 * 3. CustoemrRecord 클래스의 modify() 호출 
			 * 4. 각 TextField 초기화
			 */
			CustomerVO vo = new CustomerVO();
			vo.setName(tfCustName.getText());
			vo.setTel(tfCustTel.getText());
			vo.setAddtel(tfCustTelAid.getText());
			vo.setEmail(tfCustEmail.getText());
			vo.setAddr(tfCustAddr.getText());
			
			try {
				dao.modify(vo);
				JOptionPane.showMessageDialog(null, "회원정보수정성공");
				clearScreen();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			

		} else if (o == bCustNameSearch) {
			/*
			 * 1. 검색부분의 이름 입력하는 TextField에서 입력값 얻어오기 
			 * 2. CustoemrRecord 클래스의 searchName() 호출
			 * 3. CustoemrRecord 클래스의 getter 메소드를 이용하여 DB에서 검색한 데이타를 각 텍스트 필드에 지정한다 
			 * # 동명 이인이 있는 경우 고려 ( 나중에 )
			 */
			String name = tfCustNameSearch.getText();
			ArrayList list = new ArrayList();
			
			try {
				list = dao.searchName(name);
				System.out.println(list);
				
				CustomerView parent = new CustomerView();
				
				SameName sn = new SameName(list, parent);
				
				
//				tfCustName.setText(vo.getName());
//				tfCustTel.setText(vo.getTel());
//				tfCustTelAid.setText(vo.getAddtel());
//				tfCustAddr.setText(vo.getAddr());
//				tfCustEmail.setText(vo.getEmail());
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "일치하는 회원정보 없음");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if (o == bCustTelSearch) {
			/*
			 * 1. 검색부분의 이름 입력하는 TextField에서 입력값 얻어오기 
			 * 2. CustoemrRecord 클래스의 searchTel() 호출
			 * 3. CustoemrRecord 클래스의 getter 메소드를 이용하여 DB에서 검색한 데이타를 각 텍스트 필드에 지정한다
			 */
			String tel = tfCustTelSearch.getText();
			try {
				CustomerVO vo = dao.searchTel(tel);
				tfCustName.setText(vo.getName());
				tfCustTel.setText(vo.getTel());
				tfCustTelAid.setText(vo.getAddtel());
				tfCustAddr.setText(vo.getAddr());
				tfCustEmail.setText(vo.getEmail());
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "일치하는 회원정보 없음");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public void clearScreen() {		
		tfCustName.setText(null);
		tfCustTel.setText(null);
		tfCustTelAid.setText(null);
		tfCustAddr.setText(null);
		tfCustEmail.setText(null);
	}

	public void searchTel(String tel, ActionEvent ev) {
		// TODO Auto-generated method stub
		System.out.println("searchTel"+tel);
		try {
			CustomerVO vo = dao.searchTel(tel);
			
			System.out.println();
			tfCustName.setText("수정해");
			tfCustName.setText(vo.getName());
			tfCustTel.setText(vo.getTel());
			tfCustTelAid.setText(vo.getAddtel());
			tfCustAddr.setText(vo.getAddr());
			tfCustEmail.setText(vo.getEmail());
			System.out.println("검색 성공!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "검색 실패");
		}
		
	}

}
