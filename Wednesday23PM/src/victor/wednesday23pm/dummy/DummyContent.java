package victor.wednesday23pm.dummy;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import victor.wednesday23pm.GetWebPage;
import victor.wednesday23pm.R;


public class DummyContent {

    public static class DummyItem {

        public String id;
        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            if (id == "2")
            {
                content = content + " concat";
            }
            this.content = content;
            
        }

        @Override
        public String toString() {
            return content;
        }
    }

    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    // VR 10-11-12 : it's hard coded, can we do something regarding this ?
    static {
        addItem(new DummyItem("1", "Item 1"));
        addItem(new DummyItem("2", "ITem 2"));
        addItem(new DummyItem("3", "Item 3"));//(String) R.string.item_3)); // VR TODO 10.11.12
        addItem(new DummyItem("4", "Item GetHttp 4"));
        addItem(new DummyItem("5", "Location"));
        addItem(new DummyItem("6", "Sql"));
        addItem(new DummyItem("7", "Nfc"));
        addItem(new DummyItem("8", "Take Picture"));
        addItem(new DummyItem("9", "Map"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

}
