public class Semaphore {
    private int value ;
    public Semaphore( int value ){
        this.value = value ;
    }

    public synchronized void lock(){
        --value ;
        if ( value < 0 ){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void unlock(){
        ++value ;
        if ( value <= 0 ){
            notify();
        }
    }
}
