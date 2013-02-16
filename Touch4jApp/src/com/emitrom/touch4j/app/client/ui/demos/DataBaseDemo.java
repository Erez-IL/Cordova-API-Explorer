package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.core.handlers.storage.DatabaseHandler;
import com.emitrom.pilot.device.client.core.handlers.storage.SQLTransactionHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.pilot.device.client.storage.Database;
import com.emitrom.pilot.device.client.storage.SQLError;
import com.emitrom.pilot.device.client.storage.SQLResultSet;
import com.emitrom.pilot.device.client.storage.SQLTransaction;
import com.emitrom.pilot.device.client.storage.Storage;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Spacer;
import com.emitrom.touch4j.client.ui.TextArea;
import com.emitrom.touch4j.client.ui.ToolBar;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;

public class DataBaseDemo extends DemoPanel {

    private static final DataBaseDemo INSTANCE = new DataBaseDemo();
    private Panel sqlPanel;
    private TextArea sqlTextArea;
    private Database db = Storage.get().createDatabase("demodb", "1.0", "Demo DB", 1000000);

    public static DataBaseDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new DataBaseDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel buttonPanel;
    private TextArea logTextArea;

    private DataBaseDemo() {

        sqlTextArea = new TextArea();
        sqlTextArea.getElement().getStyle().setFontSize(15, Unit.PX);
        sqlTextArea.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
        sqlTextArea.getElement().getStyle().setFontWeight(FontWeight.BOLD);
        sqlTextArea.setValue(Core.getSql());
        sqlTextArea.setHeight(5000);

        sqlPanel = Core.createModalPanel();
        sqlPanel.add(sqlTextArea);

        ToolBar toolBar = new ToolBar();
        toolBar.setDocked(Dock.BOTTOM);
        toolBar.add(new Spacer());
        toolBar.add(new Button("executeSQL", UI.CONFIRM, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                sqlPanel.hide();
                executeSQL(sqlTextArea.getValue());
            }
        }));
        toolBar.add(new Button("Clear", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                sqlTextArea.setValue("");
                sqlTextArea.setPlaceHolder("Enter SQL here");
            }
        }));
        toolBar.add(new Button("Cancel", UI.DECLINE, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                sqlPanel.hide();
            }
        }));
        sqlPanel.add(toolBar);

        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("Database");
        panel.add(fieldSet);

        fieldSet = new FieldSet("Log");
        logTextArea = new TextArea();
        logTextArea.setMaxRows(5);
        // logTextArea.setReadOnly(true);
        fieldSet.add(logTextArea);
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("Enter SQL", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                sqlPanel.show();
            }
        });
        b.setMargin(10);
        buttonPanel.add(b);

        b = new Button("Clear Log", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                logTextArea.setValue("");
            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        this.add(panel);

    }

    private void executeSQL(final String sql) {
        if (sql == null || sql.isEmpty() || sql.equalsIgnoreCase("")) {
            Notification.get().alert("Database", "SQL statement can't be empty");
        } else {
            db.transaction(new DatabaseHandler() {

                @Override
                public void onSuccess() {
                    log("Transaction succeeded");
                }

                @Override
                public void onError(SQLError error) {
                    log("Transaction failed :" + error.getMessage());
                }

                @Override
                public void execute(SQLTransaction tx) {
                    String[] statements = sql.trim().split(";");
                    for (String statement : statements) {
                        if (statement.trim().length() != 0) {
                            tx.executeSql(statement, new SQLTransactionHandler() {
                                @Override
                                public void onSuccess(SQLTransaction tx, SQLResultSet results) {
                                    processResult(tx, results);
                                }

                                @Override
                                public void onError(SQLError error) {
                                    log("Statement error : " + error.getMessage());
                                }
                            });
                        }
                    }

                }
            });
        }
    }

    private void log(String statement) {
        String currentLog = logTextArea.getValue();
        currentLog += statement + "\r\n";
        logTextArea.setValue(currentLog);
    }

    private void processResult(final SQLTransaction tx, final SQLResultSet results) {
        int length = results.getRows().getLength();
        if (length > 0) {

        }
        if (results.getRowAffected() >= 0) {
            log(results.getRowAffected() + " rows affected");
        } else {
            log(" no rows affected");
        }

    }
}
