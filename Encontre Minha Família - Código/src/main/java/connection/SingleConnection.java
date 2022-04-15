package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static String urlBancoDados = "jdbc:postgresql://ec2-3-230-238-86.compute-1.amazonaws.com:5432/dfue0f60h66gka?sslmode=require&autoReconnect=true";
	private static String usuario = "auhtybnzazvxgo";
	private static String senha = "57dea7bdd927baf08765bee9659a200a2d908eeaf113b73c33ee78902847d08d";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");//carrega o driver de conexao do banco
				connection = DriverManager.getConnection(urlBancoDados, usuario, senha);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}