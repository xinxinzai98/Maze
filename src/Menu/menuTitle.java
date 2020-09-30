package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;


/**
 * 主菜单中的Title类
 * 用于展示
 */
public class menuTitle extends JLabel {
    public menuTitle() {
        setIcon(menuDefault.titleIcon);
        setBounds((menuDefault.menuWidth - menuDefault.titleWidth) / 2,
                menuDefault.titleOffset,
                menuDefault.titleWidth, menuDefault.titleHeight);
    }
}
