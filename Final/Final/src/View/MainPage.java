/*
 * CS5004
 * Dean Beebe
 * Final Project
 *
 * This file contains the view for the main page of the tee time reservation system.
 */

package View;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.lgooddatepicker.components.CalendarPanel;

import Model.Golfer;
import Model.TimeLoader;




@SuppressWarnings("serial")
public class MainPage extends JFrame {
	private JTable table;
	private Golfer golfer;
	
	
	
	public MainPage(Golfer golfer) {
		this.golfer = golfer;
		
		FlatDarkLaf.install();
		UIManager.put( "Button.arc", 999 );
		UIManager.put( "TextComponent.arc", 999 );
		getContentPane().setLayout(null);
		
		JPanel panel_CalendarBorder = new JPanel();
		panel_CalendarBorder.setBounds(6, 72, 788, 356);
		panel_CalendarBorder.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
				"Select a Date:", TitledBorder.LEADING, TitledBorder.TOP, 
				null, new Color(255, 255, 255)));
		
		getContentPane().add(panel_CalendarBorder);
		panel_CalendarBorder.setLayout(null);
		panel_CalendarBorder.setVisible(true);
		
		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setBounds(142, 16, 500, 318);
		panel_CalendarBorder.add(calendarPanel);
		
		GridBagLayout gridBagLayout = (GridBagLayout) calendarPanel.getLayout();
		gridBagLayout.columnWidths = new int[] {20, 100, 20, 0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowHeights = new int[]{0, 0, 5, 138, 0, 0, 0};
		calendarPanel.getNextYearButton().setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		calendarPanel.getNextMonthButton().setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		calendarPanel.getPreviousMonthButton().setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		calendarPanel.getPreviousYearButton().setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		
		JPanel panel_TeeSheet = new JPanel();
		panel_TeeSheet.setBounds(0, 72, 794, 356);
		getContentPane().add(panel_TeeSheet);
		panel_TeeSheet.setLayout(null);
		
		JLabel lbl_TeeSheet = new JLabel("Available Tee Times:");
		lbl_TeeSheet.setBounds(6, 6, 152, 16);
		panel_TeeSheet.add(lbl_TeeSheet);
		
		JButton btn_registerTeeTime = new JButton("Book!");
		btn_registerTeeTime.setBounds(138, 440, 117, 29);
		getContentPane().add(btn_registerTeeTime);
		btn_registerTeeTime.setVisible(true);
		
		JButton btn_backButton = new JButton("Go Back");
		btn_backButton.setBounds(528, 440, 117, 29);
		getContentPane().add(btn_backButton);
		btn_backButton.setVisible(true);
		
		
		JButton btn_backButton2 = new JButton("Go Back");
		btn_backButton2.setBounds(528, 440, 117, 29);
		getContentPane().add(btn_backButton2);
		btn_backButton2.setVisible(false);
		
		JButton btn_DateSelect = new JButton("View Tee Times");
		
		JButton btn_bookedTimes = new JButton("View My Reservations");
		
		// Listener for calendar date selection
		btn_DateSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String dateSelected = calendarPanel.getSelectedDate().toString();
					LocalDate selected = calendarPanel.getSelectedDate();
					LocalDate today = LocalDate.now();
					
					if (selected.compareTo(today) < 0) {
						throw new NullPointerException();
					}
					else {
						TimeLoader load = new TimeLoader(dateSelected);
						DefaultTableModel timesToLoad = load.fillTable();
						
						table = new JTable(timesToLoad);
						table.setBounds(16, 44, 460, 500);
						panel_TeeSheet.add(table);
						table.setVisible(true);
						
						panel_TeeSheet.setVisible(true);
						panel_CalendarBorder.setVisible(false);
						btn_backButton.setVisible(true);
						btn_registerTeeTime.setVisible(true);
					}
				} catch (NullPointerException | IOException | SQLException e1) {
					JOptionPane.showMessageDialog(panel_TeeSheet, "Error:\n Please select a date on or after "+ 
													LocalDate.now() + " to continue.");
				}
				
			}
		});
		
		// Listener for "booking tee time" button
		btn_registerTeeTime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int column = 1;
				
				String time = table.getModel().getValueAt(row, column).toString();
				
				golfer.registerTime(calendarPanel.getSelectedDate().toString(), time);
				
				JOptionPane.showMessageDialog(panel_TeeSheet, "Tee Time Booked!");
				
				OptionsPage options = new OptionsPage(golfer);
				options.setTitle("The Roux Golf Club");
				options.setSize(800, 600);
				options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				options.setVisible(true);
				closeMainPage();
				
				
			}
		});
		
		
		// Listener for "go back" button
		btn_backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_CalendarBorder.setVisible(true);
				panel_TeeSheet.setVisible(false);
				
				String currentSelected = String.valueOf(calendarPanel.getSelectedDate());
				panel_TeeSheet.remove(table);
				btn_backButton.setVisible(false);
				btn_registerTeeTime.setVisible(false);
			}
		});
		
		btn_DateSelect.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_btn_DateSelect = new GridBagConstraints();
		gbc_btn_DateSelect.gridwidth = 2;
		gbc_btn_DateSelect.anchor = GridBagConstraints.EAST;
		gbc_btn_DateSelect.insets = new Insets(0, 0, 0, 5);
		gbc_btn_DateSelect.gridx = 1;
		gbc_btn_DateSelect.gridy = 6;
		calendarPanel.add(btn_DateSelect, gbc_btn_DateSelect);
		
		// listener for "view my reservations" button
		btn_bookedTimes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btn_backButton2.setVisible(true);
				calendarPanel.setVisible(false);
				panel_CalendarBorder.setVisible(false);
				btn_backButton.setVisible(false);
				btn_registerTeeTime.setVisible(false);
				
				DefaultTableModel userTimes;
				try {
					userTimes = golfer.findUserTeeTimes();
					table = new JTable(userTimes);
					table.setBounds(16, 44, 460, 500);
					panel_TeeSheet.add(table);
					table.setVisible(true);
					panel_TeeSheet.setVisible(true);
					
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(panel_TeeSheet, "No Tee Times currently booked for Phone: " + golfer.getPhone());
				}
			}
		});
		btn_bookedTimes.setBounds(308, 440, 167, 29);
		getContentPane().add(btn_bookedTimes);
		
		btn_backButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_CalendarBorder.setVisible(true);
				btn_backButton.setVisible(true);
				calendarPanel.setVisible(true);
				panel_TeeSheet.setVisible(false);
				panel_TeeSheet.remove(table);
				btn_backButton2.setVisible(false);
				btn_registerTeeTime.setVisible(false);
			}
		});
		
		
	}
	
	private void closeMainPage() {
		this.setVisible(false);
	}
}
