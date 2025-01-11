package com.wsz.loadbalance;

import com.wsz.common.URL;

import java.util.List;
import java.util.Random;

public class LoadBalance {
    public static URL random(List<URL> urlList) {
        Random random = new Random();
        int index = random.nextInt(urlList.size());
        return urlList.get(index);
    }
}
