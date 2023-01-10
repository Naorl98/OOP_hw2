package Ex2_part2;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TheadPool class with priority option of the tasks arrangement
 */
public class CustomExecutor extends ThreadPoolExecutor {
    //save all the priorities of the task that enter to the priority queue
    private int[] priorities= new int[10];

    /**
     * Build the threadPool with Super
     * enter:
     * corePoolSize = half of the available processors
     * MaximumPoolSize = the available processors -1
     * KeepAliveTime = 300 Millisecond
     * TimeUnit = Millisecond
     * Queue = PriorityBlockingQueue
     */
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2
                , Runtime.getRuntime().availableProcessors() - 1
                , 300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    /**
     *  Cast Task object to TaskFuture object
     * @param t - Task object
     * @return TaskFuture Object
     * @param <T>
     */
    protected <T> TaskFuture<T> newTask(Task t) {
        return new TaskFuture<T>(t.getCall(), t.getPriority());
    }

    /**
     * Search of the biggest priority:
     * start from 0-9, if the value in cell in bigger than zero,
     * so it's mean that we have task with this value of priority in the queue.
     * @return The index of the first cell that his value is bigger than zero , plus one = (index +1)
     * if the array is empty return zero
     */
    public int getCurrentMax() {
        for(int i= 0;i<10;i++){
            if(this.priorities[i] != 0){
                return i+1;
            }
        }
        return 0;
    }

    /**
     * Enter the priority of the task to the suitable cell
     * Cast task to a TaskFuture with newTask method
     * Ececute the new TaskFuture
     * @param task - Task object
     * @return The new TaskFuture object
     * @param <T>
     */
    public <T> Future<T> submitTask(Task task) {

        if (task == null) throw new NullPointerException();
        this.priorities[task.getPriority()-1] += 1;
        TaskFuture<T> tTask = newTask(task);
        execute(tTask);
        return tTask;
    }

    /**
     * Create Task object with callable object and tasktype object
     * Call submitTask Method
     * @param myFunc callable
     * @param type TaskType
     * @return The reture value of sbmitTask
     * @param <T>
     */
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
     *  FutureTask class with a priority and comparable options
     * @param <V>
     */
    public class TaskFuture<V> extends FutureTask<V> implements Comparable<TaskFuture> {
        //priority variable
        private int priority;

        /**
         * Create TaskFuture object
         * use super of FutureTask
         * @param callable callable function
         * @param p priority value
         */
        public TaskFuture(Callable<V> callable, int p) {
            super(callable);
            this.priority = p;
        }

        /**
         * @return THe priority
         */
        public int getPriority(){
            return this.priority;
        }

        /**
         * @return The FutureTask properties plus priority value
         */
        @Override
        public String toString() {
            return super.toString() + String.valueOf(" Priority: "+this.priority);
        }

        /**
         * @return The priority value at string
         */
        public String printPriority()
        {
            return String.valueOf(this.priority);
        }

        /**
         * Override method that cast the Task class to be
         * a comparable class
         * @param o the TaskFuture object to be compared.
         * @return 1 - if this bigger than o, -1 - if this smaller than o, 0 - equals
         */
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