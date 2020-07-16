import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Examples for using concurrency classes in Java
 */
public class ConcurrencyDemo {
    public static void main(String[] args) {
        ConcurrencyDemo demo = new ConcurrencyDemo();
        demo.checkProcessors();
        demo.runnableDemo();
        demo.callableDemo();
    }

    public void checkProcessors() {
        System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors() );
    }

    /**
     * runnable was introduced since Java 1.0, and it does not return any values or throw exceptions.
     * This method shows an old way to create threads executing runnables
     */
    public void runnableDemo() {
        Chatter person1 = new Chatter("Person One", createDialog1());
        Thread t1 = new Thread(person1);
        Chatter person2 = new Chatter("Person Two", createDialog2());
        Thread t2 = new Thread(person2);
        System.out.println("Casual Conversation:");
        t1.start();
        t2.start();
        System.out.println("Conversation finished.");
    }

    /**
     * a runnable class used by multi-threading
     */
    class Chatter implements Runnable {
        private String chatterName;
        private List<String> dialog;

        public Chatter(String name, List<String> sentences) {
            this.chatterName = name;
            this.dialog = sentences;
        }

        @Override
        public void run() {
            if (dialog != null && dialog.size() > 0) {
                for (String sentence : dialog) {
                    System.out.println("["+ chatterName + "]: " + sentence);
                    try {
                        Thread.sleep(1000); // for demonstration purpose, add a one-second delay.
                    }
                    catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        }
    }

    public List<String> createDialog1() {
        List<String> dialog = new ArrayList<String>();
        dialog.add("Hello.");
        dialog.add("How are you?");
        dialog.add("I'm messenger One.");
        dialog.add("Nice to meet you.");
        dialog.add("What's every thing?");
        dialog.add("Good to know.");
        dialog.add("Have a nice day!");

        return dialog;
    }

    public List<String> createDialog2() {
        List<String> dialog = new ArrayList<String>();
        dialog.add("Hi there.");
        dialog.add("I'm good, thanks.");
        dialog.add("I'm messenger Two.");
        dialog.add("Same here.");
        dialog.add("Everything is good. I'm doing well.");
        dialog.add("Gotta go now for an important appointment. Talk to you later.");
        dialog.add("You too! Take care.");

        return dialog;
    }

    /**
     * callable was introduced since Java 5.0, and it can return a value and throw exceptions.
     * This method is a new way showing how to use callable objects and ExecutorService
     * Note: ExecutorService can be used for both runnable (use .execute() method) and callable (use .submit() method)
     */
    public void callableDemo() {
        ConcurrentLinkedQueue<String> msgBox = new ConcurrentLinkedQueue<>();
        System.out.println("Two messengers start sending messages...");
        Callable<Integer> msgr1 = new MessageSender("Messenger1", msgBox, 1000);
        Callable<Integer> msgr2 = new MessageSender("Messenger2", msgBox, 1200);
        ExecutorService es = Executors.newFixedThreadPool(2); // maximum number of threads in the thread pool
        Future<Integer> f1 = es.submit(msgr1);
        Future<Integer> f2 = es.submit(msgr2);

        try {
            es.shutdown();
            es.awaitTermination(5, TimeUnit.SECONDS); // wait for 5 seconds before shutting down

            Integer msgBoxSize = f1.get();
            System.out.println("Message Box contains " + msgBoxSize + " message(s) when Messenger One finished.");
            msgBoxSize = f2.get();
            System.out.println("Message Box contains " + msgBoxSize + " message(s) when Messenger Two finished.");
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        catch (ExecutionException ee) {
            ee.printStackTrace();
        }
        finally {
            // msgBox.stream().forEach( m -> System.out.println(m));
            msgBox.stream().forEach(System.out::println); // short format as above
            System.out.println(msgBox.size() + " messages are all stored.");
        }
    }

    /**
     * a callable class for multi-threading can return a value and throw exceptions
     */
    class MessageSender implements Callable<Integer> {
        private ConcurrentLinkedQueue<String> messageBox;
        private String messengerName;
        private Integer numberOfMessages;

        public MessageSender(String name, ConcurrentLinkedQueue<String> msgBox, Integer count) {
            this.messengerName = name;
            this.messageBox = msgBox;
            this.numberOfMessages = count;
        }

        @Override
        public Integer call() throws Exception{
            if (messageBox == null) {
                messageBox = new ConcurrentLinkedQueue<>();
            }
            for (int i = 1; i <= numberOfMessages; ++i) {
                messageBox.add("[" + messengerName + "] sent " + i);
            }
            return messageBox.size();
        }
    }
}
