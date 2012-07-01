/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.statelet.core.pool;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author KKwams
 */

public class TaskThreadPool {
    private static int DEFAULT_CAPACITY = 100;
    private static int DEFAULT_THREADS = 10;
    
    ArrayBlockingQueue<Task> taskQueue;
    ArrayBlockingQueue<Task> terminatedQueue;
    ArrayList<Thread> threads = new ArrayList<Thread>();
    private int numberOfThreads;
    
    private void init() throws Exception
    {
        for(int i=0; i < numberOfThreads; i++)
        {
            Thread thread = new Thread(new TaskRunner());
            threads.add(thread);
            thread.start();
        }
    }
    
    public TaskThreadPool() throws Exception
    {
        taskQueue = new ArrayBlockingQueue<Task>(DEFAULT_CAPACITY);
        terminatedQueue = new ArrayBlockingQueue<Task>(DEFAULT_CAPACITY);
        numberOfThreads = DEFAULT_THREADS;
        
        init();
    }
    
    public TaskThreadPool(int capacity, int numberOfThreads) throws Exception
    {
        taskQueue = new ArrayBlockingQueue<Task>(capacity);
        terminatedQueue = new ArrayBlockingQueue<Task>(capacity);
        this.numberOfThreads = numberOfThreads;
        
        init();
    }
    
    private class TaskRunner implements Runnable
    {
        public void run() {
            while(true)
            {
                Task task = null;
                try {
                    task = taskQueue.take();
                    task.setStatus(TaskStatus.RUNNING);
                    task.execute();
                    task.setStatus(TaskStatus.COMPLETED);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    task.setStatus(TaskStatus.ABORTED);
                } finally {
                    while(!terminatedQueue.offer(task))
                    {
                        terminatedQueue.poll();
                    }
                }
            }
        }
    }
    
    public boolean push(Task task)
    {
        task.setStatus(TaskStatus.READY);
        if(taskQueue.offer(task))
            return true;
        else
        {
            task.setStatus(TaskStatus.CREATED);
            return false;
        }
    }
}