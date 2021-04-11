package model.tmbd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditsResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("cast")
    private List<TmdbActor> cast;

    @JsonProperty("crew")
    private List<TmdbActor> crew;
}
