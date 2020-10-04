package mazeCore;

import DefaultParam.mazeDefault;
import mazeCore.core.generateMaze;
import mazeCore.core.latticePriority;
import mazeCore.core.map;
import mazeCore.core.solution;

import javax.swing.*;
import java.awt.*;

/**
 * 绘制迷宫面板
 */
public class mazePanel extends JPanel {
    //保存迷宫大小变量
    private Dimension mazeDimension;
    //内置待绘制迷宫指针
    private map maze;
    //内置题解
    private solution sol;

    public mazePanel(Dimension mazeDimension) {
        this.mazeDimension = mazeDimension;
        //面板基础设置
        setBackground(mazeDefault.mazePanelColor);
        setPreferredSize(new Dimension(mazeDimension.width * mazeDefault.mazeLatticeSize + mazeDefault.mazeFrameOffset
                , mazeDimension.height * mazeDefault.mazeLatticeSize + mazeDefault.mazeFrameOffset));
        //初始化地图
        maze = new generateMaze(mazeDimension.height, mazeDimension.width).getMaze("B");
        sol = new solution(maze);
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        paintMaze(graphics);
        paintBorad(graphics);
        //paintLattice(graphics);
    }

    /**
     * 格子可视化
     *
     * @param graphics 画笔
     */
    private void paintLattice(Graphics graphics) {
        for (int i = 0; i < this.getHeight(); i += mazeDefault.mazeLatticeSize) {
            graphics.setColor(mazeDefault.mazeLineColor);
            graphics.drawLine(0, i, getWidth(), i);
        }
        for (int i = 0; i < this.getWidth(); i += mazeDefault.mazeLatticeSize) {
            graphics.setColor(mazeDefault.mazeLineColor);
            graphics.drawLine(i, 0, i, getHeight());
        }
    }

    /**
     * 地图边框可视化
     *
     * @param graphics 画笔
     */
    private void paintBorad(Graphics graphics) {
        graphics.setColor(mazeDefault.mazeLineColor);
        graphics.drawLine(0, 0, getWidth(), 0);
        graphics.drawLine(0, getHeight(), getWidth(), getHeight());
        graphics.drawLine(0, 0, 0, getHeight());
        graphics.drawLine(getWidth(), 0, getWidth(), getHeight());
    }

    /**
     * 迷宫可视化
     *
     * @param graphics 画笔
     */
    private void paintMaze(Graphics graphics) {
        for (int i = 1; i < maze.getMapSizeRow() - 1; i++) {
            for (int j = 1; j < maze.getMapSizeColumn() - 1; j++) {
                if (maze.getLattice(i, j).isPlayerPassed()) {
                    graphics.setColor(mazeDefault.mazePlayerPassedColor);
                    graphics.fillRect((j - 1) * mazeDefault.mazeLatticeSize,
                            (i - 1) * mazeDefault.mazeLatticeSize,
                            mazeDefault.mazeLatticeSize, mazeDefault.mazeLatticeSize);
                }
                if (maze.getLatticeState(i, j) != latticePriority.UNTOUCHED) {
                    if (maze.getLatticeState(i, j) == latticePriority.WALL) {
                        graphics.setColor(mazeDefault.mazeWallColor);
                        graphics.fillRect((j - 1) * mazeDefault.mazeLatticeSize,
                                (i - 1) * mazeDefault.mazeLatticeSize,
                                mazeDefault.mazeLatticeSize, mazeDefault.mazeLatticeSize);
                    } else if (maze.getLatticeState(i, j) == latticePriority.RIGHTWARD) {
                        graphics.setColor(mazeDefault.mazeRightPathColor);
                        graphics.fillRect((j - 1) * mazeDefault.mazeLatticeSize,
                                (i - 1) * mazeDefault.mazeLatticeSize,
                                mazeDefault.mazeLatticeSize, mazeDefault.mazeLatticeSize);
                    } else if (maze.getLatticeState(i, j) == latticePriority.TOUCHABLE && isOver()) {
                        graphics.setColor(mazeDefault.mazeTouchableColor);
                        graphics.fillRect((j - 1) * mazeDefault.mazeLatticeSize,
                                (i - 1) * mazeDefault.mazeLatticeSize,
                                mazeDefault.mazeLatticeSize, mazeDefault.mazeLatticeSize);
                    }
                }
                if (sol.isPlayer(i, j)) {
                    graphics.setColor(mazeDefault.mazePlayerColor);
                    graphics.fillOval((j - 1) * mazeDefault.mazeLatticeSize,
                            (i - 1) * mazeDefault.mazeLatticeSize,
                            mazeDefault.mazeLatticeSize, mazeDefault.mazeLatticeSize);
                }
                if (maze.isStart(i, j) || maze.isEnd(i, j)) {
                    graphics.setColor(mazeDefault.mazeSEColor);
                    graphics.fillOval((j - 1) * mazeDefault.mazeLatticeSize,
                            (i - 1) * mazeDefault.mazeLatticeSize,
                            mazeDefault.mazeLatticeSize, mazeDefault.mazeLatticeSize);
                }
            }
        }
    }

    /**
     * 迷宫解A
     */
    public void getSolutionA() {
        maze = sol.solutionA();
    }

    /**
     * 迷宫解A的小球可视化
     */
    public void paintSolutionA() {
        //重新定位
        sol.relocatePlayer();
        maze = sol.getSolutionMap();
        repaint();
    }

    /**
     * 画原始迷宫
     */
    public void paintOrigin() {
        sol.resetSolution();
        sol.resetPlayerLocation();//重置小球位置
        maze = sol.solutionA();
        repaint();
    }

    /**
     * 小球到达终点动画结束
     *
     * @return 小球是否到达终点
     */
    public boolean isOver() {
        return sol.isPlayer(maze.getMapEndRow(), maze.getMapEndColumn());
    }

    /**
     * 新建地图A
     */
    public void newMapA() {
        maze = new generateMaze(mazeDimension.height, mazeDimension.width).getMaze("A");
        sol = new solution(maze);
        repaint();
    }

    /**
     * 新建地图B
     */
    public void newMapB() {
        maze = new generateMaze(mazeDimension.height, mazeDimension.width).getMaze("B");
        sol = new solution(maze);
        repaint();
    }

    public long getSolutionTime() {
        return sol.getSolutionTime();
    }

    public int getCount() {
        return sol.getCount();
    }
}
