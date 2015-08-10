package jclass;


import jclass.FlatClass;
import jclass.UserClass;
import jclass.CommentClass;
import java.io.IOException;
import static java.lang.Class.forName;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BurakKaan
 */
public class Database {
public String s;
static boolean status;
static Connection baglanti;
public static String nameforEdit;
public static int userId,flatId;
public static String tokenId, tokenName;
 
public static Connection openConnection(){            
             try {
                    forName("com.mysql.jdbc.Driver").newInstance();
                    baglanti=DriverManager.getConnection("jdbc:mysql://localhost:3306/stajtubitak","root","root");    
}
             catch (Exception e) {
             e.printStackTrace();
             }
             return baglanti;
       }

public static void closeConnection(){           
             try { 
                    baglanti.close();
 
             } catch (SQLException e) {
                    e.printStackTrace();
             }                         
       }  

public static Result checkUser(UserClass user){
if(user.getName().equals("")){
return Result.FAILURE_PARAM_MISMATCH;
}
else
    return Result.SUCCESS;
}

public static Result signup(UserClass user) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date=formatter.parse(user.getBirthday());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    try{
        Connection baglanti=openConnection();
        PreparedStatement ps=baglanti.prepareStatement
                  ("insert into user(Name, Surname, Email, Username, Password,Gender,Image,Birthday) values(?,?,?,?,?,?,?,?)");
        
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getUsername());
        ps.setString(5, user.getPassword());
        ps.setString(7, user.getImage());
        ps.setString(6, user.getSex());
        ps.setDate(8,sqlDate);

        int i=ps.executeUpdate();
        
          if(i>0)
          {
              Result success = Result.SUCCESS.setContent(user);
              return success;
          }
        }
        catch(Exception se)
        {
            se.printStackTrace();
            return Result.FAILURE_DB.setContent(se.getMessage());
        }finally{
            closeConnection();
        }
	return Result.FAILURE_DB;
        }

public static boolean login(String user,String pass) throws NoSuchAlgorithmException, ServletException, IOException, SQLException{                   
            try {
                    Connection baglanti=openConnection();
                    String sorgu="select * from user where Username=? and Password=?";
                    PreparedStatement ps=baglanti.prepareStatement(sorgu);  
                    ps.setString(1,user);
                    ps.setString(2,pass);
                    ResultSet rs= ps.executeQuery();
                    status=rs.next();
                    
                    return status;
                    }
                    catch (Exception e) {           
                    e.printStackTrace();
                    return status;
                    
             }
finally{
            closeConnection();
            }
}

public static Result addTokenDB(String Username,String token) throws SQLException{
try{
    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("insert into token(id,Username) values(?,?)");
        ps.setString(1, token);
        ps.setString(2, Username);
        int i=ps.executeUpdate();
        if(i>0){
            return Result.SUCCESS.setContent("Token eklendi!");
        }   
}
catch(Exception se){
se.printStackTrace();
return Result.FAILURE_DB.setContent("eklenmedi!");
}
finally{
closeConnection();
}
    return null;
}

public static Result deleteToken(String token) throws SQLException{
try{
    Connection baglanti=openConnection();
    String sql= "DELETE FROM token WHERE id=?";
    PreparedStatement ps=baglanti.prepareStatement(sql);
      ps.setString(1, token);
      ps.execute();
      return Result.SUCCESS.setContent("Token Silindi, Cikis basarili.");
}
catch(Exception e){
e.printStackTrace();
return Result.FAILURE_DB.setContent("Token silinemedi");
}
finally{
closeConnection();
}
}

public static boolean getCookie(HttpServletRequest request){
    final Cookie[] cookies=request.getCookies();
    boolean t=false;
    String name="?";
    String value;
    if(cookies!=null){
    for (int i = 0; i < cookies.length; i++) {
    Cookie cookie = cookies[i];
    name=cookie.getName();
    value=cookie.getValue();
    tokenId=name;
    tokenName=value;
    t=Database.checkToken(value);
    if(t){
    return true;
    }
    }
    
    }
    return false;   
}

public static boolean checkToken (String token){

String value="";
try{
    Connection baglanti=openConnection();
    String sql= "select * FROM token where id='"+token+"'";
    PreparedStatement ps=baglanti.prepareStatement(sql);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    value=(rs.getString("id"));
    nameforEdit=(rs.getString("Username"));
    if(token.equals(value)){
        getUser(nameforEdit);
        return true;
    }
}
}
    catch(Exception e){
    e.getMessage();
    }
    
return false;
}

public static Result getUser(String username) throws SQLException{
try{
    UserClass user=new UserClass();
    Connection baglanti=openConnection();
PreparedStatement ps=baglanti.prepareStatement("select * FROM user where Username="+"'"+username+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    user.setName(rs.getString("Name"));
    user.setSurname(rs.getString("Surname"));
    userId=rs.getInt("id");
return Result.SUCCESS.setContent(user);
    }}

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}
finally{
closeConnection();
}
    return null;
}

public static Result addFlat(FlatClass flat){
try{
        FlatClass flat1=flat;
        Connection baglanti=openConnection();
        PreparedStatement ps=baglanti.prepareStatement
                  ("insert into flat(Country, City, State, Street, "
                          + "BuildingName,BuildingNo,FlatNo) values(?,?,?,?,?,?,?)");
        
        ps.setString(1, flat1.getCountry());
        ps.setString(2, flat1.getCity());
        ps.setString(3, flat1.getState());
        ps.setString(4, flat1.getStreet());
        ps.setString(5, flat1.getBuildingName());
        ps.setString(6, flat1.getBuildingNo());
        ps.setString(7, flat1.getFlatNo());
        ps.executeUpdate();
        return Result.SUCCESS.setContent(flat1);
     
        }
        catch(Exception se)
        {
            se.printStackTrace();
            return null;
        }
        finally{
            closeConnection();
        }

      }

public static Result addComment(CommentClass comment){
try{
        CommentClass comment1=comment;
        Connection baglanti=openConnection();
        String sql="insert into comment(FlatId, UserId, Content) values(?,?,?)";
        PreparedStatement ps=baglanti.prepareStatement(sql, new String[] { "id"});
        ps.setInt(1, comment1.getFlatId());
        ps.setInt(2, comment1.getUserId());
        ps.setString(3, comment1.getContent());
        int i=ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        while(null != generatedKeys && generatedKeys.next()) {
        comment1.setCommentId(generatedKeys.getInt(1));
        }

        if(i>0){
        
        return Result.SUCCESS.setContent(comment1);
        }
        }
        catch(Exception se)
        {
            se.printStackTrace();
            return Result.FAILURE_DB.setContent(se.getMessage());
        }
        finally{
            closeConnection();
        }
    return null;

}

public static Result searchFlat(String column,String info){
    
    try{
    List myList = new ArrayList();
    ArrayList<FlatClass> liste=new ArrayList<FlatClass>();
    
    String name="";
    Connection baglanti=openConnection();
    String sql = "SELECT * FROM flat WHERE"+" "+column+" "+"LIKE '%"+info+"%'";
    PreparedStatement ps=baglanti.prepareStatement(sql);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    FlatClass getFlat=new FlatClass();
    getFlat.setCity(rs.getString("City"));
    getFlat.setCountry(rs.getString("Country"));
    getFlat.setState(rs.getString("State"));
    getFlat.setStreet(rs.getString("Street"));
    getFlat.setBuildingName(rs.getString("BuildingName"));
    getFlat.setBuildingNo(rs.getString("BuildingNo"));
    getFlat.setFlatNo(rs.getString("FlatNo"));
    getFlat.setId(rs.getInt("id"));
    liste.add(getFlat);
    }
    rs.close();
    return Result.SUCCESS.setContent(liste);
}

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}
    finally{
    closeConnection();
    }
}

public static Result getFlat(int flatid){
try{
    FlatClass flat=new FlatClass();
    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("select * FROM flat where id="+"'"+flatid+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    flat.setCountry(rs.getString("Country"));
    flat.setCity(rs.getString("City"));
    flat.setState(rs.getString("State"));
    flat.setStreet(rs.getString("Street"));
    flat.setBuildingName(rs.getString("BuildingName"));
    flat.setBuildingNo(rs.getString("BuildingNo"));
    flat.setFlatNo(rs.getString("FlatNo"));
    flatId=rs.getInt("id");
    return Result.SUCCESS.setContent(flat);
    } 
}

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}
finally{
closeConnection();
}
    return Result.FAILURE_DB.setContent(flatid +"gecersiz bir link");
}

public static Result editProfile(String username,String password){
   try { 
                Connection baglanti=openConnection(); 
                PreparedStatement ps = baglanti.prepareStatement("update user set Password=? where Username='"+username+"'"); 
                ps.setString(1,password); 
    
                int i = ps.executeUpdate(); 
                if(i!=0)    {
                    return Result.SUCCESS.setContent("updated"); 
                } 
                else{ 
                    return Result.FAILURE_DB.setContent("failed"); 
                } 
        } 
        catch(Exception e)  { 
            return Result.FAILURE_DB.setContent(e.getMessage());
        }
}

public static boolean checkFlat(String Country,String City,String State,String 
        Street,String BuildingName,String BuildingNo,String FlatNo){
String country="";String city="";String state="";String street="";String buildingName="";
String buildingNo="";String flatNo="";
try{
    Connection baglanti=openConnection();
    String sql="select * FROM flat where Country='"+Country+"'"+
            " and City='"+City+"'"+" and State='"+State+"'"+
            " and Street='"+Street+"'"+" and BuildingName='"+BuildingName+"'"+
            " and BuildingNo='"+BuildingNo+"'"+" and FlatNo='"+FlatNo+"'";
    PreparedStatement ps=baglanti.prepareStatement(sql);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    country=(rs.getString("Country"));city=(rs.getString("City"));state=(rs.getString("State"));
    street=(rs.getString("Street"));buildingNo=(rs.getString("BuildingNo"));buildingName=(rs.getString("BuildingName"));
    flatNo=(rs.getString("FlatNo"));
    if(country.equals(Country)&&city.equals(City)&&state.equals(State)
            &&street.equals(Street)&&buildingNo.equals(BuildingNo)&&
            buildingName.equals(BuildingName)&&flatNo.equals(FlatNo)){
            return true;
    }
}
}
    catch(Exception e){
    e.getMessage();
    }
 finally{
closeConnection();
}
    return false;
}

public static Result getCommentbyUser(int id){
try{
    List myList = new ArrayList();
    ArrayList<CommentClass> liste=new ArrayList<CommentClass>();    
    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("select * FROM comment where userId="+"'"+id+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    CommentClass comment=new CommentClass();
    comment.setContent(rs.getString("content"));
    comment.setUserId(rs.getInt("UserId"));
    comment.setCommentId(rs.getInt("id"));
    comment.setFlatId(rs.getInt("FlatId"));
     SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
     String datetime=sdfr.format(rs.getTimestamp("Date"));
     comment.setDate(datetime);
    liste.add(comment);}
    rs.close();
    return Result.SUCCESS.setContent(liste);
    } 

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}

finally{
closeConnection();
}
}

public static Result getCommentbyFlat(int id){
try{
    List myList = new ArrayList();
    ArrayList<CommentClass> liste=new ArrayList<CommentClass>();
    
    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("select * FROM comment where flatId="+"'"+id+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    CommentClass comment=new CommentClass();
    comment.setContent(rs.getString("content"));
    comment.setUserId(rs.getInt("UserId"));
    comment.setCommentId(rs.getInt("id"));
    comment.setFlatId(rs.getInt("FlatId"));
     SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     String datetime=sdfr.format(rs.getTimestamp("Date"));
     comment.setDate(datetime);
    liste.add(comment);}
    rs.close();
    return Result.SUCCESS.setContent(liste);
    } 

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}

finally{
closeConnection();
}

}

public static Result getLatestComments(){
try{
    List myList = new ArrayList();
    ArrayList<CommentClass> liste=new ArrayList<CommentClass>();
    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("select * from comment ORDER by Date desc LIMIT 10");
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    CommentClass comment=new CommentClass();
    comment.setContent(rs.getString("content"));
    comment.setUserId(rs.getInt("UserId"));
    comment.setCommentId(rs.getInt("id"));
    comment.setFlatId(rs.getInt("FlatId"));
    SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd,HH:mm:sss");
    Date date=rs.getDate("Date");
    String datetime=sdfr.format(date.getTime());
    comment.setDate(datetime);
    liste.add(comment);}
    rs.close();
    return Result.SUCCESS.setContent(liste);
    } 

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}
}

public static Result getLatestUsers(){
try{
    List myList = new ArrayList();
    ArrayList<UserClass> liste=new ArrayList<UserClass>();
    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("select * from user ORDER by Date desc LIMIT 10");
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    UserClass user=new UserClass();
   user.setName(rs.getString("Name"));
   user.setSurname(rs.getString("Surname"));
   user.setEmail(rs.getString("Email"));
   user.setUsername(rs.getString("Username"));
    liste.add(user);}
    rs.close();
    return Result.SUCCESS.setContent(liste);
    } 

catch(Exception e){
return Result.FAILURE_DB.setContent(e.getMessage());
}

}

public static boolean checkUserbyUsername(String username){

String Username="";
try{
    Connection baglanti=openConnection();
    String sql="select * FROM user where Username='"+username+"'";
    PreparedStatement ps=baglanti.prepareStatement(sql);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    Username=(rs.getString("Username"));
    if(username.equals(Username)){
            return true;
    }
    else{
    return false;}
}
}
    catch(Exception e){
    e.getMessage();
    }
 finally{
closeConnection();
}
    return false;
}

public static boolean checkUserbyEmail(String email){
String Email="";
try{
    Connection baglanti=openConnection();
    String sql="select * FROM user where Email='"+email+"'";
    PreparedStatement ps=baglanti.prepareStatement(sql);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    Email=(rs.getString("Email"));
    if(email.equals(Email)){
            return true;
    }
    else{
    return false;}
}
}
    catch(Exception e){
    e.getMessage();
    }
 finally{
closeConnection();
}
    return false;
}

public static Result garbageCollect(){
try{

    Connection baglanti=openConnection();
    PreparedStatement ps=baglanti.prepareStatement("DELETE FROM token WHERE Date < (NOW() - INTERVAL 1 MINUTE)");
    ps.executeUpdate();
    return Result.SUCCESS.setContent("tokenlar silindi");
    } 
    catch(Exception e){
    return Result.FAILURE_DB.setContent(e.getMessage());
}
}

}

















