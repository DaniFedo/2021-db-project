package org.example;

public class Worker extends Thread{

	final int id;
	private final Data data;
	
	public Worker(int id, Data d){
		this.id = id;
		data = d;
		this.start();
	}
	
	@Override
	public void run(){
		//super.run();
		for (int i = 0; i < 100; i++){
			synchronized (data) {
				if (data.getState() == 1) data.Tic();
				else if (data.getState() == 2) data.Tak();
				else data.Toy();
			}
		}
	}
	
}
