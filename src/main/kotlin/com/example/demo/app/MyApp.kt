package com.example.demo.app

import com.example.demo.view.MainView
import com.example.demo.view.PersonEditor
import tornadofx.App

class MyApp: App(PersonEditor::class, Styles::class)