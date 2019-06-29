package hospital; 

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HandlerLogin implements Initializable
{
    // JDBC �����������ݿ� URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_data?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    static final String USER = "root";
    static final String PASS = "jiangyixing";
    static Connection conn = null;
    static Statement stmt = null;
    
    ObservableList<String> ob_pat = FXCollections.observableArrayList();
    ObservableList<String> ob_doc = FXCollections.observableArrayList();
    Vector<String> pat_list,doc_list;
    
    @FXML
    private Button btn_exit,btn_login;
    @FXML
    private AnchorPane anchorpane_down;
    @FXML
    public ComboBox<String> combo_account,combo_type;
    @FXML
    private TextField text_pass;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	pat_list = new Vector<>();
    	doc_list = new Vector<>();
    	combo_type.getItems().addAll("患者","医生");//设置combobox的所有选项
    	combo_type.getSelectionModel().select(0);//设置选择哪一个选项，或者说默认选项
		try
		{
  	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
  	         stmt = conn.createStatement();
   	         String sql = "select pat_num,pat_name from patient_info";//////患者编号 患者姓名
   	         ResultSet rs = stmt.executeQuery(sql);					/////
   	         String pat_num,pat_name;
   	         while(rs.next())
   	         {
   	        	 pat_num = rs.getString("pat_num");
   	             pat_name  = rs.getString("pat_name");
   	             ob_pat.add(pat_name);//按顺序的患者姓名
   	             pat_list.add(pat_num);//按顺序的患者编号
   	         }
   	         sql = "select doc_num,doc_name from doctor_info";
   	         rs = stmt.executeQuery(sql);
   	         String doc_name,doc_num;
   	         while(rs.next())
   	         {
   	        	 doc_num = rs.getString("doc_num");
   	        	 doc_name = rs.getString("doc_name");
   	        	 ob_doc.add(doc_name);//按顺序的医生姓名
   	        	 doc_list.add(doc_num);//按顺序的医生编号
   	         }
   	         rs.close();
   	         stmt.close();
   	         conn.close();
		}
    	catch(SQLException se){
	         	// ���� JDBC ����
	         	se.printStackTrace();
	    }
		combo_account.setItems(ob_pat);//默认选择患者
		combo_account.getSelectionModel().select(0);
		////////////////////////////////类型上设置一个监听器，当选择的登录类型发生变化就需要改变combobox的内容
		combo_type.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)-> 
		{
        	int sel = combo_type.getSelectionModel().getSelectedIndex();
        	if(sel==0)
        	{
        		combo_account.setItems(ob_pat);
        	}
        	else if(sel==1)
        	{
        		combo_account.setItems(ob_doc);
        	}
        	combo_account.getSelectionModel().select(0);
	    });
		/*combo_type.getEditor().textProperty().addListener(new ChangeListener<String>()
    	{
 		    @Override
 		    public void changed(ObservableValue<? extends String> observable, 
 		                                    String oldValue, String newValue) 
 		    {
 		    	System.out.println(combo_type);
 		    	combo_type.show();
 		    }
 		});*/
    }

    @FXML
    private void on_btn_exit_clicked(ActionEvent event) throws SQLException
    {
    	Event.fireEvent(MainApp.getPrimaryStage(),
    			new WindowEvent(MainApp.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
    }
    
    @FXML
    private void on_btn_login_clicked(ActionEvent event)//按下登录
    {
    	if(combo_account.getValue() != null &&
            false == combo_account.getValue().toString().isEmpty())//账号有输入
    	{
        	int type = combo_type.getSelectionModel().getSelectedIndex();//类型
    		String pass = text_pass.getText();//密码
    		int sel_index = combo_account.getSelectionModel().getSelectedIndex();
    		String pat_doc_num = type==0?pat_list.elementAt(sel_index):doc_list.elementAt(sel_index);////////////////////////////////////////////////////////////////////////
    		if(pass.isEmpty())//没输入密码
    		{
    			JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
              			"���������룡", "����", JOptionPane.WARNING_MESSAGE);
    			return;
    		}
    		try
    		{
      	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
      	         stmt = conn.createStatement();
       	         String sql = null;
       	         ResultSet rs = null;
       	         if(type==0)
       	         {
       	        	sql = "select login_cmd from patient_info "
           	         		+ "where pat_num = '"+pat_doc_num+"'";
       	         }
       	         else if(type==1)
       	         {
       	        	sql = "select login_cmd from doctor_info "
           	         		+ "where doc_num = '"+pat_doc_num+"'";
       	         }
       	         else return;
       	         rs = stmt.executeQuery(sql);//查询对应账号的密码。
       	         if(rs.next())//有返回值，即存在对应的账号。
       	         {
       	             String login_cmd  = rs.getString("login_cmd");
       	             if(login_cmd.equals(pass))//密码和数据库记录的密码相等，即存在对应的账号并且密码也输入正确
       	             {
       	            	MainApp.pat_doc_num = pat_doc_num;
       	            	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建一个Date，记录下时间。
       	            	 if(type==0)
       	            	 {
       	            		sql = "update patient_info set last_login = "
         							+ "'"+df.format(new Date())+"' where pat_num= '"+pat_doc_num+"'";
       	            	 }
       	            	 else if(type==1)
       	            	 {
       	            		sql = "update doctor_info set last_login = "
         							+ "'"+df.format(new Date())+"' where doc_num= '"+pat_doc_num+"'";
       	            	 }
       	            	 else return;
       	            	stmt.executeUpdate(sql);
     					text_pass.clear();
     					System.out.println("��¼�ɹ���");
     					if(type==0)
     					{
     						MainApp.setRegUi();
     					}
     					else if(type==1)
     					{
     						MainApp.setDocUi();
     					}
       	             }
     				 else
     				 {
     					JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
     	              			"�������", "����", JOptionPane.WARNING_MESSAGE);
     				 }
       	         }
       	         rs.close();
       	         stmt.close();
       	         conn.close();
    		}
        	catch(SQLException se){
   	         	// ���� JDBC ����
   	         	se.printStackTrace();
   	     	}
    	}
    }
}
