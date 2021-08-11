package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Golfer;
import Model.TimeLoader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class OptionsPage extends JFrame {
	private Golfer golfer;
	private JTable bookedTeeTimes;
	
	public OptionsPage(Golfer golfer) {
		this.golfer = golfer;
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btn_viewMyTeeTimes = new JButton("View Booked Tee Times");
		btn_viewMyTeeTimes.setBounds(6, 26, 203, 52);
		
		btn_viewMyTeeTimes.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		panel.add(btn_viewMyTeeTimes);
		
		JButton btn_bookAnother = new JButton("Book Another Tee Time");
		btn_bookAnother.setBounds(313, 26, 203, 52);
		
		btn_bookAnother.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		panel.add(btn_bookAnother);
		
		JButton btn_exitButton = new JButton("Exit");
		btn_exitButton.setBounds(595, 26, 203, 52);
		btn_exitButton.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		panel.add(btn_exitButton);
		
		JPanel panel_UserBookedTimes = new JPanel();
		panel_UserBookedTimes.setBounds(6, 90, 523, 277);
		panel.add(panel_UserBookedTimes);
		panel_UserBookedTimes.setLayout(null);
		
		JLabel lbl_UserTimeDate = new JLabel("Date");
		lbl_UserTimeDate.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		lbl_UserTimeDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_UserTimeDate.setBounds(6, 21, 61, 16);
		panel_UserBookedTimes.add(lbl_UserTimeDate);
		
		JLabel lbl_UserTeeTimeBook = new JLabel("Tee Time");
		lbl_UserTeeTimeBook.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_UserTeeTimeBook.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		lbl_UserTeeTimeBook.setBounds(241, 21, 80, 16);
		panel_UserBookedTimes.add(lbl_UserTeeTimeBook);
		
		JButton btn_cancelButton = new JButton("Cancel Reservation");
		btn_cancelButton.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		btn_cancelButton.setBounds(595, 315, 203, 52);
		panel.add(btn_cancelButton);
		btn_cancelButton.setVisible(false);
		panel_UserBookedTimes.setVisible(false);
		
		
		
		
		// listener for the exit button
		
		btn_exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeOptionsWindow();
			}
		});
		
		// listener for the book another time button
		btn_bookAnother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainPage main = new MainPage(golfer);
				
				main.setTitle("The Roux Golf Club");
				main.setSize(800, 600);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setVisible(true);
				
				closeOptionsWindow();
			}
		});
		
		// listener for the "view my times" button
		btn_viewMyTeeTimes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_UserBookedTimes.setVisible(true);
		
				DefaultTableModel userTimes;
				try {
					userTimes = golfer.findUserTeeTimes();
					bookedTeeTimes = new JTable(userTimes);
					bookedTeeTimes.setBounds(16, 44, 460, 500);
					panel_UserBookedTimes.add(bookedTeeTimes);
					bookedTeeTimes.setVisible(true);
					btn_cancelButton.setVisible(true);
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(panel_UserBookedTimes, "No Tee Times currently booked for Phone: " + golfer.getPhone());
				}
				
				
			}
		});
		
		
		// listener for the "cancel reservations" button
		btn_cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = bookedTeeTimes.getSelectedRow();
					int dateColumn = 0;
					int timeColumn = 1;
					
					String date = bookedTeeTimes.getModel().getValueAt(row,dateColumn).toString();
					String time = bookedTeeTimes.getModel().getValueAt(row, timeColumn).toString();
					
					if (golfer.cancelReservation(date, time)) {
						JOptionPane.showMessageDialog(panel, "Reservation Successfully Cancelled");
					}
					else {
						throw new ArrayIndexOutOfBoundsException();
					}
					bookedTeeTimes.setVisible(false);
					bookedTeeTimes = new JTable(golfer.findUserTeeTimes());
					bookedTeeTimes.setBounds(16, 44, 460, 500);
					panel_UserBookedTimes.add(bookedTeeTimes);
					bookedTeeTimes.setVisible(true);
				} catch (SQLException | ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(panel, "Error - please make sure a date is selected, and try again.");
				}
			}
		});
	}
	
	public void closeOptionsWindow() {
		this.setVisible(false);
	}
}
