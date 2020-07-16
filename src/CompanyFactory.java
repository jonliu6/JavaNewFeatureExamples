import java.util.ArrayList;
import java.util.Collection;

public class CompanyFactory {
    public static Collection<Company> createCompanies() {
        Collection<Company> companies = new ArrayList<Company>();
        companies.add(new Company("Rona", "Canada"));
        companies.add(new Company("Home Depot", "United States"));
        companies.add(new Company("Walmart", "United States"));
        companies.add(new Company("Costco", "United States"));
        companies.add(new Company("Superstore", "Canada"));

        return companies;
    }
}
