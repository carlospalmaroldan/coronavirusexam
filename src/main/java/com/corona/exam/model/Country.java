package com.corona.exam.model;

public class Country {
    String region;

    String name;

    Integer totalCases;

    Integer totalTests;

    Integer activeCases;

    public String getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalCases() {
        return totalCases;
    }

    public Integer getTotalTests() {
        return totalTests;
    }

    public Integer getActiveCases() {
        return activeCases;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalCases(Integer totalCases) {
        this.totalCases = totalCases;
    }

    public void setTotalTests(Integer totalTests) {
        this.totalTests = totalTests;
    }

    public void setActiveCases(Integer activeCases) {
        this.activeCases = activeCases;
    }


    public static final class CountryBuilder {
        String region;
        String name;
        Integer totalCases;
        Integer totalTests;
        Integer activeCases;

        private CountryBuilder() {
        }

        public static CountryBuilder aCountry() {
            return new CountryBuilder();
        }

        public CountryBuilder withRegion(String region) {
            this.region = region;
            return this;
        }

        public CountryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CountryBuilder withTotalCases(Integer totalCases) {
            this.totalCases = totalCases;
            return this;
        }

        public CountryBuilder withTotalTests(Integer totalTests) {
            this.totalTests = totalTests;
            return this;
        }

        public CountryBuilder withActiveCases(Integer activeCases) {
            this.activeCases = activeCases;
            return this;
        }

        public Country build() {
            Country country = new Country();
            country.setRegion(region);
            country.setName(name);
            country.setTotalCases(totalCases);
            country.setTotalTests(totalTests);
            country.setActiveCases(activeCases);
            return country;
        }
    }
}
