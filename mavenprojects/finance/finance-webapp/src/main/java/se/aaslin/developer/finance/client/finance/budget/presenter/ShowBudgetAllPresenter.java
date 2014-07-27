package se.aaslin.developer.finance.client.finance.budget.presenter;

import se.aaslin.developer.finance.client.ClientFactory;
import se.aaslin.developer.finance.client.finance.budget.service.AbstractBudgetOutcomeService;

public class ShowBudgetAllPresenter extends AbstractBudgetOutcomePresenter {

	public ShowBudgetAllPresenter(View view, AbstractBudgetOutcomeService srv, ClientFactory clientFactory) {
		super(view, srv, clientFactory);
	}
}
