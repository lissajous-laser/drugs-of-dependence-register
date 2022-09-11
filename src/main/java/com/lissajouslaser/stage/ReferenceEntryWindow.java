package com.lissajouslaser.stage;

import javafx.stage.Stage;

/**
 * A window that creates a table entry
 * that is referenced by another table.
 */
abstract class ReferenceEntryWindow extends Stage {
    static final int SUBWINDOW_A_WIDTH = 480;
    static final int SUBWINDOW_A_HEIGHT = 200;

    ReferenceEntryWindow() {
        super();
    }
}
