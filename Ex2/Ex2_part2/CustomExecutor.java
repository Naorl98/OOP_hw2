package Ex2_part2;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class CustomExecutor extends ThreadPoolExecutor {
    private int Max;

    public CustomExecutor() {

        super(Runtime.getRuntime().availableProcessors() / 2
                , Runtime.getRuntime().availableProcessors() - 1
                , 300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    protected <T> TaskFuture<T> newTask(Task t) {
        return new TaskFuture<T>(t.getCall(), t.priority);
    }

    public int getCurrentMax() {
        return this.Max;
    }

    public <T> Future<T> submitTask(Task task) {

        if (task == null) throw new NullPointerException();
        TaskFuture<T> tTask = newTask(task);
        execute(tTask);
        return tTask;
    }

    public <T> Future<T> submit(Callable<T> myFunc, TaskType type) {
        if (myFunc == null || type == null) throw new NullPointerException();
        return submitTask(Task.createTask(myFunc, type));
    }

    public <T> Future<T> submit(Task task) {
        if (task == null) throw new NullPointerException();
        return submitTask(task);
    }

    public <T> Future<T> submit(Callable<T> myFunc) {

        if (myFunc == null) throw new NullPointerException();
        return submitTask(Task.createTask(myFunc));
    }

    public void gracefullyTerminate() {
        super.shutdown();
        try {
            super.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {

        if (!super.getQueue().isEmpty()) {
            if (((TaskFuture) r).getPriority() < ((TaskFuture) super.getQueue().peek()).getPriority()) {
                this.Max = ((TaskFuture) super.getQueue().peek()).getPriority();
            }
        }
           else{
            this.Max = 0;
        }
    }

    public BlockingQueue<Runnable> getQueueP() {
        return super.getQueue();
    }
}
