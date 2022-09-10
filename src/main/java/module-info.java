module com.lissajouslaser {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;
    requires transitive java.sql;
    opens com.lissajouslaser to javafx.fxml;
    exports com.lissajouslaser;
}