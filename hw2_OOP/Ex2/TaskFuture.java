/*
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



public class TaskFuture<V> extends FutureTask<V> implements Comparable<TaskFuture>{

    public  int priority;
    public TaskFuture(Callable<V> callable, int p) {
        super(callable);
        this.priority = p;
    }
    @Override
    public String toString() {
        return String.valueOf(this.priority);
    }
    @Override
    public int compareTo(TaskFuture o) {
        if(this.priority>o.priority)
            return  1;
        else if(this.priority < o.priority)
            return  -1;
        return 0;
    }
}
