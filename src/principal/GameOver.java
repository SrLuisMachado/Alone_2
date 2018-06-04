package principal;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 * 
 * @author Luis Ernesto
 *Se inicia al final del juego
 */
public class GameOver extends JFrame {
	/**
	 * Variable que llama a la clase JPANEL
	 */
	private JPanel contentPane;
	/**
	 * Constructor de la ventana de Game Over
	 */
	public GameOver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GAME OVER");
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 33));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(79, 47, 266, 91);
		contentPane.add(lblNewLabel);
		
		JButton btnPlay = new JButton("PLAY AGAIN");
		btnPlay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				
				dispose();
			}
		});
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPlay.setForeground(Color.RED);
		btnPlay.setBackground(Color.LIGHT_GRAY);
		btnPlay.setBounds(139, 178, 133, 23);
		contentPane.add(btnPlay);
	}
}
