/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.statelet.core.pool;

/**
 *
 * @author KKwams
 */
public abstract class BaseTask implements Task
{
    protected String uuid;
    protected TaskStatus taskStatus;
    
    public BaseTask()
    {
        taskStatus = TaskStatus.CREATED;
    }
    
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    public void setStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStatus getStatus() {
        return taskStatus;
    }
    
    
}
