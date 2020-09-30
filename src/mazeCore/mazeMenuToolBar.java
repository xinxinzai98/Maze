package mazeCore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * 面板顶栏工具
 */
public class mazeMenuToolBar extends JMenuBar {
    public mazeMenuToolBar() {
        //TODO 添加上菜单栏内容
        add(createFileMenu());
        setVisible(true);
    }

    /**
     * @return
     */
    private JMenu createFileMenu() {
        JMenu menu = new JMenu("文件(F)");
        menu.setMnemonic(KeyEvent.VK_F);    //设置快速访问符
        JMenuItem item = new JMenuItem("新建(N)", KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu.add(item);
        item = new JMenuItem("打开(O)", KeyEvent.VK_O);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menu.add(item);
        item = new JMenuItem("保存(S)", KeyEvent.VK_S);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menu.add(item);
        menu.addSeparator();
        item = new JMenuItem("退出(E)", KeyEvent.VK_E);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menu.add(item);
        return menu;
    }

    /**
     * @return
     */
    private JMenu createEditMenu() {
        return new JMenu();
    }

    /**
     * @return
     */
    private JMenu createHelpMenu() {
        return new JMenu();
    }
}
