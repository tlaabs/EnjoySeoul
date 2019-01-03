package site.devsim.enjoyseoul.Util;

public class SearchQueryBuilder {
    public static String getSearchQuery(String table, SearchCondition condition){
        boolean leastAndFlag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + table);

        if(isDefaultCondition(condition)) return sql.toString(); //Default값이면 전체 검색
        else{
            sql.append(" WHERE ");
        }
        if(!condition.getSearchKeyword().equals("")){
            sql.append("TITLE LIKE '%" + condition.getSearchKeyword() + "%'");
            leastAndFlag = true;
        }
        if(!condition.getSearchGenre().equals("전체")){
            sql.append(and(leastAndFlag));
            sql.append("GENRE = \"" + condition.getSearchGenre()+"\"");
            leastAndFlag = true;
        }
        if(!condition.getSearchFee().equals("요금무관")){
            sql.append(and(leastAndFlag));
            sql.append("IS_FREE = \"" + condition.getSearchFee()+"\"");
        }

        return sql.toString();

    }

    private static boolean isDefaultCondition(SearchCondition condition){
        if(!condition.getSearchKeyword().equals("")) return false;
        if(!condition.getSearchGenre().equals("전체")) return false;
        if(!condition.getSearchFee().equals("요금무관")) return false;
        return true;
    }

    private static String and(boolean leastAndFlag){
        if(leastAndFlag) return " AND ";
        return "";
    }
}
