package controller;

import core.ScapegoatTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private ScapegoatTree<String> tree = new ScapegoatTree<>();

    @FXML
    private Label treeText;
    @FXML
    private TextField addT;
    @FXML
    private TextField removeT;
    @FXML
    private TextField findT;
    @FXML
    private TextField alphaT;
    @FXML
    private TextField containsT;

    public Controller() {
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String ident = ((Button) event.getSource()).idProperty().get();
        switch (ident) {
            case "addB":
                tree.add(addT.getText());
                repaint();
                break;
            case "removeB":
                tree.remove(removeT.getText());
                repaint();
                break;
            case "containsB":
                if (tree.contains(containsT.getText())) containsT.setText("true");
                else containsT.setText("false");
                break;
            case "findB":
                String text = treeText.getText();
                if (!text.equals(""))
                    treeText.setText(treeText.getText().replace("(!)", ""));
                treeText.setText(treeText.getText().replace(findT.getText(), findT.getText() + "(!)"));
                break;
            case "clear":
                tree.clear();
                repaint();
                break;
            case "alphaB":
                try {
                    double value = Double.parseDouble(alphaT.getText());
                    tree.setAlpha(value);
                } catch (NumberFormatException e) {
                    alphaT.setText("Wrong value");
                }
                repaint();
                break;
        }
    }

    private void repaint() {
        treeText.setText("");
        List<ScapegoatTree.Node> current = new ArrayList<>();
        current.add(tree.getRoot());
        int currPartSize = 100;
        for (int i = 0; i <= tree.findCurrentMaxDepth(); i++) {
            List<ScapegoatTree.Node> nextStep = new ArrayList<>();
            StringBuilder spaces = new StringBuilder();
            for (int j = 0; j < currPartSize / (Math.pow(2, i) + 1); j++) {
                spaces.append(" ");
            }
            treeText.setText(treeText.getText() + "\n");
            for (ScapegoatTree.Node node : current) {
                String toWrite;
                if (node == null) {
                    toWrite = " ";
                } else toWrite = node.getValue().toString();
                treeText.setText(treeText.getText() + spaces.toString() + toWrite);
                if (node != null) nextStep.add(node.getLeft());
                else nextStep.add(null);
                if (node != null) nextStep.add(node.getRight());
                else nextStep.add(null);
            }
            current = nextStep;
        }
    }
}
//Уезжает при больших значениях и setAlpha и find плохо работает с минусами
