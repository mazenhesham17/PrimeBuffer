public class Consumer extends Thread {
    private final Buffer buffer ;
    private final Controller controller ;

    public Consumer( Buffer buffer , Controller controller ){
        this.buffer = buffer ;
        this.controller = controller ;
    }

    public void run(){
        while ( true ){
            int current = buffer.consume() ;
            if ( current == -1 ){
                controller.update(-1);
                break;
            }else{
                if ( current != 0 ){
                    controller.update(current);
                }
            }
        }
    }
}
