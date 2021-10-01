package com.example.db_hw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    String password;

    @Value("${db.port}")
    String port;

    @Value("${db.host}")
    String host;

    @Value("${db.schema}")
    String schemaName;

    String tableName = "persons";
    List<String > persons = new ArrayList();



    @GetMapping("/")
    public String home(Model model){

        connectAndQuery();

        if(persons.isEmpty()){
            persons = getPersons();
        }
        model.addAttribute("persons", persons);
        return "index";
    }

    ///////////////

    public String getUrl(String host, String port){
        return "jdbc:mysql://"+host+":"+port+"/";
    }

    private void connectAndQuery(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(getUrl(host,port), username,password)){
            if(!conn.isClosed()){
                System.out.println("DB Connection ok " );
                initializeDatabase(conn);
//                // Get the rows:
                String sql = "SELECT * FROM " + tableName;
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                persons.clear();
                while (resultSet.next()){
                    String firstName = resultSet.getString("name");
                    System.out.println("Name: " + firstName);
                    persons.add(firstName);
                }
            }
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }

    private void initializeDatabase(Connection conn) throws Exception{
        // 1. make sure there exists a schema, named mydb. If not, create one
        String sql = "CREATE DATABASE IF NOT EXISTS " + schemaName;
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.execute("USE " + schemaName );

        // 2. make sure there exists a table, named persons. If not, create one:
        //    Primary key: idpersons INT AUTO_INCREMENT
        //    Column: name VARCHAR(45)
        sql = "CREATE TABLE IF NOT EXISTS `mydb2`.`persons` (\n" +
                "  `idpersons` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  PRIMARY KEY (`idpersons`));";
        statement.execute(sql); // DDL

        // 3. If there was no table named persons, then insert two rows into the new table: "Anna" and "Bent"
        // lav denne øvelse indtil kl 9.05
        sql = "INSERT IGNORE INTO " + tableName + " VALUES (1, 'Anna')";
        statement.execute(sql);
        sql = "INSERT IGNORE INTO " + tableName + " VALUES (2, 'Bent')";
        statement.execute(sql);
    }

    public List<String> getPersons() {
        return persons;
    }


}
