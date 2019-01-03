package site.devsim.enjoyseoul.Util;

import java.io.Serializable;

public class SearchCondition implements Serializable {
    private String searchKeyword = "";
    private String searchGenre = "전체";
    private String searchFee = "요금무관";

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchGenre() {
        return searchGenre;
    }

    public void setSearchGenre(String searchGenre) {
        this.searchGenre = searchGenre;
    }

    public String getSearchFee() {
        return searchFee;
    }

    public void setSearchFee(String searchFee) {
        this.searchFee = searchFee;
    }
}
