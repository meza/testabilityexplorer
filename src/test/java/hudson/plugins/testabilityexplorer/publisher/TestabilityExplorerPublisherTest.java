package hudson.plugins.testabilityexplorer.publisher;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import hudson.maven.MavenModuleSet;
import hudson.model.Descriptor;
import hudson.model.FreeStyleProject;
import hudson.plugins.testabilityexplorer.PluginBaseTest;
import hudson.plugins.testabilityexplorer.helpers.ParseDelegate;
import hudson.plugins.testabilityexplorer.parser.StatisticsParser;
import hudson.plugins.testabilityexplorer.report.health.ReportBuilder;
import hudson.tasks.Publisher;

/**
 * Tests the {@link FreestylePublisher}.
 *
 * @author reik.schatz
 */
@Test
public class TestabilityExplorerPublisherTest extends PluginBaseTest {

    public void testPublisher() {
        FreestylePublisher testabilityExplorerPublisher = new FreestylePublisher("reports.xml", "",
                "", "100", "50");
        ParseDelegate parseDelegate = testabilityExplorerPublisher.newParseDelegate();
        assertNotNull(parseDelegate);

        StatisticsParser parser = testabilityExplorerPublisher.newStatisticsParser();
        assertNotNull(parser);

        ReportBuilder builder = testabilityExplorerPublisher.newReportBuilder();
        assertNotNull(builder);

        assertEquals(testabilityExplorerPublisher.getReportFilenamePattern(), "reports.xml");
        assertEquals(testabilityExplorerPublisher.getThreshold(), 100);

        Descriptor<Publisher> publisherDescriptor = testabilityExplorerPublisher.getDescriptor();
        assertNotNull(publisherDescriptor);
        assertEquals(publisherDescriptor.getDisplayName(),
                TestabilityExplorerDescriptor.DISPLAY_NAME);
    }

    public void testToInt() {
        AbstractPublisherImpl testabilityExplorerPublisher = new FreestylePublisher("reports.xml",
                "", "", "100", "88");
        assertEquals(testabilityExplorerPublisher.toInt("1", 0), 1);
        assertEquals(testabilityExplorerPublisher.toInt("x", 0), 0);
        assertEquals(testabilityExplorerPublisher.toInt("", 0), 0);
        assertEquals(testabilityExplorerPublisher.toInt("9999", 0), 9999);
        assertEquals(testabilityExplorerPublisher.toInt("999999999999999999999999999999", 0), 0);
    }

    public void testApplicable() {
        TestabilityExplorerDescriptor testabilityExplorerDescriptor = new TestabilityExplorerDescriptor();
        assertTrue(testabilityExplorerDescriptor.isApplicable(FreeStyleProject.class));
        assertFalse(testabilityExplorerDescriptor.isApplicable(MavenModuleSet.class));
    }
}
