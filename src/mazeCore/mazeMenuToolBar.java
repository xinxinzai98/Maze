package mazeCore;

import DefaultParam.mazeDefault;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * 面板顶栏工具
 */
public class mazeMenuToolBar extends JMenuBar {
    //文件菜单栏
    public JMenu FileMenu;
    public JMenuItem newFile;//新建
    public JMenuItem openFile;
    public JMenuItem saveFile;
    public JMenuItem exit;
    //编辑菜单栏
    public JMenu EditMenu;
    //帮助菜单栏
    public JMenu HelpMenu;

    public mazeMenuToolBar() {
        setForeground(mazeDefault.mazePanelColor);
        //TODO 添加上菜单栏内容
        FileMenu = createFileMenu();
        add(FileMenu);


        setVisible(true);
    }

    /**
     * 创建FileMenu
     *
     * @return FileMenu
     */
    private JMenu createFileMenu() {
        JMenu menu = new JMenu("文件(F)");
        menu.setMnemonic(KeyEvent.VK_F);    //设置快速访问符

        newFile = new JMenuItem("新建(N)", KeyEvent.VK_N);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu.add(newFile);

        openFile = new JMenuItem("打开(O)", KeyEvent.VK_O);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menu.add(openFile);

        saveFile = new JMenuItem("保存(S)", KeyEvent.VK_S);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menu.add(saveFile);

        menu.addSeparator();
        exit = new JMenuItem("退出(E)", KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menu.add(exit);
        return menu;
    }

    /**
     * 创建EditMenu
     *
     * @return EditMenu
     */
    private JMenu createEditMenu() {
        return new JMenu();
    }

    /**
     * 创建HelpMenu
     *
     * @return HelpMenu
     */
    private JMenu createHelpMenu() {
        return new JMenu();
    }
}
