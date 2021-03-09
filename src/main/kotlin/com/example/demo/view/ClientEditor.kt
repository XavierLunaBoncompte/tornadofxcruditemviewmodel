package com.example.demo.view

import com.example.demo.controller.ClientController
import com.example.demo.model.Client
import com.example.demo.model.ClientModel
import com.example.demo.utilities.PopupDialog
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.scene.layout.BorderPane
import javafx.stage.StageStyle
import tornadofx.*

class ClientEditor : View("Client Editor") {
    override val root: BorderPane = BorderPane()
    private var clients: ObservableList<Client>
    private val model: ClientModel = ClientModel(Client())
    private val clientController: ClientController by inject()

    private val newTelefonString = SimpleStringProperty()
    private val newNomString = SimpleStringProperty()

    init {
        clients = clientController.getAllClients()
        with(root) {
            center {
                tableview(clients) {
                    column("Telefon", Client::telefonProperty)
                    column("Nom", Client::nomProperty)

                    // Update the person inside the view model on selection change
                    model.rebindOnChange(this) { selectedPerson ->
                        item = selectedPerson ?: Client()
                    }

                    bindSelected(model)
                }
            }

            right {
                form {
                    fieldset("GESTIÓ CLIENTS") {
                        field("Telefon") {
                            textfield(model.telefon)
                        }
                        field("Nom") {
                            textfield(model.nom)
                        }
                        hbox {
                            button("CREA") {
                                enableWhen(model.dirty)
                                action {
                                    create()
                                }
                            }
                            button("MODIFICA") {
                                enableWhen(model.dirty)
                                action {
                                    save()
                                }
                            }
                            button("ELIMINA") {
                                //enableWhen(model.dirty)
                                action {
                                    delete()
                                }
                            }
                            button("RESET").action {
                                model.rollback()
                            }
                        }

                    }
                }
            }
        }
    }

    private fun save() {
        // Flush changes from the text fields into the model
        model.commit()

        // The edited person is contained in the model
        val client = model.item

        clientController.putClient(
                client,
                model.telefon.value,
                model.nom.value
        )

        // A real application would persist the person here
        clientController.save(client)
        //clients = clientController.getAllClients()
    }

    private fun delete() {
        // Flush changes from the text fields into the model
        model.commit()

        // The edited person is contained in the model
        val client = model.item

        clientController.deleteClients(client)

        with(clients) {
            remove(client)
        }

        find<PopupDialog>(mapOf("message" to "Client eliminat")).openModal(stageStyle = StageStyle.UTILITY)

        // A real application would persist the person here
        clientController.delete(client)
        //clients = clientController.getAllClients()
    }

    private fun create() {
        // Flush changes from the text fields into the model
        model.commit()

        // The edited person is contained in the model
        val client = model.item

        clientController.postClient(model.telefon.value, model.nom.value)

        with(clients) {
            add(client)
        }

        find<PopupDialog>(params = mapOf("message" to "New Client Added")).openModal(stageStyle = StageStyle.UTILITY)


        // A real application would persist the person here
        clientController.create(client)
        //clients = clientController.getAllClients()
    }
}