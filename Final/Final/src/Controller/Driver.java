package Controller;

import java.awt.Color;

import javax.swing.JFrame;

import View.UserLogin;

public class Driver {
	
	public static void main(String[] args) {

		UserLogin user = new UserLogin();

		user.setTitle("The Roux Golf Club");
		user.setBackground(new Color(48, 46, 48));
		user.setSize(800, 600);
		user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		user.setVisible(true);
		
		
	}

}
