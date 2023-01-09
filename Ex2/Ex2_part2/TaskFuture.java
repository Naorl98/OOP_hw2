package Ex2_part2;/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.concurrent.*;


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
        if (this.priority > o.priority)
            return 1;
        else if (this.priority < o.priority)
            return -1;
        return 0;
    }
}
