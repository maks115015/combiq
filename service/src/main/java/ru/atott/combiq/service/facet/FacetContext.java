package ru.atott.combiq.service.facet;

import ru.atott.combiq.service.site.UserContext;

import java.util.List;

public class FacetContext {

    private List<Facet> allFacets;

    private UserContext userContext;

    public List<Facet> getAllFacets() {
        return allFacets;
    }

    public void setAllFacets(List<Facet> allFacets) {
        this.allFacets = allFacets;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }
}
