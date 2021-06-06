package filosofos;
import java.util.concurrent.Semaphore;
public class Garfo {
	private int id;
	private Semaphore mutex;
	public Garfo(int id) {
		super();
		this.id = id;
		this.mutex= new Semaphore(1);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Semaphore getMutex() {
		return mutex;
	}
	public void setMutex(Semaphore mutex) {
		this.mutex = mutex;
	}	
}
