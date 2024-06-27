package ren.lawliet.mc.mcalg;

import java.util.Random;

/**
 * @author Coaixy
 * @createTime 2024-06-27
 * @packageName ren.lawliet.mc.mcalg
 */

public class maze {
    // 0 通路 1 路障 2 起点 3 终点
    public static int[][] generateMaze(int rows, int cols, double obstaclePercentage) {
        // 初始化迷宫，全部设为路障
        int[][] maze = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = 1;
            }
        }
        int startRow = 0;
        int startCol = 0;
        int endRow = rows - 1;
        int endCol = cols - 1;

        // 设置起点和终点
        maze[startRow][startCol] = 2;
        maze[endRow][endCol] = 3;

        // 创建从起点到终点的路径
        createPath(maze, startRow, startCol, endRow, endCol);


        // 随机设置障碍物
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] != 6) {
                    if (maze[i][j] != 2 && maze[i][j] != 3 && random.nextDouble(1) < obstaclePercentage) {
                        maze[i][j] = 1;
                    } else if (maze[i][j] != 2 && maze[i][j] != 3) {
                        maze[i][j] = 0;
                    }
                }
            }
        }

        return maze;
    }

    private static void createPath(int[][] maze, int startRow, int startCol, int endRow, int endCol) {
        // 使用简单的贪心算法创建路径
        int currentRow = startRow;
        int currentCol = startCol;
        Random random = new Random();
        while (currentRow != endRow || currentCol != endCol) {
            double seed = random.nextDouble(1);
            if (seed >= 0.5 && currentRow < endRow) {
                currentRow++;
            } else if (currentCol < endCol) {
                currentCol++;
            }
            maze[currentRow][currentCol] = 6;
        }
        maze[endRow][endCol] = 3;
    }
}
