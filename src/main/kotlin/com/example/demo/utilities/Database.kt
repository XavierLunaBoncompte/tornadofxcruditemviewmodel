package com.example.demo.utilities

import java.sql.Connection
import java.sql.DriverManager

class Database {
    var connection: Connection

    init {
        Class.forName("com.mysql.jdbc.Driver")
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pizzeria","admins","admin123");
    }
}