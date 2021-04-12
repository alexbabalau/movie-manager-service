package model.tmbd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscoverResponse {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("results")
    private List<MovieCompressed> results;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @Override
    public String toString() {
        return "DiscoverResponse{" +
                "page=" + page +
                ", results=" + results +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                '}';
    }
}
