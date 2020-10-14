import DefaultParam.mapDefault;
import DefaultParam.mazeDefault;
import DefaultParam.menuDefault;
import Menu.menuFrame;
import mazeCore.core.map;
import mazeCore.mazeFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
        menu.menupanel.loadButton.addActionListener(e -> {
            ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
            service.schedule(() -> {
                menu.setVisible(false);
                //用户读取迷宫
                try {
                    map loadMap = loadMap();
                    if (loadMap != null) {
                        mazeframe = new mazeFrame(loadMap);
                        mazeframe.setVisible(true);
                    } else {
                        menu.setVisible(true);
                    }
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

    /**
     * 打开已有地图
     *
     * @return 是否打开成功
     */
    private static map loadMap() {
        File saveDocument = new File(mazeDefault.savePath);    //文件路径（路径+文件名）
        if (!saveDocument.exists()) {    //目录不存在则创建目录并返回保存名称
            saveDocument.mkdir();
            return null;
        }
        JFileChooser fc = new JFileChooser(mazeDefault.savePath);
        int option = fc.showOpenDialog(null);//文件保存对话框
        if (option == JFileChooser.APPROVE_OPTION) {//如果选择了文件
            File file = fc.getSelectedFile();
            if (!file.exists()) {                //文件不存在
                JOptionPane.showConfirmDialog(null, "文件不存在！", "WARNING", JOptionPane.WARNING_MESSAGE);
                return null;
            } else {
                try (InputStream input = new FileInputStream(file)) {
                    try (ObjectInputStream oInput = new ObjectInputStream(input)) {
                        try {//成功读取到地图
                            return new map((map) oInput.readObject());
                        } catch (ClassNotFoundException e) {//文件内容错误
                            JOptionPane.showConfirmDialog(null, "无法读取文件:" + file.getName() + "!", "WARNING", JOptionPane.WARNING_MESSAGE);
                            return null;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
