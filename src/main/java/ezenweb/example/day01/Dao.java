package ezenweb.example.day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dao {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/springweb","root","1234");
            System.out.println(" 연동성공 ");
        }catch ( Exception e ){   System.out.println( "연동실패 : " + e);  }
    }
    // 1.
    public boolean setmember( LombokDto dto ){
        String sql = "insert into testmember values(?,?,?,?,?)";
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt( 1 , dto.getMno() );          ps.setString( 2 , dto.getMid() );
            ps.setString( 3 , dto.getMpassword() ); ps.setLong( 4 , dto.getMpoint() );
            ps.setString( 5 , dto.getPhone() );     ps.executeUpdate(); return true;
        }catch ( Exception e ){  System.out.println(e);   } return  false;
    }
}

