package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.core.handlers.file.DirectoryHandler;
import com.emitrom.pilot.device.client.core.handlers.file.FileReaderHandler;
import com.emitrom.pilot.device.client.core.handlers.file.FileWriterCreationHandler;
import com.emitrom.pilot.device.client.core.handlers.file.FileWriterHandler;
import com.emitrom.pilot.device.client.core.handlers.file.LocalFileSystemHandler;
import com.emitrom.pilot.device.client.file.DirectoryEntry;
import com.emitrom.pilot.device.client.file.FileError;
import com.emitrom.pilot.device.client.file.FileReader;
import com.emitrom.pilot.device.client.file.FileSystem;
import com.emitrom.pilot.device.client.file.FileWriter;
import com.emitrom.pilot.device.client.file.Flags;
import com.emitrom.pilot.device.client.file.LocalFileSystem;
import com.emitrom.pilot.device.client.file.LocalFileSystemType;
import com.emitrom.pilot.device.client.notification.Notification;
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
import com.emitrom.touch4j.client.ui.Text;
import com.emitrom.touch4j.client.ui.TextArea;
import com.emitrom.touch4j.client.ui.ToolBar;

public class FileDemo extends DemoPanel {

    private static final FileDemo INSTANCE = new FileDemo();

    public static FileDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new FileDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel fileContentPanel;
    private TextArea fileContentTextArea = new TextArea();
    private Panel buttonPanel;
    private Text<String> text;

    private FileDemo() {

        fileContentTextArea.setPlaceHolder("Write file content here");
        fileContentPanel = Core.createModalPanel();
        fileContentPanel.add(fileContentTextArea);

        ToolBar toolbar = new ToolBar();
        toolbar.setDocked(Dock.BOTTOM);
        toolbar.add(new Spacer());
        Button saveButton = new Button("Create", UI.CONFIRM, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                createFile();
            }
        });
        toolbar.add(saveButton);
        toolbar.add(new Button("Cancel", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                fileContentPanel.hide();
            }
        }));
        fileContentPanel.add(toolbar);

        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("File");
        panel.add(fieldSet);

        fieldSet = new FieldSet();

        text = new Text<String>();
        text.setValue("readme.txt");
        text.setLabelWidth(120);
        text.setLabel("File Name");
        fieldSet.add(text);
        panel.add(fieldSet);

        buttonPanel = Core.createHBoxPanel();

        Button b = new Button("Load", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                loadFile();
            }
        });
        b.setMargin(5);
        buttonPanel.add(b);

        b = new Button("Create", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                fileContentPanel.show();
            }
        });
        b.setMargin(5);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        this.add(panel);

    }

    private void loadFile() {
        final String fileName = text.getValue();
        LocalFileSystem.requestFileSystem(LocalFileSystemType.PERSISTENT, new LocalFileSystemHandler() {
            @Override
            public void onSuccess(FileSystem fileSystem) {
                Flags flags = new Flags(false, false);
                fileSystem.getRoot().getFile(fileName, flags, new DirectoryHandler() {

                    @Override
                    public void onSuccess(DirectoryEntry entry) {
                        FileReader fileReader = new FileReader();
                        fileReader.onLoad(new FileReaderHandler() {
                            @Override
                            public void onEvent(FileReader reader) {
                                String result = reader.getResult();
                                if (result == null) {
                                    result = fileName + " is empty";
                                }
                                Notification.get().alert("File content ", result);
                            }
                        });
                        fileReader.onError(new FileReaderHandler() {
                            @Override
                            public void onEvent(FileReader reader) {
                                Notification.get().alert("Error reading file");
                            }
                        });
                        fileReader.readAsText(entry);

                    }

                    @Override
                    public void onError(FileError error) {
                        Notification.get().alert("File Open", "The file " + fileName + " does not exist.");

                    }
                });
            }

            @Override
            public void onError(FileError error) {
                Notification.get().alert("Filesystem error", "Error code : " + error.getCode());
            }
        });
    }

    private void createFile() {
        final String fileName = text.getValue();
        if (fileName == null || fileName.isEmpty() || fileName.equalsIgnoreCase("")) {
            Notification.get().alert("File", "Filecontent can't be empty");
        } else {
            LocalFileSystem.requestFileSystem(LocalFileSystemType.PERSISTENT, new LocalFileSystemHandler() {
                @Override
                public void onSuccess(FileSystem fileSystem) {
                    Flags flags = new Flags();
                    flags.setCreate(true);
                    flags.setExclusive(false);

                    fileSystem.getRoot().getFile(fileName, flags, new DirectoryHandler() {

                        @Override
                        public void onSuccess(DirectoryEntry entry) {
                            entry.createWriter(new FileWriterCreationHandler() {
                                @Override
                                public void onSuccess(FileWriter writer) {
                                    writer.onWrite(new FileWriterHandler() {
                                        @Override
                                        public void onEvent(FileWriter writer) {
                                            Notification.get().alert(fileName + " successfully saved !");
                                            fileContentPanel.hide();
                                        }
                                    });
                                    writer.onError(new FileWriterHandler() {
                                        @Override
                                        public void onEvent(FileWriter writer) {
                                            Notification.get().alert("File", "Error while writing file : " + fileName);
                                        }
                                    });
                                    writer.write("Hello World");
                                }

                                @Override
                                public void onError(FileError error) {
                                    Notification.get().alert("File",
                                                    "FileWriter creation error : " + error.getCode().getValue());
                                }
                            });

                        }

                        @Override
                        public void onError(FileError error) {
                            Notification.get().alert("File creation error");
                        }
                    });
                }

                @Override
                public void onError(FileError error) {
                    Notification.get().alert("FileSystem error");

                }
            });

        }

    }
}
