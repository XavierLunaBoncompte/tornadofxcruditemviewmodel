package com.example.demo.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Client(telefon: String? = null, nom: String? = null) {
    val telefonProperty = SimpleStringProperty(this, "telefon", telefon)
    var telefon by telefonProperty

    val nomProperty = SimpleStringProperty(this, "nom", nom)
    var nom by nomProperty
}

class ClientModel(client: Client) : ItemViewModel<Client>(client) {
    val telefon = bind(Client::telefonProperty)
    val nom = bind(Client::nomProperty)

    override fun onCommit() {
        super.onCommit()
        println("onCommit invoked")
    }
    override fun onCommit(commits: List<Commit>) {
        // The println will only be called if findChanged is not null
        commits.findChanged(telefon)?.let { println("Telefon changed from ${it.second} to ${it.first}")}
        commits.findChanged(nom)?.let { println("First-Name changed from ${it.second} to ${it.first}")}
    }

    private fun <T> List<Commit>.findChanged(ref: Property<T>): Pair<T, T>? {
        val commit = find { it.property == ref && it.changed}
        return commit?.let { (it.newValue as T) to (it.oldValue as T) }
    }
}