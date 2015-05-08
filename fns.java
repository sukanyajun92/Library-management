package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.io.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.io.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class fns extends JFrame {
	private static final ActionListener ActionListener = null;
	static JPanel myPanel=new JPanel();
	static String selectedData1,selectedData2,selectedData3,selectedData4,selectedData5; 
	static JButton myButton1,myButton2;
	static int i=0,flag1=0,flag2=0,m=0,n=0,copies=0,ret2;
	static JLabel myLabel1,myLabel2,myLabel3,myLabel4,myLabel5;
	static JTextField text1,text2,text3;//book_id,title,author_name
	static JTextField text4,text5,text6;//part2,text4=branch_id&&text5=card_no
	static String book_idq,book_title,author_name;
	static String bookid_r,branchid_r,total_r,available_r,card_no;
	static String borrowername,fname,lname,address,phone;//just this
	static String lastquery,checkquery;//this too
	static String myquery;//for 1st part
	static String mytitlequery,mybookquery,myauthorquery,query2,myquery1,insertquery,deletequery;
	static String bcquery,borrowquery,myfinalquery,cardquery1,cardquery2,book_lquery,addquery;//for 2nd part
	static Connection conn=null;
	static String driver="com.mysql.jdbc.Driver";
	static String dBName="hw3";
	static String username="root";
	static String password="Modern09";
	static ResultSet resultset1,resultset2,resultset3,resultset4,resultset5;
	static int resultset6;
	static JTextArea result_area=new JTextArea(6,20);
	static String joinquery,displayquery;
	static Vector columnNames = new Vector();
	static Vector data = new Vector();
	public ButtonGroup group1 = new ButtonGroup();
	public JRadioButton btnOne = new JRadioButton();
	static int f=0;
	
	fns()
	{
		
		//DataInputStream in=new DataInputStream(System.in);
	}
	public static boolean connect()
	{
			boolean isConn=false;
			try
			{
				Class.forName(driver);
				conn=(Connection)DriverManager.getConnection("jdbc:mysql://"+"localhost:3306/"+dBName,username,password);
				isConn=true;
			}
			catch(Exception ex)
			{
				System.out.println("SQLException: " + ex.getMessage());
			    
			}
			return isConn;
			
	}
	
	public static ResultSet select(String query)
	{
		connect();
		ResultSet result=null;
		try
		{
			Statement s= (Statement)conn.createStatement();
			result=s.executeQuery(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void showSelect(ResultSet result) 
	{
		resultset1=result;
		if(result!=null)
		{
			try
			{
				ResultSetMetaData rsmd=(ResultSetMetaData) result.getMetaData();
				int noColumns=rsmd.getColumnCount();
				for (int i=0;i<noColumns;i++)
				{
					result_area.setText(result_area.getText()+(rsmd.getColumnName(i+1)+"\t"));//(rsmd.getColumnName(i+1)+"  ");
					//colnames[i]=rsmd.getColumnName(i+1);		
				}
				result_area.setText(result_area.getText()+"\n");
				//int q=0;
				while(result.next())
				{
					for (int i=0;i<noColumns;i++)
					{
						result_area.setText(result_area.getText()+result.getString(i+1)+"\t");
						//data[q][i]=result.getString(i+1);
								
					}
					result_area.setText(result_area.getText()+"\n");
					//q++;
				}
				           
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static int query (String query)
	{
		int result=-1;
		try
		{
			Statement s=(Statement) conn.createStatement();
			result=s.executeUpdate(query);
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public static void disconnect()
	{
		try
		{
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("\nError in terminating the connection\n");
		}
	}
	public static int insert(String query)
	{
		connect();
		int result1=0;
		try
		{
			Statement s= (Statement)conn.createStatement();
			result1=s.executeUpdate(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result1;
	}
	public static void signup()
    {
		try
		{
			JFrame myWin1=new JFrame("Sign up here");
			myWin1.setLayout(null);
			myWin1.setVisible(true);
			myWin1.setSize(800, 1400);
					
			myButton1=new JButton("create account");
			
			//myButton.setBackground(Color.CYAN);
			myButton1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try
					{
						 fname=text1.getText();
						 phone=text2.getText();
						 lname=text4.getText();
						 address=text5.getText();
						 
						 
						 checkquery="select fname,lname,address from borrower";
						 
						
						 resultset1=select(checkquery);//change the variable myquery
						 
						 
						 if(fname.isEmpty()&&lname.isEmpty()&&address.isEmpty())
						 {
							 JOptionPane.showMessageDialog(null, "Enter Your name and address");
						 }
						 if(phone.isEmpty())
						 {
							 phone="NULL";
						 }
						 int row=0;
						 while(resultset1.next())//Is card_no there in borrowers???
						 {
							 
							 row++;
						 }
						 String R=String.valueOf(row+1+9000);
						 lastquery="insert into borrower values('"+R+"','"+fname+"','"+lname+"','"+address+"','"+phone+"')";
						 resultset1=select(checkquery);
						 while(resultset1.next())
						 {
							 //System.out.println("you r in while loop");
							 if((fname.equalsIgnoreCase(resultset1.getString(1)))&&(lname.equalsIgnoreCase(resultset1.getString(2)))&&(address.equalsIgnoreCase(resultset1.getString(3))))
							 {
								 f=1;
								 JOptionPane.showMessageDialog(null, "You have an account already");
								 break;
							 }
						 }
						 
						 if(f==0)
						 {
							 int rt=insert(lastquery);
							 if(rt==0)
							 {
								 JOptionPane.showMessageDialog(null, "Not able to check in");
							 }
							 
							 else
							 {
								 JOptionPane.showMessageDialog(null, "Signed up successfully");
							 }
						 }
						
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}
				
			});
		
			myLabel1=new JLabel("First name");
			myLabel4=new JLabel("Last name");
			myLabel5=new JLabel("Address");
			myLabel2=new JLabel("Phone");
			
			text1=new JTextField(30);
			text4=new JTextField(80);
			text5=new JTextField(200);
			text2=new JTextField(40);
			
			
		
			myLabel1.setLocation(27,20);
			myLabel4.setLocation(27,200);
			myLabel5.setLocation(27,380);
			myLabel2.setLocation(27,110);
			
			text1.setLocation(265,20);
			text4.setLocation(265,200);
			text5.setLocation(265,380);
			text2.setLocation(265,110);
			
			
			myButton1.setLocation(400,600);
			
			
			myLabel1.setVisible(true);
			myLabel4.setVisible(true);
			myLabel5.setVisible(true);
			myLabel2.setVisible(true);
			
			
			myButton1.setVisible(true);
			
			
			text1.setVisible(true);
			text4.setVisible(true);
			text5.setVisible(true);
			text2.setVisible(true);
					
			myLabel1.setSize(100,20);
			myLabel4.setSize(100,20);
			myLabel5.setSize(100,20);
			myLabel2.setSize(100,20);
			
			myButton1.setSize(180,60);
			
			text1.setSize(105,40);
			text4.setSize(105,40);
			text5.setSize(250,105);
			text2.setSize(105,40);
			
			myWin1.add(myLabel1);
			myWin1.add(myButton1);
			
			myWin1.add(myLabel4);
			myWin1.add(myLabel5);
			myWin1.add(myLabel2);
			
			
			myWin1.add(text1);
			myWin1.add(text2);
			myWin1.add(text4);
			myWin1.add(text5);
						
			
			myWin1.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void first()
	{
		
		try
		{
			JFrame myWin=new JFrame("Database Application");
			//myPanel=new JPanel();
			//myWin.add(myPanel);
			myWin.setLayout(null);
			myWin.setVisible(true);
			myWin.setSize(800, 1400);
		
			myButton1=new JButton("Proceed");
			
			//myButton.setBackground(Color.CYAN);
			myButton1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// TODO Auto-generated method stub
					try
					{
						 book_idq=text1.getText();
						 //System.out.println(book_id);
						 book_title=text2.getText();
						 author_name=text3.getText();
						 mytitlequery="select a.book_id,a.branch_id,a.no_of_copies as Total_Copies,a.no_of_copies-count(card_no) as Available_Copies from (select * from book natural join book_copies where title like '%"+book_title+"%')"+"as a left join (select * from book natural join book_loans where title like '%"+ book_title+"%') as b on a.branch_id=b.branch_id group by b.branch_id";
						 mybookquery="select a.book_id,a.branch_id,a.no_of_copies as Total_Copies,a.no_of_copies-count(card_no) as Available_Copies from (select * from book natural join book_copies where book_id like '%"+book_idq+"%') as a left join (select * from book natural join book_loans where book_id like '%"+book_idq+"%') as b on a.branch_id=b.branch_id group by b.branch_id";
						 myauthorquery="select a.book_id,a.branch_id,a.no_of_copies as Total_Copies,CASE WHEN a.book_id=b.book_id then a.no_of_copies-count(card_no) ELSE a.no_of_copies END AS Available from ( select * from book_authors natural join book_copies where author_name like '%"+author_name+"%') as a left join(select * from book_authors natural join book_loans where author_name like '%"+author_name+"%') as b on a.branch_id=b.branch_id group by a.branch_id,a.author_name";
						 bcquery="select * from book_copies";
						 if((book_idq.isEmpty())&&(book_title.isEmpty())&&(author_name.isEmpty()))
						 {
					 		JOptionPane.showMessageDialog(null, "Please provide at least one search query");
						 }
						 else if((!book_idq.isEmpty())&&(book_title.isEmpty())&&(author_name.isEmpty()))
						 {
							 myquery=mybookquery;
							 //System.out.println("I have to search with the Book_id");
							 //System.out.println(myquery);
							 
						 }
						 
						 else if ((book_idq.isEmpty())&&(!book_title.isEmpty())&&(author_name.isEmpty()))
						 {
							 myquery=mytitlequery;
							 /*System.out.println("\n I have to search with Book_title");
							 System.out.println(myquery);*/
							 
						 }
						 else if((book_idq.isEmpty())&&(book_title.isEmpty())&&(!author_name.isEmpty()))
						 {
							 myquery=myauthorquery;
							 //System.out.println("\n I have to search with Author_name");
						 }
						 else if ((!book_idq.isEmpty())&&(!book_title.isEmpty())&&(author_name.isEmpty()))
						 {
							  myquery=mybookquery+" UNION "+mytitlequery;
							 
						 }
						 else if ((book_idq.isEmpty())&&(!book_title.isEmpty())&&(!author_name.isEmpty()))
						 {
							 myquery=mytitlequery+ " UNION "+myauthorquery;
						 }
						 else if ((!book_idq.isEmpty())&&(book_title.isEmpty())&&(!author_name.isEmpty()))
						 {
							 myquery=mybookquery+" UNION "+myauthorquery;
						 }
						 else if ((!book_idq.isEmpty())&&(!book_title.isEmpty())&&(!author_name.isEmpty()))
						 {
							 myquery=mybookquery+" UNION "+mytitlequery+" UNION "+myauthorquery;
							 //System.out.println(myquery);
						 }
						
						 showSelect(select(myquery));
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}
				
			});
		
			myLabel1=new JLabel("Enter the Book_id");
			myLabel2=new JLabel("Enter the Book_title");
			myLabel3=new JLabel("Enter the Author name");
			text1=new JTextField(30);
			text2=new JTextField(50);
			text3=new JTextField(80);
			
		
			myLabel1.setLocation(27,20);
			myLabel2.setLocation(27,60);
			myLabel3.setLocation(27,100);
			text1.setLocation(265,20);
			text2.setLocation(265, 60);
			text3.setLocation(265,100);
			myButton1.setLocation(200,220);
			result_area.setLocation(100,400);
		
			myLabel1.setVisible(true);
			myLabel2.setVisible(true);
			myLabel3.setVisible(true);
			myButton1.setVisible(true);
			text1.setVisible(true);
			text2.setVisible(true);
			text3.setVisible(true);
			result_area.setVisible(true);
		
		
			myLabel1.setSize(100,14);
			myLabel2.setSize(130,20);
			myLabel3.setSize(150,20);
			myButton1.setSize(180,60);
			text1.setSize(105,25);
			text2.setSize(105,25);
			text3.setSize(105,25);
			result_area.setSize(800,300);
		
			myWin.add(myLabel1);
			myWin.add(myButton1);
			myWin.add(myLabel2);
			myWin.add(myLabel3);
			myWin.add(text1);
			myWin.add(text2);
			myWin.add(text3);
			myWin.add(result_area);
			
			myWin.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void checkout()
    {
		try
		{
			JFrame myWin1=new JFrame("Check out the books");
			myWin1.setLayout(null);
			myWin1.setVisible(true);
			myWin1.setSize(800, 1400);
					
			myButton1=new JButton("Checkout Books");
			
			//myButton.setBackground(Color.CYAN);
			myButton1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try
					{
						 book_idq=text1.getText();
						 branchid_r=text4.getText();
						 card_no=text5.getText();
						 
						 query2="select a.book_id,a.branch_id,a.no_of_copies as Total_Copies,a.no_of_copies-count(card_no) as Available_Copies from (select * from book natural join book_copies where book_id like '%"+book_idq+"%' and branch_id like '%"+branchid_r+"%') as a left join (select * from book natural join book_loans where book_id like '%"+book_idq+"%' and branch_id like '%"+branchid_r+"%') as b on a.branch_id=b.branch_id group by b.branch_id";
						 bcquery="select book_id,branch_id from book_copies where book_id like '%"+book_idq+"%' and branch_id like '%"+branchid_r+"%'";
						 borrowquery="select card_no from borrower where card_no='"+card_no+"'";
						 cardquery1="select book_id,branch_id,count(book_id),card_no from book_loans where book_id like '%"+book_idq+"%' and branch_id like '%"+branchid_r+"%' group by card_no";
						 cardquery2="select book_id,branch_id,count(book_id),card_no from book_loans where book_id like '%"+book_idq+"%' and branch_id like '%"+branchid_r+"%' and card_no like '%"+card_no+"%' group by card_no";
						 insertquery="insert into book_loans values('"+book_idq+"','"+branchid_r+"','"+card_no+"',curdate(),curdate()+14)";
						
						
						 int bcount=0,avail=0,flag3=0;
						 resultset1=select(query2);//change the variable myquery
						 resultset2=select(bcquery);
						 resultset3=select(borrowquery);
						 //resultset5=select(cardquery1);
						 resultset4=select(cardquery2);
						 if(book_idq.isEmpty()&&branchid_r.isEmpty()&&card_no.isEmpty())
						 {
							 JOptionPane.showMessageDialog(null, "Enter all the fields");
						 }
						 while(resultset3.next())//Is card_no there in borrowers???
						 {
							 if(card_no.equals(resultset3.getString(1)))
							 {
								 flag1=1;
								 //System.out.println("hello");
							 }
						 }
						 if(flag1==0)
						 {
							 JOptionPane.showMessageDialog(null, "No such card number exists");
						 }
						 while((resultset4.next())&& (resultset1.next()))
						 {
							 bcount=Integer.parseInt(resultset4.getString(3));
							 avail=Integer.parseInt(resultset1.getString(4));
							 if((bcount<3)&&(avail>0)&&(flag1==1)&&(flag2==1))
							 {
									resultset6=insert(insertquery);
									//System.out.println("hi& hello");
									//System.out.println(insertquery);
									if(resultset6==0)
									{
										JOptionPane.showMessageDialog(null, "No such record found!!Sry");
									}
									else
										 JOptionPane.showMessageDialog(null, "Checked out successfully");
							 }
												 		
						}
						 while(resultset2.next())//is the book_id && branch_id combo correct???
						 {
							 	 flag2=1;
								 //System.out.println("hai");
						 }
						 if(flag2==0)
						 {
							 JOptionPane.showMessageDialog(null, "No such combination of book-id and branch_id exists");
						 }
						 while(resultset1.next())
						{
							 		avail=Integer.parseInt(resultset1.getString(4));
									//System.out.println("hi");
									if((flag1==1)&&(flag2==1)&&(avail>0))
									{
											resultset6=insert(insertquery);
											//System.out.println("hi");
											//System.out.println(insertquery);
											if(resultset6==0)
											{
												JOptionPane.showMessageDialog(null, "No such record found!!Sry");
											}
									}
							}
							
						}			 						
						
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}
				
			});
		
			myLabel1=new JLabel("Enter the Book_id");
			myLabel4=new JLabel("Enter the branch_id");
			myLabel5=new JLabel("Enter the card_no");
			
			text1=new JTextField(30);
			text4=new JTextField(80);
			text5=new JTextField(80);
			
			
		
			myLabel1.setLocation(27,20);
			myLabel4.setLocation(27,60);
			myLabel5.setLocation(27,100);
			
			text1.setLocation(265,20);
			text4.setLocation(265, 60);
			text5.setLocation(265,100);
			
			
			myButton1.setLocation(400,220);
			
			
			result_area.setLocation(300,400);
		
			myLabel1.setVisible(true);
			myLabel4.setVisible(true);
			myLabel5.setVisible(true);
			
			
			myButton1.setVisible(true);
			
			
			text1.setVisible(true);
			text4.setVisible(true);
			text5.setVisible(true);
					
			myLabel1.setSize(100,14);
			myLabel4.setSize(150,30);
			myLabel5.setSize(150,15);
			
			myButton1.setSize(180,60);
			
			text1.setSize(105,25);
			text4.setSize(105,25);
			text5.setSize(105,25);
			
			myWin1.add(myLabel1);
			myWin1.add(myButton1);
			
			myWin1.add(myLabel4);
			myWin1.add(myLabel5);
			
			myWin1.add(text1);
			myWin1.add(text4);
			myWin1.add(text5);
						
			
			myWin1.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void checkin()
    {
    	try
		{
    		final JFrame myWin1=new JFrame("Check in the books");
    		myWin1.setLayout(null);
			myWin1.setVisible(true);
			myWin1.setSize(800, 1400);
			
			myButton1=new JButton("Submit");
			myButton2=new JButton("Proceed");
			
			myWin1.add(myButton1);
			
			myButton1.setVisible(true);
			
			myLabel1=new JLabel("Enter the Book_id");
			myLabel4=new JLabel("Enter the card_no");
			myLabel5=new JLabel("Name");
			
			text1=new JTextField(30);
			text4=new JTextField(80);
			text5=new JTextField(80);
					
			myLabel1.setLocation(27,20);
			myLabel4.setLocation(27,60);
			myLabel5.setLocation(27,100);
			
			text1.setLocation(265,20);
			text4.setLocation(265,100);
			text5.setLocation(265,60);
						
			myButton1.setLocation(400,220);
						
			myLabel1.setVisible(true);
			myLabel4.setVisible(true);
			myLabel5.setVisible(true);
						
			text1.setVisible(true);
			text4.setVisible(true);
			text5.setVisible(true);
					
			myLabel1.setSize(100,14);
			myLabel4.setSize(150,30);
			myLabel5.setSize(150,15);
			
			myButton1.setSize(180,60);
			
			text1.setSize(105,25);
			text4.setSize(105,25);
			text5.setSize(105,25);
			
			myWin1.add(myLabel1);
			myWin1.add(myButton1);
			
			myWin1.add(myLabel4);
			myWin1.add(myLabel5);
			
			myWin1.add(text1);
			myWin1.add(text4);
			myWin1.add(text5);
						
			
			myWin1.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
			myButton1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					book_idq=text1.getText();
					card_no=text5.getText();
					borrowername=text4.getText();
					
					joinquery="select book_id,card_no,branch_id,fname,lname from book_loans natural join borrower where book_id='"+book_idq+"' and card_no='"+card_no+"'";
					displayquery="select book_id,card_no,branch_id,fname,lname from book_loans natural join borrower where book_id='"+book_idq+"' and card_no='"+card_no+"' and fname like '%"+borrowername+"%' or lname like '%"+borrowername+"%'";
					try{
							resultset1=select(joinquery);
							resultset2=select(displayquery);
							if(resultset1==null)
							{
								JOptionPane.showMessageDialog(null, "No such record found!!Sry");
							}
							else if(resultset2==null)
							{
								JOptionPane.showMessageDialog(null, "No such record found!!Sry");
							}
							ResultSetMetaData md1 = (ResultSetMetaData)resultset1.getMetaData();
							ResultSetMetaData md2= (ResultSetMetaData)resultset2.getMetaData();
							int columns = md1.getColumnCount();
							
							for (int i = 1; i <= columns; i++) 
							{
								columnNames.addElement( md1.getColumnName(i) );
							}
							if((book_idq.isEmpty())&&(card_no.isEmpty())&&(borrowername.isEmpty()))
							{
								JOptionPane.showMessageDialog(null, "Please provide the search query_All r empty");
							}
							else if ((book_idq.isEmpty())||(card_no.isEmpty()))
							{
								JOptionPane.showMessageDialog(null, "Please provide the search query->card_no/bookId is empty");
							}
							else if ((!book_idq.isEmpty())&&(!card_no.isEmpty()))
							{
								while (resultset1.next()) 
								{
									Vector row = new Vector(columns);
									for (int i = 1; i <= columns; i++)
									{
										row.addElement( resultset1.getString(i) );
										//System.out.println(resultset1.getString(i));
									}
									data.addElement( row );
								}
							}
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
					final JTable table = new JTable(data, columnNames);
					 /*  ListSelectionModel cellSelectionModel = table.getSelectionModel();
				    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); */
					  table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				      public void valueChanged(ListSelectionEvent e) {
				        selectedData1 = null;
				        selectedData2 = null;
				        selectedData3 = null;
				        selectedData4 = null;
				        selectedData5 = null;

				        int[] selectedRow = table.getSelectedRows();
				        int[] selectedCol=table.getSelectedColumns();
				        
				        for (int i = 0; i < selectedRow.length; i++) {
				        	      selectedData1 = (String) table.getValueAt(selectedRow[i],0 );
				        	      selectedData2 = (String) table.getValueAt(selectedRow[i],1 );
				        	      selectedData3 = (String) table.getValueAt(selectedRow[i],2);
				        	      selectedData4 = (String) table.getValueAt(selectedRow[i],3 );
				        	      selectedData5 = (String) table.getValueAt(selectedRow[i],4);
				        }
				        	      myButton2.addActionListener(new ActionListener(){
				        	    	public  void actionPerformed(ActionEvent e)
				        	    	{
				        	    			deletequery="delete from book_loans where book_id='"+selectedData1+"' and card_no='"+selectedData2+"' and branch_id='"+selectedData3+"'";
				        	    			ret2=insert(deletequery);
				        	    			if(ret2!=0)
				        	    				 JOptionPane.showMessageDialog(null, "checked in successfully");
				        	    	}
				                	  
				          });
				        
				        //System.out.println("Selected: " + selectedData);
				      }

				    });

					TableColumn col;
					for (int i = 0; i < table.getColumnCount(); i++) 
					{
						col = table.getColumnModel().getColumn(i);
						col.setMaxWidth(250);
					}
					JScrollPane scrollPane = new JScrollPane( table );
					myPanel.add( scrollPane );
					myPanel.add(myButton2);
					
					JFrame f=new JFrame();
					f.add(myPanel);
					f.setSize(600,400);
					f.setVisible(true);
					
					
				}
			});
		}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
										
    }

	public static void main(String[] args)
	{
		fns f=new fns();
		//Graphics g=new Graphics();
		final JFrame MainWin=new JFrame("Comet library");
		JButton button=new JButton("Check availability");
		JButton button1=new JButton("Check out");
		JButton button2=new JButton("Checkin");
		JButton button3=new JButton("Signup");
		JLabel label1=new JLabel("Welcome to comet library");
		JLabel label2=new JLabel("Select one of the options");
		//image.setImage(image);
		MainWin.add(button1);
		MainWin.add(button2);
		MainWin.add(button);
		MainWin.add(label1);
		MainWin.add(label2);
		MainWin.add(button3);
		MainWin.setLayout(null);
		MainWin.show();
		MainWin.setSize(1000,1000);
		button1.show();
		button.show();
		button3.show();
		label1.show();
		label1.setLocation(400,100);
		label1.setSize(500, 100);
		label2.show();
		label2.setLocation(400,200);
		label2.setSize(500, 100);
		button1.setLocation(300,300);
		button.setLocation(100,300 );
		button1.setSize(120,20);
		button.setSize(120,20);
		button3.setSize(120,20);
		button3.setLocation(500,300);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				MainWin.hide();
				first();
			}
		});
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				MainWin.hide();
				checkout();
			}
		});
		button2.show();
		button2.setLocation(700,300);
		button2.setSize(120,20);
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				MainWin.hide();
				checkin();
			}
		});
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				MainWin.hide();
				signup();
			}
		});
		}
		
	}


