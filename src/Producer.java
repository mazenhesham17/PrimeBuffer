import java.util.Arrays;

public class Producer extends Thread{
    private final Buffer buffer ;
    private final int numberSize ;
    private final boolean[] numbers ;

    public Producer( Buffer buffer , int numberSize ){
        this.buffer = buffer ;
        this.numberSize = numberSize ;
        numbers = new boolean[numberSize+1] ;
        Arrays.fill(numbers,true);
    }

    public void run(){
        for ( int i = 2 ; i <= numberSize ; ++i ){
            if ( numbers[i] ){
                long prime = i ;
                buffer.produce(i);
                if ( prime*prime > numberSize ) continue;
                for ( int j = i*i ; j <= numberSize ; j += i ){
                    numbers[j] = false ;
                }
            }
        }
        buffer.produce(-1);
    }
}
