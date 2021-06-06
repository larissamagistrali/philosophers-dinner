package filosofos;

import java.util.Random;

public class Filosofo implements Runnable {
	private int id;	
	private String estado; 	//PENSANDO,COMENDO,TENTANDO_COMER
	private int direito; 	//index do garfo direito no arraylist garfos em App.java
	private int esquerdo;	//index do garfo esquerdo no arraylist garfos em App.java
	public Filosofo(int id, int numFilosofos) {
		super();
		this.id=id;
		this.estado="";
		this.direito = id; 
		this.esquerdo = (id+1)%numFilosofos;
	}
	@Override
	public void run() {
		while(true) {
			try {	
				if(estado=="COMENDO" || estado=="") { 
					pensar();
					estado="TENTANDO_COMER";//PENSANDO->TENTANDO_COMER->COMENDO->PENSANDO...
				}	
				if(estado=="TENTANDO_COMER") {
					if(App.garfos.get(direito).getMutex().tryAcquire()) {
						//System.out.println("Filósofo " +id+ " pegou o garfo " +direito+".");
						if(App.garfos.get(esquerdo).getMutex().tryAcquire()) {
							//System.out.println("Filósofo " +id+ " pegou o garfo " +esquerdo+".");
							//System.out.println("Filósofo " +id+ " pegou os garfos " +direito+" e "+esquerdo+".");
							comer();
							largarGarfoDireito();
							largarGarfoEsquerdo();
							//System.out.println("Filósofo " +id+ " largou os garfos " +direito+" e "+esquerdo+".");
						}else {
							//System.out.println("Filósofo " +id+ " largou o garfo " +direito+".");
							largarGarfoDireito();
							tentarComer();						
						}				
					}else{
						tentarComer();
					}
				}				
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	private void largarGarfoEsquerdo() {
		App.garfos.get(esquerdo).getMutex().release();
		
	}
	private void largarGarfoDireito(){
		App.garfos.get(direito).getMutex().release();
	}
	public void pensar() throws InterruptedException {
		this.estado="PENSANDO";
		App.relatorio[id][0]++;
		System.out.println("Filósofo "+id+" esta pensando.");
		Thread.sleep(5000);
	}
	public void tentarComer() throws InterruptedException {
		this.estado="TENTANDO_COMER";
		App.relatorio[id][2]++;
		System.out.println("Filósofo "+id+" tentou e não conseguiu comer. Foi pensar.");
		Random aleatorio = new Random();
		int r = aleatorio.nextInt(3) + 1;
		Thread.sleep(r*1000);
	}
	public void comer() throws InterruptedException {
		this.estado="COMENDO";
		App.relatorio[id][1]++;
		System.out.println("Filósofo "+this.id+" esta comendo.");
		Thread.sleep(2000);
	}	
}
