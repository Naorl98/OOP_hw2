import java.util.Comparator;
import java.util.concurrent.*;


public class CustomExecutor extends ThreadPoolExecutor {

    private int Max;
//Runtime.getRuntime().availableProcessors() / 2

    public CustomExecutor() {

        super(1
                , 1
                , 300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());

    }
    protected <T> TaskFuture<T> newTask(Task t) {
        return new TaskFuture<T>(t.getCall(),t.priority );
    }
    public int getCurrentMax(){return this.Max;}

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
    public <T> Future<T> submit(Callable<T> myFunc) {
        if(myFunc instanceof Task )
            return submitTask((Task)myFunc);
        else if (myFunc == null ) throw new NullPointerException();
        return submitTask(Task.createTask(myFunc));
    }
    public void gracefullyTerminate(){
        super.shutdown();
    }
    @Override
    protected void afterExecute(Runnable r, Throwable t) {

       if(!super.getQueue().isEmpty()){
           if(((TaskFuture) r).priority <((TaskFuture) super.getQueue().peek()).priority){
               this.Max = ((TaskFuture) super.getQueue().peek()).priority;
           }
       }

    }
    public BlockingQueue<Runnable> getQueueP(){
        return super.getQueue();
    }
}