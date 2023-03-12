package com.invoice;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

public class Input{
	public static void main(String[] ar) throws Exception
	{
		JFrame f=new JFrame("Main Page");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton b1=new JButton("Sales Invoice");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("org.sqlite.JDBC");
					Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
					Statement stmt=c.createStatement();
					String sql="create table if not exists invoice(cm int(7),dm int(7))";
					stmt.executeUpdate(sql);
					stmt.close();
					c.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				JFrame f2=new JFrame("GST Invoice");
				f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				GridBagLayout gb=new GridBagLayout();
				f2.getContentPane().setLayout(gb);
				GridBagConstraints gbc=new GridBagConstraints();
				gbc.fill=GridBagConstraints.HORIZONTAL;
				gbc.gridx=0;
				gbc.gridy=0;
				gbc.gridwidth=1;
				JLabel ino=new JLabel("Invoice No.: ");
				ino.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(ino,gbc);
				JTextField invt=new JTextField(12);
				invt.setFont(new Font("Verdana",Font.PLAIN,20));
				String[] ny=new String[] {"No","Yes"};
				JComboBox nfyco=new JComboBox(ny);
				nfyco.setFont(new Font("Verdana",Font.PLAIN,20));
				try
				{
					Class.forName("org.sqlite.JDBC");
					Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
					Statement stmt=c.createStatement();
					String sql="select cm from invoice";
					ResultSet rs=stmt.executeQuery(sql);
					if(rs.next())
					{
						invt.setText("C"+String.valueOf(rs.getInt(1)));
					}
					else
					{
						stmt.close();
						rs.close();
						sql="insert into invoice values(1,1)";
						stmt.executeUpdate(sql);
						invt.setText("C1");
					}
					stmt.close();
					rs.close();
					c.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				gbc.gridx=1;
				f2.add(invt,gbc);
				JLabel intype=new JLabel("Invoice Type: ",SwingConstants.RIGHT);
				intype.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=2;
				gbc.gridwidth=1;
				f2.add(intype,gbc);
				String[] strinv= {"Cash Memo","Debit Memo"};
				JComboBox intypc=new JComboBox(strinv);
				intypc.setFont(new Font("Verdana",Font.PLAIN,20));
				intypc.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(intypc.getSelectedIndex()==1)
						{
							if(nfyco.getSelectedIndex()==0)
							{
							try
							{
								Class.forName("org.sqlite.JDBC");
								Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
								Statement stmt=c.createStatement();
								String sql="select dm from invoice";
								ResultSet rs=stmt.executeQuery(sql);
								if(rs.next())
								{
									invt.setText("D"+String.valueOf(rs.getInt(1)));
								}
								else
								{
									stmt.close();
									rs.close();
									sql="insert into invoice values(1,1)";
									stmt.executeUpdate(sql);
									invt.setText("D1");
								}
								stmt.close();
								rs.close();
								c.close();
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}
							}
							else
							{
								invt.setText("D1");
							}
						}
						else
						{
							if(nfyco.getSelectedIndex()==0)
							{
							try
							{
								Class.forName("org.sqlite.JDBC");
								Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
								Statement stmt=c.createStatement();
								String sql="select cm from invoice";
								ResultSet rs=stmt.executeQuery(sql);
								if(rs.next())
								{
									invt.setText("C"+String.valueOf(rs.getInt(1)));
								}
								else
								{
									stmt.close();
									rs.close();
									sql="insert into invoice values(1,1)";
									stmt.executeUpdate(sql);
									invt.setText("C1");
								}
								stmt.close();
								rs.close();
								c.close();
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}
							}
							else
							{
								invt.setText("C1");
							}
						}
					}
				});
				gbc.gridx=3;
				gbc.gridwidth=1;
				f2.add(intypc,gbc);
				gbc.gridx=4;
				gbc.gridy=0;
				gbc.gridwidth=1;
				JLabel idt=new JLabel("Invoice Date: ",SwingConstants.RIGHT);
				idt.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(idt,gbc);
				JDateChooser dc=new JDateChooser();
				gbc.gridx=5;
				gbc.gridy=0;
				gbc.gridwidth=1;
				f2.add(dc,gbc);
				JLabel des=new JLabel("Description");
				des.setFont(new Font("Verdana",Font.PLAIN,20));
				des.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				gbc.gridx=0;
				gbc.gridy=4;
				gbc.gridwidth=1;
				f2.add(des,gbc);
				gbc.gridx=1;
				gbc.gridwidth=1;
				JLabel hsn=new JLabel("HSN Code");
				hsn.setFont(new Font("Verdana",Font.PLAIN,20));
				hsn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				f2.add(hsn,gbc);
				gbc.gridx=2;
				JLabel q=new JLabel("Quantity");
				q.setFont(new Font("Verdana",Font.PLAIN,20));
				q.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				f2.add(q,gbc);
				gbc.gridx=3;
				JLabel rate=new JLabel("Rate");
				rate.setFont(new Font("Verdana",Font.PLAIN,20));
				rate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				f2.add(rate,gbc);
				gbc.gridx=4;
				JLabel amo=new JLabel("Amount");
				amo.setFont(new Font("Verdana",Font.PLAIN,20));
				amo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				f2.add(amo,gbc);
				gbc.gridx=5;
				JLabel exm=new JLabel("Exempted/Taxable");
				exm.setFont(new Font("Verdana",Font.PLAIN,20));
				exm.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				f2.add(exm,gbc);
				DynamicArray combo=new DynamicArray();
				AutoCompleteComboBox proc=new AutoCompleteComboBox();
				proc.setFont(new Font("Verdana",Font.PLAIN,20));
				proc.putClientProperty("id", "des");
				DynamicArray hsni=new DynamicArray();
				JTextField hsnt=new JTextField(8);
				hsnt.putClientProperty("id", "hsn");
				hsnt.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridy=5;
				gbc.gridx=1;
				hsni.addElement(hsnt);
				f2.add(hsnt,gbc);
				DynamicArray qi=new DynamicArray();
				JTextField qt=new JTextField(5);
				qt.putClientProperty("id", "q");
				qt.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=2;
				qi.addElement(qt);
				f2.add(qt,gbc);
				DynamicArray ri=new DynamicArray();
				JTextField rt=new JTextField(7);
				rt.putClientProperty("id", "rate");
				rt.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=3;
				ri.addElement(rt);
				f2.add(rt,gbc);
				DynamicArray ami=new DynamicArray();
				JTextField amt=new JTextField(7);
				amt.putClientProperty("id", "amt");
				amt.setFont(new Font("Verdana",Font.PLAIN,20));
				amt.disable();
				gbc.gridx=4;
				ami.addElement(amt);
				f2.add(amt,gbc);
				DynamicArray taxex=new DynamicArray();
				String[] eto={"Taxable","Exempted"};
				JComboBox taxexe=new JComboBox(eto);
				taxexe.disable();
				taxexe.putClientProperty("id", "taxex");
				taxexe.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=5;
				gbc.gridy=5;
				taxex.addElement(taxexe);
				f2.add(taxexe,gbc);
				JLabel nm=new JLabel("Name of the recipient: ");
				nm.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridy=1;
				gbc.gridx=0;
				f2.add(nm,gbc);
				JTextField nmt=new JTextField(30);
				nmt.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=1;
				gbc.gridwidth=7;
				f2.add(nmt,gbc);
				JLabel addr=new JLabel("Address of the recipient: ");
				addr.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridy=2;
				gbc.gridx=0;
				gbc.gridwidth=1;
				f2.add(addr,gbc);
				JTextField adt=new JTextField(30);
				adt.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=1;
				gbc.gridwidth=7;
				f2.add(adt,gbc);
				JLabel gstin=new JLabel("GSTIN of the recipient: ");
				gstin.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridy=3;
				gbc.gridx=0;
				gbc.gridwidth=1;
				f2.add(gstin,gbc);
				JTextField gsttb=new JTextField(15);
				gsttb.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=1;
				gbc.gridwidth=3;
				f2.add(gsttb,gbc);
				JLabel nfy=new JLabel("Generate New Series? ",SwingConstants.RIGHT);
				nfy.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=4;
				gbc.gridwidth=1;
				f2.add(nfy,gbc);
				nfyco.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(nfyco.getSelectedIndex()==1)
						{
							String[] op={"Yes","No"};
							int res=JOptionPane.showOptionDialog(null,"Pressing Yes would reset the serial number of invoice to C1/D1. Are you sure  you want to continue?", "Generate New Series?",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);
							if(res==1 || res==JOptionPane.CLOSED_OPTION)
							{
								nfyco.setSelectedIndex(0);
							}
							else
							{
								if(intypc.getSelectedIndex()==0)
								{
									invt.setText("C1");
								}
								else
								{
									invt.setText("D1");
								}
							}
					}
				}
				});
				gbc.gridx=5;
				gbc.gridwidth=1;
				f2.add(nfyco,gbc);
				Statement stmt=null;
				String sql;
				proc.setEditable(true);
				try {
					Class.forName("org.sqlite.JDBC");
					Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
					stmt=c.createStatement();
					sql="select distinct pr_name from product";
					stmt=c.createStatement();
					ResultSet rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						proc.addItem(rs.getObject(1));
					}
					rs.close();
					stmt.close();
					c.close();
				}catch (Exception ex) {
					ex.printStackTrace();
					}
				proc.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						try {
							Class.forName("org.sqlite.JDBC");
							Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
							Statement stmt=c.createStatement();
							if(proc.getSelectedItem()!=null)
							{
							String sql="select * from product where pr_name='"+proc.getSelectedItem().toString()+"'";
							ResultSet rs=stmt.executeQuery(sql);
							if(rs.next())
							{
								hsnt.setText(String.valueOf(rs.getInt(2)));
								rt.setText(String.valueOf(rs.getInt(3)));
								if(rs.getInt(4)!=0)
								{
									taxexe.setSelectedIndex(0);
									f2.revalidate();
									f2.repaint();
									f2.setSize(1300,700);
								}
								else
								{
									taxexe.setSelectedIndex(1);
									f2.revalidate();
									f2.repaint();
									f2.setSize(1300,700);
								}
							}
							rs.close();
							stmt.close();
							c.close();
							}
						}catch (Exception ex) {
							ex.printStackTrace();
							}
					}
				});
				proc.setSelectedItem(null);
				JButton addrow=new JButton("Add Row");
				addrow.setFont(new Font("Verdana",Font.PLAIN,20));
				JButton remrow=new JButton("Remove Last Row");
				remrow.setFont(new Font("Verdana",Font.PLAIN,20));
				JButton pdf=new JButton("Generate PDF");
				pdf.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=0;
				gbc.gridy=6;
				addrow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						f2.remove(addrow);
						f2.remove(remrow);
						f2.remove(pdf);
						int rows=combo.count;
						AutoCompleteComboBox proc=new AutoCompleteComboBox();
						proc.setFont(new Font("Verdana",Font.PLAIN,20));
						JTextField hsnt=new JTextField(8);
						hsnt.setFont(new Font("Verdana",Font.PLAIN,20));
						hsnt.putClientProperty("id", "hsn"+rows);
						gbc.gridy=rows+5;
						gbc.gridx=1;
						hsni.addElement(hsnt);
						f2.add(hsnt,gbc);
						JTextField qt=new JTextField(5);
						qt.setFont(new Font("Verdana",Font.PLAIN,20));
						qt.putClientProperty("id", "q"+rows);
						gbc.gridx=2;
						qi.addElement(qt);
						f2.add(qt,gbc);
						JTextField rt=new JTextField(7);
						rt.setFont(new Font("Verdana",Font.PLAIN,20));
						rt.putClientProperty("id","rate"+rows);
						gbc.gridx=3;
						ri.addElement(rt);
						f2.add(rt,gbc);
						JTextField amt=new JTextField(7);
						amt.setFont(new Font("Verdana",Font.PLAIN,20));
						amt.disable();
						amt.putClientProperty("id", "amt"+rows);
						gbc.gridx=4;
						ami.addElement(amt);
						f2.add(amt,gbc);
						String[] eto={"Taxable","Exempted"};
						JComboBox taxexe=new JComboBox(eto);
						taxexe.disable();
						taxexe.putClientProperty("id", "taxex"+rows);
						taxexe.setFont(new Font("Verdana",Font.PLAIN,20));
						gbc.gridx=5;
						taxex.addElement(taxexe);
						f2.add(taxexe,gbc);
						Statement stmt=null;
						String sql;
						proc.setEditable(true);
						try {
							Class.forName("org.sqlite.JDBC");
							Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
							stmt=c.createStatement();
							sql="select distinct pr_name from product";
							stmt=c.createStatement();
							ResultSet rs=stmt.executeQuery(sql);
							while(rs.next())
							{
								proc.addItem(rs.getObject(1));
							}
							rs.close();
							stmt.close();
							c.close();
						}catch (Exception ex) {
							ex.printStackTrace();
							}
						proc.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								// TODO Auto-generated method stub
								try {
									Class.forName("org.sqlite.JDBC");
									Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
									Statement stmt=c.createStatement();
									if(proc.getSelectedItem()!=null)
									{
									String sql="select * from product where pr_name='"+proc.getSelectedItem().toString()+"'";
									ResultSet rs=stmt.executeQuery(sql);
									if(rs.next())
									{
										hsnt.setText(String.valueOf(rs.getInt(2)));
										rt.setText(String.valueOf(rs.getInt(3)));
										if(rs.getInt(4)!=0)
										{
											taxexe.setSelectedIndex(0);
											f2.revalidate();
											f2.repaint();
											f2.setSize(1300,700);
										}
										else
										{
											taxexe.setSelectedIndex(1);
											f2.revalidate();
											f2.repaint();
											f2.setSize(1300,700);
										}
									}
									rs.close();
									stmt.close();
									c.close();
									}
								}catch (Exception ex) {
									ex.printStackTrace();
									}
							}
						});
						proc.setSelectedItem(null);
						proc.putClientProperty("id","des"+rows);
						gbc.gridx=0;
						combo.addElement(proc);
						f2.add(proc,gbc);
						gbc.gridy=rows+6;
						gbc.gridx=0;
						f2.add(addrow,gbc);
						gbc.gridx=1;
						f2.add(remrow,gbc);
						gbc.gridx=2;
						f2.add(pdf,gbc);
						f2.revalidate();
						f2.repaint();
						f2.setSize(1300,700);
					}
				});
				f2.add(addrow,gbc);
				remrow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int rows=combo.count-1;
						{
						Component[] comp=f2.getContentPane().getComponents();
						for(int i=0;i<comp.length;i++)
						{
							if(comp[i] instanceof JComboBox)
							{
								JComboBox cb=(JComboBox)comp[i];
								if(cb.getClientProperty("id") instanceof String)
								{
									String id=(String)cb.getClientProperty("id");
									if(id.equals("des"+rows))
									{
										f2.remove(comp[i]);
										
										if(rows>1)
										{
											remrow.disable();
											combo.count--;
										}
									}
									else if(id.equals("taxex"+rows))
									{
										f2.remove(comp[i]);
									}
								}
							}
							else if(comp[i] instanceof JTextField)
							{
								JTextField cb=(JTextField)comp[i];
								if(cb.getClientProperty("id") instanceof String)
								{
									String id=(String)cb.getClientProperty("id");
									if(id.equals("hsn"+rows) || id.equals("q"+rows) || id.equals("amt"+rows) || id.equals("rate"+rows))
									{
										f2.remove(comp[i]);
									}
								}
							}
						}
						f2.revalidate();
						f2.repaint();
						f2.setSize(1300,700);
					}
					}
				});
				gbc.gridx=1;
				gbc.gridy=6;
				f2.add(remrow,gbc);
				pdf.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							if(!invt.getText().isEmpty())
							{
							PDDocument doc=new PDDocument();
							PDPage page=new PDPage();
							doc.addPage(page);
							PDPageContentStream cs = new PDPageContentStream(doc, page);
							cs.beginText();
							cs.setLeading(15);
							cs.setFont(PDType1Font.TIMES_BOLD,10);
							cs.newLineAtOffset(20,770);
							cs.showText("Shree Shankheshwar Parshwanathay Namah:");
							cs.newLineAtOffset(280, 0);
							cs.showText("Bill of Supply");
							cs.newLine();
							if(intypc.getSelectedItem().toString().equals("Cash Memo"))
							{
								cs.showText("Cash Memo");
							}
							else
							{
								cs.showText("Debit Memo");
							}
							cs.newLineAtOffset(195, 15);
							cs.showText("Shree Ganeshay Namah:");
							cs.newLine();
							cs.showText("Jay Ambe \"MOJ\"");
							cs.newLineAtOffset(-475, -30);
							cs.setFont(PDType1Font.TIMES_BOLD,15);
							cs.showText("Salot Chandrakant Mohanlal (Kanthariyavala)");
							cs.newLine();
							cs.setFont(PDType1Font.TIMES_ROMAN, 10);
							cs.showText("Undivakhar, Bhavnagar-364001.");
							cs.newLine();
							cs.showText("State: Gujarat State Code: 24" );
							cs.newLine();
							cs.showText("GSTIN No: 24BJPPS1531M1ZT");
							cs.newLine();
							cs.showText("PAN: BJPPS1531M");
							cs.newLineAtOffset(400, 45);
							cs.showText("Contact details");
							cs.newLine();
							cs.showText("Shop: (0278)2427237, 9427339695");
							cs.newLine();
							cs.showText("Keval: 9537430031");
							cs.newLineAtOffset(-400, -30);
							cs.showText("Details of Receiver (Billed to)");
							cs.newLine();
							cs.showText("Name: "+nmt.getText().toString());
							cs.newLine();
							cs.showText("Address: "+adt.getText().toString());
							cs.newLine();
							cs.showText("GSTIN: "+gsttb.getText().toString());
							cs.newLineAtOffset(400, 30);
							cs.showText("Invoice No.: "+invt.getText().toString());
							cs.newLine();
							cs.showText("Date: "+dc.getCalendar().getTime().getDate()+"/"+(dc.getCalendar().getTime().getMonth()+1)+"/"+(dc.getCalendar().getTime().getYear()+1900));
							cs.newLineAtOffset(-400, -30);
							cs.setFont(PDType1Font.TIMES_BOLD,10);
							cs.showText("Sr. No.");
							cs.newLineAtOffset(40, 0);
							cs.showText("Description");
							cs.newLineAtOffset(190, 0);
							cs.showText("HSN Code");
							cs.newLineAtOffset(60, 0);
							cs.showText("Quantity");
							cs.newLineAtOffset(60, 0);
							cs.showText("Rate");
							cs.newLineAtOffset(60, 0);
							cs.showText("Amount");
							cs.newLineAtOffset(60, 0);
							cs.showText("Exempted");
							cs.newLineAtOffset(60, 0);
							cs.showText("Taxable");
							cs.endText();
							cs.addRect(15, 600, 590, 180);
							cs.stroke();
							cs.moveTo(15, 660);
							cs.lineTo(605,660);
							cs.stroke();
							cs.addRect(15, 585, 40, 15);
							cs.stroke();
							cs.addRect(55,585, 190, 15);
							cs.stroke();
							cs.addRect(245, 585, 60, 15);
							cs.stroke();
							cs.addRect(305,585, 60, 15);
							cs.stroke();
							cs.addRect(365, 585, 60, 15);
							cs.stroke();
							cs.addRect(425,585, 60, 15);
							cs.stroke();
							cs.addRect(485, 585, 60, 15);
							cs.stroke();
							cs.addRect(545, 585, 60, 15);
							cs.stroke();
							Component[] comp=f2.getContentPane().getComponents();
							int desy=575;
							int sr=1;
							for(int i=0;i<comp.length;i++)
							{
								if(comp[i] instanceof JComboBox)
								{
									JComboBox cb=(JComboBox)comp[i];
									String id;
									if(cb.getClientProperty("id") instanceof String)
									{
										id=(String)cb.getClientProperty("id");
										if(id.contains("des"))
										{
											if(cb.getSelectedItem()!=null)
											{
												cs.beginText();
												cs.newLineAtOffset(20, desy);
												cs.setFont(PDType1Font.TIMES_ROMAN, 10);
												cs.showText(sr+"");
												cs.newLineAtOffset(40, 0);
												cs.showText(cb.getSelectedItem().toString());
												cs.endText();
												cs.addRect(15, desy-5, 40, 15);
												cs.addRect(55, desy-5, 190, 15);
												cs.stroke();
												sr++;
												desy-=15;
											}
											else
											{
												JOptionPane.showMessageDialog(f2, "Input all mandatory fields.","Alert",JOptionPane.WARNING_MESSAGE);
												return;
											}
										}
									}
								}
							}
							desy=575;
							for(int i=0;i<comp.length;i++)
							{
								if(comp[i] instanceof JTextField)
								{
									JTextField hsnt=(JTextField)comp[i];
									String id="";
									if(hsnt.getClientProperty("id") instanceof String)
									{
										id=(String)hsnt.getClientProperty("id");
										if(id.contains("hsn"))
										{
											if(!hsnt.getText().equals("0"))
											{
											cs.beginText();
											cs.newLineAtOffset(250, desy);
											cs.setFont(PDType1Font.TIMES_ROMAN, 10);
											cs.showText(hsnt.getText());
											cs.endText();
											}
											cs.addRect(245, desy-5, 60, 15);
											cs.stroke();
											desy-=15;
										}
									}
								}
							}
							desy=575;
							for(int i=0;i<comp.length;i++)
							{
								if(comp[i] instanceof JTextField)
								{
									JTextField qt=(JTextField)comp[i];
									String id;
									if(qt.getClientProperty("id") instanceof String)
									{
										id=(String)qt.getClientProperty("id");
										if(id.contains("q"))
										{
											if(Float.parseFloat(qt.getText())>0)
											{
												cs.beginText();
												cs.newLineAtOffset(310, desy);
												cs.setFont(PDType1Font.TIMES_ROMAN, 10);
												cs.showText(qt.getText());
												cs.endText();
												cs.addRect(305, desy-5, 60, 15);
												cs.stroke();
												desy-=15;
											}
											else
											{
												JOptionPane.showMessageDialog(f2, "Input quantity properly.","Alert",JOptionPane.WARNING_MESSAGE);
												return;
											}
										}
									}
								}
							}
							desy=575;
							for(int i=0;i<comp.length;i++)
							{
								if(comp[i] instanceof JTextField)
								{
									JTextField rt=(JTextField)comp[i];
									String id;
									if(rt.getClientProperty("id") instanceof String)
									{
										id=(String)rt.getClientProperty("id");
										if(id.contains("rate"))
										{
											if(Float.parseFloat(rt.getText())>0)
											{
												cs.beginText();
												cs.newLineAtOffset(370, desy);
												cs.setFont(PDType1Font.TIMES_ROMAN, 10);
												cs.showText(rt.getText());
												cs.endText();
												cs.addRect(365, desy-5, 60, 15);
												cs.stroke();
												desy-=15;
											}
											else
											{
												JOptionPane.showMessageDialog(f2, "Input rate properly.","Alert",JOptionPane.WARNING_MESSAGE);
												return;
											}
										}
									}
								}
							}
							desy=575;
							float qant=Float.parseFloat(qt.getText());
							float rate=Float.parseFloat(rt.getText());
							int tax=0;
							int exe=0;
							int amount=(int)(qant*rate);
							amt.setText(amount+"");
							String taxex=taxexe.getSelectedItem().toString();
							int taxab=0;
							if(taxex.contains("Tax"))
							{
								taxab=1;
							}
							cs.beginText();
							cs.newLineAtOffset(430, desy);
							cs.setFont(PDType1Font.TIMES_ROMAN, 10);
							cs.showText(amount+"");
							cs.endText();
							cs.beginText();
							if(taxab==0)
							{
								cs.newLineAtOffset(490, desy);
								cs.showText(amount+"");
								exe+=amount;
							}
							else
							{
								cs.newLineAtOffset(550, desy);
								cs.showText(amount+"");
								tax+=amount;
							}
							cs.endText();
							cs.addRect(425, desy-5, 60, 15);
							cs.addRect(485,desy-5,60,15);
							cs.addRect(545, desy-5, 60, 15);
							cs.stroke();
							desy-=15;
							int rows=combo.count-1;
							for(int i=1;i<=rows;i++)
							{
								float qt=0;
								float rt=0;
								taxab=0;
								for(int j=0;j<comp.length;j++)
								{
									if(comp[j] instanceof JTextField)
									{
										JTextField tf=(JTextField)comp[j];
										String id="";
										if(tf.getClientProperty("id") instanceof String)
										{
											id=(String)tf.getClientProperty("id");
											if(id.equals("q"+i))
											{
												qt=Float.parseFloat(tf.getText());
											}
											else if(id.equals("rate"+i))
											{
												rt=Float.parseFloat(tf.getText());
											}
										}
									}
									else if(comp[j] instanceof JComboBox)
									{
										JComboBox cb=(JComboBox)comp[j];
										String id="";
										if(cb.getClientProperty("id") instanceof String)
										{
											id=(String)cb.getClientProperty("id");
											if(id.equals("taxex"+i))
											{
												if(cb.getSelectedItem().toString().contains("Tax"))
												{
													taxab=1;
												}
											}
										}
									}
								}
								int am=(int)(qt*rt);
								for(int j=0;j<comp.length;j++)
								{
									JTextField tf;
									String id="";
									if(comp[j] instanceof JTextField)
									{
										tf=(JTextField)comp[j];
										if(tf.getClientProperty("id") instanceof String)
										{
											id=(String)tf.getClientProperty("id");
											if(id.equals("amt"+i))
											{
												tf.setText(am+"");
												cs.beginText();
												cs.newLineAtOffset(430, desy);
												cs.setFont(PDType1Font.TIMES_ROMAN, 10);
												cs.showText(am+"");
												cs.endText();
												cs.beginText();
												if(taxab==0)
												{
													cs.newLineAtOffset(490, desy);
													cs.showText(am+"");
													exe+=am;
												}
												else
												{
													cs.newLineAtOffset(550, desy);
													cs.showText(am+"");
													tax+=am;
												}
												cs.endText();
												cs.addRect(425, desy-5, 60, 15);
												cs.addRect(485,desy-5,60,15);
												cs.addRect(545, desy-5, 60, 15);
												cs.stroke();
												desy-=15;
												break;
											}
										}
									}
								}
							}
							cs.beginText();
							cs.newLineAtOffset(20, desy);
							cs.setFont(PDType1Font.TIMES_BOLD, 10);
							cs.showText("Total");
							cs.endText();
							if(exe>0)
							{
							cs.beginText();
							cs.newLineAtOffset(490, desy);
							cs.setFont(PDType1Font.TIMES_BOLD, 10);
							cs.showText(exe+"");
							cs.endText();
							}
							if(tax>0)
							{
							cs.beginText();
							cs.newLineAtOffset(550, desy);
							cs.setFont(PDType1Font.TIMES_BOLD, 10);
							cs.showText(tax+"");
							cs.endText();
							}
							cs.addRect(15, desy-5, 470, 15);
							cs.addRect(485, desy-5, 60, 15);
							cs.addRect(545, desy-5, 60, 15);
							cs.stroke();
							desy-=15;
							int tot=exe+tax;
							cs.beginText();
							cs.newLineAtOffset(20, desy);
							cs.setFont(PDType1Font.TIMES_BOLD, 10);
							cs.showText("Grand Total");
							cs.endText();
							cs.beginText();
							cs.newLineAtOffset(490, desy);
							cs.setFont(PDType1Font.TIMES_BOLD, 10);
							cs.showText(tot+"");
							cs.endText();
							cs.addRect(15, desy-5, 470, 15);
							cs.addRect(485, desy-5, 120, 15);
							cs.stroke();
							desy-=15;
							cs.beginText();
							cs.newLineAtOffset(20, desy);
							cs.setFont(PDType1Font.TIMES_ROMAN, 10);
							cs.showText("Subject to Bhavnagar Jurisdiction");
							cs.newLine();
							cs.showText("E & O.E.");
							cs.endText();
							cs.addRect(15, desy-20, 590, 30);
							cs.stroke();
							desy-=30;
							if(intypc.getSelectedItem().toString().contains("Debit"))
							{
								cs.beginText();
								cs.newLineAtOffset(20, desy);
								cs.setFont(PDType1Font.TIMES_BOLD,10);
								cs.showText("BANK DETAILS:-");
								cs.newLine();
								cs.showText("BANK: KOTAK MAHINDRA BANK");
								cs.newLine();
								cs.showText("BRANCH: KHARGATE, BHAVNAGAR");
								cs.newLine();
								cs.showText("A/c No.: 6132150113");
								cs.newLine();
								cs.showText("IFSC: KKBK0003018");
								cs.endText();
								cs.addRect(15, desy-65, 590, 75);
								cs.stroke();
							}
							cs.close();
							int month=dc.getCalendar().getTime().getMonth()+1;
							int year=dc.getCalendar().getTime().getYear()+1900;
							File dir;
							if(intypc.getSelectedIndex()==0)
							{
								dir=new File("Cash_Memo/"+month+"_"+year);
							}
							else
							{
								dir=new File("Debit_Memo/"+month+"_"+year);
							}
							if (! dir.exists()){
						        dir.mkdirs();
						    }
							doc.save(dir+"/Invoice"+invt.getText()+".pdf");
							doc.close();
							Class.forName("org.sqlite.JDBC");
							Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
							if(intypc.getSelectedItem().toString().contains("Debit"))
							{
								Statement stmt=c.createStatement();
								String iold=invt.getText().toString();
								int ino=Integer.parseInt(iold.substring(1));
								ino++;
								String sql="update invoice set dm="+ino;
								stmt.executeUpdate(sql);
							}
							else
							{
								Statement stmt=c.createStatement();
								String iold=invt.getText().toString();
								int ino=Integer.parseInt(iold.substring(1));
								ino++;
								String sql="update invoice set cm="+ino;
								stmt.executeUpdate(sql);
							}
							Statement s=c.createStatement();
							s.executeUpdate("create table if not exists report(dt integer not null, month integer not null, year integer not null, cmexe integer, cmtax integer, dmexe integer, dmtax integer)");
							s.close();
							s=c.createStatement();
							String date=dc.getCalendar().getTime().getDate()+"";
							ResultSet r=s.executeQuery("select * from report where dt="+date+" and month="+month+" and year="+year);
							if(r.next())
							{
								s.close();
								if(intypc.getSelectedItem().toString().contains("Debit"))
								{
									s.executeUpdate("update report set dmexe=dmexe+"+exe+", dmtax=dmtax+"+tax+" where dt="+date+" and month="+month+" and year="+year);
								}
								else
								{
									s.executeUpdate("update report set cmexe=cmexe+"+exe+", cmtax=cmtax+"+tax+" where dt="+date+" and month="+month+" and year="+year);
								}
							}
							else
							{
								s.close();
								if(intypc.getSelectedItem().toString().contains("Debit"))
								{
									s.executeUpdate("insert into report values("+date+","+month+","+year+",0,0,"+exe+","+tax+")");
								}
								else
								{
									s.executeUpdate("insert into report values("+date+","+month+","+year+","+exe+","+tax+",0,0)");
								}
							}
							JOptionPane.showMessageDialog(f2, "PDF Generated successfully.");
							Class.forName("org.sqlite.JDBC");
							Connection cn = DriverManager.getConnection("jdbc:sqlite:product.db");
							Statement stmt=cn.createStatement();
							String sql="select cm from invoice";
							ResultSet rs=stmt.executeQuery(sql);
							if(rs.next())
							{
								invt.setText("C"+String.valueOf(rs.getInt(1)));
							}
							else
							{
								stmt.close();
								rs.close();
								sql="insert into invoice values(1,1)";
								stmt.executeUpdate(sql);
								invt.setText("C1");
							}
							stmt.close();
							rs.close();
							c.close();
							intypc.setSelectedIndex(0);
							nfyco.setSelectedIndex(0);
							nmt.setText("");
							adt.setText("");
							gsttb.setText("");
							int row=combo.count-1;
							Component[] compo=f2.getContentPane().getComponents();
							for(int i=0;i<comp.length;i++)
							{
								if(comp[i] instanceof JComboBox)
								{
									JComboBox cb=(JComboBox)comp[i];
									if(cb.getClientProperty("id") instanceof String)
									{
										String id=(String)cb.getClientProperty("id");
										if(id.contains("des") && !id.equals("des"))
										{
											f2.remove(comp[i]);
										}
										else if(id.contains("taxex") && !id.equals("taxex"))
										{
											f2.remove(comp[i]);
										}
										else if(id.equals("des")||id.equals("taxex"))
										{
											cb.setSelectedItem(null);
										}
									}
								}
								else if(comp[i] instanceof JTextField)
								{
									JTextField cb=(JTextField)comp[i];
									if(cb.getClientProperty("id") instanceof String)
									{
										String id=(String)cb.getClientProperty("id");
										if((id.contains("hsn") && !id.equals("hsn")) || (id.contains("q") && !id.equals("q")) || (id.contains("amt") && !id.equals("amt")) || (id.contains("rate") && !id.equals("rate")))
										{
											f2.remove(comp[i]);
										}
										else
										{
											cb.setText("");
										}
									}
								}
							}
							f2.revalidate();
							f2.repaint();
							f2.setSize(1300,700);
							}
							else
							{
								JOptionPane.showMessageDialog(f2, "Input all mandatory fields.","Alert",JOptionPane.WARNING_MESSAGE);
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(f2, e1.getMessage(),"Error",JOptionPane.WARNING_MESSAGE);
							e1.printStackTrace();
						}	
					}
				});
				gbc.gridx=2;
				f2.add(pdf,gbc);
				gbc.gridx=0;
				gbc.gridy=5;
				combo.addElement(proc);
				f2.add(proc,gbc);
				f2.setVisible(true);
				f2.setSize(1300,700);
				f.dispose();
			}
		});
		b1.setBounds(450, 100, 400, 100);
		b1.setFont(new Font("Verdana",Font.PLAIN,20));
		f.getContentPane().add(b1);
		f.getContentPane().setLayout(null);
		JButton b2 = new JButton("Product Pricing Add/Update");
		b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame f2=new JFrame("Product Pricing Add/Update");
				f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				GridBagLayout gb=new GridBagLayout();
				f2.getContentPane().setLayout(gb);
				GridBagConstraints gbc=new GridBagConstraints();
				gbc.fill=GridBagConstraints.HORIZONTAL;
				gbc.gridx=0;
				gbc.gridy=0;
				JLabel pn=new JLabel("Product Name: ");
				pn.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(pn,gbc);
				gbc.gridx=0;
				gbc.gridy=1;
				JLabel hc=new JLabel("HSN Code: ");
				hc.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(hc,gbc);
				gbc.gridx=1;
				gbc.gridy=1;
				JTextField hct=new JTextField(8);
				hct.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(hct,gbc);
				gbc.gridx=0;
				gbc.gridy=2;
				JLabel pr=new JLabel("Price (in INR): ");
				pr.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(pr,gbc);
				gbc.gridx=1;
				gbc.gridy=2;
				JTextField prt=new JTextField(12);
				prt.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(prt,gbc);
				gbc.gridx=0;
				gbc.gridy=3;
				JLabel et=new JLabel("Taxable/Exempted: ");
				et.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(et,gbc);
				gbc.gridx=1;
				gbc.gridy=3;
				String[] eto={"Taxable","Exempted"};
				JComboBox etcom=new JComboBox(eto);
				etcom.setFont(new Font("Verdana",Font.PLAIN,20));
				f2.add(etcom,gbc);
				Connection c=null;
				Statement stmt=null;
				JComboBox combo = new AutoCompleteComboBox();
				combo.setEditable(true);
				try {
					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:product.db");
					stmt=c.createStatement();
					String sql="create table if not exists product(pr_name text not null unique,hsn integer,price integer not null,taxable integer not null)";
					stmt.executeUpdate(sql);
					stmt.close();
					sql="select distinct pr_name from product";
					stmt=c.createStatement();
					ResultSet rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						combo.addItem(rs.getObject(1));
					}
					rs.close();
					stmt.close();
					c.close();
				}catch (Exception e) {
					e.printStackTrace();
					}
				combo.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						try {
							Class.forName("org.sqlite.JDBC");
							Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
							Statement stmt=c.createStatement();
							if(combo.getSelectedItem()!=null)
							{
							String sql="select * from product where pr_name='"+combo.getSelectedItem().toString()+"'";
							ResultSet rs=stmt.executeQuery(sql);
							if(rs.next())
							{
								hct.setText(String.valueOf(rs.getInt(2)));
								prt.setText(String.valueOf(rs.getInt(3)));
								if(rs.getInt(4)!=0)
								{
									etcom.setSelectedIndex(0);
								}
								else
								{
									etcom.setSelectedIndex(1);
								}
							}
							rs.close();
							stmt.close();
							c.close();
							}
						}catch (Exception ex) {
							JOptionPane.showMessageDialog(f2, ex.getMessage(),"Alert",JOptionPane.WARNING_MESSAGE);
							}
					}
				});
				combo.setSelectedItem(null);
				combo.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=1;
				gbc.gridy=0;
				f2.add(combo,gbc);						
				JButton sbm=new JButton("Add/Update");
				sbm.setFont(new Font("Verdana",Font.PLAIN,20));
				sbm.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						try {
							Class.forName("org.sqlite.JDBC");
							Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
							Statement stmt=c.createStatement();
							String sql="select pr_name from product where pr_name='"+combo.getSelectedItem().toString()+"'";
							ResultSet rs=stmt.executeQuery(sql);
							if(rs.next())
							{
								stmt.close();
								stmt=c.createStatement();
								int taxe=1;
								if(etcom.getSelectedIndex()!=0)
								{
									taxe=0;
								}
								if(!(prt.getText().isEmpty()) && !(hct.getText().isEmpty()))
								{
									sql="update product set pr_name='"+combo.getSelectedItem().toString()+"',hsn="+hct.getText()+",price="+prt.getText()+",taxable="+taxe+" where pr_name='"+combo.getSelectedItem().toString()+"'";
									stmt.executeUpdate(sql);
									JOptionPane.showMessageDialog(f2, "Product updated successfully.");
									combo.setSelectedItem(null);
									etcom.setSelectedItem(null);
									hct.setText("");
									prt.setText("");
								}
								else if(!(prt.getText().isEmpty()))
								{
									sql="update product set pr_name='"+combo.getSelectedItem().toString()+"',hsn=null,price="+prt.getText()+",taxable="+taxe+" where pr_name='"+combo.getSelectedItem().toString()+"'";
									stmt.executeUpdate(sql);
									JOptionPane.showMessageDialog(f2, "Product updated successfully.");
									combo.setSelectedItem(null);
									etcom.setSelectedItem(null);
									hct.setText("");
									prt.setText("");
								}
								else
								{
									JOptionPane.showMessageDialog(f2, "Input all mandatory fields.","Alert",JOptionPane.WARNING_MESSAGE);
								}
							}
							else
							{
								stmt.close();
								stmt=c.createStatement();
								int taxe=1;
								if(etcom.getSelectedIndex()!=0)
								{
									taxe=0;
								}
								if(!(prt.getText().isEmpty()) && !(hct.getText().isEmpty()))
								{
								sql="insert into product values('"+combo.getSelectedItem().toString()+"',"+hct.getText()+","+prt.getText()+","+taxe+")";
								stmt.executeUpdate(sql);
								JOptionPane.showMessageDialog(f2, "Product inserted successfully.");
								combo.addItem(combo.getSelectedItem().toString());
								combo.setSelectedItem(null);
								etcom.setSelectedItem(null);
								hct.setText("");
								prt.setText("");
								}
								else if(!(prt.getText().isEmpty()))
								{
									sql="insert into product values('"+combo.getSelectedItem().toString()+"',null,"+prt.getText()+","+taxe+")";
									stmt.executeUpdate(sql);
									JOptionPane.showMessageDialog(f2, "Product inserted successfully.");
									combo.addItem(combo.getSelectedItem().toString());
									combo.setSelectedItem(null);
									etcom.setSelectedItem(null);
									hct.setText("");
									prt.setText("");
								}
								else
								{
									JOptionPane.showMessageDialog(f2, "Input all mandatory fields.","Alert",JOptionPane.WARNING_MESSAGE);
								}
							}
							rs.close();
							stmt.close();
							c.close();
						}catch (Exception e) {
							JOptionPane.showMessageDialog(f2, e.getMessage(),"Alert",JOptionPane.WARNING_MESSAGE);
							}
					}
				});
				gbc.gridx=0;
				gbc.gridy=4;
				gbc.gridwidth=2;
				f2.add(sbm,gbc);
				f2.setVisible(true);
				f2.setSize(1300,700);
				f.dispose();
			}
		});
		b2.setBounds(450, 300, 400, 100);
		b2.setFont(new Font("Verdana",Font.PLAIN,20));
		f.getContentPane().add(b2);
		JButton b3 = new JButton("Generate Report");
		b3.setBounds(450, 500, 400, 100);
		b3.setFont(new Font("Verdana",Font.PLAIN,20));
		b3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame f2=new JFrame("Report");
				f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				GridBagLayout gb=new GridBagLayout();
				f2.getContentPane().setLayout(gb);
				GridBagConstraints gbc=new GridBagConstraints();
				gbc.fill=GridBagConstraints.HORIZONTAL;
				JLabel invt=new JLabel("Cash Memo/Debit Memo:");
				invt.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=0;
				gbc.gridy=0;
				f2.add(invt,gbc);
				JComboBox ic=new JComboBox(new String[]{"Cash Memo","Debit Memo"});
				ic.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=1;
				f2.add(ic,gbc);
				JLabel my=new JLabel("Select month and year:");
				my.setFont(new Font("Verdana",Font.PLAIN,20));
				gbc.gridx=0;
				gbc.gridy=1;
				f2.add(my,gbc);
				JComboBox myc=new JComboBox();
				myc.setFont(new Font("Verdana",Font.PLAIN,20));
				try
				{
					Class.forName("org.sqlite.JDBC");
					Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
					Statement stmt=c.createStatement();
					String sql="select distinct (month || '/'|| year) from report";
					ResultSet rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						myc.addItem(rs.getString(1));
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(f2, ex.getMessage(),"Alert",JOptionPane.WARNING_MESSAGE);
				}
				gbc.gridx=1;
				f2.add(myc,gbc);
				JButton rep=new JButton("Generate Report");
				rep.setFont(new Font("Verdana",Font.PLAIN,20));
				rep.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try
						{
						PDDocument doc=new PDDocument();
						PDPage page=new PDPage();
						doc.addPage(page);
						PDPageContentStream cs = new PDPageContentStream(doc, page);
						cs.beginText();
						cs.setLeading(15);
						cs.newLineAtOffset(70,770);
						cs.setFont(PDType1Font.TIMES_BOLD,15);
						cs.showText("Salot Chandrakant Mohanlal");
						cs.newLine();
						cs.setFont(PDType1Font.TIMES_ROMAN, 10);
						cs.showText("Undivakhar, Bhavnagar-364001.");
						cs.newLine();
						cs.showText("State: Gujarat " );
						cs.newLine();
						cs.showText("GSTIN: 24BJPPS1531M1ZT");
						cs.newLine();
						cs.showText("PAN: BJPPS1531M");
						cs.newLineAtOffset(285, 45);
						cs.showText("Contact details");
						cs.newLine();
						cs.showText("Shop: (0278)2427237, 9427339695");
						cs.newLine();
						cs.showText("Keval: 9537430031");
						cs.endText();
						cs.setFont(PDType1Font.TIMES_BOLD,10);
						cs.beginText();
						cs.newLineAtOffset(230,695);
						cs.showText("Sale Summary: "+ic.getSelectedItem().toString());
						cs.newLine();
						String my=myc.getSelectedItem().toString();
						String[] m=my.split("/");
						int mo=Integer.parseInt(m[0]);
						String mon;
						switch(mo)
						{
						case 1:
							mon="January";
							break;
						case 2:
							mon="February";
							break;
						case 3:
							mon="March";
							break;
						case 4:
							mon="April";
							break;
						case 5:
							mon="May";
							break;
						case 6:
							mon="June";
							break;
						case 7:
							mon="July";
							break;
						case 8:
							mon="August";
							break;
						case 9:
							mon="September";
							break;
						case 10:
							mon="October";
							break;
						case 11:
							mon="November";
							break;
						default:
							mon="December";
						}
						cs.showText("Month: "+mon+", "+m[1]);
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(70, 665);
						cs.setLeading(15);
						cs.showText("Date");
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(130, 665);
						cs.showText("Exempted");
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(250, 665);
						cs.showText("Taxable");
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(370, 665);
						cs.showText("Total");
						cs.endText();
						cs.addRect(65, 705, 475, 80);
						cs.addRect(65, 660, 60, 15);
						cs.addRect(125, 660, 120, 15);
						cs.addRect(245, 660, 120, 15);
						cs.addRect(365, 660, 120, 15);
						cs.stroke();
						cs.setFont(PDType1Font.TIMES_ROMAN,10);
						int desy=650;
						String[] spl=myc.getSelectedItem().toString().split("/");
						int month=Integer.parseInt(spl[0]);
						int year=Integer.parseInt(spl[1]);
						int days=31;
						if(month==4 || month==6 || month==9 || month==11)
						{
							days=30;
						}
						if(month==2)
						{
							if(year%4!=0)
							{
								days=28;
							}
							else if(year%100!=0)
							{
								days=29;
							}
							else
							{
								days=28;
							}
						}
						Class.forName("org.sqlite.JDBC");
						Connection c = DriverManager.getConnection("jdbc:sqlite:product.db");
						Statement stmt=c.createStatement();
						String sql;
						int exe=0;
						int tax=0;
						if(ic.getSelectedIndex()==0)
						{
							for(int i=1;i<=days;i++)
							{
								sql="select cmexe, cmtax from report where dt="+i+" and month="+month+" and year="+year;
								ResultSet rs=stmt.executeQuery(sql);
								cs.beginText();
								cs.newLineAtOffset(70,desy);
								cs.showText(i+"");
								cs.endText();
								if(rs.next())
								{
									cs.beginText();
									cs.newLineAtOffset(130,desy);
									cs.showText(rs.getInt(1)+"");
									exe+=rs.getInt(1);
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(250,desy);
									cs.showText(rs.getInt(2)+"");
									tax+=rs.getInt(2);
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(370,desy);
									cs.showText((rs.getInt(1)+rs.getInt(2))+"");
									cs.endText();
								}
								else
								{
									cs.beginText();
									cs.newLineAtOffset(130,desy);
									cs.showText("0");
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(250,desy);
									cs.showText("0");
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(370,desy);
									cs.showText("0");
									cs.endText();
								}
								cs.addRect(65, desy-5,60,15);
								cs.addRect(125, desy-5,120,15);
								cs.addRect(245, desy-5,120,15);
								cs.addRect(365, desy-5,120,15);
								cs.stroke();
								desy-=15;
							}
						}
						else
						{
							for(int i=1;i<=days;i++)
							{
								sql="select dmexe, dmtax from report where dt="+i+" and month="+month+" and year="+year;
								ResultSet rs=stmt.executeQuery(sql);
								cs.beginText();
								cs.newLineAtOffset(70,desy);
								cs.showText(i+"");
								cs.endText();
								if(rs.next())
								{
									cs.beginText();
									cs.newLineAtOffset(130,desy);
									cs.showText(rs.getInt(1)+"");
									exe+=rs.getInt(1);
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(250,desy);
									cs.showText(rs.getInt(2)+"");
									tax+=rs.getInt(2);
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(370,desy);
									cs.showText((rs.getInt(1)+rs.getInt(2))+"");
									cs.endText();
								}
								else
								{
									cs.beginText();
									cs.newLineAtOffset(130,desy);
									cs.showText("0");
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(250,desy);
									cs.showText("0");
									cs.endText();
									cs.beginText();
									cs.newLineAtOffset(370,desy);
									cs.showText("0");
									cs.endText();
								}
								cs.addRect(65, desy-5,60,15);
								cs.addRect(125, desy-5,120,15);
								cs.addRect(245, desy-5,120,15);
								cs.addRect(365, desy-5,120,15);
								cs.stroke();
								desy-=15;
							}
						}
						cs.setFont(PDType1Font.TIMES_BOLD,10);
						cs.beginText();
						cs.newLineAtOffset(70, desy);
						cs.showText("Total");
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(130, desy);
						cs.showText(exe+"");
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(250, desy);
						cs.showText(tax+"");
						cs.endText();
						cs.beginText();
						cs.newLineAtOffset(370, desy);
						cs.showText((exe+tax)+"");
						cs.endText();
						cs.addRect(65, desy-5,60,15);
						cs.addRect(125, desy-5,120,15);
						cs.addRect(245, desy-5,120,15);
						cs.addRect(365, desy-5,120,15);
						cs.stroke();
						c.close();
						cs.close();
						File dir=new File("Report");
						if (! dir.exists()){
					        dir.mkdir();
					    }
						doc.save(dir+"/"+myc.getSelectedItem().toString().replace("/", "_")+"_"+ic.getSelectedItem().toString().replace(" ","_")+".pdf");
						doc.close();
						JOptionPane.showMessageDialog(f2, "Report generated successfully.");
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
							JOptionPane.showMessageDialog(f2, ex.getMessage());
						}
					}
				});
				gbc.gridwidth=2;
				gbc.gridy=2;
				gbc.gridx=0;
				f2.add(rep,gbc);
				f2.setVisible(true);
				f2.setSize(1300,700);
				f.dispose();
			}
		});
		f.getContentPane().add(b3);
		f.setVisible(true);
		f.setSize(1300,700);
		}
}
class AutoCompleteComboBox extends JComboBox {
	   public int caretPos = 0;
	   public JTextField tfield = null;
	   public AutoCompleteComboBox() {
	      setEditor(new BasicComboBoxEditor());
	      setEditable(true);
	   }
	   public void setSelectedIndex(int index) {
	      super.setSelectedIndex(index);
	      tfield.setText(getItemAt(index).toString());
	      tfield.setSelectionEnd(caretPos + tfield.getText().length());
	      try
	      {
	      tfield.moveCaretPosition(caretPos);
	      }
	      catch(Exception ex)
	      {
	      }
	   }
	   public void setEditor(ComboBoxEditor editor) {
	      super.setEditor(editor);
	      if(editor.getEditorComponent() instanceof JTextField) {
	         tfield = (JTextField) editor.getEditorComponent();
	         tfield.addKeyListener(new KeyAdapter() {
	            public void keyReleased(KeyEvent ke) {
	               char key = ke.getKeyChar();
	               if (!(Character.isLetterOrDigit(key) || Character.isSpaceChar(key) )) return;
	               caretPos = tfield.getCaretPosition();
	               String text="";
	               try {
	                  text = tfield.getText(0, caretPos);
	               } catch (javax.swing.text.BadLocationException e) {
	                  e.printStackTrace();
	               }
	               for (int i=0; i < getItemCount(); i++) {
	                  String element = (String) getItemAt(i);
	                  if (element.startsWith(text)) {
	                     setSelectedIndex(i);
	                     return;
	                  }
	               }
	            }
	         });
	      }
	   }
	}
