import java.util.Scanner;

public class coop {
	public static String message = "";
	public static Object object = new Object();

	// main program
	public static void main(String[] args) {
		// creat 2 threads and start
		Thread input = new input();
		Thread printer = new printer();
		input.start();
		printer.start();
	}

	// input-thread
	static class input extends Thread {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + "input-thread: start");
			// to check input of console in while loop
			while (!message.equals("x")) {
				Scanner s = new Scanner(System.in);
				message = s.nextLine();
				// if input is empty input-thread go sleep
				if (message.isEmpty()) {
					try {
						System.out.println(Thread.currentThread() + "Input-Thread: sleep....");
						// input-thread sleep 0.5sec
						input.sleep(500);
						System.out.println("please input!");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					// if input is not empty use notify() to notify print-thread
					synchronized (object) {
						object.notify();
						// display what console input in input-thread
						if (!message.equals("x")) {
							System.out.println(Thread.currentThread() + "Input-Thread: " + message);
						}
					}
				}
			}//end while loop
			System.out.println(Thread.currentThread() + "Input-Thread: exit");
		}

	}

	// print-thread
	static class printer extends Thread {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + "print-thread: start");
			// use wait() to wait input-thread
			synchronized (object) {
				// create a loop to print
				while (!message.equals("x")) {
					try {
						System.out.println(Thread.currentThread() + "print-thread: wait");
						object.wait();
						// when message is not empty and not x print it out
						if (!message.isEmpty() && !message.equals("x")) {
							System.out.println(Thread.currentThread() + "Print-Thread: " + message);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // end loop
				if (message.equals("x")) {
					System.out.println(Thread.currentThread() + "Print-Thread: exit");
				}
			}
		}
	}
}
