package com.example.db_hw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MyDB {
    String username = "root";
    String password = "nwk52xAE";
    String url = "jdbc:mysql://54.163.23.187:3306/";
    String schemaName = "mydb2";
    String tableName = "persons";
    List<String > persons = new ArrayList();



    public MyDB(){

    }



}
