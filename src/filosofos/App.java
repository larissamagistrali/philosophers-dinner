package filosofos;
import java.util.ArrayList;
import java.util.Scanner;
public class App {
	
	static Thread[]  filosofos;
	static ArrayList<Garfo> garfos;
	static int[][] relatorio;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		garfos = new ArrayList<Garfo>();
		
		System.out.println("=====JANTAR DOS FILÓSOFOS=====");
		System.out.print("Número de filósofos: ");
		int numFilosofos=in.nextInt();
		System.out.print("Tempo de simulação (segundos): ");
		long tempoJantar = in.nextLong();
		
		
		//Cria threads de filosofos e garfos
		filosofos = new Thread[numFilosofos];
		for(int i=0;i<numFilosofos;i++) {
			filosofos[i] = new Thread(new Filosofo(i,numFilosofos));
			garfos.add(new Garfo(i));
		}
		
		//Relatorio 
		//OBS: é necessário um array global para o relatório pois não é possível 
		//retornar atributos de uma classe que implementa Runnable)
		//[i][0] = pensou x vezes
		//[i][1] = comeu x vezes 
		//[i][2] = tentou comer x vezes
		relatorio=new int[numFilosofos][3];
		
		//Inicia contagem do tempo da simulação
		long tempoInicial = System.currentTimeMillis();
		long tempoFinal=0,duracao=0;
		
		//Inicia simulação
		System.out.println("\n========SIMULAÇÃO=============");		
		for(int i=0;i<numFilosofos;i++) {
			filosofos[i].start();
		}			
		
		//Verifica fim do tempo da execução e imprime o relatório
		while(true) {
			tempoFinal = System.currentTimeMillis();
			duracao = (tempoFinal - tempoInicial);
			if(duracao>=(tempoJantar*1000)) {
				for(int i=0;i<numFilosofos;i++) {
					filosofos[i].stop();
				}
				System.out.println("\n=========RELATÓRIO============");	
				System.out.println("Filosofo|Pensou|Comeu|TentouComer");
				for(int i=0;i<numFilosofos;i++) {
					System.out.println("   "+i + "\t   " + relatorio[i][0] + "\t  " +relatorio[i][1] + "\t " + relatorio[i][2]);
				}
				break;		
			}
		}
		in.close();
	}
}
