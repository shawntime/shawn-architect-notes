package com.shawntime.architect.notes.concurrency.waitandnotify.express;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ExpressTest {

    public static void main(String[] args) throws InterruptedException {
        Express express = new Express();
        for (int i = 0; i < 3; ++i) {
            CheckKm checkKm = new CheckKm("thread-check-km-" + i, express);
            checkKm.start();

            CheckSite checkSite = new CheckSite("thread-check-site-" + i, express);
            checkSite.start();
        }

        ChangeKm changeKm = new ChangeKm("thread-change-km", express);
        changeKm.start();

        ChangeSite changeSite = new ChangeSite("thread-change-site", express);
        changeSite.start();

    }

    private static class CheckSite extends Thread {
        private Express express;

        public CheckSite(String name, Express express) {
            super(name);
            this.express = express;
        }

        @Override
        public void run() {
            try {
                express.waitSite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class CheckKm extends Thread {
        private Express express;

        public CheckKm(String name, Express express) {
            super(name);
            this.express = express;
        }

        @Override
        public void run() {
            try {
                express.waitKm();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ChangeKm extends Thread {

        private Express express;

        public ChangeKm(String name, Express express) {
            super(name);
            this.express = express;
        }

        @Override
        public void run() {
            int i = 0;
            do {
                try {
                    i += 15;
                    express.changeKm(i);
                    int time = ThreadLocalRandom.current().nextInt(1000);
                    TimeUnit.MILLISECONDS.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while  (i <= 100);
        }
    }


    private static class ChangeSite extends Thread {

        private Express express;

        public ChangeSite(String name, Express express) {
            super(name);
            this.express = express;
        }

        @Override
        public void run() {
            List<String> cityList = new ArrayList<>();
            cityList.add("北京");
            cityList.add("北京");
            cityList.add("北京");
            cityList.add("北京");
            cityList.add("北京");
            cityList.add("北京");
            cityList.add("上海");
            for (String site : cityList) {
                try {
                    express.changeSite(site);
                    int time = ThreadLocalRandom.current().nextInt(1000);
                    TimeUnit.MILLISECONDS.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
