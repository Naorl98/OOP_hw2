import java.util.concurrent.Callable;

public class Task implements Callable {
    private Callable myFunc ;
    public int priority;
    private TaskType tType ;

    public <T> Callable<T> getCall(){
        return this.myFunc;
    }
    private Task( Callable myFunc , TaskType type, int p){
        this.myFunc = myFunc;
        this.tType =type;
        this.priority = p;
    
    }
    public static <T> Task createTask(Callable<T> myFunc , TaskType type)
    {
        return new Task(myFunc, type, type.getPriorityValue());
    }
    public static <T> Task createTask(Callable<T> myFunc )
    {
        return new Task(myFunc,TaskType.OTHER, 3);
    }

    public int compareTo(Task t)
    {
        if(this.priority < t.priority)
            return -1;
        else if(this.priority > t.priority)
            return 1;
        else
            return 0;
    }

    @Override
    public Object call() throws Exception
    {
        return myFunc.call();
    }


}
