package edu.gcu.gcuadmin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.models.ExpandableMenuModel;

/**
 * Created by Shrivats on 4/5/2017.
 */

public class GenerateMenuItems {
    private ArrayList<ExpandableMenuModel> menuModel;
    private ArrayList<String> subMenu;



    private HashMap<ExpandableMenuModel, List<String>> subMenuMap;


    public HashMap<ExpandableMenuModel, List<String>> getSubMenuMap() {
        return subMenuMap;
    }

    private void initMenu() {
        String menu[] = {"Home", "Admissions", "Hostel"};
        int menu_icon[] = {R.drawable.ic_nav_home, R.drawable.ic_login_user,R.drawable.ic_nav_students};
        for(int i = 0; i < menu.length ; i++){
            ExpandableMenuModel eModel = new ExpandableMenuModel();
            eModel.setIconName(menu[i]);
            eModel.setIconImg(menu_icon[i]);
            menuModel.add(eModel);
        }
        initSubMenu(menuModel);
    }

    private void initSubMenu(ArrayList<ExpandableMenuModel> menuModel) {
        String submenu[][] = {{},{"Enquiry sources", "Year-wise Enquiry", "Course-wise enquiry"},
                {}};
        int i = 0;
        for (String[] aSubmenu : submenu) {
            subMenu = new ArrayList<>();
            Collections.addAll(subMenu, aSubmenu);
            subMenuMap.put(menuModel.get(i++), subMenu);
        }

    }

    public GenerateMenuItems(){
        menuModel = new ArrayList<>();
        subMenu = new ArrayList<>();
        subMenuMap = new HashMap<>();
        initMenu();
    }



    public ArrayList<ExpandableMenuModel> getMenuModel() {
        return menuModel;
    }
}
