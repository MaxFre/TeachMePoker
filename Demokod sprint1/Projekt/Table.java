package Projekt;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * 
 * @author Max Frennessen
 * GUI klass med logik i sig...
 * Snabbt skissad prototyp som visar att 
 * Ai har en fungerande logik och tänker ut ett svar.
 *
 */

public class Table extends JFrame implements ActionListener {

	private JLabel Ai1bet = new JLabel("TEST");
	private JLabel Ai2bet = new JLabel("TEST");
	private JLabel Ai3bet = new JLabel("TEST");
	private JLabel Ai4bet = new JLabel("TEST");
	private JLabel pot = new JLabel("pot:");

	private JLabel Ai1 = new JLabel("Ai1");
	private JLabel Ai2 = new JLabel("Ai2");
	private JLabel Ai3 = new JLabel("Ai3");
	private JLabel Ai4 = new JLabel("Ai4");
	private JTextField chat = new JTextField("");
	private Cards cards;
	private String[] firstTurn = new String[10];
	private JButton check = new JButton("Check");
	private JButton fold = new JButton("new");
	private Ai ai;
	String[] fromDeck;
	private int test = 0;

	public Table(int players) {

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 500));
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		BuildTable();

		cards = new Cards();
		firstTurn = cards.getCards();
		updateBoard(firstTurn);
	}

	public void newRound() {
		Random nbr = new Random();
		String potten = String.valueOf(nbr.nextInt(1000));
		pot.setText("pot: " + potten);
		firstTurn = cards.getCards();
		updateBoard(firstTurn);
	}

	public void BuildTable() {
		Random nbr = new Random();
		String potten = String.valueOf(nbr.nextInt(1000));
		pot.setText("pot: " + potten);

		pot.setBounds(330, 150, 200, 100);

		Ai1bet.setBounds(100, 200, 200, 100);
		Ai2bet.setBounds(210, 100, 200, 100);
		Ai3bet.setBounds(410, 100, 200, 100);
		Ai4bet.setBounds(540, 200, 200, 100);

		Ai1.setBounds(70, 140, 200, 100);
		Ai2.setBounds(190, 40, 200, 100);
		Ai3.setBounds(390, 40, 200, 100);
		Ai4.setBounds(510, 140, 200, 100);

		chat.setBounds(300, 240, 150, 100);
		check.setBounds(230, 380, 120, 70);
		fold.setBounds(380, 380, 120, 70);

		add(pot);
		add(Ai1);
		add(Ai2);
		add(Ai3);
		add(Ai4);

		add(chat);
		add(check);
		add(fold);

		add(Ai1bet);
		add(Ai2bet);
		add(Ai3bet);
		add(Ai4bet);

		Icon icon = new ImageIcon("images/gubbe.jpg");
		Ai1.setIcon(icon);
		Ai2.setIcon(icon);
		Ai3.setIcon(icon);
		Ai4.setIcon(icon);

		check.addActionListener(this);
		fold.addActionListener(this);
	}

	public void updateBoard(String[] cards) {
		this.fromDeck = cards;
		Ai1.setText(cards[2] + " and " + cards[3]);
		Ai2.setText(cards[4] + " and " + cards[5]);
		Ai3.setText(cards[6] + " and " + cards[7]);
		Ai4.setText(cards[8] + " and " + cards[9]);
		chat.setText("            " + cards[0] + " and " + cards[1]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean aibet;

		if (e.getSource() == check) {

			if (test == 0) {
				ai = new Ai(fromDeck[2], fromDeck[3], 500);
				aibet = ai.decision();
				if (aibet) {
					Ai1bet.setText("BET");
				} else
					Ai1bet.setText("FOLD");

			}

			if (test == 1) {
				ai = new Ai(fromDeck[4], fromDeck[5], 500);
				aibet = ai.decision();
				if (aibet) {
					Ai2bet.setText("BET");
				} else
					Ai2bet.setText("FOLD");

			}

			if (test == 2) {
				ai = new Ai(fromDeck[6], fromDeck[7], 500);
				aibet = ai.decision();
				if (aibet) {
					Ai3bet.setText("BET");
				} else
					Ai3bet.setText("FOLD");

			}

			if (test == 3) {
				ai = new Ai(fromDeck[8], fromDeck[9], 500);
				aibet = ai.decision();
				if (aibet) {
					Ai4bet.setText("BET");
				} else
					Ai4bet.setText("FOLD");

			}

			if (test == 3) {
				check.setEnabled(false);
			}
			test++;
		}

		if (e.getSource() == fold) {
			cards.newRound();
			newRound();
			check.setEnabled(true);
			Ai1bet.setText("TEST");
			Ai2bet.setText("TEST");
			Ai3bet.setText("TEST");
			Ai4bet.setText("TEST");
			test = 0;
		}

	}

}
