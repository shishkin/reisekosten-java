package application.integration.usecases;

import org.springframework.beans.factory.annotation.Autowired;

public class Behavior {
    @Autowired
    TravelDsl travel;

    @Autowired
    ReportDsl report;

    @Autowired
    AccountingDsl accounting;
}
