import com.atlantbh.jmeter.plugins.jtlparser.JtlParser;
import com.atlantbh.jmeter.plugins.jtlparser.builder.JunitModelBuilder;
import com.atlantbh.jmeter.plugins.jtlparser.builder.JunitXmlBuilder;
import com.atlantbh.jmeter.plugins.jtlparser.model.jtl.ThreadGroup;
import com.atlantbh.jmeter.plugins.jtlparser.model.junit.TestCase;
import com.atlantbh.jmeter.plugins.jtlparser.model.junit.TestStep;
import com.atlantbh.jmeter.plugins.jtlparser.model.junit.TestSuite;
import com.sun.javadoc.Doc;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by adnan on 12/12/15.
 */
public class Example {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JtlParser parser = new JtlParser();
        String fileLocation = "/Users/adnan/example.jtl";
        try {
            NodeList list = parser.getStartNode(fileLocation);
            parser.parseJtl(list, null);

            ArrayList<ThreadGroup> threads = parser.getThreadGroups();
            JunitModelBuilder builder = JunitModelBuilder.newInstance();
            TestSuite testSuite = builder.generateTestSuite(threads);
            ArrayList<TestSuite> testSuites = new ArrayList<TestSuite>();
            testSuites.add(testSuite);

            JunitXmlBuilder xmlBuilder = JunitXmlBuilder.newInstance();
            Document doc = xmlBuilder.generateXmlDoc(testSuites);
            xmlBuilder.writeXmlDoc(doc, "/Users/adnan/parsedjunit.xml");


            /*
            System.out.println("Test Suite name: " + testSuite.getName());
            System.out.println("Number of tests in Test Suite: " + testSuite.getTests());
            System.out.println("Test Suite Execution time: " + testSuite.getTime() + "ms");
            System.out.println("FAILURES: " + testSuite.getFailures());
            System.out.println("///////////////////////////////////\n");

            for (TestCase testCase : testSuite.getTestCases()) {
                System.out.println("Test Case name: " + testCase.getClassName());
                System.out.println("");
                for (TestStep testStep : testCase.getTestSteps()) {
                    System.out.println("Test Step name: " + testStep.getName());
                    System.out.println("Test Step Execution time: " + testStep.getTime());
                    if (!testStep.getFailureMessage().equals(""))
                        System.out.println("Failure: " + testStep.getFailureMessage());
                    System.out.println("");
                }
                System.out.print("/////////////////////////////\n");
            }*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
