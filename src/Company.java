public class Company {
    private String name;
    private String country;

    public Company(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object other) {
        if (other ==null) {
            return false;
        }

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        if (otherCompany.name.equals(this.name) && otherCompany.country.equals(this.country)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
