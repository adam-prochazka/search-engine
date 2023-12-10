package cli;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("-------------------------------------------------------");
            System.out.println("This is a Search Query where you can write a word and \n" +
                    "search for the books in which it appears.");
            System.out.println("Please, enter a word (use ':q' to quit) ");
            System.out.println("-------------------------------------------------------");
            command = scanner.nextLine();

            if (command.equals(":q")) {
                break;
            } else{
                new QueryMethods().queryWord(command);
            }
        }
        scanner.close();
    }


}

