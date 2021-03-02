package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.model.Person
import com.example.demo.model.PersonModel
import javafx.collections.ObservableList
import javafx.scene.layout.BorderPane
import tornadofx.*

class PersonEditor : View("Person Editor") {
    override val root: BorderPane = BorderPane()
    private var persons: ObservableList<Person>
    private val model: PersonModel = PersonModel(Person())
    private val personController: PersonController by inject()

    init {
        persons = personController.getAllPersons()
        with(root) {
            center {
                tableview(persons) {
                    column("Name", Person::nameProperty)
                    column("Title", Person::titleProperty)

                    // Update the person inside the view model on selection change
                    model.rebindOnChange(this) { selectedPerson ->
                        item = selectedPerson ?: Person()
                    }

                    //bindSelected(model)
                }
            }

            right {
                form {
                    fieldset("Edit person") {
                        field("Name") {
                            textfield(model.name)
                        }
                        field("Title") {
                            textfield(model.title)
                        }
                        button("Save") {
                            enableWhen(model.dirty)
                            action {
                                save()
                            }
                        }
                        button("Reset").action {
                            model.rollback()
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
        val person = model.item

        // A real application would persist the person here
        personController.save(person)
    }

}