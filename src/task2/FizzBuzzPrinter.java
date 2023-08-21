package task2;

import java.util.concurrent.Semaphore;

class FizzBuzzPrinter {
    private int n;
    private Semaphore fizzSemaphore;
    private Semaphore buzzSemaphore;
    private Semaphore fizzBuzzSemaphore;
    private Semaphore numberSemaphore;

    public FizzBuzzPrinter(int n) {
        this.n = n;
        fizzSemaphore = new Semaphore(0);
        buzzSemaphore = new Semaphore(0);
        fizzBuzzSemaphore = new Semaphore(0);
        numberSemaphore = new Semaphore(1);
    }

    public void printFizz() throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            if (i % 5 != 0) {
                fizzSemaphore.acquire();
                System.out.println("fizz");
                numberSemaphore.release();
            }
        }
    }

    public void printBuzz() throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            if (i % 3 != 0) {
                buzzSemaphore.acquire();
                System.out.println("buzz");
                numberSemaphore.release();
            }
        }
    }

    public void printFizzBuzz() throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            fizzBuzzSemaphore.acquire();
            System.out.println("fizzbuzz");
            numberSemaphore.release();
        }
    }

    public void printNumber() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            numberSemaphore.acquire();
            if (i % 3 == 0 && i % 5 == 0) {
                fizzBuzzSemaphore.release();
            } else if (i % 3 == 0) {
                fizzSemaphore.release();
            } else if (i % 5 == 0) {
                buzzSemaphore.release();
            } else {
                System.out.println(i);
                numberSemaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzPrinter fizzBuzzPrinter = new FizzBuzzPrinter(n);

        Thread threadFizz = new Thread(() -> {
            try {
                fizzBuzzPrinter.printFizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadBuzz = new Thread(() -> {
            try {
                fizzBuzzPrinter.printBuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadFizzBuzz = new Thread(() -> {
            try {
                fizzBuzzPrinter.printFizzBuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadNumber = new Thread(() -> {
            try {
                fizzBuzzPrinter.printNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadFizz.start();
        threadBuzz.start();
        threadFizzBuzz.start();
        threadNumber.start();

        try {
            threadFizz.join();
            threadBuzz.join();
            threadFizzBuzz.join();
            threadNumber.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}