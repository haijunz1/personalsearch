package g1105.ps.entity;

import java.util.ArrayList;
import java.util.List;

public class Query {
	private List<String> query;
	
	public Query(String queryString){
		query = new ArrayList();
		for(String k:queryString.split(" ")){
			query.add(k);
		}
	}
	public List<String> getQuery() {
		return query;
	}

	public void setQuery(List<String> query) {
		this.query = query;
	}
	
	public void addKeyWord(String k){
		query.add(k);
	}
}
