package controller;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Item;
import model.ItemList;
import view.ViewItemGui;
import view.ItemListGui;

public class Controller {

    private ItemListGui gui = null;
    private ViewItemGui view = null;
    private ItemList list = null;

    public Controller() {
        list = new ItemList();

        // path => resources/orchid-figures.csv
        list.loadItemList("src/resources/orchid-figures.csv");

        view = new ViewItemGui();
        view.setVisible(true);

        gui = new ItemListGui();
        gui.act(new ActSearch());
        gui.actItemSelection(new ActTable());
        gui.setVisible(true);
    }

    class ActSearch implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ev) {
            updateList(gui.search.getText() + ev.getKeyChar());
        }

        @Override
        public void keyPressed(KeyEvent ev) {
        }

        @Override
        public void keyReleased(KeyEvent ev) {
            if (ev.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                updateList(gui.search.getText());
            }
        }
    }

    class ActTable implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent ev) {
            int column = 0;
            int row = gui.table.getSelectedRow();
            String label = gui.table.getModel().getValueAt(row, column).toString();

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(gui,
                    "Item selecionado: " + label + "\nConfirmar?",
                    "Enviar Sinal", dialogButton);
            if (dialogResult == 0) {
                displayData(label);
            }
        }

        @Override
        public void mousePressed(MouseEvent ev) {
        }

        @Override
        public void mouseReleased(MouseEvent ev) {
        }

        @Override
        public void mouseEntered(MouseEvent ev) {
        }

        @Override
        public void mouseExited(MouseEvent ev) {
        }
    }

    public boolean updateList(String text) {
        if (text.equals("")) {
            return false;
        }
        Map<String, Item> itemsFound = list.search(text);
        ArrayList<String> labels = new ArrayList<>();

        itemsFound.keySet().stream().filter(
                (k) -> (k != null)).forEachOrdered((k) -> {
                    labels.add(itemsFound.get(k).label);
                });

        Collections.sort(labels);
        String[][] elements = new String[labels.size()][2];
        for (int i = 0; i < labels.size(); i++) {
            elements[i][0] = itemsFound.get(labels.get(i)).label;
            elements[i][1] = itemsFound.get(labels.get(i)).detail;
        }

        gui.table.setModel(new javax.swing.table.DefaultTableModel(elements,
                new String[]{"Itens Encontrados", "Cor"}
        ) {
            boolean[] canEdit = new boolean[]{false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        gui.infoItem.setText("Itens encontrados: "
                + String.valueOf(labels.size()));

        return true;
    }

    private void displayData(String label) {
        Item item = list.itemPath(label);
        JOptionPane.showMessageDialog(gui, item.toString());
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.filePath)
                .getImage().getScaledInstance(view.output.getWidth(),
                        view.output.getHeight(), Image.SCALE_DEFAULT));
        view.output.setIcon(imageIcon);
        view.output.setText("");
        System.out.println(item.toString());
    }
}
