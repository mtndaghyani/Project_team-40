package View.ShopingMenus.ProductsAndOffsMenus;

import Interfaces.Sortable;
import View.Menu;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.HashMap;

public class SortsMenu<E extends Sortable> extends Menu {
    E controller;

    public SortsMenu(Menu parentMenu, E controller) {
        super("Filters Menu", parentMenu);
        this.controller = controller;
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowSorts());
        submenus.put(2, getSortByField());
        submenus.put(3, getShowCurrentSort());
        submenus.put(4, getDisableSort());
        setSubMenus(submenus);
    }

    public Menu getShowSorts(){
        return new Menu("Show available sorts", this) {
            @Override
            public void show() {
                for (String sort : controller.getAvailableSorts()) {
                    System.out.println(sort);
                }
                System.out.println("Enter anything to return.");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getSortByField(){
        return null;
    }

    public Menu getShowCurrentSort(){
        return null;
    }

    public Menu getDisableSort() {
        return null;
    }
}
