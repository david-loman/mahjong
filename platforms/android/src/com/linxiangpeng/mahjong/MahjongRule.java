package com.linxiangpeng.mahjong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by linxiangpeng on 15-8-6.
 * About :
 */

public class MahjongRule {

    private boolean ting = false;
    private List<Mahjong> mMahjongList = new ArrayList<Mahjong>();
    private int mTingCount = 0;
    private int[] mMahjonsMap = new int[45];
    private int[] mMahjongs = new int[15];
    private int[] mUsedMahjongs = new int[15];
    private int[] mTingMahjongs = new int[10];

    public MahjongRule(ArrayList<Mahjong> mahjongs) {
        mMahjongList = mahjongs;
        init();
    }

    private void init() {
        sortMahjong();
        updataStatus();
    }

    public boolean checkPong(int n) {
        return hasEyes(n);
    }

    public boolean checkKong(int n) {
        return mMahjonsMap[n] == 3 ? true : false;
    }

    public String checkChow(int n) {
        if (mMahjonsMap[n - 2] != 0 && mMahjonsMap[n - 1] != 0 && n - 2 > 0) {
            return String.valueOf(n) + "," + String.valueOf(n - 2) + "," + String.valueOf(n - 1);
        } else if (mMahjonsMap[n - 1] != 0 && mMahjonsMap[n + 1] != 0 && n - 1 > 0) {
            return String.valueOf(n) + "," + String.valueOf(n - 1) + "," + String.valueOf(n + 1);
        } else if (mMahjonsMap[n + 1] != 0 && mMahjonsMap[n + 2] != 0) {
            return String.valueOf(n) + "," + String.valueOf(n + 1) + "," + String.valueOf(n + 2);
        } else {
            return "0";
        }
    }

    public boolean checkWin(int n) {

        return false;
    }

    public boolean hasEyes(int n) {
        return mMahjonsMap[n] == 2 ? true : false;
    }

    // 碰(碰，杠，吃都缺少对已经使用方法的解决)
    public void pong(int n) {
        getOne(n);
        mMahjonsMap[n] -= 3;
        usedMahjong(n, 3);
        sortMahjong();
    }

    // 杠
    public void kong(int n) {
        getOne(n);
        mMahjonsMap[n] -= 4;
        usedMahjong(n, 4);
        sortMahjong();
    }

    // 吃
    public void chow(int i, int j, int k) {
        getOne(i);
        mMahjonsMap[i] -= 1;
        usedMahjong(i, 1);
        mMahjonsMap[j] -= 1;
        usedMahjong(j, 1);
        mMahjonsMap[k] -= 1;
        usedMahjong(k, 1);
        sortMahjong();
    }

    public int[] getmMahjongs() {
        return mMahjongs;
    }

    public int[] getmUsedMahjongs() {
        return mUsedMahjongs;
    }

    // 取牌
    public void getOne(int n) {
        mMahjonsMap[n]++;
        Mahjong m = new Mahjong(n);
        mMahjongList.add(m);
        sortMahjong();
    }

    // 弃牌
    public void throwOne(int n) {
        mMahjonsMap[n]--;
        for (Mahjong m : mMahjongList) {
            if (m.number == n && !m.isUsed()) {
                mMahjongList.remove(m);
            }
        }
        sortMahjong();
    }

    // 判断听牌
    public boolean isTing() {
        return ting;
    }

    public int[] getTingMahjongs() {
        return mTingMahjongs;
    }

    //
    private boolean checkThree(int n, int m, int l) {
        if (2 * m == (n + l)) {
            return true;
        } else if (n == m && m == l) {
            return true;
        } else {
            return false;
        }
    }

    //
    private boolean updataTing(int a, int b, int c, List<String> subMahjongs) {
        boolean isThree = checkThree(a, b, c);
        if (subMahjongs.size() > 1) {
            int max = subMahjongs.size();
            for (int i = 0; i < max; i++) {
                List<String> tmpMahjongs = new ArrayList<String>();
                for (String s : subMahjongs) {
                    tmpMahjongs.add(s);
                }
                tmpMahjongs.remove(subMahjongs.get(i));
                for (int j = i + 1; j < max; j++) {
                    tmpMahjongs.remove(subMahjongs.get(j));
                    for (int k = j + 1; k < max; k++) {
                        tmpMahjongs.remove(subMahjongs.get(k));
                        return updataTing(Integer.parseInt(subMahjongs.get(i)), Integer.parseInt(subMahjongs.get(j)),
                                Integer.parseInt(subMahjongs.get(k)), tmpMahjongs);
                    }
                }
            }
        } else {
            if (isThree) {
                mTingMahjongs[mTingCount++] = Integer.parseInt(subMahjongs.get(0));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // 洗牌
    private void sortMahjong() {
        for (int i = 0; i < mMahjongList.size(); i++) {
            if (!mMahjongList.get(i).isUsed()) {
                mMahjongs[i] = mMahjongList.get(i).number;
            }
        }
        Arrays.sort(mMahjongs);
    }

    // 更新状态
    private void updataStatus() {
        for (int i = 0; i <= 42; i++) {
            mMahjonsMap[i] = 0;
        }
        for (int i : mMahjongs) {
            mMahjonsMap[i]++;
        }
    }

    private void usedMahjong(int n, int m) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < mMahjongList.size(); j++) {
                if (mMahjongList.get(j).number == n && !mMahjongList.get(j).isUsed()) {
                    mMahjongList.get(j).setUsed(true);
                }
            }
        }
    }

}
