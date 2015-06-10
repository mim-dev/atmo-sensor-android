package com.mim_development.android.atmosensor.models.services.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by luther stanton on 6/4/15.
 */
public abstract class ServiceImpl implements Service {

    protected class WorkQueueValue {

        public HttpCallback callback;
        public HttpOperation operation;

        public WorkQueueValue(HttpCallback callback, HttpOperation operation){

            if(callback == null){
                throw new IllegalArgumentException("The callback argument is required!!");
            }

            if(operation == null){
                throw new IllegalArgumentException("The operation argument is required!!");
            }

            this.callback = callback;
            this.operation = operation;
        }

        public WorkQueueValue(WorkQueueValue workQueueValue){

            if(workQueueValue == null || !workQueueValue.isValid()){
                throw new IllegalArgumentException("A valid workQueueValue argument is required!!");
            }

            this.callback = workQueueValue.callback;
            this.operation = workQueueValue.operation;
        }

        public boolean isValid(){
            return callback != null && operation != null;
        }
    }

    private Map<String, WorkQueueValue> operationWorkQueue;
    private ReentrantLock workQueueLock;

    protected ServiceImpl(){
        operationWorkQueue = new HashMap<>(10);
        workQueueLock = new ReentrantLock();
    }

    @Override
    public void cancelOperation(String operationInstanceIdentifier){

        if(operationInstanceIdentifier == null || operationInstanceIdentifier.length() == 0){
            throw new IllegalArgumentException("The operationInstanceIdentifier argument is required!!");
        }

        workQueueLock.lock();
        try {
            operationWorkQueue.remove(operationInstanceIdentifier);
        } finally {
            workQueueLock.unlock();
        }

    }

    protected void addValueToOperationWorkQueue(String operationInstanceIdentifier, WorkQueueValue workQueueValue){

        if(operationInstanceIdentifier == null || operationInstanceIdentifier.length() == 0){
            throw new IllegalArgumentException("The operationInstanceIdentifier argument is required!!");
        }

        if(workQueueValue == null || !workQueueValue.isValid()){
            throw new IllegalArgumentException("A valid workQueueValue argument is required!!");
        }

        workQueueLock.lock();
        try {
            operationWorkQueue.put(operationInstanceIdentifier, workQueueValue);
        } finally {
            workQueueLock.unlock();
        }
    }

    protected WorkQueueValue fetchValueFromOperationWorkQueue(String operationInstanceIdentifier,
                                           boolean deleteWorkQueueValue){

        if(operationInstanceIdentifier == null || operationInstanceIdentifier.length() == 0){
            throw new IllegalArgumentException("The operationInstanceIdentifier argument is required!!");
        }

        WorkQueueValue workQueueValue = null;

        workQueueLock.lock();
        try {
            WorkQueueValue workQueueValueBuffer = operationWorkQueue.get(operationInstanceIdentifier);
            if(workQueueValueBuffer != null){
                workQueueValue = new WorkQueueValue(workQueueValueBuffer);
                if(deleteWorkQueueValue) {
                    operationWorkQueue.remove(operationInstanceIdentifier);
                }
            }
        } finally {
            workQueueLock.unlock();
        }

        return workQueueValue;
    }
}
