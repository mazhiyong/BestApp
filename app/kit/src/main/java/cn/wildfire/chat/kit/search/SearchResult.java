package cn.wildfire.chat.kit.search;

import java.util.List;

public class SearchResult {
    SearchableModule module;
    public List<Object> result;

    public SearchResult(SearchableModule module, List<Object> result) {
        this.module = module;
        this.result = result;
    }
}
