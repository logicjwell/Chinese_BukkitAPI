package org.bukkit.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * 这个类提供了简单的处理调度任务的方法。
 */
public abstract class BukkitRunnable implements Runnable {
    private int taskId = -1;

    /**
     * 尝试取消这个任务。
     * <p>
     * 原文：Attempts to cancel this task.
     *
     * @throws IllegalStateException 如果任务还没有调度
     */
    public synchronized void cancel() throws IllegalStateException {
        Bukkit.getScheduler().cancelTask(getTaskId());
    }

    /**
     * 让Bukkit调度器在下一个tick运行任务。
     * <p>
     * 原文：Schedules this in the Bukkit scheduler to run on next tick.
     *
     * @param plugin 调度任务的插件
     * @return 包含id编号的BukkitTask
     * @throws IllegalArgumentException 如果插件为null
     * @throws IllegalStateException 如果任务已经调度
     * @see BukkitScheduler#runTask(Plugin, Runnable)
     */
    public synchronized BukkitTask runTask(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        checkState();
        return setupId(Bukkit.getScheduler().runTask(plugin, (Runnable) this));
    }

    /**
     * <b>异步任务应该不访问Bukkit里的API。着重考虑应保证异步任务的安全。</b>
     * <p>
     * 在Bukkit调度器运行这个异步任务。
     * <p>
     * 原文：<b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules this in the Bukkit scheduler to run asynchronously.
     *
     * @param plugin 调度任务的插件
     * @return 包含id编号的BukkitTask
     * @throws IllegalArgumentException 如果插件为null
     * @throws IllegalStateException 如果这已经调度
     * @see BukkitScheduler#runTaskAsynchronously(Plugin, Runnable)
     */
    public synchronized BukkitTask runTaskAsynchronously(Plugin plugin) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(Bukkit.getScheduler().runTaskAsynchronously(plugin, (Runnable) this));
    }

    /**
     * Schedules this to run after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException 如果插件为null
     * @throws IllegalStateException 如果这已经调度
     * @see BukkitScheduler#runTaskLater(Plugin, Runnable, long)
     */
    public synchronized BukkitTask runTaskLater(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(Bukkit.getScheduler().runTaskLater(plugin, (Runnable) this, delay));
    }

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules this to run asynchronously after the specified number of
     * server ticks.
     *
     * @param plugin 调度任务的插件
     * @param delay the ticks to wait before running the task
     * @return 包含id编号的BukkitTask
     * @throws IllegalArgumentException 如果插件为null
     * @throws IllegalStateException 如果这已经调度
     * @see BukkitScheduler#runTaskLaterAsynchronously(Plugin, Runnable, long)
     */
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, (Runnable) this, delay));
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the
     * specified number of server ticks.
     *
     * @param plugin 调度任务的插件
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return 包含id编号的BukkitTask
     * @throws IllegalArgumentException 如果插件为null
     * @throws IllegalStateException 如果这已经调度
     * @see BukkitScheduler#runTaskTimer(Plugin, Runnable, long, long)
     */
    public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(Bukkit.getScheduler().runTaskTimer(plugin, (Runnable) this, delay, period));
    }

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules this to repeatedly run asynchronously until cancelled,
     * starting after the specified number of server ticks.
     *
     * @param plugin 调度任务的插件
     * @param delay the ticks to wait before running the task for the first
     *     time
     * @param period the ticks to wait between runs
     * @return 包含id编号的BukkitTask
     * @throws IllegalArgumentException 如果插件为null
     * @throws IllegalStateException 如果这已经调度
     * @see BukkitScheduler#runTaskTimerAsynchronously(Plugin, Runnable, long,
     *     long)
     */
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, (Runnable) this, delay, period));
    }

    /**
     * 获取这个runnable的任务id。
     * <p>
     * 原文：Gets the task id for this runnable.
     *
     * @return runnable调度过的任务
     * @throws IllegalStateException 如果任务还没有调度
     */
    public synchronized int getTaskId() throws IllegalStateException {
        final int id = taskId;
        if (id == -1) {
            throw new IllegalStateException("Not scheduled yet");
        }
        return id;
    }

    private void checkState() {
        if (taskId != -1) {
            throw new IllegalStateException("Already scheduled as " + taskId);
        }
    }

    private BukkitTask setupId(final BukkitTask task) {
        this.taskId = task.getTaskId();
        return task;
    }
}