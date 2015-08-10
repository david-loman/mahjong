package com.linxiangpeng.mahjong;

import java.util.Random;

/**
 * Created by linxiangpeng 2015-08-05.
 * About :
 */

public class Mahjong {
    /*
        public static int TYPE_STANDARD = 0;
        public static int TYPE_FLOWER = 1;
        public static int TYPE_WIND = 2;
        public static int TYPE_SEASON = 3;
    */
    public int number;
    public int type;
    public boolean back;
    public String imgURL;
    public String name;

    private boolean used = false;
    private String baseURL = "http://7xkomi.com1.z0.glb.clouddn.com/mahjong_";

    public Mahjong(int n) {
        number = n;
        back = false;
        init(n);
    }

    public Mahjong() {
        number = new Random().nextInt(41) + 1;
        back = false;
        init(number);
    }

    private void init(int number) {
        int tmp = number / 10;
        int subNumber = number % 10;
        if (tmp == 0) {
            name = "bamboo";
        } else if (tmp == 1) {
            name = "man";
        } else if (tmp == 2) {
            name = "pin";
        }
        if (subNumber == 0) {
            name = "dragon";
            subNumber = tmp + 1;

        }
        if (tmp > 3) {
            tmp = number / 4;
            subNumber = number % 4 + 1;
            if (tmp == 0) {
                name = "flower";
            } else if (tmp == 1) {
                name = "wind";
            } else if (tmp == 2) {
                name = "season";
            }
        }


        imgURL = baseURL + name + String.valueOf(subNumber) + ".png";
    }

    public void turnBack() {
        number = 0;
        back = true;
        name = "Back";
        imgURL = baseURL + String.valueOf(number) + ".PNG";
    }

    public void setUsed(boolean use) {
        used = use;
    }

    public boolean isUsed() {
        return used;
    }
}
