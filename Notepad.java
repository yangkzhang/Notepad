//package note;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.undo.*;

import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.regex.*;

public class Notepad {
	private static void createAndShowGUI(){
		JFrame win = new FirstWindow("notepad");
	}
	public static void main(String[] args) {
       //Ϊ�¼������̰߳���һ������
        //��������ʾ��������ͼ���û�����
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}


class FirstWindow extends JFrame implements ActionListener, DocumentListener,CaretListener,ItemListener,WindowListener{
	JMenuBar menubar;
	JMenu menuFile, menuEdit,menuView,menuFormat,menuHelp;
	JMenuItem itemNew,itemOpen, itemSave,itemSave_As, itemPage_Setup,itemPrint,itemExit;
	JMenuItem itemUndo,itemCut,itemCopy, itemPaste,itemDelete,itemFind,itemFind_Next,itemReplace,itemGoto,itemSelect_All,itemDate;
	JMenuItem itemFont;
	JCheckBox itemWrap,itemStatu;
	JMenuItem itemHelp,itemAbout;
	Clipboard clip ;
	public static JTextArea textArea = new JTextArea();
//	JTextArea textAreaRow=new JTextArea();
	JLabel statusBar,statusBar1;
	UndoManager manager;
	boolean flag=true;
	int i=0;
	JButton confirm=new JButton("ȷ��");
	//FirstWindow�Ĺ��췽��
	FirstWindow(){}
	FirstWindow(String s){
		setTitle(s); 
		setSize(500,400);
		setLocation(250,250);
		setVisible(true);
		setResizable(true);
		
		
		 manager= new UndoManager();
		clip = new Clipboard(null);
		//����״̬��
		statusBar = new JLabel();
		statusBar1 = new JLabel();
		//�����˵���
		menubar=new JMenuBar();
		//�����˵�
		menuFile = new JMenu("�ļ�(F)"); //�ļ��˵�
		menuEdit = new JMenu("�༭(E)"); //�༭�˵�
		menuFormat = new JMenu("��ʽ(O)");
		menuView = new JMenu("�鿴(V)");
		menuHelp = new JMenu("����(H)"); //�����˵�
		//�����˵���
		itemNew = new JMenuItem("�½�(N)");
		itemOpen = new JMenuItem("��(O)");
		itemSave = new JMenuItem("����(S)");
		itemSave_As = new JMenuItem("���Ϊ(A)");
		itemPage_Setup = new JMenuItem("ҳ������(U)");
		itemPrint =new JMenuItem("��ӡ(P)");
		itemExit = new JMenuItem("�˳�");
		
		itemUndo =new JMenuItem("����(U)");
		itemCut = new JMenuItem("����(T)");
		itemCopy = new JMenuItem("����(C)");
		itemPaste = new JMenuItem("ճ��(P)");
		itemDelete = new JMenuItem("ɾ��(L)");
		itemFind =new JMenuItem("����(F)");
		itemFind_Next =new JMenuItem("������һ��(N)");
		itemReplace = new JMenuItem("�滻(R)");
		itemGoto =new JMenuItem("ת��(G)");
		itemSelect_All =new JMenuItem("ȫѡ(A)");
		itemDate = new JMenuItem("ʱ��/����(D)");
		
		itemFont =new JMenuItem("����(F)...");
		itemWrap =  new JCheckBox("�Զ�����(W)");
		
		itemStatu = new JCheckBox("״̬��(S)");
		
		itemHelp = new JMenuItem("�鿴����(H)");
		itemAbout = new JMenuItem("���ڼ��±�(A)");
	
		//���ñ���˵���
		itemSave.setEnabled(false);
		
		//���ÿ�ݼ�
		itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		
		itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK));
		itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
		itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		itemDelete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		itemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK));
		itemFind_Next.setAccelerator(KeyStroke.getKeyStroke("F3"));
		itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
		itemGoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,InputEvent.CTRL_MASK));
		itemSelect_All.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		itemDate.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_F5));
		//�������Ƿ�
		menuFile.setMnemonic('F');
		menuEdit.setMnemonic('E');
		menuFormat.setMnemonic('O');
		menuView.setMnemonic('V');
		menuHelp.setMnemonic('H');
		
		itemNew.setMnemonic('N');
		itemOpen.setMnemonic('O');
		itemSave.setMnemonic('S');
		itemSave_As.setMnemonic('A');
		itemPage_Setup.setMnemonic('U');
		itemPrint.setMnemonic('P');
		
		itemUndo.setMnemonic('U');
		itemCut.setMnemonic('T');
		itemCopy.setMnemonic('C');
		itemPaste.setMnemonic('P');
		itemDelete.setMnemonic('L');
		itemFind.setMnemonic('F');
		itemFind_Next.setMnemonic('N');
		itemReplace.setMnemonic('R');
		itemGoto.setMnemonic('G');
		itemSelect_All.setMnemonic('A');
		itemDate.setMnemonic('D');
		
		itemFont.setMnemonic('F');
		itemWrap.setMnemonic('W');
		
		itemStatu.setMnemonic('S');
		
		itemHelp.setMnemonic('H');
		itemAbout.setMnemonic('A');
		
		(textArea.getDocument()).addDocumentListener(this);
		//textArea.addCaretListener(this);
		//Ϊ�˵��������¼���Ӧ
		itemNew.addActionListener(this);
		itemOpen.addActionListener(this);
		itemSave.addActionListener(this);
		itemSave_As.addActionListener(this);
		itemPage_Setup.addActionListener(this);
		itemPrint.addActionListener(this);
		itemExit.addActionListener(this);
		itemUndo.addActionListener(this);
		itemCut.addActionListener(this);
		itemCopy.addActionListener(this);
		itemPaste.addActionListener(this);
		itemDelete.addActionListener(this);
		itemFind.addActionListener(this);
		itemFind_Next.addActionListener(this);
		itemReplace.addActionListener(this);
		itemGoto.addActionListener(this);
		itemSelect_All.addActionListener(this);
		itemDate.addActionListener(this);
		itemFont.addActionListener(this);
		itemWrap.addItemListener(this);
		itemStatu.addItemListener(this);
		itemHelp.addActionListener(this);
		itemAbout.addActionListener(this);
		textArea.addCaretListener(this);
		confirm.addActionListener(this);
		this.addWindowListener(this);
		
		//Ϊ�ļ��˵����Ӳ˵���
		menuFile.add(itemNew);
		menuFile.add(itemOpen);
		menuFile.add(itemSave);
		menuFile.add(itemSave_As);
		menuFile.add(itemPage_Setup);
		menuFile.add(itemPrint);
		menuFile.addSeparator();
		menuFile.add(itemExit);
		//Ϊ�༭�˵����Ӳ˵���
		menuEdit.add(itemUndo);
		menuEdit.add(itemCut);
		menuEdit.add(itemCopy);
		menuEdit.add(itemPaste);
		menuEdit.add(itemDelete);
		menuEdit.add(itemFind);
		menuEdit.add(itemFind_Next);
		menuEdit.add(itemReplace);
		menuEdit.add(itemGoto);
		menuEdit.add(itemSelect_All);
		menuEdit.add(itemDate);
		//Ϊ��ʽ�˵����Ӳ˵���
		menuFormat.add(itemWrap);
		menuFormat.add(itemFont);
		//Ϊ�鿴�˵����Ӳ˵���
		menuView.add(itemStatu);
		//Ϊ�����˵����Ӳ˵���
		menuHelp.add(itemHelp);
		menuHelp.add(itemAbout);
		
		//���˵����ӵ��˵�����
		menubar.add(menuFile);
		menubar.add(menuEdit);
		menubar.add(menuFormat);
		menubar.add(menuView);
		menubar.add(menuHelp);
		
		
		textArea.getDocument().addUndoableEditListener(manager);
		//Ϊ�������ò˵���
		this.setJMenuBar(menubar);
		//�����ı�����
		//
		//����ı����򵽴����У������ӹ�����
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
		//���״̬������JLabel��Ϊ״̬��
		
		textArea.getMouseListeners();
		//��������
		Font f = new Font("����", Font.BOLD, 24);
		textArea.setFont(f);
		validate();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
	}
	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();
		//����ǲ˵�<��>������
		if(src == itemOpen){
			String fileName;
			JFileChooser c = new JFileChooser();
			int rVal = c.showOpenDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				fileName = c.getSelectedFile().getAbsolutePath();
				try{
					File file = new File(fileName);
					
					FileInputStream fis = new FileInputStream(file);
					
					byte b[] = new byte[(int)file.length()];
					fis.read(b);
					fis.close();
					
					textArea.setText(new String(b));
					
					itemSave.setEnabled(false);
					statusBar.setText("�ı��ַ�����" + textArea.getText().length());
					
					setTitle(fileName);
				}
				catch(IOException ep){
					JOptionPane.showConfirmDialog(this, ep.toString(), "���ļ�����", JOptionPane.YES_NO_OPTION);
				}
				
			}	
		}//����²˵�<�½�>������
		else if(src == itemNew){
			textArea.setText("");
		}
		//����ǲ˵�<����>������
		else if(src == itemSave){
			flag=false;
			String fileName = new String();
			
			JFileChooser c = new JFileChooser();
			//��ʾ����Ի���
			int rVal = c.showSaveDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				//��ȡ�ļ���
				fileName = c.getSelectedFile().getAbsolutePath();
				
				try{
					
					FileWriter fw = new FileWriter(fileName);
					fw.write(textArea.getText());
					fw.close();
					setTitle(fileName);
				}
				catch(IOException ep){
					JOptionPane.showConfirmDialog(this, ep.toString(), "�����ļ�����", JOptionPane.YES_NO_OPTION);
				}
			}			
		}
		//����ǲ˵�<�˳�>������
		else if(src == itemExit){
			this.dispose();
		}

		//����ǲ˵�<����>������
		else if(src == itemAbout){
			JDialog dia=new JDialog(this,"���ڡ����±���",true);
			dia.setBounds(200, 300, 400, 300);
			JLabel label;
			String iaital="<html>\n"+
					         "<font size = +4><font color = green>Window7�콢��<font>\n"+
							"________________________________________\n"+
							 "<font size = -1>microsoft windows<font>\n"+
							"<font size = -1>��Ȩ���й�notepad<font>\n"+
							 "<font color = red>֪ʶ��ȨҪ�ܵ�����<font>\n"+
							 "</html>\n";
			label=new JLabel(iaital);
			dia.add(confirm,BorderLayout.SOUTH);
			dia.add(label,BorderLayout.NORTH);
			dia.setVisible(true);
		}
		//����˵�<����>������
		else if(src==itemFont){
			Notefont box=new Notefont("����");
		}
		//����˵�<����>������
		else if(src == itemCut){
			textArea.cut();
		}
		//����˵�<ճ��>������
		else if(src == itemPaste){
			textArea.paste();
		}
		//����˵�<����>������
		else if(src == itemCopy){
			textArea.copy();
		}
		//����˵�<ȫѡ>������
		else if(src == itemSelect_All){
			textArea.selectAll();
		}
		//����˵�<ʱ��/����>������
		else if(src == itemDate){
			Date now=new Date();
			DateFormat da=DateFormat.getTimeInstance();
			String str = da.format(now);
			DateFormat da1=DateFormat.getDateInstance();
			String str1 = da1.format(now);
			textArea.append(str+" ");
			textArea.append(str1);
		}
		//����˵�<����>������
				else if(src == itemUndo){
					if(manager.canUndo()){
						manager.undo();
					}else{
						JOptionPane.showMessageDialog(this, "�����ٳ�������ֹͣ", "����", JOptionPane.ERROR_MESSAGE);
					}
				}
		//����˵�<����>������
				else if(src == itemFind){
					Notefind find=new Notefind();
					
				}
		//����˵�<�滻>������
				else if(src== itemReplace){
					Notereplace replace=new Notereplace();
				}
		//����˵�<ɾ��>������
				else if(src == itemDelete){
					textArea.replaceSelection("");
				}
		//����˵�<���Ϊ>������
				else if(src == itemSave_As){
					String fileName = new String();
					
					JFileChooser c = new JFileChooser();
					//��ʾ����Ի���
					int rVal = c.showDialog(this, "���Ϊ");
					if (rVal == JFileChooser.APPROVE_OPTION) {
						//��ȡ�ļ���
						fileName = c.getSelectedFile().getAbsolutePath();
						
						try{
							
							FileWriter fw = new FileWriter(fileName);
							fw.write(textArea.getText());
							fw.close();
							setTitle(fileName);
						}
						catch(IOException ep){
							JOptionPane.showConfirmDialog(this, ep.toString(), "���Ϊ�ļ�����", JOptionPane.YES_NO_OPTION);
						}
					}			
				}
	}
	//�ı�������
	public void changedUpdate(DocumentEvent e)
	{
		statusBar.setText("�ı��ַ�����" + textArea.getText().length());
		//���ı��������˸��ģ������б���
		itemSave.setEnabled(true);
	}
	//�ı���ɾ���ַ�
	public void removeUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
	//�ı��������ַ�
	public void insertUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
	
	//����setTitle����
	public void setTitle(String s){
		if(s=="")
			super.setTitle("���±�");
		else
			super.setTitle(s+" - ���±�");
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED){
		JCheckBox obj = (JCheckBox)e.getItemSelectable();
		//�����Զ�����
		
		 if(obj==itemWrap){
			 if(itemWrap.isSelected()){
				 textArea.setLineWrap(! textArea.getLineWrap() );
			 	add(statusBar, BorderLayout.SOUTH);
			 	statusBar.setVisible(true);
			 	statusBar1.setVisible(false);
			 	//itemStatu.setEnabled(false);
			 }
		}
		//����˵�<״̬��>������
		else if(obj==itemStatu){
			if(itemStatu.isSelected()){
				add(statusBar1,BorderLayout.SOUTH);
				statusBar1.setVisible(true);
				statusBar.setVisible(false);
				//itemWrap.setEnabled(false);
			}
		}
	}
	}
	//����������
	public void caretUpdate(CaretEvent e) {
		textArea=(JTextArea)e.getSource();
		int pos=textArea.getCaretPosition();
		try {
			int row=textArea.getLineOfOffset(pos)+1;
			int column=pos-textArea.getLineStartOffset(row-1)+1;
			statusBar1.setText("�� "+row+" ��"+" , �� "+column+" ��");
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	public void windowOpened(WindowEvent e) {
		
	}
	public void windowClosing(WindowEvent e) {
		if(flag){
			int n=JOptionPane.showConfirmDialog(this,"�ļ���û���棬ȷ���˳���", "����", JOptionPane.YES_NO_OPTION);
			if(n==JOptionPane.YES_OPTION){
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			else{}
		}
		
	}
	public void windowClosed(WindowEvent e) {
		
	}
	
	public void windowIconified(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}
	
	public void windowActivated(WindowEvent e) {
		
	}
	
	public void windowDeactivated(WindowEvent e) {
		
	}
}

 class Notereplace extends FirstWindow implements ActionListener{
	JDialog dialog=new JDialog(this,"�滻");
	JButton buttonfindnext,buttonreplace,buttonreplaceall,buttoncancel;
	JLabel label1,label2;
	JTextField field1,field2;
	JCheckBox check;
	int pos=0;
	boolean flag3=false;
	Pattern p;//ģʽ����
	Matcher m;//ƥ�����
	Notereplace(){
		dialog.setBounds(200, 300, 400,200);
		dialog.setVisible(true);
		dialog.setResizable(false);
		buttonfindnext=new JButton("������һ��");
		buttonreplace=new JButton("      �滻       ");
		buttonreplaceall=new JButton("  ȫ���滻    ");
		buttoncancel=new JButton("    ȡ��          ");
		check=new JCheckBox("���ִ�Сд");
		label1=new JLabel("�������ݣ�");
		label2=new JLabel("�滻Ϊ��    ");
		field1=new JTextField(20);
		field2=new JTextField(20);
		
		buttonfindnext.addActionListener(this);
		buttonreplace.addActionListener(this);
		buttonreplaceall.addActionListener(this);
		buttoncancel.addActionListener(this);
		check.addItemListener(this);
		
		Box boxV1,boxV2,boxV3,boxV4,boxV5,boxV6;
		boxV1=Box.createHorizontalBox();
		boxV1.add(label1);
		boxV1.add(Box.createHorizontalStrut(5));
		boxV1.add(field1);
		boxV1.add(Box.createHorizontalStrut(5));
		boxV1.add(buttonfindnext);
		
		boxV2=Box.createHorizontalBox();
		boxV2.add(label2);
		boxV2.add(Box.createHorizontalStrut(5));
		boxV2.add(field2);
		boxV2.add(Box.createHorizontalStrut(5));
		boxV2.add(buttonreplace);
		
		boxV3=Box.createHorizontalBox();
		boxV3.add(Box.createHorizontalStrut(293));
		boxV3.add(buttonreplaceall);
		
		boxV4=Box.createHorizontalBox();
		boxV4.add(check);
		boxV4.add(Box.createHorizontalStrut(203));
		boxV4.add(buttoncancel);
		
		boxV5=Box.createVerticalBox();
		boxV5.add(Box.createVerticalStrut(20));
		boxV5.add(boxV1);
		boxV5.add(Box.createVerticalStrut(15));
		boxV5.add(boxV2);
		boxV5.add(Box.createVerticalStrut(10));
		boxV5.add(boxV3);
		boxV5.add(Box.createVerticalStrut(10));
		boxV5.add(boxV4);
		boxV6=Box.createHorizontalBox();
		boxV5.add(boxV6);
		dialog.add(boxV5);
	}
	public void actionPerformed(ActionEvent e){
		Object src=e.getSource();
		String str,strfield;
		if(flag3){
			 str= textArea.getText().toLowerCase();
			 strfield=field1.getText().toLowerCase();
		}
		else{
			str=textArea.getText();
			strfield=field1.getText();
		}
		p=Pattern.compile(strfield);
		m=p.matcher(str);
		pos=textArea.getCaretPosition();
		if(src == buttonfindnext){
			
				if(m.find(pos)){
					textArea.select(m.start(), m.end());
					pos=m.end();
				}else {
					JOptionPane.showMessageDialog(this,
				"�Ҳ���"+"��"+field1.getText()+"��", "����", JOptionPane.WARNING_MESSAGE);
				}
			}
		
		
		else if(src == buttonreplace){
				textArea.replaceSelection(field2.getText());
			}
			else if(src == buttonreplaceall){
				pos=0;
				while(m.find(pos)){
				if(m.find(pos)){
					textArea.select(m.start(), m.end());
					textArea.replaceSelection(field2.getText());
					pos=m.end();
				}
			}
		}
			else if(src == buttoncancel){
				this.dispose();
			}
	}
	public void itemStateChanged(ItemEvent e) {
		JCheckBox box=(JCheckBox)e.getSource();
		if(box.isSelected()){
			flag3=true;
		}
	}
}

   class Notefind extends FirstWindow implements ActionListener{
	JLabel label = new JLabel("��������(N):");
	JTextField text = new JTextField(20);
	JButton buttonfind,buttoncancel;
	JCheckBox check= new JCheckBox("���ִ�Сд(C)");
	JRadioButton buttondown,buttonup;
	JPanel panel = new JPanel();
	JDialog dialog = new JDialog(this,"����");
	int pos=0;
	int time=0;
	boolean flag1=false,flag2=true;
	Pattern p;//ģʽ����
	Matcher m;//ƥ�����
	Notefind(){
		
		Box boxV1,boxV2,boxV3;
		
		dialog.setVisible(true);
		dialog.setBounds(300, 200, 400,150);
		
		buttonfind = new JButton("������һ��(F)");
		buttoncancel = new JButton("     ȡ��             ");
		
		buttondown = new JRadioButton("����");
		buttonup = new JRadioButton("����");
		
		buttonup.addActionListener(this);
		buttondown.addActionListener(this);
		buttonfind.addActionListener(this);
		buttoncancel.addActionListener(this);
		check.addItemListener(this);
		buttondown.setActionCommand("down");
		buttondown.setSelected(true);
		buttonup.setActionCommand("up");
		
		ButtonGroup group = new ButtonGroup();
		group.add(buttonup);
		group.add(buttondown);
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("����"),
				BorderFactory.createEmptyBorder(10,10,10,10)));
		panel.setLayout(new GridLayout(1,2));
		panel.add(buttondown);
		panel.add(buttonup);
		
		boxV1 = Box.createHorizontalBox();
		boxV1.add(label);
		boxV1.add(Box.createHorizontalStrut(5));
		boxV1.add(text);
		boxV1.add(Box.createHorizontalStrut(5));
		boxV1.add(buttonfind);
		
		boxV2 = Box.createHorizontalBox();
		boxV2.add(check);
		boxV2.add(Box.createHorizontalStrut(5));
		boxV2.add(panel);
		boxV2.add(Box.createHorizontalStrut(5));
		boxV2.add(buttoncancel);
		
		boxV3=Box.createVerticalBox();
		boxV3.add(Box.createVerticalStrut(15));
		boxV3.add(boxV1);
		boxV3.add(Box.createVerticalStrut(5));
		boxV3.add(boxV2);
		
		dialog.add(boxV3);
	}
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("down")){
				flag2=true;
			}
			else if(e.getActionCommand().equals("up")){
				flag2=false;
				time=1;
			}
				
			Object src=e.getSource();
			String str="",strtext="";
			if(!flag1){
				 str= textArea.getText().toLowerCase();  
				 strtext=text.getText().toLowerCase();
			}
			else if(flag1){
				str=textArea.getText();     
				strtext=text.getText();
			}
			p=Pattern.compile(strtext);
			m=p.matcher(str);
			pos=textArea.getCaretPosition();
				if(src == buttonfind){
					if(flag2){
						if(m.find(pos)){
							if(text.getText().equals("")){
								JOptionPane.showMessageDialog(this, 
										"������Ҫ���ҵ��ַ�","����",JOptionPane.ERROR_MESSAGE);
							}else if(!text.getText().equals("")){
								textArea.select(m.start(), m.end());
							}
						}else{
							JOptionPane.showMessageDialog(this, 
									"�Ҳ���"+"��"+text.getText()+"��", "����",JOptionPane.WARNING_MESSAGE);
						}
					}
					else if(!flag2){    
						if(pos!=0){
							if(text.getText().equals("")){
								JOptionPane.showMessageDialog(this, 
										"������Ҫ���ҵ��ַ�","����",JOptionPane.ERROR_MESSAGE);
							}else if(!text.getText().equals("")){   
							if(time==1){
								pos=str.lastIndexOf(strtext, pos);
								time--; 
							}else if(time==0){  
								pos=str.lastIndexOf(strtext, (pos-2*strtext.length()));  
							}
								textArea.select(pos, pos+strtext.length());
							}
						}else if(pos==0){
							JOptionPane.showMessageDialog(this, 
									"�Ҳ���"+"��"+text.getText()+"��", "����",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			if(src==buttoncancel){
				int n=JOptionPane.showConfirmDialog(this,
						"��ȷ��Ҫ�˳�������","����",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION){
					dialog.dispose();
				}
			}
		}
		public void itemStateChanged(ItemEvent e) {
			JCheckBox box=(JCheckBox)e.getSource();
			if(box.isSelected()){
				flag1=true;
			}
			else if(!box.isSelected()){
				flag1=false;
			}
		}
}
  class Notefont extends FirstWindow implements ItemListener,ActionListener{
   	Font f1=new Font("����", Font.BOLD, 24);
   	int vul = Font.BOLD;
   	String str = "����",str2="8";
   	String []string = {"��ŷ����","����GB2312"};
   	Box basebox,boxV1,boxV2,boxV3,boxV4,boxV5;
   	JComboBox<String> combo;
   	JButton buttonconfirm,buttoncancel;
   	JTextField textfont,textstatu,textsize;
   	List listfont,liststatu,listsize;
   	JPanel panel,panel1,panel2,panel3; 
   	JLabel label,label1;
   	
   	public Notefont(String s){
   		setTitle(s);
   		setBounds(200, 50, 500, 470);
   		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   		setVisible(true);
   		setResizable(false);
   		//�ı���
   		textfont=new JTextField(25);
   		textstatu=new JTextField(15);
   		textsize=new JTextField(10);
   		//�����б�
   		listfont=new List(6);
   		liststatu=new List(6);
   		listsize=new List(6);
   		
   		combo = new JComboBox<String>(string);
   		panel = new JPanel();
   		panel1 = new JPanel();
   		panel2 = new JPanel();
   		panel3 = new JPanel();
   		label = new JLabel("AaBbYyZz");
   		label1 = new JLabel("�ű���");
   		buttonconfirm = new JButton("ȷ��");
   		buttoncancel = new JButton("ȡ��");
   		buttonconfirm.addActionListener(this);
   		buttoncancel.addActionListener(this);
   		//������б�<����>�����б���
   		GraphicsEnvironment get = GraphicsEnvironment.getLocalGraphicsEnvironment();
   		String font[] = get.getAvailableFontFamilyNames();// �����
   		int i=0;
   		for(i=0;i<font.length;i++){
   			listfont.add(font[i]);
   		}
   		//������б�<����>�����б���
   		liststatu.add("����");
   		liststatu.add("б��");
   		liststatu.add("����");
   		liststatu.add("���� ��б");
   		//������б�<��С>�����б���
   		listsize.add("8");
   		listsize.add("9");
   		listsize.add("10");
   		listsize.add("11");
   		listsize.add("12");
   		listsize.add("14");
   		listsize.add("16");
   		listsize.add("18");
   		listsize.add("20");
   		listsize.add("22");
   		listsize.add("24");
   		listsize.add("26");
   		listsize.add("28");
   		listsize.add("36");
   		listsize.add("48");
   		listsize.add("72");
   		
   		listfont.addItemListener(this);
   		liststatu.addItemListener(this);
   		listsize.addItemListener(this);
   		
   		textfont.setText(listfont.getItem(0));
   		textstatu.setText(liststatu.getItem(0));
   		textsize.setText(listsize.getItem(10));
   		//BoxLayout����
   		//����
   		JLabel labelfont=new JLabel("����(F)");
   		JLabel labelstatu=new JLabel("����(Y)");
   		JLabel labelsize=new JLabel("��С(S)");
   		boxV1=Box.createVerticalBox();
   		boxV1.add(labelfont);
   		boxV1.add(Box.createVerticalStrut(5));
   		boxV1.add(textfont);
   		boxV1.add(new JScrollPane(listfont));
   		//����
   		boxV2=Box.createVerticalBox();
   		boxV2.add(labelstatu);
   		boxV2.add(Box.createVerticalStrut(5));
   		boxV2.add(textstatu);
   		boxV2.add(new JScrollPane(liststatu));
   		//��С
   		boxV3=Box.createVerticalBox();
   		boxV3.add(labelsize);
   		boxV3.add(Box.createVerticalStrut(5));
   		boxV3.add(textsize);
   		boxV3.add(listsize);
   		
   		basebox=Box.createHorizontalBox();
   		basebox.add(boxV1);
   		basebox.add(Box.createHorizontalStrut(10));
   		basebox.add(boxV2);
   		basebox.add(Box.createHorizontalStrut(10));
   		basebox.add(boxV3);
   		
   		setLayout(new GridLayout(3,1));
   		//ʾ����
   		panel.setLayout(new GridLayout(1,2));
   		panel.add(panel2);
   		panel1.setBorder(BorderFactory.createCompoundBorder(
   				BorderFactory.createTitledBorder("ʾ��"),
   				BorderFactory.createEmptyBorder(10,10,10,10)));
   		panel1.add(label);
   		//panel.add(panel1);
   		//�ű�
   		boxV4 = Box.createVerticalBox();
   		boxV4.add(label1);
   		boxV4.add(Box.createVerticalStrut(2));
   		boxV4.add(combo);
   		//Box���֣�ʾ����ű���
   		
   		JSplitPane split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel1,boxV4);
   		split.setDividerLocation(90);
   		panel.add(split);
   		//ȷ�Ϻ�ȡ��
   		//null�ղ���
   		panel3.setLayout(null);
   		panel3.add(buttonconfirm);
   		buttonconfirm.setBounds(300, 70, 80, 30);
   		panel3.add(buttoncancel);
   		buttoncancel.setBounds(400, 70, 80, 30);
   		
   		add(basebox);
   		add(panel);
   		add(panel3);
   	}
   		public void itemStateChanged(ItemEvent e) {
   			if(e.getStateChange()==ItemEvent.SELECTED){
   				if(listfont==(List)e.getItemSelectable()){
   					int j=listfont.getSelectedIndex();
   					String str3=listfont.getItem(j);
   					textfont.setText(str3);
   					str=str3;
   				}
   				else if(liststatu==(List)e.getItemSelectable()){
   					int k=liststatu.getSelectedIndex();
   					String str4=liststatu.getItem(k);
   					textstatu.setText(str4);
   					if(k==0){
   						vul=Font.PLAIN;
   					}
   				else if(k==1){
   					vul=Font.ITALIC;
   				}
   				else if(k==2){
   					vul=Font.BOLD;
   				}
   				else if(k==3){
   					vul=Font.BOLD+Font.ITALIC;
   				}
   			}
   			else if(listsize==(List)e.getItemSelectable()){
   				int i=listsize.getSelectedIndex();
   				String str5=listsize.getItem(i);
   				textsize.setText(str5);
   				str2=str5;
   			}
   		}
   			Font ff=new Font(str, vul, Integer.parseInt(str2));
   			label.setFont(ff);
   			f1=ff;
   	}
   		public void actionPerformed(ActionEvent e){
   			Object obj = e.getSource();
   			if(obj==buttonconfirm){
   				textArea.setFont(f1);
   				this.dispose();
   			}
   			else if(obj==buttoncancel){
   				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   			}
   		}
   }
