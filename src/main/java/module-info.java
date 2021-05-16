module Telecom {
    requires java.base;
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires org.jfxtras.styles.jmetro;
    requires org.controlsfx.controls;
    requires java.desktop;

    exports launcher;
    exports controllers;

}