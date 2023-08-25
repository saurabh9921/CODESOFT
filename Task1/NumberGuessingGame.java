import java.util.Scanner;
public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 10;
        int win = 0;
	int outOf = 0;
        String x;
        System.out.println("Welcome to the Number Guessing Game!");


          do
	   {
	    int randomNumber =1+(int)(100*Math.random());
            int attempts = 0;

            System.out.println("\nI have generated a number between " + minRange + " and " + maxRange +
                    ". Can you guess it? You have " + maxAttempts + " attempts.");
		outOf++;
            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();
                attempts++;

                if (guess == randomNumber) {
                    System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                    win++;
                    break;
                } else if (guess < randomNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }

                if (attempts == maxAttempts) {
                    System.out.println("Sorry, you used all of your attempts.\n The correct number was: " + randomNumber);
                }
            }

            System.out.print("Do you want to play again? (yes/no):");
          x = sc.next().toLowerCase();
        }while(x.equals("yes") || x.equals("y"));
           sc.close();
        System.out.println("Game over! Your score(win/outOF): "+win+"/"+outOf);
     
    }
}
