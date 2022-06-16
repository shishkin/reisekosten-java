package acceptance;

import application.integration.usecases.Behavior;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

public class StepDefinitions extends Behavior {

    @Given("accounting exists")
    public void accountingExists() {
        accounting.exists().block();
    }

    @And("I enter travel to {string} from {string} until {string}")
    public void enterTravel(String destination, String start, String end) {
        travel.enter(destination, start, end).block();
    }

    @When("I create report")
    public void createReport() {
        // Behavior DSL has no explicit step for this
        // We may consider splitting reporting DSL into report creation and report verification
    }

    @Then("report contains {int} entry")
    public void reportContainsEntries(int count) {
        report.withNumberOfEntries(count).block();
    }

    @And("total reported allowance is {bigdecimal}")
    public void totalReportedAllowance(BigDecimal sum) {
        report.withTotalSum(sum).block();
    }
}
