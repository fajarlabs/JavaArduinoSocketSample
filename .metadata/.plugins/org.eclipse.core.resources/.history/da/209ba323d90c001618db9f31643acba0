package com.arduinoboarddesktop.controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultCaret;

import org.json.simple.JSONObject;

import com.arduinoboarddesktop.util.FArduino;
import com.arduinoboarddesktop.util.FSocket;

/**
 * 
 * @author masfajar
 *
 */

public class BoardArduino extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private FSocket socket;
	Map<String,Boolean> lock = new HashMap<String,Boolean>();
	
	int init = 0;
	private FArduino arduino;
	private JTextField txtServerName;
	private JTextField txtPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardArduino frame = new BoardArduino();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BoardArduino() {
		// Init socket
		socket = new FSocket("127.0.0.1", "12000");

		setTitle("Simulasi Komunikasi Serial Arduino (Duemilanove) dengan Java");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 357);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea txtarea = new JTextArea();
		txtarea.setForeground(new Color(255, 255, 255));
		txtarea.setBackground(new Color(0, 0, 0));
		txtarea.setEditable(false);

		// Ambil caret-nya agar selalu update dan scroll kebawah
		DefaultCaret caret = (DefaultCaret) txtarea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		// Masukkan textarea kedalam scrollpane
		JScrollPane sp = new JScrollPane(txtarea);
		sp.setVisible(true);
		sp.setSize(100, 100);
		sp.setBounds(10, 213, 376, 110);

		// Tambahkan kedalam komponen
		contentPane.add(sp);

		JButton btnS1 = new JButton("Hidupkan LED Merah");
		btnS1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnS1.setEnabled(false);
		btnS1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if ((lock.get("red") == null) || lock.get("red") == false ) {
					lock.put("red",true);
					btnS1.setText("LED Merah Hidup");
					btnS1.setBackground(Color.RED);
					arduino.sendData("r");
				} else {
					lock.put("red",false);
					btnS1.setBackground(null);
					btnS1.setText("Hidupkan LED Merah");
					arduino.sendData("R");
				}
			}
		});
		btnS1.setBounds(10, 65, 123, 63);
		contentPane.add(btnS1);

		JButton btnS2 = new JButton("Hidupkan LED Hijau");
		btnS2.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnS2.setEnabled(false);

		btnS2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if ((lock.get("green") == null) || lock.get("green") == false ) {
					lock.put("green", true);
					btnS2.setText("LED Hijau Hidup");
					arduino.sendData("g");
					btnS2.setBackground(Color.GREEN);
				} else {
					lock.put("green", false);
					btnS2.setBackground(null);
					btnS2.setText("Hidupkan LED Hijau");
					arduino.sendData("G");
				}
			}
		});
		btnS2.setBounds(143, 65, 117, 63);
		contentPane.add(btnS2);
		
		JButton btnS3 = new JButton("Hidupkan LED Kuning");
		btnS3.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnS3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((lock.get("yellow") == null) || lock.get("yellow") == false ) {
					lock.put("yellow",true);
					btnS3.setText("LED Kuning Hidup");
					btnS3.setBackground(Color.YELLOW);
					arduino.sendData("y");
				} else {
					btnS3.setBackground(null);
					lock.put("yellow",false);
					btnS3.setText("Hidupkan LED Kuning");
					arduino.sendData("Y");
				}
			}
		});
		btnS3.setEnabled(false);
		btnS3.setBounds(269, 65, 117, 63);
		contentPane.add(btnS3);		

		JButton btnInit = new JButton("Hubungkan Arduino");
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (init == 0) {
					init = 1;
					arduino = new FArduino(txtarea);
					// Aktifkan arduino
					if (arduino.initialize()) {
						btnInit.setBackground(Color.RED);
						btnInit.setText("Matikan Arduino");
						btnS1.setEnabled(true);
						btnS2.setEnabled(true);
						btnS3.setEnabled(true);
					}
				} else {
					init = 0;
					if (arduino != null) {
						// Tutup Arduino
						arduino.close();
						arduino = null;
						btnInit.setBackground(Color.GREEN);
						btnInit.setText("Hubungkan Arduino");
						btnS1.setEnabled(false);
						btnS2.setEnabled(false);
						btnS3.setEnabled(false);
						txtarea.append("Perangkat : Mati \n");
						txtarea.setCaretPosition(txtarea.getDocument().getLength());
					}

				}
			}
		});
		btnInit.setForeground(Color.WHITE);
		btnInit.setBackground(Color.GREEN);
		btnInit.setBounds(10, 11, 378, 43);
		contentPane.add(btnInit);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(10, 139, 376, 63);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Server Bridge");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setForeground(new Color(0, 255, 0));
		lblNewLabel.setBounds(10, 11, 85, 14);
		panel.add(lblNewLabel);

		txtServerName = new JTextField();
		txtServerName.setBounds(10, 26, 132, 20);
		panel.add(txtServerName);
		txtServerName.setText("127.0.0.1");
		txtServerName.setColumns(10);

		JLabel lblPort = new JLabel("Port Bridge");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPort.setForeground(new Color(0, 255, 0));
		lblPort.setBounds(144, 11, 85, 14);
		panel.add(lblPort);

		txtPort = new JTextField();
		txtPort.setBounds(144, 26, 70, 20);
		panel.add(txtPort);
		txtPort.setText("12000");
		txtPort.setColumns(10);

		JButton btnOnlineService = new JButton("Socket ON");
		btnOnlineService.setBounds(224, 25, 142, 23);
		btnOnlineService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (socket.connect()) {
					JSONObject obj = new JSONObject();
					obj.put("command", "r");
					socket.sendRequest(obj.toString());
					txtarea.append("FEEDBACK : " + socket.getResponse() + "\n");
					txtarea.setCaretPosition(txtarea.getDocument().getLength());
					socket.disconnect();
				}

			}
		});
		panel.add(btnOnlineService);
	}
}
