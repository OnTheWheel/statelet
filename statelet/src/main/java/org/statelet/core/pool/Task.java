/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.statelet.core.pool;

/**
 *
 * @author KKwams
 */
public interface Task {
    public void execute() throws Exception;
    
    public void setUUID(String uuid);
    public String getUUID();
    
    public void setStatus(TaskStatus taskStatus);
    public TaskStatus getStatus();
    
    public String getDetail();

}
