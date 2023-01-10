package Ex2_part2;

import java.util.concurrent.*;

/**
 * Callable class with priority option
 */
public class Task <T> implements Comparable<Task>{
    // save the callable
    private Callable myFunc;
    //save the priority of the TaskType
    private int priority;

    private TaskType tType ;

    /**
     * @return The priority of the task
     */
    public int getPriority(){
        return this.priority;
    }

    /**
     *
     * @return the callable object
     * @param <T>
     */
    public <T> Callable<T> getCall() {
        return this.myFunc;
    }

    /**
     * @param myFunc The function
     * @param type  The task type
     * @param p The priority
     */
    private Task(Callable myFunc, TaskType type, int p) {
        this.myFunc = myFunc;
        this.tType = type;
        this.priority = p;

    }

    /**
     * Factory method that crate Task object
     * @param myFunc - the callable function
     * @param type - the type of the function
     * @return Task object
     * @param <T>
     */
    public static <T> Task createTask(Callable<T> myFunc, TaskType type) {
        return new Task(myFunc, type, type.getPriorityValue());
    }
    /**
     * Factory method that crate Task object
     * @param myFunc - the callable function
     * @return Task object
     * @param <T>
     */
    public static <T> Task createTask(Callable<T> myFunc) {
        return new Task(myFunc, TaskType.OTHER, 3);
    }

    /**
     * Override method that cast the Task class to be
     * a comparable class
     * @param t the object to be compared.
     * @return 1 - if this bigger than t, -1 - if this smaller than t, 0 - equals
     */
    @Override
    public int compareTo(Task t) {
        if (this.priority < t.priority)
            return -1;
        else if (this.priority > t.priority)
            return 1;
        else
            return 0;
    }

    /**
     * Start the callable function
     * @return the return vakue of the callable function
     * @throws Exception
     */
    public T call() throws Exception {
        var a = myFunc.call();
        return (T) a;
    }
}
