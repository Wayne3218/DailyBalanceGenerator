import service.DailyBalanceService;

public class Main {
    private static final String APP_START_TEXT = "Starting App";
    private static final  String APP_END_TEXT = "End of Balances";
    private static final String GENERATE_REPORT = "Generating Report";
    private static final String REPORT = "report";

    public static void main(String[] args) {
        System.out.println(APP_START_TEXT);
        boolean generateReport = false;
        try {
            if (args.length == 1) {
                String reportFlag = args[0];
                if (REPORT.equalsIgnoreCase(reportFlag)) {
                    generateReport = true;
                    System.out.println(GENERATE_REPORT);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // no action require for this block
        }

        DailyBalanceService service = new DailyBalanceService();
        service.run();
        String output = service.display(generateReport);
        System.out.println(output);
        System.out.println(APP_END_TEXT);
    }
}
