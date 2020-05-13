package machine;

public class Machine {

    private int change;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private State state;

    public boolean isInChooseActionMode() {
        return this.state == State.CHOOSE_ACTION;
    }

    enum State {
        CHOOSE_ACTION, CHOOSE_COFFEE, FILL_WATER, FILL_MILK, FILL_BEANS, ADD_CUPS, FINISH;

    }

    enum CoffeeType {
        ESPRESSO(1), LATTE(2), CAPPUCCINO(3);

        private final int type;

        CoffeeType(int type) {
            this.type = type;
        }

        static CoffeeType of(int type) {
            for (CoffeeType ct : CoffeeType.values()) {
                if (ct.type == type) {
                    return ct;
                }
            }
            throw new IllegalArgumentException();
        }

    }

    private Machine(int change, int water, int milk, int beans, int cups) {
        this.change = change;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.state = State.CHOOSE_ACTION;
    }

    public static Machine basic() {
        return new Machine(550, 400, 540, 120, 9);
    }

    public boolean isWorking() {
        return this.state != State.FINISH;
    }

    public void execute(String command) {
        switch (command) {
            case "buy":
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                this.state = State.CHOOSE_COFFEE;
                break;
            case "back":
                this.state = State.CHOOSE_ACTION;
                break;
            case "remaining":
                printMachineState();
                break;
            case "exit":
                this.state = State.FINISH;
                break;
            case "fill":
                System.out.println("Write how many ml of water do you want to add:");
                this.state = State.FILL_WATER;
                break;
            case "take":
                take();
                break;
            default:
                processingNumbersAsCommand(command);
        }
    }

    private void processingNumbersAsCommand(String command) {
        int number = Integer.parseInt(command);
        switch (this.state) {
            case CHOOSE_COFFEE:
                buy(CoffeeType.of(number));
                break;
            case FILL_WATER:
                addWater(number);
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case FILL_MILK:
                addMilk(number);
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case FILL_BEANS:
                addBeans(number);
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
            case ADD_CUPS:
                addCups(number);
                break;
        }
    }

    private void printMachineState() {
        String sb = "The coffee machine has:\n" +
                this.water + " of water\n" +
                this.milk + " of milk\n" +
                this.beans + " of coffee beans\n" +
                this.cups + " of disposable cups\n" +
                "$" + this.change + " of money\n";
        System.out.println(sb);
    }

    private void addWater(int mlOfWater) {
        this.water += mlOfWater;
        this.state = State.FILL_MILK;
    }

    private void addMilk(int mlOfMilk) {
        this.milk += mlOfMilk;
        this.state = State.FILL_BEANS;
    }

    private void addBeans(int beans) {
        this.beans += beans;
        this.state = State.ADD_CUPS;
    }

    private void addCups(int cups) {
        this.cups += cups;
        this.state = State.CHOOSE_ACTION;
    }

    private void take() {
        System.out.println("I give you $" + this.change);
        this.change = 0;
    }

    private void buy(CoffeeType type) {
        switch (type) {
            case ESPRESSO:
                makeEspresso();
                break;
            case LATTE:
                makeLatte();
                break;
            case CAPPUCCINO:
                makeCappuccino();
                break;
            default:
                throw new IllegalStateException();
        }
        this.state = State.CHOOSE_ACTION;
    }

    private void makeCappuccino() {
        if (this.water < 200) {
            showMissingIngredientsMessage("water");
        } else if (this.milk < 100) {
            showMissingIngredientsMessage("milk");
        } else if (this.beans < 12) {
            showMissingIngredientsMessage("beans");
        } else {
            this.water -= 200;
            this.milk -= 100;
            this.beans -= 12;
            this.change += 6;
            this.cups--;
            showAvailableIngredientsMsg();
        }
    }

    private void showMissingIngredientsMessage(String ingredient) {
        System.out.println("Sorry, not enough " + ingredient + "!");
    }

    private void showAvailableIngredientsMsg() {
        System.out.println("I have enough resources, making you a coffee!");
    }

    private void makeLatte() {
        if (this.water < 350) {
            showMissingIngredientsMessage("water");
        } else if (this.milk < 75) {
            showMissingIngredientsMessage("milk");
        } else if (this.beans < 20) {
            showMissingIngredientsMessage("beans");
        } else {
            this.water -= 350;
            this.milk -= 75;
            this.beans -= 20;
            this.change += 7;
            this.cups--;
            showAvailableIngredientsMsg();
        }
    }

    private void makeEspresso() {
        if (this.water < 250) {
            showMissingIngredientsMessage("water");
        } else if (this.beans < 16) {
            showMissingIngredientsMessage("beans");
        } else {
            this.water -= 250;
            this.beans -= 16;
            this.change += 4;
            this.cups--;
            showAvailableIngredientsMsg();
        }
    }
}