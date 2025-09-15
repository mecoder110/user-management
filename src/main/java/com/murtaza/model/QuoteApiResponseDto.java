package com.murtaza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteApiResponseDto {


    @JsonProperty("q")
    private String quote;
    @JsonProperty("a")
    private String author;
    @JsonProperty("h")
    private String html;
}
