public class Buffer {
    private final int maxSize ;
    private final int[] arr ;
    private int leftPointer = 0 ;
    private int rightPointer = 0 ;

    Semaphore FULL ;
    Semaphore EMPTY ;

    Buffer( int maxSize ){
        this.maxSize = maxSize ;
        arr = new int[maxSize] ;
        EMPTY = new Semaphore(maxSize) ;
        FULL = new Semaphore(0) ;
    }

    public void produce( int number ){
        FULL.lock();
        arr[rightPointer] = number ;
        rightPointer = (rightPointer+1)%maxSize ;
        EMPTY.unlock();
    }

    public int consume(){
        int value ;
        EMPTY.lock();
        value = arr[leftPointer] ;
        leftPointer = (leftPointer+1)%maxSize ;
        FULL.unlock();
        return value ;
    }
}
