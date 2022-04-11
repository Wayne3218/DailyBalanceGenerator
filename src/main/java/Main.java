import service.DailyBalanceService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting App");
        boolean generateReport = false;
        try {
            if (args.length == 1) {
                String reportFlag = args[0];
                if ("report".equalsIgnoreCase(reportFlag)) {
                    generateReport = true;
                    System.out.println("Generating Report");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // no action require for this block
        }

        DailyBalanceService service = new DailyBalanceService();
        service.run();
        String output = service.display(generateReport);
        System.out.println(output);
        System.out.println("Done");
    }
}
