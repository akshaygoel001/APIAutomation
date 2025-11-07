package utils;



import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

public class ExtentReport {

    public static ExtentReports extentreport = null;
    public static ExtentTest extentlog;

    public static void initialize(String extentConfigXMLPath) {

        if (extentreport == null) {

            extentreport = new ExtentReports(extentConfigXMLPath,true);

            extentreport.addSystemInfo("Host Name", System.getProperty("user.name"));

            extentreport.addSystemInfo("Environment","QA");

            extentreport.loadConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));
        }

    }
}
