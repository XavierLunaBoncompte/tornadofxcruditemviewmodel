package com.example.demo.utilities


import com.example.demo.model.Client
import java.sql.ResultSet
import java.sql.Timestamp

class ClientDAO {
    fun addClient (client: Client) {
        val connection = Database().connection
        val preparedStatement = connection.prepareStatement("INSERT INTO client(telefon, nom) VALUES (?, ?)")
        preparedStatement.setString(1, client.telefon)
        preparedStatement.setString(2, client.nom)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }

    fun readClient(): List<Client> {
        val connection = Database().connection
        val resultSet = connection.createStatement().executeQuery("SELECT * FROM client")
        val clientList = ArrayList<Client>()
        while (resultSet.next()) {
            val telefon = resultSet.getString("telefon")
            val nom = resultSet.getString("nom")
            clientList += Client(telefon, nom)
        }
        resultSet.close()
        connection.close()
        return clientList
    }

    fun updateClient(telefon : String, client : Client): Client {
        val connection = Database().connection
        var param = ""
        val paramNom = ", nom = ? "
        var optinalParamIndexNom = 2
        if (client.nom.isNotEmpty()) param = paramNom
        val preparedStatement = connection.prepareStatement("UPDATE client set telefon = ? $param WHERE telefon = ?")
        preparedStatement.setString(1, client.telefon)
        if (param.isNotEmpty()) {
            preparedStatement.setString(optinalParamIndexNom, client.nom)
            optinalParamIndexNom = optinalParamIndexNom.inc()
        }
        preparedStatement.setString(optinalParamIndexNom, telefon)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
        return client
    }

    fun deleteClient(telefon: String) {
        val timestamp = Timestamp(System.currentTimeMillis())
        val connection = Database().connection
        val preparedStatement = connection.prepareStatement("DELETE FROM client WHERE telefon = ?")
        //preparedStatement.setString(1, timestamp.toString())
        preparedStatement.setString(1, telefon)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }
}