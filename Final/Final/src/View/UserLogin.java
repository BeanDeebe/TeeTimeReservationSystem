package View;
/*
 * CS5004
 * Dean Beebe
 * Final Project
 * 
 * This file contains the View component of the project.
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import Model.Golfer;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class UserLogin extends JFrame {
	
	private JTextField txtEnterName;
	private JTextField txtEnterdigitPhone;
	
	public UserLogin() {
		FlatDarkLaf.install();
		UIManager.put( "Button.arc", 999 );
		UIManager.put( "TextComponent.arc", 999 );
		getContentPane().setSize(500, 500);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(48, 46, 48));
		JPanel panel_login = new JPanel();
		panel_login.setBounds(0, -4, 994, 849);
		getContentPane().add(panel_login);
		SpringLayout sl_panel_login = new SpringLayout();
		panel_login.setLayout(sl_panel_login);
		
		JLabel lbl_LoginTitle = new JLabel("Register");
		sl_panel_login.putConstraint(SpringLayout.NORTH, lbl_LoginTitle, 65, SpringLayout.NORTH, panel_login);
		sl_panel_login.putConstraint(SpringLayout.WEST, lbl_LoginTitle, 361, SpringLayout.WEST, panel_login);
		sl_panel_login.putConstraint(SpringLayout.EAST, lbl_LoginTitle, 486, SpringLayout.WEST, panel_login);
		panel_login.add(lbl_LoginTitle);
		lbl_LoginTitle.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 27));
		lbl_LoginTitle.setBackground(Color.WHITE);
		
		txtEnterName = new JTextField();
		sl_panel_login.putConstraint(SpringLayout.SOUTH, lbl_LoginTitle, -6, SpringLayout.NORTH, txtEnterName);
		sl_panel_login.putConstraint(SpringLayout.NORTH, txtEnterName, 188, SpringLayout.NORTH, panel_login);
		sl_panel_login.putConstraint(SpringLayout.WEST, txtEnterName, 319, SpringLayout.WEST, panel_login);
		sl_panel_login.putConstraint(SpringLayout.EAST, txtEnterName, -461, SpringLayout.EAST, panel_login);
		panel_login.add(txtEnterName);
		txtEnterName.setColumns(10);
		
		txtEnterdigitPhone = new JTextField();
		sl_panel_login.putConstraint(SpringLayout.SOUTH, txtEnterName, -14, SpringLayout.NORTH, txtEnterdigitPhone);
		sl_panel_login.putConstraint(SpringLayout.EAST, txtEnterdigitPhone, -461, SpringLayout.EAST, panel_login);
		sl_panel_login.putConstraint(SpringLayout.NORTH, txtEnterdigitPhone, 228, SpringLayout.NORTH, panel_login);
		sl_panel_login.putConstraint(SpringLayout.WEST, txtEnterdigitPhone, 319, SpringLayout.WEST, panel_login);
		sl_panel_login.putConstraint(SpringLayout.SOUTH, txtEnterdigitPhone, -595, SpringLayout.SOUTH, panel_login);
		panel_login.add(txtEnterdigitPhone);
		txtEnterdigitPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		txtEnterdigitPhone.setColumns(10);
		
		JButton btn_submitRegister = new JButton("Submit");
		sl_panel_login.putConstraint(SpringLayout.NORTH, btn_submitRegister, 44, SpringLayout.SOUTH, txtEnterdigitPhone);
		sl_panel_login.putConstraint(SpringLayout.WEST, btn_submitRegister, 0, SpringLayout.WEST, txtEnterName);
		sl_panel_login.putConstraint(SpringLayout.SOUTH, btn_submitRegister, -511, SpringLayout.SOUTH, panel_login);
		sl_panel_login.putConstraint(SpringLayout.EAST, btn_submitRegister, 0, SpringLayout.EAST, txtEnterName);
		btn_submitRegister.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		
		/**
		 * mouse click event that - upon the user hitting submit - stores the name field and phone number field into a MySQL database, with the phone value acting
		 * as an ID. Closes the window and opens the MainPage class window view.
		 */
		btn_submitRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String phone = txtEnterdigitPhone.getText();
				String name = txtEnterName.getText();
				JOptionPane.showMessageDialog(panel_login, "Registered!\nName: " + name + "\nPhone Number: " + phone);
				Golfer register = new Golfer(name, phone);
				
				MainPage main = new MainPage(register);
				
				main.setTitle("The Roux Golf Club");
				main.setSize(800, 600);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setVisible(true);
				closeLoginWindow();
			}
		});
		panel_login.add(btn_submitRegister);
		
		JLabel lbl_userName = new JLabel("Name:");
		sl_panel_login.putConstraint(SpringLayout.NORTH, lbl_userName, 5, SpringLayout.NORTH, txtEnterName);
		lbl_userName.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		panel_login.add(lbl_userName);
		
		JLabel lbl_phoneNum = new JLabel("Phone Number:");
		sl_panel_login.putConstraint(SpringLayout.NORTH, lbl_phoneNum, 24, SpringLayout.SOUTH, lbl_userName);
		sl_panel_login.putConstraint(SpringLayout.WEST, lbl_phoneNum, 193, SpringLayout.WEST, panel_login);
		sl_panel_login.putConstraint(SpringLayout.WEST, lbl_userName, 0, SpringLayout.WEST, lbl_phoneNum);
		panel_login.add(lbl_phoneNum);
	}
	
	public void closeLoginWindow() {
		this.setVisible(false);
	}
	
	
	
}
