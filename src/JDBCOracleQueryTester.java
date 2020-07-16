import oracle.sql.converter.JdbcCharacterConverters;

import java.sql.*;
import java.util.Scanner;

/**
 * A sample application checking Oracle version using JDBC
 * Library dependencies: ojdbc7.jar
 * Compile: javac -cp ojdbc7.jar JDBCOracleQueryTester.java
 * Run: java -cp .;ojdbc7.jar JDBCOracleQueryTester databaseServerName <database port> serviceName/SID
 */
public class JDBCOracleQueryTester {
    private final static String JDBC_DRIVER_TYPE = "jdbc:oracle:thin";
    private static String dbHost = null;
    private static int dbPort = 1521;
    private static String dbName = null;
    private static String dbSql = "SELECT * FROM v$version";

    public static void main(String[] args) {
        JDBCOracleQueryTester demo = new JDBCOracleQueryTester();
        if (args == null || args.length < 2) {
            System.out.println("Usage: JDBCOracleVersion databaseHost <database port> serviceName/SID");
            System.exit(0);
        }
        else {
            dbHost = args[0];
            if (args.length == 2) { // 2 arguments should be dbHost dbName with default port 1521
                dbName = args[1];
            }
            else { // 3 arguments should be dbHost dbPort dbName
                dbPort = Integer.valueOf(args[1]);
                dbName = args[2];
            }

            String dbConnStr = "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + ":" + dbName;
            System.out.println("JDBC Oracle Connection String: " + dbConnStr);
            Scanner scan = new Scanner(System.in);
            System.out.print("User ID: ");
            String dbUser = scan.nextLine();
            System.out.print("Password: ");
            String dbPass = scan.nextLine();
            System.out.print("SQL Query (use a blank line or a \";\" to finish): ");
            StringBuilder sql = new StringBuilder();
            String line = "";
            boolean shouldContinue = true;
            while (shouldContinue) {
                line = scan.nextLine();
                if (line == null || line.trim().length() < 1 || line.endsWith(";")) {
                    shouldContinue = false;
                }
                sql.append(line + " ");
                if (! shouldContinue) {
                    break;
                }
            }

            if (sql.toString().trim().length() > 10 && sql.toString().toUpperCase().contains("SELECT") && sql.toString().toUpperCase().contains("FROM")) { // a valid SQL statement at least contains SELECT and FROM (> 10 characters)
                dbSql = sql.toString().replaceAll(";", "").trim();
            }

            checkOracleVersion(dbUser, dbPass, dbConnStr);
        }

    }

    private static void checkOracleVersion(String user, String pass, String connStr) {
        if (connStr != null && connStr.length() > 0 && connStr.contains(JDBC_DRIVER_TYPE) && user != null && user.length() > 0 && pass != null && pass.length() > 0) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = DriverManager.getConnection(connStr, user, pass);
                System.out.println("Query: " + dbSql);
                ps = conn.prepareStatement(dbSql);
                rs = ps.executeQuery();
                System.out.println("Result (1st column): ");
                int i = 0;
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    ++i;
                }

                System.out.println(i + " record(s) found.");
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
                catch (SQLException sqle1) {
                    sqle1.printStackTrace();
                }
                finally {
                    rs = null;
                    ps = null;
                    conn = null;
                }
            }
        }
        else {
            System.err.println("Error: please enter database login user ID and password!");
            System.exit((-1));
        }
    }
}
