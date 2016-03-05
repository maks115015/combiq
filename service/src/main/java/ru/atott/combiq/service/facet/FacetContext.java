package ru.atott.combiq.service.facet;

import java.util.List;

public class FacetContext {

    private List<Facet> allFacets;

    public List<Facet> getAllFacets() {
        return allFacets;
    }

    public void setAllFacets(List<Facet> allFacets) {
        this.allFacets = allFacets;
    }
}
