package model;

public class Item {

    public String label;
    public String detail;
    public String filePath;

    public Item(String label, String color, String filePath) {
        this.label = label;
        this.detail = color;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Item: " + label + "\nDetalhe: " + detail
                + "\nPaht: " + filePath;
    }
}
