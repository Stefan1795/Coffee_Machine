package machine;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Machine machine = Machine.basic();

        Scanner scanner = new Scanner(System.in);
        while (machine.isWorking()) {
            if (machine.isInChooseActionMode()) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
            }
            machine.execute(scanner.nextLine());
        }
    }
}