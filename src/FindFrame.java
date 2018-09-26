import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FindFrame extends JFrame {
	
	private static final long serialVersionUID = 1048947243784273892L;
	JTextField sub;
	private JButton findNext;
	private Font appFont = new Font("Tahoma", Font.PLAIN, 11);
	
	FindFrame() {
		
		setTitle("Find Something...");
		setBounds(350, 150, 335, 115);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setVisible(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel what = new JLabel("Find What:");
		what.setFont(appFont);
		what.setBounds(5, 10, 60, 25);
		
		sub = new JTextField();
		sub.setBounds(65, 10, 150, 25);
		
		JRadioButton up = new JRadioButton("Up");
		up.setBounds(75, 45, 40, 20);
		up.setSelected(true);
		up.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				MegaFrame.index = 0;
			}});
		up.setBackground(Color.WHITE);
		
		JRadioButton down = new JRadioButton("Down");
		down.setBounds(120, 45, 60, 20);
		down.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				MegaFrame.index = MegaFrame.text.getText().length()-1;
			}});
		down.setBackground(Color.WHITE);
		
		ButtonGroup gp = new ButtonGroup();
		gp.add(up);
		gp.add(down);
		
		findNext = new JButton("Find Next");
		findNext.setFont(appFont);
		findNext.setBounds(220, 10, 89, 23);
		findNext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				if(up.isSelected()) {
					
				int subIndex = MegaFrame.text.getText().indexOf(sub.getText(), MegaFrame.index);
				
				if(subIndex != -1) {
				
					MegaFrame.text.select(subIndex, subIndex + sub.getText().length());
				
					MegaFrame.index = subIndex+1;
				}
				else {
					JOptionPane.showMessageDialog(null, "Can't find "+sub.getText(), "Eror", JOptionPane.ERROR_MESSAGE);
				 }}
				else if(down.isSelected()) {
					
					int subIndex = MegaFrame.text.getText().lastIndexOf(sub.getText(), MegaFrame.index);
					
					if(subIndex != -1) {
					
						MegaFrame.text.select(subIndex, subIndex + sub.getText().length());
					
						MegaFrame.index = subIndex-1;
					}
					else {
						JOptionPane.showMessageDialog(null, "Can't find "+sub.getText(), "Eror", JOptionPane.ERROR_MESSAGE);
					 }}}});
		JButton cancel = new JButton("Cancel");
		cancel.setFont(appFont);
		cancel.setBounds(220, 40, 89, 23);
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				setVisible(false);
			}});
		JLabel pane = new JLabel("Direction:");
		pane.setFont(appFont);
		pane.setBounds(5, 45, 60, 20);
		
		getContentPane().add(what);
		getContentPane().add(sub);
		getContentPane().add(findNext);
		getContentPane().add(cancel);
		getContentPane().add(pane);
		getContentPane().add(up);
		getContentPane().add(down);
	}
}
