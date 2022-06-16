package application.integration.usecases;

import org.springframework.beans.factory.annotation.Autowired;

public class Behavior {
    @Autowired
    public TravelDsl travel;

    @Autowired
    public ReportDsl report;

    @Autowired
    public AccountingDsl accounting;
}
