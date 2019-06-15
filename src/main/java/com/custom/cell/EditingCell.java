package com.custom.cell;

import com.abs.ConfigNode;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

/**
 * 重写表格提交事件
 * @param <T>
 */
public class EditingCell<T extends ConfigNode> extends TableCell<T, String> {

    private TextField textField;

    public EditingCell() {}

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty()
                .addListener(
                        (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                            if (!arg2) {
                                commitEdit(textField.getText());
                            }
                        });
    }

}
