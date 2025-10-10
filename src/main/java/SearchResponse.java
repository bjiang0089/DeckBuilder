import java.util.List;

public class SearchResponse {
    private int total_cards;
    private boolean has_more;
    private String next_page;
    private List<Card> data;

    public int getTotal_cards() {
        return total_cards;
    }

    public void setTotal_cards(int total_cards) {
        this.total_cards = total_cards;
    }

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }
}
