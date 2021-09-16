package paket.RadSaBazomKlase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * KLASA U KOJOJ SU DEFINISANE POTREBNE SQL KOMANDE 
 *
 */

public class Baza {
	
	public Connection connection;
	
	public Baza(String databaseName)throws ClassNotFoundException, SQLException
	{
		String driver="net.ucanaccess.jdbc.UcanaccessDriver";
		Class.forName(driver);
		connection=DriverManager.getConnection("jdbc:ucanaccess://"+databaseName);
		
		
	}
	public ResultSet query(String SQL) throws SQLException
	{
		Statement statement=connection.createStatement();
        ResultSet result=statement.executeQuery(SQL);
        return result;
	}
	public int update(String SQL) throws SQLException
	{
		Statement statement=connection.createStatement();
		int done=statement.executeUpdate(SQL);
		return done;
	}
	public int updateReturnID(String SQL) throws SQLException
	{
		Statement statement= connection.createStatement();
		int id=-1;
		statement.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
		ResultSet result=statement.getGeneratedKeys();
		if(result.next())
		{
			id=result.getInt(1);
		}
		return id;
	}
	public PreparedStatement prepared(String sql)throws SQLException
	{
		PreparedStatement statement = null;
		statement=connection.prepareStatement(sql);
		return statement;
				
	}
	
	
}
