package com.xs.medium.lifegame;

/**
 * @author xs
 * create time:2020-04-14 12:36:22
 */
public class Solution {
    public int[] xAround = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
    public int[] yAround = new int[]{1,1,1,0,0,-1,-1,-1};
    public void gameOfLife(int[][] board) {
        int[][] result = new int[board.length][board[0].length];
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                result[i][j] = this.nextStatus(board[i][j], this.aroundLifeCount(board, i, j));
            }
        }
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                board[i][j] = result[i][j];
            }
        }
    }

    private int nextStatus(int currentStatus, int aroundLifeCount) {
        if (currentStatus == 0) {
            if (aroundLifeCount == 3) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (aroundLifeCount < 2) {
                return 0;
            } else if (aroundLifeCount < 4) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private int aroundLifeCount(int[][] board, int currentX, int currentY) {
        int count = 0;
        for (int i=0; i<xAround.length; i++) {
            int disX = currentX + xAround[i];
            int disY = currentY + yAround[i];
            if (disX < 0 || disY < 0 || disX >= board.length || disY >= board[disX].length) {
                continue;
            }
            count += board[disX][disY];
        }
        return count;
    }

    public static void main(String[] args) {

        Object obj = null;
        System.out.println((Integer) obj);
    }
}
