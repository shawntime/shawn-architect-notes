package com.shawntime.enjoy.architect.concurrency.cyclicbarrier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

/**
 * 用CyclicBarrier模拟运动员跑步比赛
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("比赛开始");
        final List<Score> scoreList = new ArrayList<>();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,  new ScoreRunnable(scoreList));
        Sportsman sportsman1 = new Sportsman("张三", cyclicBarrier, scoreList);
        sportsman1.start();
        Sportsman sportsman2 = new Sportsman("李四", cyclicBarrier, scoreList);
        sportsman2.start();
        Sportsman sportsman3 = new Sportsman("王五", cyclicBarrier, scoreList);
        sportsman3.start();

        sportsman1.join();
        sportsman2.join();
        sportsman3.join();
        System.out.println("比赛结束");
    }

    private static class ScoreRunnable implements Runnable {

        private List<Score> scoreList;

        public ScoreRunnable(List<Score> scoreList) {
            this.scoreList = scoreList;
        }

        @Override
        public void run() {
            Map<Integer, List<Score>> distanceMap = scoreList.stream()
                    .collect(Collectors.groupingBy(Score::getDistance));
            distanceMap.forEach((distance, scores) -> {
                System.out.println(distance + "米跑步结果");
                scores.stream()
                        .sorted(Comparator.comparingInt(Score::getTime))
                        .forEach(score -> System.out.println(score.getSportsmanName() + " : " + score.getTime() + "秒"));
            });
        }
    }

    private static class Sportsman extends Thread {

        private String sportsmanName;

        private CyclicBarrier cyclicBarrier;

        private List<Score> scoreList;

        public Sportsman(String sportsmanName,
                         CyclicBarrier cyclicBarrier,
                         List<Score> scoreList) {
            this.sportsmanName = sportsmanName;
            this.cyclicBarrier = cyclicBarrier;
            this.scoreList = scoreList;
        }

        @Override
        public void run() {
            try {
                System.out.println(sportsmanName + "100米准备就绪...");
                int time = ThreadLocalRandom.current().nextInt(5);
                SleepUtils.sleepBySeconds(time);
                Score score = getScore(sportsmanName, 100, time);
                scoreList.add(score);
                System.out.println(sportsmanName + "100米完成，1000米准备就绪...");
                cyclicBarrier.await();

                time = ThreadLocalRandom.current().nextInt(10);
                SleepUtils.sleepBySeconds(time);
                Score score2 = getScore(sportsmanName, 1000, time);
                scoreList.add(score2);
                System.out.println(sportsmanName + "1000米完成，5000米准备就绪...");
                cyclicBarrier.await();

                time = ThreadLocalRandom.current().nextInt(20);
                SleepUtils.sleepBySeconds(time);
                Score score3 = getScore(sportsmanName, 5000, time);
                scoreList.add(score3);
                System.out.println(sportsmanName + "5000米完成");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private Score getScore(String name, int distance, int time) {
            Score score = new Score();
            score.setDistance(distance);
            score.setSportsmanName(name);
            score.setTime(time);
            return score;
        }
    }
}
