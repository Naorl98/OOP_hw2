package Ex2_part2;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class CustomExecutor extends ThreadPoolExecutor {
    private int[] priorities= new int[10];
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2
                , Runtime.getRuntime().availableProcessors() - 1
                , 300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    protected <T> TaskFuture<T> newTask(Task t) {
        return new TaskFuture<T>(t.getCall(), t.getPriority());
    }

    public int getCurrentMax() {
        for(int i= 0;i<10;i++){
            if(this.priorities[i] != 0){
                return i+1;
            }
        }
        return 0;
    }

    public <T> Future<T> submitTask(Task task) {

        if (task == null) throw new NullPointerException();
        this.priorities[task.getPriority()-1] += 1;
        TaskFuture<T> tTask = newTask(task);
        execute(tTask);
        return tTask;
    }

    public <T> Future<T> submit(Callable<T> myFunc, TaskType type) {
        if (myFunc == null || type == null) throw new NullPointerException();
        return submitTask(Task.createTask(myFunc, type));
    }

    /**
     * call submitTask
     * @param task
     * @return TaskFuture object
     * @param <T>
     */

    public <T> Future<T> submit(Task task) {
        if (task == null) throw new NullPointerException();
        return submitTask(task);
    }

    /**
     * Create Task object and call sumbitTask
     * @param myFunc the task to submit
     * @return TaskFuture object
     * @param <T>
     */
    public <T> Future<T> submit(Callable<T> myFunc) {

        if (myFunc == null) throw new NullPointerException();
        return submitTask(Task.createTask(myFunc));
    }

    /**
     * Terminated the TheardPool
     */
    public void gracefullyTerminate() {
        super.shutdown();
        try {
            super.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculated the max priority
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     * execution completed normally
     */

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        TaskFuture r1 = (TaskFuture) r;
        this.priorities[r1.getPriority()-1] -= 1;
    }

    /**
     * @return Parent's Queue
     */
    public BlockingQueue<Runnable> getQueueP() {
        return super.getQueue();
    }

    /**
     *  FutureTask class with
     * @param <V>
     */
    public class TaskFuture<V> extends FutureTask<V> implements Comparable<TaskFuture> {

        private int priority;

        public TaskFuture(Callable<V> callable, int p) {
            super(callable);
            this.priority = p;
        }
        public int getPriority(){
            return this.priority;
        }

        @Override
        public String toString() {
            return super.toString() + String.valueOf(" Priority: "+this.priority);
        }
        public String printPriority()
        {
            return String.valueOf(this.priority);
        }


        @Override
        public int compareTo(TaskFuture o) {
            if (this.priority > o.getPriority())
                return 1;
            else if (this.priority < o.getPriority())
                return -1;
            return 0;
        }
    }

}