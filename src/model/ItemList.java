package model;

import controller.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemList {

    private final Map<String, Item> list;
    private final ArrayList<Item> item;

    public ItemList() {
        list = new HashMap<>();
        item = new ArrayList<>();
    }

    public Map<String, Item> search(String text) {
        Map<String, Item> itemsFound = new HashMap<>();
        for (int i = 0; i < item.size(); i++) {
            text = text.toLowerCase();
            String label = item.get(i).label;
            if (label.toLowerCase().contains(text)) {
                itemsFound.put(label, item.get(i));
            }
        }
        return itemsFound;
    }

    public void loadItemList(String path) {
        ArrayList<String> listTemp = Util.loadFile(path);
        for (int i = 0; i < listTemp.size(); i++) {
            String[] s = listTemp.get(i).split(Util.DELIM);
            if (s.length == 3) {
                Item e = new Item(s[0], s[1], s[2]);
                e.toString();
                list.put(s[0], e);
                item.add(e);
            }
        }
    }

    public Item itemPath(String label) {
        return list.get(label);
    }
}
