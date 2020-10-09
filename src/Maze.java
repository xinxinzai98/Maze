import DefaultParam.mapDefault;
import DefaultParam.menuDefault;
import Menu.menuFrame;
import mazeCore.mazeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 主程序
 */
public class Maze {
    protected static menuFrame menu;
    protected static mazeFrame mazeframe;

    /**
     * 程式主入口
     */
    public static void main(String[] args) {
        //初始化菜单
        menu = new menuFrame();
        menu.setVisible(true);
        //为NewButton绑定监听器,并设置执行延迟,打开mazeFrame并关闭menuFrame
        menu.menupanel.newButton.addActionListener(e -> {
            ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
            service.schedule(() -> {
                menu.setVisible(false);
                //用户自定义迷宫
                try {
                    Dimension mazeDimension = userInitializeMapSize();
                    mazeframe = new mazeFrame(mazeDimension);
                    mazeframe.setVisible(true);
                } catch (Exception exception) {
                    menu.setVisible(true);
                    exception.printStackTrace();
                }
            }, menuDefault.buttonDelay, TimeUnit.MILLISECONDS);
        });
    }

    /**
     * 初始化迷宫大小
     * 弹出对话框接受用户输入
     *
     * @return 用户输入的迷宫大小
     */
    public static Dimension userInitializeMapSize() throws Exception {
        String sMazeSizeRow = JOptionPane.showInputDialog(
                "请输入迷宫行数(row):",
                mapDefault.mapSizeRow);
        int iMazeSizeRow;
        if (sMazeSizeRow != null) {
            try {
                iMazeSizeRow = Integer.parseInt(sMazeSizeRow);
            } catch (NumberFormatException e) {
                iMazeSizeRow = mapDefault.mapSizeRow;
            }
        } else {
            throw new Exception();
        }
        String sMazeSizeColumn = JOptionPane.showInputDialog(
                "请输入迷宫列数(column):",
                mapDefault.mapSizeColumn);
        int iMazeSizeColumn;
        if (sMazeSizeColumn != null) {
            try {
                iMazeSizeColumn = Integer.parseInt(sMazeSizeColumn);
            } catch (NumberFormatException e) {
                iMazeSizeColumn = mapDefault.mapSizeColumn;
            }
        } else {
            throw new Exception();
        }
        return new Dimension(iMazeSizeColumn, iMazeSizeRow);
    }
}
