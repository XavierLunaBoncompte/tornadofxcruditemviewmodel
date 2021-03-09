package com.example.demo.controller

import com.example.demo.model.Client
import com.example.demo.utilities.ClientDAO
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.SortedFilteredList
import tornadofx.observable

class ClientController: Controller() {

    val clients = SortedFilteredList(items = getAllClients().observable())

    fun save (client: Client?) {
        println("Client modificat ${client?.telefon} / ${client?.nom}")
    }

    fun putClient(oldClient : Client, newTelefon: String, newNom: String) {
        val newClient = Client(newTelefon, newNom)
        val dao = ClientDAO()
        dao.updateClient(oldClient.telefon, newClient)
        with(clients) {
            remove(oldClient)
            add(newClient)
        }
    }

    fun create (client: Client?) {
        println("Client creat ${client?.telefon} / ${client?.nom}")
    }

    fun postClient(telefon: String, nom: String) {
        val client = Client(telefon, nom)
        val dao = ClientDAO()
        dao.addClient(client)

        with(clients) {
            add(client)
        }
    }

    fun delete (client: Client?) {
        println("Client eliminat ${client?.telefon} / ${client?.nom}")
    }

    fun deleteClients(client: Client) {
        val dao = ClientDAO()
        dao.deleteClient(client.telefon)
        clients.remove(client)
        with(clients) {
            remove(client)
        }
    }

    fun getAllClients(): ObservableList<Client> = ClientDAO().readClient().observable()
}