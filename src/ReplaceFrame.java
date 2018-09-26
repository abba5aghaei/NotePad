import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ReplaceFrame extends JFrame {
	
	private static final long serialVersionUID = -7455425722345017925L;
	public JTextField sub2, rep;
	private JButton findNext2, repla, replaAll;
	private Font appFont = new Font("Tahoma", Font.PLAIN, 11);
	
	ReplaceFrame() {
		
		setBackground(Color.WHITE);
		setTitle("Replace Somthing...");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 290, 159);
		setVisible(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel what2 = new JLabel("Find What:");
		what2.setFont(appFont);
		what2.setBounds(10, 11, 60, 14);
		contentPane.add(what2);
		
		JLabel with = new JLabel("Replace With:");
		with.setFont(appFont);
		with.setBounds(10, 38, 81, 21);
		contentPane.add(with);
		
		sub2 = new JTextField();
		sub2.setBounds(83, 9, 184, 21);
		contentPane.add(sub2);
		
		rep = new JTextField();
		rep.setBounds(83, 39, 184, 20);
		contentPane.add(rep);
		
		findNext2 = new JButton("Find Next");
		findNext2.setFont(appFont);
		findNext2.setBounds(10, 64, 125, 23);
		findNext2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				int sub2Index = MegaFrame.text.getText().indexOf(sub2.getText(), MegaFrame.index);
				
				if(sub2Index != -1) {
				
					MegaFrame.text.select(sub2Index, sub2Index + sub2.getText().length());
				
					MegaFrame.index = sub2Index+1;
				}
				else {
					JOptionPane.showMessageDialog(null, "Can't find "+sub2.getText(), "Eror", JOptionPane.ERROR_MESSAGE);
				 }
			}
		});
		contentPane.add(findNext2);
		
		repla = new JButton("Replace");
		repla.setFont(appFont);
		repla.setBounds(140, 64, 125, 23);
		repla.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				MegaFrame.text.replaceSelection(rep.getText());
				
				MegaFrame.flagSave = false;
			}
		});
		contentPane.add(repla);
		
		replaAll = new JButton("Replace All");
		replaAll.setFont(appFont);
		replaAll.setBounds(140, 92, 125, 23);
		replaAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				MegaFrame.index = 0;
				
				String org = sub2.getText();
				
				if(!org.isEmpty())
				{
				int sub2Index = MegaFrame.text.getText().indexOf(org, MegaFrame.index);
				
				while(sub2Index != -1) {
					
					MegaFrame.text.select(sub2Index, sub2Index + org.length());
				
					MegaFrame.text.replaceSelection(rep.getText());
				
					MegaFrame.index = sub2Index+1;
				
				sub2Index = MegaFrame.text.getText().indexOf(org, MegaFrame.index);
				}
				MegaFrame.flagSave = false;
				}}});
		contentPane.add(replaAll);
		
		JButton cancel2 = new JButton("Cancel");
		cancel2.setFont(appFont);
		cancel2.setBounds(10, 92, 125, 23);
		cancel2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				setVisible(false);
			}});
		contentPane.add(cancel2);
	}
}
