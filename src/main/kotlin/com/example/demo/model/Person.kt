package com.example.demo.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Person(name: String? = null, title: String? = null) {
    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val titleProperty = SimpleStringProperty(this, "title", title)
    var title by titleProperty
}

class PersonModel(person: Person) : ItemViewModel<Person>(person) {
    val name = bind(Person::nameProperty)
    val title = bind(Person::titleProperty)

    override fun onCommit() {
        super.onCommit()
        println("onCommit invoked")
    }
    override fun onCommit(commits: List<Commit>) {
        // The println will only be called if findChanged is not null
        commits.findChanged(name)?.let { println("First-Name changed from ${it.second} to ${it.first}")}
        commits.findChanged(title)?.let { println("Last-Name changed from ${it.second} to ${it.first}")}
    }

    private fun <T> List<Commit>.findChanged(ref: Property<T>): Pair<T, T>? {
        val commit = find { it.property == ref && it.changed}
        return commit?.let { (it.newValue as T) to (it.oldValue as T) }
    }
}