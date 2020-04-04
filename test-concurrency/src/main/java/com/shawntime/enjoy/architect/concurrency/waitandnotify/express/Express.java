package com.shawntime.enjoy.architect.concurrency.waitandnotify.express;

/**
 * 当快递的公里数大于100公里或城市不在北京时，给客户发出提醒消息
 */
public class Express {

    public static final String CURR_CITY = "北京";

    private Object object = new Object();

    /**
     * 快递行走的公里数
     */
    private int km;

    /**
     * 快递当前位置
     */
    private String site = "北京";

    /**
     * 修改快递公里数
     * @param km
     */
    public void changeKm(int km) {
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + " km : " + km);
            this.km = km;
            object.notifyAll();
        }
    }

    /**
     * 修改快递城市
     * @param city
     */
    public void changeSite(String city) {
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + " site : " + site);
            this.site = city;
            object.notifyAll();
        }
    }

    /**
     * 等待公里数变化
     */
    public void waitKm() throws InterruptedException {
        synchronized (object) {
            while (this.km <= 100) {
                object.wait();
                System.out.println(Thread.currentThread().getName() + " check km...");
            }
            System.out.println(Thread.currentThread().getName() + " , send change, curr km : " + km);
        }
    }

    /**
     * 等待城市变化
     */
    public void waitSite() throws InterruptedException {
        synchronized (object) {
            while (CURR_CITY.equals(site)) {
                object.wait();
                System.out.println(Thread.currentThread().getName() + " check site...");
            }
            System.out.println(Thread.currentThread().getName() + " send change, curr site : " + site);
        }
    }
}
