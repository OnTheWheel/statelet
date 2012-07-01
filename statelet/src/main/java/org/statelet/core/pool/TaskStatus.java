/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.statelet.core.pool;

/**
 *
 * @author KKwams
 */
public enum TaskStatus {
    CREATED,
    READY,
    RUNNING,
    COMPLETED,
    CANCELLED,
    ABORTED
}
