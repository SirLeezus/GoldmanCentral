package lee.code.central.utils;

import lee.code.central.Central;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class CountdownUtil implements Runnable {
  private final Central central;
  private Integer assignedTaskId;
  private final int seconds;
  private int secondsLeft;
  private final Consumer<CountdownUtil> everySecond;
  private final Runnable beforeTimer;
  private final Runnable afterTimer;

  public CountdownUtil(Central central, int seconds, Runnable beforeTimer, Runnable afterTimer, Consumer<CountdownUtil> everySecond) {
    this.central = central;
    this.seconds = seconds;
    this.secondsLeft = seconds;
    this.beforeTimer = beforeTimer;
    this.afterTimer = afterTimer;
    this.everySecond = everySecond;
  }

  @Override
  public void run() {
    if (secondsLeft < 1) {
      afterTimer.run();
      if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
      return;
    }
    if (secondsLeft == seconds) beforeTimer.run();
    everySecond.accept(this);
    secondsLeft--;
  }

  public void stop() {
    if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
  }

  public int getTotalSeconds() {
    return seconds;
  }

  public int getSecondsLeft() {
    return secondsLeft;
  }

  public void scheduleTimer() {
    this.assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(central, this, 0L, 20L);
  }
}
