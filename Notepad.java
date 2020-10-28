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
       //为事件调度线程安排一个任务
        //创建并显示这个程序的图形用户界面
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
	JButton confirm=new JButton("确定");
	//FirstWindow的构造方法
	FirstWindow(){}
	FirstWindow(String s){
		setTitle(s); 
		setSize(500,400);
		setLocation(250,250);
		setVisible(true);
		setResizable(true);
		
		
		 manager= new UndoManager();
		clip = new Clipboard(null);
		//创建状态栏
		statusBar = new JLabel();
		statusBar1 = new JLabel();
		//创建菜单栏
		menubar=new JMenuBar();
		//创建菜单
		menuFile = new JMenu("文件(F)"); //文件菜单
		menuEdit = new JMenu("编辑(E)"); //编辑菜单
		menuFormat = new JMenu("格式(O)");
		menuView = new JMenu("查看(V)");
		menuHelp = new JMenu("帮助(H)"); //帮助菜单
		//创建菜单项
		itemNew = new JMenuItem("新建(N)");
		itemOpen = new JMenuItem("打开(O)");
		itemSave = new JMenuItem("保存(S)");
		itemSave_As = new JMenuItem("另存为(A)");
		itemPage_Setup = new JMenuItem("页面设置(U)");
		itemPrint =new JMenuItem("打印(P)");
		itemExit = new JMenuItem("退出");
		
		itemUndo =new JMenuItem("撤销(U)");
		itemCut = new JMenuItem("剪切(T)");
		itemCopy = new JMenuItem("复制(C)");
		itemPaste = new JMenuItem("粘帖(P)");
		itemDelete = new JMenuItem("删除(L)");
		itemFind =new JMenuItem("查找(F)");
		itemFind_Next =new JMenuItem("查找下一个(N)");
		itemReplace = new JMenuItem("替换(R)");
		itemGoto =new JMenuItem("转到(G)");
		itemSelect_All =new JMenuItem("全选(A)");
		itemDate = new JMenuItem("时间/日期(D)");
		
		itemFont =new JMenuItem("字体(F)...");
		itemWrap =  new JCheckBox("自动换行(W)");
		
		itemStatu = new JCheckBox("状态栏(S)");
		
		itemHelp = new JMenuItem("查看帮助(H)");
		itemAbout = new JMenuItem("关于记事本(A)");
	
		//禁用保存菜单项
		itemSave.setEnabled(false);
		
		//设置快捷键
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
		//设置助记符
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
		//为菜单项增加事件响应
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
		
		//为文件菜单增加菜单项
		menuFile.add(itemNew);
		menuFile.add(itemOpen);
		menuFile.add(itemSave);
		menuFile.add(itemSave_As);
		menuFile.add(itemPage_Setup);
		menuFile.add(itemPrint);
		menuFile.addSeparator();
		menuFile.add(itemExit);
		//为编辑菜单增加菜单项
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
		//为格式菜单增加菜单项
		menuFormat.add(itemWrap);
		menuFormat.add(itemFont);
		//为查看菜单增加菜单项
		menuView.add(itemStatu);
		//为帮助菜单增加菜单项
		menuHelp.add(itemHelp);
		menuHelp.add(itemAbout);
		
		//将菜单增加到菜单栏中
		menubar.add(menuFile);
		menubar.add(menuEdit);
		menubar.add(menuFormat);
		menubar.add(menuView);
		menubar.add(menuHelp);
		
		
		textArea.getDocument().addUndoableEditListener(manager);
		//为窗口设置菜单栏
		this.setJMenuBar(menubar);
		//创建文本区域
		//
		//添加文本区域到窗口中，并增加滚动条
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
		//添加状态条，以JLabel作为状态条
		
		textArea.getMouseListeners();
		//设置字体
		Font f = new Font("宋体", Font.BOLD, 24);
		textArea.setFont(f);
		validate();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
	}
	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();
		//如果是菜单<打开>被按下
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
					statusBar.setText("文本字符数：" + textArea.getText().length());
					
					setTitle(fileName);
				}
				catch(IOException ep){
					JOptionPane.showConfirmDialog(this, ep.toString(), "打开文件出错", JOptionPane.YES_NO_OPTION);
				}
				
			}	
		}//如果新菜单<新建>被按下
		else if(src == itemNew){
			textArea.setText("");
		}
		//如果是菜单<保存>被按下
		else if(src == itemSave){
			flag=false;
			String fileName = new String();
			
			JFileChooser c = new JFileChooser();
			//显示保存对话框
			int rVal = c.showSaveDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				//获取文件名
				fileName = c.getSelectedFile().getAbsolutePath();
				
				try{
					
					FileWriter fw = new FileWriter(fileName);
					fw.write(textArea.getText());
					fw.close();
					setTitle(fileName);
				}
				catch(IOException ep){
					JOptionPane.showConfirmDialog(this, ep.toString(), "保存文件出错", JOptionPane.YES_NO_OPTION);
				}
			}			
		}
		//如果是菜单<退出>被按下
		else if(src == itemExit){
			this.dispose();
		}

		//如果是菜单<关于>被按下
		else if(src == itemAbout){
			JDialog dia=new JDialog(this,"关于“记事本”",true);
			dia.setBounds(200, 300, 400, 300);
			JLabel label;
			String iaital="<html>\n"+
					         "<font size = +4><font color = green>Window7旗舰版<font>\n"+
							"________________________________________\n"+
							 "<font size = -1>microsoft windows<font>\n"+
							"<font size = -1>版权所有归notepad<font>\n"+
							 "<font color = red>知识产权要受到保护<font>\n"+
							 "</html>\n";
			label=new JLabel(iaital);
			dia.add(confirm,BorderLayout.SOUTH);
			dia.add(label,BorderLayout.NORTH);
			dia.setVisible(true);
		}
		//如果菜单<字体>被按下
		else if(src==itemFont){
			Notefont box=new Notefont("字体");
		}
		//如果菜单<剪切>被按下
		else if(src == itemCut){
			textArea.cut();
		}
		//如果菜单<粘贴>被按下
		else if(src == itemPaste){
			textArea.paste();
		}
		//如果菜单<复制>被按下
		else if(src == itemCopy){
			textArea.copy();
		}
		//如果菜单<全选>被按下
		else if(src == itemSelect_All){
			textArea.selectAll();
		}
		//如果菜单<时间/日期>被按下
		else if(src == itemDate){
			Date now=new Date();
			DateFormat da=DateFormat.getTimeInstance();
			String str = da.format(now);
			DateFormat da1=DateFormat.getDateInstance();
			String str1 = da1.format(now);
			textArea.append(str+" ");
			textArea.append(str1);
		}
		//如果菜单<撤销>被按下
				else if(src == itemUndo){
					if(manager.canUndo()){
						manager.undo();
					}else{
						JOptionPane.showMessageDialog(this, "不能再撤销，请停止", "错误", JOptionPane.ERROR_MESSAGE);
					}
				}
		//如果菜单<查找>被按下
				else if(src == itemFind){
					Notefind find=new Notefind();
					
				}
		//如果菜单<替换>被按下
				else if(src== itemReplace){
					Notereplace replace=new Notereplace();
				}
		//如果菜单<删除>被按下
				else if(src == itemDelete){
					textArea.replaceSelection("");
				}
		//如果菜单<另存为>被按下
				else if(src == itemSave_As){
					String fileName = new String();
					
					JFileChooser c = new JFileChooser();
					//显示保存对话框
					int rVal = c.showDialog(this, "另存为");
					if (rVal == JFileChooser.APPROVE_OPTION) {
						//获取文件名
						fileName = c.getSelectedFile().getAbsolutePath();
						
						try{
							
							FileWriter fw = new FileWriter(fileName);
							fw.write(textArea.getText());
							fw.close();
							setTitle(fileName);
						}
						catch(IOException ep){
							JOptionPane.showConfirmDialog(this, ep.toString(), "另存为文件出错", JOptionPane.YES_NO_OPTION);
						}
					}			
				}
	}
	//文本区更新
	public void changedUpdate(DocumentEvent e)
	{
		statusBar.setText("文本字符数：" + textArea.getText().length());
		//如文本区进行了更改，则运行保存
		itemSave.setEnabled(true);
	}
	//文本区删除字符
	public void removeUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
	//文本区增加字符
	public void insertUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
	
	//重载setTitle函数
	public void setTitle(String s){
		if(s=="")
			super.setTitle("记事本");
		else
			super.setTitle(s+" - 记事本");
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED){
		JCheckBox obj = (JCheckBox)e.getItemSelectable();
		//设置自动换行
		
		 if(obj==itemWrap){
			 if(itemWrap.isSelected()){
				 textArea.setLineWrap(! textArea.getLineWrap() );
			 	add(statusBar, BorderLayout.SOUTH);
			 	statusBar.setVisible(true);
			 	statusBar1.setVisible(false);
			 	//itemStatu.setEnabled(false);
			 }
		}
		//如果菜单<状态栏>被按下
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
	//列数和行数
	public void caretUpdate(CaretEvent e) {
		textArea=(JTextArea)e.getSource();
		int pos=textArea.getCaretPosition();
		try {
			int row=textArea.getLineOfOffset(pos)+1;
			int column=pos-textArea.getLineStartOffset(row-1)+1;
			statusBar1.setText("第 "+row+" 行"+" , 第 "+column+" 列");
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	public void windowOpened(WindowEvent e) {
		
	}
	public void windowClosing(WindowEvent e) {
		if(flag){
			int n=JOptionPane.showConfirmDialog(this,"文件还没保存，确定退出吗", "提醒", JOptionPane.YES_NO_OPTION);
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
	JDialog dialog=new JDialog(this,"替换");
	JButton buttonfindnext,buttonreplace,buttonreplaceall,buttoncancel;
	JLabel label1,label2;
	JTextField field1,field2;
	JCheckBox check;
	int pos=0;
	boolean flag3=false;
	Pattern p;//模式对象
	Matcher m;//匹配对象
	Notereplace(){
		dialog.setBounds(200, 300, 400,200);
		dialog.setVisible(true);
		dialog.setResizable(false);
		buttonfindnext=new JButton("查找下一个");
		buttonreplace=new JButton("      替换       ");
		buttonreplaceall=new JButton("  全部替换    ");
		buttoncancel=new JButton("    取消          ");
		check=new JCheckBox("区分大小写");
		label1=new JLabel("查找内容：");
		label2=new JLabel("替换为：    ");
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
				"找不到"+"“"+field1.getText()+"”", "提醒", JOptionPane.WARNING_MESSAGE);
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
	JLabel label = new JLabel("查找内容(N):");
	JTextField text = new JTextField(20);
	JButton buttonfind,buttoncancel;
	JCheckBox check= new JCheckBox("区分大小写(C)");
	JRadioButton buttondown,buttonup;
	JPanel panel = new JPanel();
	JDialog dialog = new JDialog(this,"查找");
	int pos=0;
	int time=0;
	boolean flag1=false,flag2=true;
	Pattern p;//模式对象
	Matcher m;//匹配对象
	Notefind(){
		
		Box boxV1,boxV2,boxV3;
		
		dialog.setVisible(true);
		dialog.setBounds(300, 200, 400,150);
		
		buttonfind = new JButton("查找下一个(F)");
		buttoncancel = new JButton("     取消             ");
		
		buttondown = new JRadioButton("向下");
		buttonup = new JRadioButton("向上");
		
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
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("方向"),
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
										"请输入要查找的字符","错误",JOptionPane.ERROR_MESSAGE);
							}else if(!text.getText().equals("")){
								textArea.select(m.start(), m.end());
							}
						}else{
							JOptionPane.showMessageDialog(this, 
									"找不到"+"“"+text.getText()+"”", "提醒",JOptionPane.WARNING_MESSAGE);
						}
					}
					else if(!flag2){    
						if(pos!=0){
							if(text.getText().equals("")){
								JOptionPane.showMessageDialog(this, 
										"请输入要查找的字符","错误",JOptionPane.ERROR_MESSAGE);
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
									"找不到"+"“"+text.getText()+"”", "提醒",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			if(src==buttoncancel){
				int n=JOptionPane.showConfirmDialog(this,
						"你确定要退出查找吗","提醒",JOptionPane.YES_NO_OPTION);
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
   	Font f1=new Font("宋体", Font.BOLD, 24);
   	int vul = Font.BOLD;
   	String str = "宋体",str2="8";
   	String []string = {"西欧语言","汉语GB2312"};
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
   		//文本框
   		textfont=new JTextField(25);
   		textstatu=new JTextField(15);
   		textsize=new JTextField(10);
   		//滚动列表
   		listfont=new List(6);
   		liststatu=new List(6);
   		listsize=new List(6);
   		
   		combo = new JComboBox<String>(string);
   		panel = new JPanel();
   		panel1 = new JPanel();
   		panel2 = new JPanel();
   		panel3 = new JPanel();
   		label = new JLabel("AaBbYyZz");
   		label1 = new JLabel("脚本：");
   		buttonconfirm = new JButton("确定");
   		buttoncancel = new JButton("取消");
   		buttonconfirm.addActionListener(this);
   		buttoncancel.addActionListener(this);
   		//向滚动列表<字体>增加列表项
   		GraphicsEnvironment get = GraphicsEnvironment.getLocalGraphicsEnvironment();
   		String font[] = get.getAvailableFontFamilyNames();// 字体库
   		int i=0;
   		for(i=0;i<font.length;i++){
   			listfont.add(font[i]);
   		}
   		//向滚动列表<字形>增加列表项
   		liststatu.add("常规");
   		liststatu.add("斜体");
   		liststatu.add("粗体");
   		liststatu.add("粗体 倾斜");
   		//向滚动列表<大小>增加列表项
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
   		//BoxLayout布局
   		//字体
   		JLabel labelfont=new JLabel("字体(F)");
   		JLabel labelstatu=new JLabel("字形(Y)");
   		JLabel labelsize=new JLabel("大小(S)");
   		boxV1=Box.createVerticalBox();
   		boxV1.add(labelfont);
   		boxV1.add(Box.createVerticalStrut(5));
   		boxV1.add(textfont);
   		boxV1.add(new JScrollPane(listfont));
   		//字形
   		boxV2=Box.createVerticalBox();
   		boxV2.add(labelstatu);
   		boxV2.add(Box.createVerticalStrut(5));
   		boxV2.add(textstatu);
   		boxV2.add(new JScrollPane(liststatu));
   		//大小
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
   		//示例框
   		panel.setLayout(new GridLayout(1,2));
   		panel.add(panel2);
   		panel1.setBorder(BorderFactory.createCompoundBorder(
   				BorderFactory.createTitledBorder("示例"),
   				BorderFactory.createEmptyBorder(10,10,10,10)));
   		panel1.add(label);
   		//panel.add(panel1);
   		//脚本
   		boxV4 = Box.createVerticalBox();
   		boxV4.add(label1);
   		boxV4.add(Box.createVerticalStrut(2));
   		boxV4.add(combo);
   		//Box布局（示例与脚本）
   		
   		JSplitPane split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel1,boxV4);
   		split.setDividerLocation(90);
   		panel.add(split);
   		//确认和取消
   		//null空布局
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
