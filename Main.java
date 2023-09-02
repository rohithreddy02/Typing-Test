import java.util.Scanner;
import java.util.Random;

class TypingTest {
    private String text;
    private int mode;
    private double typingSpeed;
    private int accuracy;

    public TypingTest(String text, int mode) {
        this.text = text;
        this.mode = mode;
    }

    public void startTest() {
        if (mode == 1) {
            typeFullText();
        } else {
            typeInOneMinute();
        }
    }

    private void typeFullText() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Start typing the following text:\n");
        System.out.println(text + "\n");
        String input;
        if (sc.hasNextLine()) {
            input = sc.nextLine();
        } else {
            System.out.println("No input provided.");
            sc.close();
            return;
        }
        sc.close();
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        long elapsedTime = (end - start) / 1000;
        int correct = 0;
        for (int i = 0; i < text.length(); i++) {
            if (i < input.length() && input.charAt(i) == text.charAt(i)) {
                correct++;
            }
        }
        accuracy = (int) ((double) correct / input.length() * 100);
        typingSpeed = (int) ((double) correct / elapsedTime);
        displayResult();
    }

    private void typeInOneMinute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Start typing the following text:\n");
        System.out.println(text + "\n");

        TimerThread timerThread = new TimerThread();
        timerThread.start();

        StringBuilder inputBuilder = new StringBuilder();
        while (timerThread.isAlive()) {
            if (sc.hasNextLine()) {
                inputBuilder.append(sc.nextLine());
            }
        }
        sc.close();
        String input = inputBuilder.toString();

        int correct = 0;
        for (int i = 0; i < text.length(); i++) {
            if (i < input.length() && input.charAt(i) == text.charAt(i)) {
                correct++;
            }
        }
        accuracy = (int) ((double) correct / input.length() * 100);
        typingSpeed = (double) correct / 60;
        displayResult();
        System.exit(0);
    }

    private void displayResult() {
       
        System.out.println("\n\nReport:\n");
        System.out.println("Typing Speed: " + typingSpeed + " characters per second");
        System.out.println("Accuracy: " + accuracy + "%");
    }

    class TimerThread extends Thread {
        @Override
        public void run() {
            try {
                sleep(60000);
                System.out.println("\n\nTime is up!\n");
            } catch (InterruptedException e) {
                // Thread interrupted, do nothing
            }
        }
    }
    
    public static String getRandomText() {
        Random random = new Random();
        String[] paragraphs = {
            "The sun was setting behind the mountains, casting a warm golden glow across the tranquil lake. Birds chirped softly in the trees, and a gentle breeze rustled the leaves. It was a perfect evening to sit by the water and reflect on the beauty of nature.",
            "In the distance, I could hear the faint sound of laughter as children played by the shore. Their joyful voices echoed through the air, adding to the overall sense of contentment. It was a scene straight out of a postcard, a snapshot of a perfect moment in time.",
            "I took a deep breath, inhaling the fresh scent of the pine trees that surrounded the lake. Nature had a way of rejuvenating the soul, and in that moment, I felt a deep connection to the natural world. It was a reminder that, amid the hustle and bustle of life, it was essential to take a step back and appreciate the beauty that surrounded us.",
            "As the sun dipped below the horizon, painting the sky with vibrant hues of orange and pink, I knew that I would carry this moment with me as a source of inspiration and tranquility. It was a reminder that, even in the busiest of days, there was always a place for stillness and appreciation of the world around us."
        };
        int randomIndex = random.nextInt(paragraphs.length);
        return paragraphs[randomIndex];
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String selectedText = TypingTest.getRandomText();
        System.out.println("Select mode:");
        System.out.println("1. Type full text");
        System.out.println("2. Type in one minute");
        int mode = sc.nextInt();
        
        TypingTest test = new TypingTest(selectedText, mode);
        test.startTest();

        sc.close();
    }
}
