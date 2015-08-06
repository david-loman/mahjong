package com.linxiangpeng.mahjong;

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

    private List<Mahjong> mMahjongList = new LinkedList<Mahjong>();
    private Map<Integer,Integer> mMahjongMap = new HashMap<Integer, Integer>();
    private List<Integer> mMahjongs = new LinkedList<Integer>();

    public MahjongRule (LinkedList<Mahjong> mahjongs){
        mMahjongList = mahjongs;
        init();
    }

    private void init (){
        // 取值

        Collections.sort(mMahjongs);
        for(Mahjong i : mMahjongList){
            Integer count = mMahjongMap.get(new Integer(i.number));

        }
    }
}
